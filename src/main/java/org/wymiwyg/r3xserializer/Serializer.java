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
package org.wymiwyg.r3xserializer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.util.XMLChar;
import org.wymiwyg.commons.jena.UnavailableLocalisationHandler;
import org.wymiwyg.commons.util.LanguageUnavailableException;
import org.wymiwyg.commons.util.MalformedURIException;
import org.wymiwyg.commons.util.URI;
import org.wymiwyg.rdfserializer.XMLEncoder;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.NsIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.RSS;
import com.hp.hpl.jena.vocabulary.VCARD;

/**
 * This is a serializer for the R3X format. R3X is a subset of RDF/XML defined
 * by Morten Frederiksen at
 * http://www.wasab.dk/morten/blog/archives/2004/05/30/transforming-rdfxml-with-xslt
 *
 * This writer does not support multiple properties with literal values of the
 * same type and language
 *
 * @author reto
 * @version $Id: $Id
 */
public class Serializer {
	private static Log log = LogFactory.getLog(Serializer.class);

	private int prefixCounter = 0;

	private Map<String, String> nonModelPrefixMap = new HashMap<String, String>();
	{
		nonModelPrefixMap.put(RDF.getURI(), "rdf");
		nonModelPrefixMap.put(RDFS.getURI(), "rdfs");
		nonModelPrefixMap.put(DC.getURI(), "dc");
		nonModelPrefixMap.put(RSS.getURI(), "rss");
		nonModelPrefixMap.put("http://www.daml.org/2001/03/daml+oil.daml#",
				"daml");
		nonModelPrefixMap.put(VCARD.getURI(), "vcard");
		nonModelPrefixMap.put("http://www.w3.org/2002/07/owl#", "owl");
		nonModelPrefixMap.put("http://jena.hpl.hp.com/gvs/aggregator#", "aggr");
		nonModelPrefixMap.put("http://discobits.org/ontology#", "disco");
		nonModelPrefixMap.put("http://gvs.hpl.hp.com/ontologies/authorization#", "gvsauth");
		nonModelPrefixMap.put("http://gvs.hpl.hp.com/ontologies/account-manager#", "gvsacc");
		nonModelPrefixMap.put("http://gvs.hpl.hp.com/ontologies/services#", "gvsser");
		nonModelPrefixMap.put("http://gvs.hpl.hp.com/ontologies/http-listener#", "gvshttp");
		nonModelPrefixMap.put("http://xmlns.com/foaf/0.1/", "foaf");
		nonModelPrefixMap.put("http://wymiwyg.org/ontologies/foaf/extensions#", "foafex");
		nonModelPrefixMap.put("http://wymiwyg.org/ontologies/foaf/postaddress#", "addr");
		nonModelPrefixMap.put("http://wymiwyg.org/ontologies/foaf/role#", "role");
		nonModelPrefixMap.put("http://wymiwyg.org/ontologies/knobot#", "knobot");
		nonModelPrefixMap.put("http://wymiwyg.org/ontologies/rss/attach#", "attach");
		nonModelPrefixMap.put("http://www.w3.org/2002/12/cal#", "calendar");
		nonModelPrefixMap.put("http://frot.org/space/0.1/", "space");
	}

	/**
	 * <p>serialize.</p>
	 *
	 * @param model a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 * @param base a {@link java.lang.String} object.
	 * @param rawOut a {@link java.io.Writer} object.
	 * @throws java.io.IOException if any.
	 */
	public void serialize(Model model, String base, Writer rawOut)
			throws IOException {
		try {
			serialize(model, base, rawOut, null, null, false);
		} catch (LanguageUnavailableException e) {
			throw new RuntimeException(
					"LanguageUnavailableException at unexpected location", e);
		}
	}

	/**
	 * <p>serialize.</p>
	 *
	 * @param model a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 * @param base a {@link java.lang.String} object.
	 * @param rawOut a {@link java.io.Writer} object.
	 * @param locales an array of {@link java.util.Locale} objects.
	 * @param localisationHandler a {@link org.wymiwyg.commons.jena.UnavailableLocalisationHandler} object.
	 * @param forceShow a boolean.
	 * @throws java.io.IOException if any.
	 * @throws org.wymiwyg.commons.util.LanguageUnavailableException if any.
	 */
	public void serialize(Model model, String base, Writer rawOut,
			Locale[] locales,
			UnavailableLocalisationHandler localisationHandler,
			boolean forceShow) throws IOException, LanguageUnavailableException {
		URI baseURI = null;
		if ((base != null) && !base.equals("")) {
			baseURI = new URI(base);
		}
		PrintWriter out = new PrintWriter(rawOut);
		writeHeader(out, model, baseURI);
		ResIterator subjects = model.listSubjects();
		while (subjects.hasNext()) {
			Resource current = subjects.nextResource();
			writeSubject(out, model, current, baseURI, locales,
					localisationHandler, forceShow);
		}
		out.print("</rdf:RDF>");
		out.flush();

	}

	/**
	 * @param out
	 * @param subject
	 * @param forceShow
	 * @throws LanguageUnavailableException
	 */
	private void writeSubject(PrintWriter out, Model model, Resource subject,
			URI baseURI, Locale[] locales,
			UnavailableLocalisationHandler localisationHandler,
			boolean forceShow) throws LanguageUnavailableException {
		if (subject.isAnon()) {
			out.print("  <rdf:Description rdf:nodeID=\"");
			out.print(escapedId(subject.getId().toString()));
		} else {
			out.print("  <rdf:Description rdf:about=\"");
			out.print(XMLEncoder.encode(relativize(baseURI, subject.getURI())));
		}
		out.println("\">");
		StmtIterator stmtIter = subject.listProperties();
		SortedSet sortedProperties = new TreeSet(new Comparator() {

			public int compare(Object arg0, Object arg1) {
				Statement stmt0 = (Statement) arg0;
				Statement stmt1 = (Statement) arg1;
				if (stmt0.equals(stmt1)) {
					return 0;
				}
				Property predicate0 = stmt0.getPredicate();
				Property predicate1 = stmt1.getPredicate();
				int ordinal0 = predicate0.getOrdinal();
				int ordinal1 = predicate1.getOrdinal();
				if (ordinal0 != ordinal1) {
					return ordinal0 > ordinal1 ? 1 : -1;
				}
				int stringCompare = predicate0.getURI().compareTo(
						predicate1.getURI());
				if (stringCompare != 0) {
					return stringCompare;
				} else {
					// diffent objects or just different languages
					return stmt0.toString().compareTo(stmt1.toString());
				}
			}

		});
		while (stmtIter.hasNext()) {
			sortedProperties.add(stmtIter.nextStatement());
		}
		Set processedLiteralPropertyTypes = new HashSet();
		Iterator sortedIterator = sortedProperties.iterator();
		while (sortedIterator.hasNext()) {
			Statement current = (Statement) sortedIterator.next();
			RDFNode object = current.getObject();
			if (object instanceof Literal) {
				Property predicate = current.getPredicate();
				if (!processedLiteralPropertyTypes.contains(predicate)) {
					processLanguageProperty(out, model, baseURI, subject,
							predicate, locales, localisationHandler, forceShow);
					processedLiteralPropertyTypes.add(predicate);
				}
			} else {
				writeProperty(out, model, current, baseURI);
			}
		}
		out.println("  </rdf:Description>");

	}

	/**
	 * This method invoke processedLiteralPropertyTypes in the order of
	 * acceptedLanguage for all acceptedLanguages or if no acceptedLanguage
	 * available for the no-languageVersion
	 * 
	 * @param out
	 * @param model
	 * @param baseURI
	 * @param subject
	 * @param predicate
	 * @param locales
	 * @param localisationHandler
	 * @param forceShow
	 * @throws LanguageUnavailableException
	 */
	private void processLanguageProperty(PrintWriter out, Model model,
			URI baseURI, Resource subject, Property predicate,
			Locale[] locales,
			UnavailableLocalisationHandler localisationHandler,
			boolean forceShow) throws LanguageUnavailableException {
		StmtIterator stmtIter = subject.listProperties(predicate);
		if (locales == null) {
			while (stmtIter.hasNext()) {
				writeProperty(out, model, model.createStatement(subject,
						predicate, stmtIter.nextStatement().getObject()),
						baseURI);
			}
		} else {

			Set noLanguageVersionStatementSet = new HashSet();
			Map availableVersions = new HashMap();
			while (stmtIter.hasNext()) {
				Statement current = stmtIter.nextStatement();
				RDFNode currentObject = current.getObject();
				if (currentObject instanceof Literal) {
					Locale currentLocale = parseLocale(((Literal) currentObject)
							.getLanguage());
					if (currentLocale == null) {
						noLanguageVersionStatementSet.add(current);
					} else {
						availableVersions.put(currentLocale, current);
					}
				} else {
					writeProperty(out, model, current, baseURI);
				}
			}
			if ((availableVersions.size() == 0) || (locales == null)) {
				for (Iterator iter = noLanguageVersionStatementSet.iterator(); iter
						.hasNext();) {
					Statement current = (Statement) iter.next();
					writeProperty(out, model, current, baseURI);

				}
				for (Iterator iter = availableVersions.values().iterator(); iter
						.hasNext();) {
					Statement current = (Statement) iter.next();
					writeProperty(out, model, current, baseURI);

				}

			} else {
				boolean localizedVersionWritten = false;
				for (int i = 0; i < locales.length; i++) {
					Locale locale = locales[i];
					Statement localizedStmt = (Statement) availableVersions
							.get(locale);
					if (localizedStmt == null) {
						// assume that who understands xx_YY also understands xx
						locale = new Locale(locale.getLanguage());
						localizedStmt = (Statement) availableVersions
								.get(locale);
					}
					if (localizedStmt != null) {
						availableVersions.remove(locale);
						writeProperty(out, model, localizedStmt, baseURI);
						localizedVersionWritten = true;
					}
				}
				if (forceShow) {
					for (Iterator iter = availableVersions.values().iterator(); iter
							.hasNext();) {
						Statement current = (Statement) iter.next();
						writeProperty(out, model, current, baseURI);
					}
				}
				if ((!localizedVersionWritten) || forceShow) {
					boolean languageIndependentVersionWritten = false;
					for (Iterator iter = noLanguageVersionStatementSet
							.iterator(); iter.hasNext();) {
						Statement current = (Statement) iter.next();
						writeProperty(out, model, current, baseURI);
						languageIndependentVersionWritten = true;
					}
					if (!languageIndependentVersionWritten || forceShow) {
						Literal[] availableLiterals = new Literal[availableVersions
								.size()];
						Iterator iter = availableVersions.values().iterator();
						for (int i = 0; i < availableLiterals.length; i++) {
							Statement currentAvailableVersion = (Statement) iter
									.next();
							availableLiterals[i] = currentAvailableVersion
									.getLiteral();

						}
						try {
							Literal[] replacement = localisationHandler
									.getReplacement(availableLiterals);
							for (int i = 0; i < replacement.length; i++) {

								writeProperty(out, model, model
										.createStatement(subject, predicate,
												replacement[i]), baseURI);
							}
						} catch (LanguageUnavailableException ex) {
							if (!localizedVersionWritten
									&& !languageIndependentVersionWritten
									&& !forceShow) {
								throw ex;
							}
						}
					}
				}
			}
		}

	}

	/**
	 * @param language
	 * @return
	 */
	private Locale parseLocale(String language) {
		if ((language == null) || language.equals("")) {
			return null;
		}
		int underscorePos = language.indexOf('-');
		if (underscorePos > -1) {
			return new Locale(language.substring(0, underscorePos - 1),
					language.substring(underscorePos + 1));
		} else {
			return new Locale(language);
		}
	}

	/**
	 * @param baseURI
	 * @param uri
	 * @return
	 */
	private String relativize(URI baseURI, String uri) {
		if (baseURI == null) {
			return uri;
		} else {
			try {
				return baseURI.relativize(uri, URI.SAMEDOCUMENT | URI.ABSOLUTE
						| URI.RELATIVE | URI.PARENT);
			} catch (MalformedURIException e) {
				log.warn("failed relativizing URI", e);
				return uri;
			}
		}
	}

	/**
	 * @param out
	 * @param model
	 * @param statement
	 */
	private void writeProperty(PrintWriter out, Model model,
			Statement statement, URI baseURI) {
		out.write("    ");
		Property predicate = statement.getPredicate();
		RDFNode object = statement.getObject();
		String nameSpaceURI = predicate.getNameSpace();
		String prefix = getPrefix(model, nameSpaceURI);
		String localName = predicate.getLocalName();
		if (predicate.getOrdinal() > 0) {
			localName = "li";
		}
		StringBuffer cNameBuffer = new StringBuffer();
		cNameBuffer.append(prefix);
		cNameBuffer.append(':');
		cNameBuffer.append(localName);
		String cName = cNameBuffer.toString();
		if (object instanceof Literal) {
			writeLiteralProperty(out, cName, (Literal) object);
		} else {
			writerResourceProperty(out, cName, (Resource) object, baseURI);
		}

	}

	/**
	 * @param out
	 * @param name
	 * @param resource
	 */
	private void writerResourceProperty(PrintWriter out, String cName,
			Resource resource, URI baseURI) {
		out.print('<');
		out.print(cName);
		if (resource.isAnon()) {
			out.print(" rdf:nodeID=\"");
			out.print(escapedId(resource.getId().toString()));
		} else {
			out.print(" rdf:resource=\"");
			out
					.print(XMLEncoder.encode(relativize(baseURI, resource
							.getURI())));
		}
		out.println("\"/>");
	}

	/**
	 * @param out
	 * @param name
	 * @param literal
	 */
	private void writeLiteralProperty(PrintWriter out, String cName,
			Literal literal) {
		out.print('<');
		out.print(cName);
		String dataTypeURI = literal.getDatatypeURI();
		if (dataTypeURI != null) {
			if (dataTypeURI
					.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral")) {
				out.print(" rdf:parseType=\"Literal\"");
				// literal.isWellFormedXML() will return true
			} else {
				out.print(" rdf:datatype=\"");
				out.print(dataTypeURI);
				out.print('\"');
			}
		} else {
			String language = literal.getLanguage();
			if ((language != null) && !language.equals("")) {
				out.print(" xml:lang=\"");
				out.print(language);
				out.print('\"');
			}
		}
		out.write('>');
		if (literal.isWellFormedXML()) {
			out.write(literal.getLexicalForm());
		} else {
			out.write(XMLEncoder.encode(literal.getLexicalForm()));
		}
		out.print("</");
		out.print(cName);
		out.println(">");
	}

	/**
	 * @param out
	 * @param model
	 * @return
	 */
	private void writeHeader(PrintWriter out, Model model, URI baseURI) {
		NsIterator nsIterator = model.listNameSpaces();
		out
				.print("<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		boolean first = true;
		while (nsIterator.hasNext()) {
			String uri = nsIterator.nextNs();
			String prefix = getPrefix(model, uri);
			if ("rdf".equals(prefix)) {
				continue;
			}
			if (first) {
				first = false;
			} else {
				out.println();
				out.print("  ");
			}
			out.print(" xmlns:");
			out.print(prefix);
			out.print("=\"");
			out.print(relativize(baseURI, uri));
			out.print('\"');
		}
		out.println('>');
	}

	/**
	 * @param model
	 * @param uri
	 * @return
	 */
	private synchronized String getPrefix(Model model, String uri) {
		if ("http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(uri)) {
			return "rdf";
		}
		String modelPrefix = model.getNsURIPrefix(uri);
		if (modelPrefix != null) {
			return modelPrefix;
		}
		String nonModelPrefix = (String) nonModelPrefixMap.get(uri);
		if (nonModelPrefix != null) {
			return nonModelPrefix;
		}
		String newPrefix = getNewPrefix(model);
		nonModelPrefixMap.put(uri, newPrefix);
		return newPrefix;
	}

	/**
	 * @return
	 */
	private String getNewPrefix(Model model) {
		StringBuffer buffer = new StringBuffer("ns");
		buffer.append(prefixCounter++);
		String newName = buffer.toString();
		if (nonModelPrefixMap.containsKey(newName)) {
			return getNewPrefix(model);
		}
		if (model.getNsPrefixURI(newName) != null) {
			return getNewPrefix(model);
		}
		return newName;
	}

	// copied from jena. BaseXMLWriter
	static private final char ESCAPE = 'X';

	static private String escapedId(String id) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < id.length(); i++) {
			char ch = id.charAt(i);
			if (ch != ESCAPE
					&& (i == 0 ? XMLChar.isNCNameStart(ch) : XMLChar
							.isNCName(ch))) {
				result.append(ch);
			} else {
				escape(result, ch);
			}
		}
		return result.toString();
	}

	static final char[] hexchar = "0123456789abcdef".toCharArray();

	static private void escape(StringBuffer sb, char ch) {
		sb.append(ESCAPE);
		int charcode = ch;
		do {
			sb.append(hexchar[charcode & 15]);
			charcode = charcode >> 4;
		} while (charcode != 0);
		sb.append(ESCAPE);
	}
}
