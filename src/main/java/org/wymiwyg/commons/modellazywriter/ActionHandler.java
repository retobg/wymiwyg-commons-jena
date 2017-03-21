package org.wymiwyg.commons.modellazywriter;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.WeakHashMap;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelLock;

/**
 * <p>ActionHandler class.</p>
 *
 * @author user
 * @version $Id: $Id
 */
public class ActionHandler {
	private Reference modelRef;
	private LinkedList actionList = new LinkedList();
	private static WeakHashMap map = new WeakHashMap();
	
	private ActionHandler(Model model) {
		final ReferenceQueue queue = new ReferenceQueue();
		modelRef = new WeakReference(model, queue);
		new Thread() {
			public void run() {
				try {
					//the result must be the model
					queue.remove();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				performActions();
			}
		}.start();
	}
	
	/**
	 * The invoker of this method must not have a Lock
	 * on the model of this ActionHandler
	 */
	public void performActions() {
		if (actionList.size() != 0) {
			Model model = (Model) modelRef.get();
			model.enterCriticalSection(ModelLock.WRITE);
			try {
				while (actionList.size() != 0) {
					ModelAction action = (ModelAction) actionList.removeFirst();
					action.perform(model);
				}
			} finally {
				model.leaveCriticalSection();
			}
		}
	}
	
	/**
	 * <p>addAction.</p>
	 *
	 * @param action a {@link org.wymiwyg.commons.modellazywriter.ModelAction} object.
	 */
	public synchronized void addAction(ModelAction action) {
		actionList.add(action);

	}
	
	/**
	 * <p>getActionHandler.</p>
	 *
	 * @param model a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 * @return a {@link org.wymiwyg.commons.modellazywriter.ActionHandler} object.
	 */
	public static ActionHandler getActionHandler(Model model) {
		ActionHandler result = (ActionHandler) map.get(model);
		if (result == null) {
			synchronized (ActionHandler.class) {
				result = (ActionHandler) map.get(model);
				if (result == null) {
					result = new ActionHandler(model);
					map.put(model, result);
				}
			}
		}
		return result;
	}

}
