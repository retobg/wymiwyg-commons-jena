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

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Selector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.SelectorImpl;
import com.hp.hpl.jena.shared.JenaException;

/**
 * <p>JenaUtil class.</p>
 *
 * @author reto
 * @date Jun 19, 2004
 * @version $Id: $Id
 */
public class JenaUtil {

	private static final Log log = LogFactory.getLog(JenaUtil.class);

	/**
	 * The model must be read-locked
	 * 
	 * @param model
	 *            must be readlocked
	 */
	/*public static void removeDuplicateAnonymous(Model model){
			removeDuplicateAnonymous(model, new DuplicateAnonymousFoundListener() {
				public void duplicateFound(Resource a, Resource b) {
					log.info("found resource to replace!");
					// do nothing
				}
			});
	}
	public static void removeDuplicateAnonymous(Model model, DuplicateAnonymousFoundListener listener) {
		StmtIterator iterator = model.listStatements();
		Set resourceSet = new HashSet();
		//Map replaceMap = new HashMap();
		while (iterator.hasNext()) {
			Statement statement = iterator.nextStatement();
			Resource subject = statement.getSubject();
			resourceSet.add(subject);

			RDFNode object = statement.getObject();
			if (object instanceof Resource) {
				resourceSet.add(object);
			}
		}
		iterator.close();
		model.leaveCriticalSection();
		long size = resourceSet.size();
		long pos = 0;
		Set knownNotImplying = new HashSet();
		for (Iterator iter = resourceSet.iterator(); iter.hasNext();) {
			pos++;
			model.enterCriticalSection(ModelLock.READ);
			try {
				if ((pos % 10) == 0) {
					log.info("Analyzing resource " + pos + " of " + size);
				}
				Resource current = (Resource) iter.next();
				Resource newResource = ResourceComparator.getImplyingResource(
						current, model, knownNotImplying);
				if (newResource != current) {
					listener.duplicateFound(newResource, current);
					model.leaveCriticalSection();
					model.enterCriticalSection(ModelLock.WRITE);
					try {
						JenaUtil.replace(model, current, newResource);
					} finally {
						model.leaveCriticalSection();
						model.enterCriticalSection(ModelLock.READ);
					}
				}
			} finally {
				model.leaveCriticalSection();
			}
		}

		model.enterCriticalSection(ModelLock.READ);
	}*/

	/**
	 * This method removes duplicate anonymous resources that are - not the
	 * object of a statement - have no anonymous resource as the object of a
	 * statement
	 *
	 * A more generic variant woul be something like "Model
	 * getMinimalIsomorphicModel(model)"
	 *
	 * @param model a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public static void removeSomeDuplicateAnonymous(Model model) {
		Set anonResDescriptors = new HashSet();
		ResIterator resourceIter = model.listSubjects();
		Set removingRes = new HashSet();
		while (resourceIter.hasNext()) {
			Resource current = resourceIter.nextResource();
			if (!current.isAnon()) {
				continue;
			}
			if (model.contains(null, null, current)) {
				continue;
			}
			AnonResDescriptor descriptor = new AnonResDescriptor(current);
			if (anonResDescriptors.contains(descriptor)) {
				// resourceIter.remove();
				removingRes.add(current);
				log.info("will remove duplicate");
			} else {
				anonResDescriptors.add(descriptor);
			}
		}
		for (Iterator iter = removingRes.iterator(); iter.hasNext();) {
			Resource current = (Resource) iter.next();
			current.removeProperties();
		}
		resourceIter.close();
	}

	/**
	 * <p>deepRemoveProperties.</p>
	 *
	 * @param resource a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 */
	public static void deepRemoveProperties(Resource resource) {
		StmtIterator propertiesIter = resource.listProperties();
		Set propertySet = new HashSet();
		while (propertiesIter.hasNext()) {
			propertySet.add(propertiesIter.nextStatement());
		}
		propertiesIter.close();
		for (Iterator iter = propertySet.iterator(); iter.hasNext();) {
			Statement statement = (Statement) iter.next();
			deepRemoveProperty(statement);
		}
	}
	/**
	 * Removes the statement passed as argument as well as any property of the
	 * object recurisively. Stops at resources that are the object o another
	 * statement
	 *
	 * @param property a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public static void deepRemoveProperty(Statement property) {
		if (!(property.getObject() instanceof Literal)) {
			Resource object = property.getResource();
			if ((object.isAnon()) && (!isObjectOfOtherStmt(object, property))) {
				Collection stmtCol = new ArrayList();
				StmtIterator iter = object.listProperties();
				while (iter.hasNext()) {
					stmtCol.add(iter.nextStatement());
				}
				iter.close();
				for (Iterator iterator = stmtCol.iterator(); iterator.hasNext();) {
					Statement current = (Statement) iterator.next();
					deepRemoveProperty(current);
				}
			}
		}
		property.remove();
	}

	private static boolean isObjectOfOtherStmt(Resource object,
			Statement property) {
		StmtIterator iter = object.getModel()
				.listStatements(null, null, object);
		try {
			Statement current = iter.nextStatement();
			if (!current.equals(property)) {
				return true;
			}
		} finally {
			iter.close();
		}
		return false;
	}

	/*
	 * renames all resources (not predicates) that start with oldName so that
	 * they start with newName, except when the resource is the object of a
	 * proposition whose subject is of type mies:AnnotationSource, in this case
	 * the Resource is left unchanged in this statement, possibly causing
	 * duplication/splitting of the resource because the same resource in
	 * another position is renamed.
	 * 
	 * @param model @param oldName @param newName
	 */
	// IMPROVE make faster (don't remove and reset statement that don't change)
	/*
	 * public static void rename(Model model, String oldName, String newName)
	 * throws NoSuchElementException, RDFException { StmtIterator stmtIter =
	 * model.listStatements(); Model newModelContent = new ModelMem();
	 * 
	 * while (stmtIter.hasNext()) { Statement current =
	 * stmtIter.nextStatement(); Resource subject = current.getSubject();
	 * //check if it is an AnnotationSource boolean subjectIsAnnotationSource =
	 * false; try { if (subject .getProperty(RDF.type) .getObject()
	 * .equals(MIES.AnnotationSource)) { subjectIsAnnotationSource = true; }
	 * else { if (logger.isDebugEnabled()) { logger.debug(
	 * subject.getProperty(RDF.type).getObject() + " is unequal " +
	 * MIES.AnnotationSource); } } } catch (RDFException ex) { //do nothing,
	 * i.e. don't continue } if (logger.isDebugEnabled()) { logger.debug( "will
	 * modify object of " + current + " " + !subjectIsAnnotationSource); }
	 * Resource newSubject = rename(subject, oldName, newName); RDFNode object =
	 * current.getObject(); RDFNode newObject; if ((object instanceof Resource) &&
	 * !subjectIsAnnotationSource) { newObject = rename((Resource) object,
	 * oldName, newName); } else { newObject = object; }
	 * newModelContent.add(newSubject, current.getPredicate(), newObject); }
	 * Model clone = new ModelMem(); clone.add(model); model.remove(clone); if
	 * (model.size() != 0) { logger.error("model not empty!"); throw new
	 * RuntimeException("model not empty"); } model.add(newModelContent); }
	 */

	/**
	 * <p>renameNamedResources.</p>
	 *
	 * @param model a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 * @param renamer a {@link org.wymiwyg.commons.jena.ResourceRenamer} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public static Model renameNamedResources(Model model,
			ResourceRenamer renamer) {
		Model result = ModelFactory.createDefaultModel();
		StmtIterator statements = model.listStatements();
		while (statements.hasNext()) {
			Statement current = statements.nextStatement();
			Resource currentSubject = current.getSubject();
			Resource subject;
			if (currentSubject.isAnon()) {
				subject = currentSubject;
			} else {
				subject = result.createResource(renamer
						.getNewName(currentSubject));
			}
			Property currentPredicate = current.getPredicate();
			Property predicate;
			if (currentPredicate.isAnon()) {
				predicate = currentPredicate;
			} else {
				predicate = result.createProperty(renamer
						.getNewName(currentPredicate));
			}
			RDFNode currentObject = current.getObject();
			RDFNode object;
			if ((currentObject instanceof Resource)
					&& (!((Resource) currentObject).isAnon())) {
				object = result.createResource(renamer
						.getNewName((Resource) currentObject));
			} else {
				object = currentObject;
			}
			result.add(subject, predicate, object);
		}
		return result;
	}

	/**
	 * replaces any ocuurent of source in the model with target, this also means
	 * that target get all the attributes of source.
	 *
	 * @param model a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 * @param source a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 * @param target a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 * @throws com.hp.hpl.jena.shared.JenaException if any.
	 */
	public static void replace(Model model, Resource source, Resource target)
			throws JenaException {
		ArrayList statementsToChangeSubject = new ArrayList();
		ArrayList statementsToChangeObject = new ArrayList();
		synchronized (model) {
			StmtIterator stmtIterator = model.listStatements(new SelectorImpl(
					source, null, (RDFNode) null));
			while (stmtIterator.hasNext()) {
				statementsToChangeSubject.add(stmtIterator.next());
			}
			for (Iterator iter = statementsToChangeSubject.iterator(); iter
					.hasNext();) {
				Statement statement = (Statement) iter.next();
				model.remove(statement);
				model.add(target, statement.getPredicate(), statement
						.getObject());
			}
			stmtIterator = model.listStatements(new SelectorImpl(null, null,
					source));
			while (stmtIterator.hasNext()) {
				statementsToChangeObject.add(stmtIterator.next());
			}
			for (Iterator iter = statementsToChangeObject.iterator(); iter
					.hasNext();) {
				Statement statement = (Statement) iter.next();
				model.remove(statement);
				model.add(statement.getSubject(), statement.getPredicate(),
						target);
			}

		}
	}

	/**
	 * <p>getExpandedResource.</p>
	 *
	 * @param resource a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 * @param expansion a int.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public static Model getExpandedResource(Resource resource, int expansion) {
		return getExpandedResource(resource, expansion, true);
	}

	/**
	 * <p>getExpandedResource.</p>
	 *
	 * @param resource a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 * @param expansion a int.
	 * @return Model
	 * @param stopAtResWithName a boolean.
	 */
	public static Model getExpandedResource(Resource resource, int expansion,
			boolean stopAtResWithName) {
		return getExpandedResource(resource, expansion, stopAtResWithName, null);
	}

	/**
	 * <p>getExpandedResource.</p>
	 *
	 * @param resource a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 * @param validProperties
	 *            null means that properties of any type are allowed
	 * @param expansion a int.
	 * @param stopAtResWithName a boolean.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public static Model getExpandedResource(Resource resource, int expansion,
			boolean stopAtResWithName, Set validProperties) {

		try {
			if (log.isDebugEnabled()) {
				StmtIterator iter = resource.listProperties();
				while (iter.hasNext()) {
					log.debug("Available property of body: " + iter.next());
				}
			}

			return getResource(resource.getModel(), resource, expansion,
					ModelFactory.createDefaultModel(), stopAtResWithName, validProperties);

			// return
			// model.query(new SelectorImpl(resource, null, (RDFNode) null));

		} catch (JenaException ex) {
			log.error("Exception getting annotations", ex);
			throw new RuntimeException(ex.toString());
		}
	}

	/**
	 * @param blockModel
	 *            subject contained in this model shall not be subject in the
	 *            result
	 */
	private static Model getResource(Model model, final Resource resource,
			int expansion, Model blockModel, boolean stopAtResWithName,
			final Set validProperties) throws JenaException {
		if (log.isDebugEnabled()) {
			log.debug("Getting expansion of " + resource);
		}
		Model result;
		if (validProperties == null) {
			result = model.query(new SelectorImpl(resource, null,
					(RDFNode) null));
		} else {
			result = model.query(new Selector() {
				public boolean test(Statement stmt) {
					boolean result = stmt.getSubject().equals(resource)
							&& (validProperties.contains(stmt.getPredicate()
									.getURI()) || (stmt.getPredicate()
									.getOrdinal() > 0));
					if (log.isDebugEnabled()) {
						log.debug(stmt.getPredicate().getURI() + " results in "
								+ result);
					}
					return result;
				}

				public Resource getSubject() {
					return resource;
				}

				public Property getPredicate() {
					return null;
				}

				public RDFNode getObject() {
					return null;
				}

				public boolean isSimple() {
					return false;
				}
			});
		}
		if (--expansion > 0) {
			Set expandableResources = new HashSet();

			StmtIterator attributes = result.listStatements();
			/*
			 * new InvertedSelector( new ObjectSelector( new
			 * SubjectIterator(blockModel.listStatements()))));
			 */
			while (attributes.hasNext()) {
				Statement currentStatement = attributes.nextStatement();
				RDFNode currentObject = currentStatement.getObject();
				Property currentProperty = currentStatement.getPredicate();
				if ((validProperties == null)
						|| (validProperties.contains(currentProperty.getURI()))
						|| (currentProperty.getOrdinal() > 0)) {
					if (currentObject instanceof Resource) {
						if ((!stopAtResWithName)
								|| (((Resource) currentObject).isAnon())) {
							log.debug("will recurse if not on blockModel");
							if (!blockModel.contains((Resource) currentObject,
									null, (RDFNode) null)) {
								// we do not directly recurse here to avoid
								// ConcurrentModificationException
								expandableResources.add(currentObject);
							}
						}
					}
				}
			}
			Iterator expandableResourcesIterator = expandableResources
					.iterator();
			while (expandableResourcesIterator.hasNext()) {
				Resource element = (Resource) expandableResourcesIterator
						.next();
				log.debug("recursing");
				result.add(getResource(model, (Resource) element, expansion,
						result, stopAtResWithName, validProperties));

			}
		}
		if (log.isDebugEnabled()) {
			log.debug("returning (result dump follows)");
			StmtIterator debugIter = result.listStatements();
			while (debugIter.hasNext()) {
				log.debug(debugIter.next());
			}
		}
		return result;
	}

	/*
	 * @return the roots of the trees in themodel
	 */
	/**
	 * <p>getRoots.</p>
	 *
	 * @param model a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 * @return an array of {@link com.hp.hpl.jena.rdf.model.Resource} objects.
	 * @throws com.hp.hpl.jena.shared.JenaException if any.
	 */
	public static Resource[] getRoots(Model model) throws JenaException {
		if (log.isDebugEnabled()) {
			log.debug("getting roots of:");
			StringWriter stringWriter = new StringWriter();
			model.write(stringWriter);
			log.debug(stringWriter.toString());
		}
		ArrayList resultList = new ArrayList();
		ResIterator subjectIterator = model.listSubjects();
		Set objectSet = new HashSet();
		NodeIterator objectIterator = model.listObjects();
		while (objectIterator.hasNext()) {
			RDFNode node = objectIterator.nextNode();
			if (node instanceof Resource) {
				objectSet.add((node));
				if (log.isDebugEnabled()) {
					log.debug(node + " added to objectset");
				}
			}
		}
		while (subjectIterator.hasNext()) {
			Resource resource = subjectIterator.nextResource();
			if (!objectSet.contains(resource)) {
				resultList.add(resource);
			} else {
				if (log.isDebugEnabled()) {
					log.debug(resource + " is also an object");
				}
			}
		}
		return (Resource[]) resultList.toArray(new Resource[resultList.size()]);
	}



}
