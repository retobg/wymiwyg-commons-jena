package org.wymiwyg.commons.modellazywriter;

import java.util.Iterator;
import java.util.LinkedList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;

/**
 * <p>AddAction class.</p>
 *
 * @author user
 * @version $Id: $Id
 */
public class AddAction implements ModelAction {
	
	LinkedList stmtList = new LinkedList();
	
	/*public void add(Resource resource, Property property, RDFNode node) {
		ModelFactory.c
	}*/
	/**
	 * <p>add.</p>
	 *
	 * @param statement a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public void add(Statement statement) {
		stmtList.add(statement);
	}

	/** {@inheritDoc} */
	public void perform(Model model) {
		for (Iterator iter = stmtList.iterator(); iter.hasNext();) {
			Statement current = (Statement) iter.next();
			model.add(current);
		}
	}

	
}
