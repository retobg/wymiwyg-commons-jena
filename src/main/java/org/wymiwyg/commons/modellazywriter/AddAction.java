package org.wymiwyg.commons.modellazywriter;

import java.util.Iterator;
import java.util.LinkedList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;

public class AddAction implements ModelAction {
	
	LinkedList stmtList = new LinkedList();
	
	/*public void add(Resource resource, Property property, RDFNode node) {
		ModelFactory.c
	}*/
	public void add(Statement statement) {
		stmtList.add(statement);
	}

	public void perform(Model model) {
		for (Iterator iter = stmtList.iterator(); iter.hasNext();) {
			Statement current = (Statement) iter.next();
			model.add(current);
		}
	}

	
}
