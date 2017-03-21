package org.wymiwyg.commons.modellazywriter;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * <p>ModelAction interface.</p>
 *
 * @author user
 * @version $Id: $Id
 */
public interface ModelAction {

	/**
	 * <p>perform.</p>
	 *
	 * @param model a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	void perform(Model model);

}
