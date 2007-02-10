package org.wymiwyg.commons.modellazywriter;

import com.hp.hpl.jena.rdf.model.Model;

public interface ModelAction {

	void perform(Model model);

}
