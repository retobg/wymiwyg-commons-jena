/*
 * Created on Dec 19, 2003
 * 
 * 
 * ====================================================================
 * 
 * The WYMIWYG Software License, Version 1.0
 * 
 * Copyright (c) 2002-2003 WYMIWYG All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer. 2. Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. 3. The end-user documentation
 * included with the redistribution, if any, must include the following
 * acknowlegement: "This product includes software developed by WYMIWYG."
 * Alternately, this acknowlegement may appear in the software itself, if and
 * wherever such third-party acknowlegements normally appear. 4. The name
 * "WYMIWYG" or "WYMIWYG.org" must not be used to endorse or promote products
 * derived from this software without prior written permission. For written
 * permission, please contact wymiwyg@wymiwyg.org. 5. Products derived from this
 * software may not be called "WYMIWYG" nor may "WYMIWYG" appear in their names
 * without prior written permission of WYMIWYG.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL WYMIWYG OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 * 
 * This software consists of voluntary contributions made by many individuals on
 * behalf of WYMIWYG. For more information on WYMIWYG, please see
 * http://www.WYMIWYG.org/.
 * 
 * This licensed is based on The Apache Software License, Version 1.1, see
 * http://www.apache.org/.
 */
package org.wymiwyg.rdfserializer;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wymiwyg.commons.jena.UnavailableLocalisationHandler;
import org.wymiwyg.commons.util.LanguageUnavailableException;
import org.wymiwyg.commons.util.MalformedURIException;
import org.wymiwyg.commons.util.URI;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;
/**
 * <p>ResourceSerializer class.</p>
 *
 * @author reto
 * @version $Id: $Id
 */
public class ResourceSerializer {
	/**
	 * @author reto
	 */
	class PropertyComparator implements Comparator {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object arg0, Object arg1) {
			if (arg0.equals(arg1)) {
				return 0;
			}
			if (log.isDebugEnabled()) {
				log.debug("comparing: " + arg0 + " with " + arg1);
			}
			int result = 0;
			if ((arg0 instanceof Property) && (arg1 instanceof Property)) {
				result = ((Property) arg0).getOrdinal()
						- ((Property) arg1).getOrdinal();
			}
			if (result != 0) {
				return result;
			}
			return arg0.toString().compareTo(arg1.toString());
		}
	}
	//private Set appendingResources = new HashSet();
	private static Log log = LogFactory.getLog(ResourceSerializer.class);
	//set this to false in order that well formed literals are not escaped
	private final static boolean escapeWellFormedLiteral = true;
	/**
	 * <p>Constructor for ResourceSerializer.</p>
	 */
	public ResourceSerializer() {
		super();
	}
	private Resource getDefaultStyle() {
		Model styleModel = ModelFactory.createDefaultModel();
		styleModel.read(getClass().getResource("default-style.rdf").toString());
		return styleModel
				.getResource("http://wymiwyg.org/rdfserializer/default-style#default");
	}
	/**
	 * <p>serialize.</p>
	 *
	 * @param resources an array of {@link com.hp.hpl.jena.rdf.model.Resource} objects.
	 * @param out a {@link java.io.Writer} object.
	 * @throws java.io.IOException if any.
	 * @throws org.wymiwyg.commons.util.LanguageUnavailableException if any.
	 */
	public void serialize(final Resource[] resources, Writer out)
			throws IOException, LanguageUnavailableException {
		serialize(resources, (String) null, out);
	}
	/**
	 * <p>serialize.</p>
	 *
	 * @param resources an array of {@link com.hp.hpl.jena.rdf.model.Resource} objects.
	 * @param urlBase a {@link java.lang.String} object.
	 * @param out a {@link java.io.Writer} object.
	 * @throws java.io.IOException if any.
	 * @throws org.wymiwyg.commons.util.LanguageUnavailableException if any.
	 */
	public void serialize(final Resource[] resources, String urlBase, Writer out)
			throws IOException, LanguageUnavailableException {
		serialize(resources, getDefaultStyle(), urlBase, out);
	}
	/**
	 * <p>serialize.</p>
	 *
	 * @param resources an array of {@link com.hp.hpl.jena.rdf.model.Resource} objects.
	 * @param urlBase a {@link java.lang.String} object.
	 * @param languages an array of {@link java.lang.String} objects.
	 * @param localisationHandler a {@link org.wymiwyg.commons.jena.UnavailableLocalisationHandler} object.
	 * @param out a {@link java.io.Writer} object.
	 * @throws java.io.IOException if any.
	 * @throws org.wymiwyg.commons.util.LanguageUnavailableException if any.
	 */
	public void serialize(Resource[] resources, String urlBase,
			String[] languages,
			UnavailableLocalisationHandler localisationHandler, Writer out)
			throws IOException, LanguageUnavailableException {
		serialize(resources, getDefaultStyle(), urlBase, languages,
				localisationHandler, out);
	}
	/**
	 * <p>serialize.</p>
	 *
	 * @param resources an array of {@link com.hp.hpl.jena.rdf.model.Resource} objects.
	 * @param styleRes a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 * @param out a {@link java.io.Writer} object.
	 * @throws java.io.IOException if any.
	 * @throws org.wymiwyg.commons.util.LanguageUnavailableException if any.
	 */
	public void serialize(final Resource[] resources, Resource styleRes,
			Writer out) throws IOException, LanguageUnavailableException {
		serialize(resources, styleRes, null, out);
	}
	/**
	 * <p>serialize.</p>
	 *
	 * @param resources an array of {@link com.hp.hpl.jena.rdf.model.Resource} objects.
	 * @param styleRes a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 * @param urlBase a {@link java.lang.String} object.
	 * @param out a {@link java.io.Writer} object.
	 * @throws java.io.IOException if any.
	 * @throws org.wymiwyg.commons.util.LanguageUnavailableException if any.
	 */
	public void serialize(final Resource[] resources, Resource styleRes,
			String urlBase, Writer out) throws IOException, LanguageUnavailableException {
		serialize(resources, styleRes, urlBase, null, null, out);
	}
	/**
	 * <p>serialize.</p>
	 *
	 * @param resources an array of {@link com.hp.hpl.jena.rdf.model.Resource} objects.
	 * @param styleRes a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 * @param urlBase a {@link java.lang.String} object.
	 * @param languages an array of {@link java.lang.String} objects.
	 * @param localisationHandler a {@link org.wymiwyg.commons.jena.UnavailableLocalisationHandler} object.
	 * @param out a {@link java.io.Writer} object.
	 * @throws java.io.IOException if any.
	 * @throws org.wymiwyg.commons.util.LanguageUnavailableException if any.
	 */
	public void serialize(final Resource[] resources, Resource styleRes,
			String urlBase, String[] languages,
			UnavailableLocalisationHandler localisationHandler, Writer out)
			throws IOException, LanguageUnavailableException {
		final ContentSinkImpl sink = new ContentSinkImpl();
		final Style style = new Style(styleRes);
		serialize(resources, style, urlBase, languages, localisationHandler,
				sink);
		for (ContentProducer current = sink.nextProducer(); current != null; current = sink
				.nextProducer()) {
			current.write(out);
		}
		out.flush();
	}
	private void serialize(Resource[] resources, Style style, String urlBase,
			String[] languages,
			UnavailableLocalisationHandler localisationHandler, ContentSink sink)
			throws IOException, LanguageUnavailableException {
		//TODO think: is a Map a good solution (variable scope etc.)?
		Map variables = new HashMap();
		DocumentTag documentTag = new DocumentTag(variables);
		sink.append(documentTag);
		List remainingResources = new ArrayList();
		for (int i = 0; i < resources.length; i++) {
			appendInlineResource(resources[i], style, variables, 0, urlBase,
					languages, localisationHandler, sink, remainingResources);
		}
		//TODO render resources in the type-descriptor order
		while (remainingResources.size() > 0) {
			Iterator appendingResourceIterator = remainingResources.iterator();
			remainingResources = new ArrayList();
			while (appendingResourceIterator.hasNext()) {
				Resource current = (Resource) appendingResourceIterator.next();
				appendInlineResource(current, style, variables, 0, urlBase,
						languages, localisationHandler, sink,
						remainingResources);
			}
		}
		sink.append(new ValueContentProducer("</rdf:RDF>"));
	}
	/**
	 * @param resource
	 * @param sink
	 */
	private void appendInlineResource(Resource resource, Style style,
			Map variables, int deepness, String urlBase, String[] languages,
			UnavailableLocalisationHandler localisationHandler,
			ContentSink sink, Collection remainingResources) throws IOException, LanguageUnavailableException {
		if (variables.containsKey(resource)) {
			return;
		}
		deepness++;
		if (log.isDebugEnabled()) {
			log.debug("Deepness :" + deepness);
			log.debug("Appending resource of type (stmt): "
					+ resource.getProperty(RDF.type));
		}
		Set usedStatements = new HashSet();
		ResourceTag resourceTag = new ResourceTag(resource, style,
				usedStatements, variables, urlBase);
		sink.append(resourceTag);
		StmtIterator propertiesIter = resource.listProperties();
		Set propertyTypes = new HashSet();
		while (propertiesIter.hasNext()) {
			propertyTypes.add(propertiesIter.nextStatement().getPredicate());
		}
		PropertyDescriptor propertyDescriptors[] = style
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor current = propertyDescriptors[i];
			Property property = current.getProperty();
			if (propertyTypes.contains(property)) {
				propertyTypes.remove(property);
				appendProperties(resource, property, usedStatements, current
						.getStyle(), variables, deepness, urlBase, languages,
						localisationHandler, sink, remainingResources);
			}
		}
		//remaning property-types:
		Iterator remainingProperties = propertyTypes.iterator();
		SortedSet sortedProperties = new TreeSet(new PropertyComparator());
		while (remainingProperties.hasNext()) {
			sortedProperties.add(remainingProperties.next());
		}
		remainingProperties = sortedProperties.iterator();
		while (remainingProperties.hasNext()) {
			Property property = (Property) remainingProperties.next();
			appendProperties(resource, property, usedStatements, style,
					variables, deepness, urlBase, languages,
					localisationHandler, sink, remainingResources);
		}
		sink.append(resourceTag.getClosingTag());
	}
	/**
	 * @param resource
	 * @param property
	 * @param usedStatements
	 * @param style
	 * @param variables
	 * @param deepness
	 * @param urlBase
	 * @param sink
	 * @param remainingResources
	 */
	private void appendProperties(Resource resource, Property property,
			Set usedStatements, Style style, Map variables, int deepness,
			String urlBase, String[] languages,
			UnavailableLocalisationHandler localisationHandler,
			ContentSink sink, Collection remainingResources) throws IOException, LanguageUnavailableException {
		List languageSet = Arrays.asList(languages);
		StmtIterator renderingProperties = resource.listProperties(property);
		/*
		 * SortedSet sortedProperties = new TreeSet(new StmtComparator()); while
		 * (renderingProperties.hasNext()) {
		 * sortedProperties.add(renderingProperties.next());
		 */
		Statement languageNeutral = null;
		boolean propertyValueAppended = false;
		Set availableLiteralSet = new HashSet();
		//sort different language versions by language priorities
		boolean languageVersionAvailable = false;
		//in this we put the available language versions at the same position
		// as the language in languages
		Statement[] supportedLanguageVersions = new Statement[languages.length];
		//Iterator propertiesIter = sortedProperties.iterator();
		while (renderingProperties.hasNext()) {
			//while (sortedProperties.size() >0) {
			Statement currentStatement = renderingProperties.nextStatement();
			if (!usedStatements.contains(currentStatement)) {
				RDFNode object = currentStatement.getObject();
				if ((languages != null) && (object instanceof Literal)) {
					Literal literalObject = (Literal) object;
					String language = literalObject.getLanguage();
					if ((language == null) || (language.equals(""))) {
						languageNeutral = currentStatement;
					} else {
						if (languageSet.contains(language)) {
							supportedLanguageVersions[languageSet
									.indexOf(language)] = currentStatement;
							languageVersionAvailable = true;
						} else {
							availableLiteralSet.add(literalObject);
						}
					}
				} else {
					appendProperty(currentStatement, style, variables,
							deepness, urlBase, languages, localisationHandler,
							sink, remainingResources);
					propertyValueAppended = true;
				}
				usedStatements.add(currentStatement);
			} else {
				//well has been appended previously
				propertyValueAppended = true;
			}
		}
		if (languageVersionAvailable) {
			for (int i = 0; i < supportedLanguageVersions.length; i++) {
				if (supportedLanguageVersions[i] != null) {
					appendProperty(supportedLanguageVersions[i], style,
							variables, deepness, urlBase, languages,
							localisationHandler, sink, remainingResources);
					propertyValueAppended = true;
				}
			}
		}
		if (!propertyValueAppended) {
			if (languageNeutral != null) {
				appendProperty(languageNeutral, style, variables, deepness,
						urlBase, languages, localisationHandler, sink,
						remainingResources);
			} else {
				Literal[] availableLiterals = (Literal[]) availableLiteralSet
						.toArray(new Literal[availableLiteralSet.size()]);
				Literal[] replacement;
				replacement = localisationHandler
						.getReplacement(availableLiterals);
				for (int i = 0; i < replacement.length; i++) {
				Statement replacementStatement = resource.getModel()
						.createStatement(resource, property, replacement[i]);
				appendProperty(replacementStatement, style, variables,
						deepness, urlBase, languages, localisationHandler,
						sink, remainingResources);
				}
			}
		}
	}
	/**
	 * @param statement
	 * @param current
	 * @param style
	 * @param variables
	 * @param sink
	 */
	private void appendProperty(Statement statement, Style style,
			Map variables, int deepness, String urlBase, String[] languages,
			UnavailableLocalisationHandler localisationHandler,
			ContentSink sink, Collection remainingResources) throws IOException, LanguageUnavailableException {
		Property propertyType = statement.getPredicate();
		XMLTag openingTag = new XMLTag(style, variables);
		openingTag.setNameSpace(propertyType.getNameSpace());
		if (propertyType.getOrdinal() > 0) {
			openingTag.setLocalName("li");
		} else {
			openingTag.setLocalName(propertyType.getLocalName());
		}
		sink.append(openingTag);
		RDFNode object = statement.getObject();
		if (object instanceof Literal) {
			openingTag.setPreserveSpaces(true);
			String language = ((Literal) object).getLanguage();
			if ((language != null) && (!language.equals(""))) {
				openingTag.addXMLAttribute("lang", language);
			}
			Literal literal = ((Literal) object);
			if (literal.getWellFormed()) {
				sink.append(new ValueContentProducer(literal.getLexicalForm()));
			} else {
				if (literal.getWellFormed()) {
					log.info("Literal " + literal + " is well formed");
				}
				/*
				 * StringReader in = new StringReader(literal.getLexicalForm());
				 * StringWriter out = new StringWriter(); for (int ch =
				 * in.read(); ch != -1; ch = in.read()) { if (ch == ' <') {
				 * out.write("&lt;"); } else { if (ch == '&') {
				 * out.write("&amp;"); } else { out.write(ch); } } }
				 */
				String literalValue = literal.getLexicalForm();
				if (!escapeWellFormedLiteral) {
					//f (!IsWellFormed.isWellFormed(literalValue)) {
					sink.append(new ValueContentProducer(XMLEncoder
							.encode(literalValue)));
					/*
					 * } else { //TODO add parseType sink.append(new
					 * ValueContentProducer(literalValue)); }
					 */
				} else {
					sink.append(new ValueContentProducer(XMLEncoder
							.encode(literalValue)));
				}
			}
			//sink.append(new ValueContentProducer(literal.getString()));
			sink.append(openingTag.getClosingTag());
		} else {
			Resource resourceObject = (Resource) object;
			StmtIterator propertiesOfObject = ((Resource) object)
					.listProperties();
			boolean hasProperty = propertiesOfObject.hasNext();
			propertiesOfObject.close();
			boolean hasChildElements = false;
			if (hasProperty) {
				ResourceTag existing = (ResourceTag) variables.get(object);
				if (((resourceObject.isAnon()) && (deepness < style
						.getAnonymousDeepness()))
						|| (deepness < style.getNonAnonymousDeepness())) {
					if (existing == null) {
						appendInlineResource((Resource) object, style,
								variables, deepness, urlBase, languages,
								localisationHandler, sink, remainingResources);
						hasChildElements = true;
					} else {
						if (!((Resource) object).isAnon()) {
							String uri = resourceObject.getURI();
							if ((urlBase != null) && (!urlBase.equals(""))) {
								try {
									uri = new URI(urlBase)
											.relativize(uri, URI.SAMEDOCUMENT
													| URI.ABSOLUTE
													| URI.RELATIVE | URI.PARENT);
								} catch (MalformedURIException e) {
									throw new RuntimeException(e);
								}
							}
							openingTag.addAttribute(RDF.getURI(), "resource",
									uri);
						} else {
							String anonID = existing.getAnonID();
							openingTag.addAttribute(RDF.getURI(), "ID", anonID);
						}
					}
				} else {
					//move to bottom
					TypeDescriptor[] typeDescriptors = style
							.getTypeDescriptors();
					boolean block = false;
					for (int i = 0; i < typeDescriptors.length; i++) {
						TypeDescriptor current = typeDescriptors[i];
						if (resourceObject.hasProperty(RDF.type, current
								.getType())) {
							block = current.isBlock();
							break;
						}
					}
					if (!block) {
						remainingResources.add(resourceObject);
					}
					if (!((Resource) object).isAnon()) {
						String uri = resourceObject.getURI();
						if ((urlBase != null) && (!urlBase.equals(""))) {
							try {
								uri = new URI(urlBase).relativize(uri,
										URI.SAMEDOCUMENT | URI.ABSOLUTE
												| URI.RELATIVE | URI.PARENT);
							} catch (MalformedURIException e) {
								throw new RuntimeException(e);
							}
						}
						openingTag.addAttribute(RDF.getURI(), "resource", uri);
					} else {
						//TODO test this code
						ResourceTag future = new ResourceTag(resourceObject,
								style, new HashSet(), variables, urlBase);
						String anonID = future.getAnonID();
						openingTag.addAttribute(RDF.getURI(), "ID", anonID);
					}
				}
			} else {
				if (!((Resource) object).isAnon()) {
					String uri = resourceObject.getURI();
					if ((urlBase != null) && (!urlBase.equals(""))) {
						try {
							uri = new URI(urlBase).relativize(uri,
									URI.SAMEDOCUMENT | URI.ABSOLUTE
											| URI.RELATIVE | URI.PARENT);
						} catch (MalformedURIException e) {
							log.warn("Failed relativazing URI " + uri
									+ " using URL base " + urlBase, e);
						}
					}
					openingTag.addAttribute(RDF.getURI(), "resource", uri);
				}
			}
			if (hasChildElements) {
				sink.append(openingTag.getClosingTag());
			} else {
				openingTag.close();
			}
		}
	}
}
