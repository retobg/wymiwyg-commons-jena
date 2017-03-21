/*
 * Copyright  2002-2004 WYMIWYG (www.wymiwyg.org)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.wymiwyg.commons.jena;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wymiwyg.commons.vocabulary.CALENDAR;
import org.wymiwyg.commons.vocabulary.FOAF;
import org.wymiwyg.commons.vocabulary.GEOFEX;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * <p>InsertionIFPResolver class.</p>
 *
 * @author reto
 *
 * Since KnoBot is typically run with a model that does not do inference. This
 * class provides a utility method to replace resources contained in an
 * insertion by their equivalents in the main-model
 * @version $Id: $Id
 */
public class InsertionIFPResolver {

	private static final Log log = LogFactory
			.getLog(InsertionIFPResolver.class);

	private static Set handledIFP = new HashSet();
	static {
		handledIFP.add(FOAF.mbox);
		handledIFP.add(FOAF.mbox_sha1sum);
		handledIFP.add(FOAF.isPrimaryTopicOf);
		handledIFP.add(CALENDAR.uid);
		handledIFP.add(GEOFEX.primarySpaceThingOf);
	}

	/**
	 * This method does not itself insert the insertion into the mainModel, but
	 * replaces resources in insertion with their counterpart in the mainModel,
	 * ab subsequent mainModel.add(insertion) will not have/have less duplicate.
	 *
	 * @param mainModel a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 * @param insertion a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public static void process(Model mainModel, Model insertion) {
		StmtIterator statements = insertion.listStatements();
		Map replaceTable = new HashMap();
		Map identitiesInExisting = new HashMap();
		//Set replacingSourceResources = new HashSet();
		while (statements.hasNext()) {
			Statement currentStmt = statements.nextStatement();
			Property currentPredicate = currentStmt.getPredicate();
			
			if (handledIFP.contains(currentPredicate)) {
				ResIterator resInMain = mainModel.listSubjectsWithProperty(
						(Property) currentPredicate.inModel(mainModel),
						currentStmt.getObject().inModel(mainModel));
				Resource targetResource;
				if (resInMain.hasNext()) {
					//Resource[] resourcePair = new Resource[2];
					Resource sourceResource = currentStmt.getSubject();
					targetResource = resInMain.nextResource();
					
					if (replaceTable.containsKey(sourceResource) && (!replaceTable.get(sourceResource).equals(targetResource))) {
						identitiesInExisting.put(replaceTable.get(sourceResource), targetResource);
					} else {
						replaceTable.put(sourceResource, targetResource);
					}
					/*resourcePair[0] = sourceResource;
					resourcePair[1] = targetResource;
					replaceTable.add(resourcePair);*/
					while (resInMain.hasNext()) {
						log
								.warn("Model contains more than one resource with property "
										+ currentStmt.getPredicate()
										+ " to value "
										+ currentStmt.getObject()
										+ " will merge resources");
						identitiesInExisting.put(resInMain.nextResource(), targetResource);
					}
				}
				resInMain.close();
			}
		}
		for (Iterator iter = replaceTable.keySet().iterator(); iter.hasNext();) {
			Resource from = (Resource) iter.next();
			Resource to = (Resource) replaceTable.get(from);
			JenaUtil.replace(insertion, from, (Resource) to.inModel(insertion));
		}
		for (Iterator iter = identitiesInExisting.keySet().iterator(); iter.hasNext();) {
			Resource from = (Resource) iter.next();
			Resource to = (Resource) identitiesInExisting.get(from);
			JenaUtil.replace(mainModel, from, to);
		}
	}
}
