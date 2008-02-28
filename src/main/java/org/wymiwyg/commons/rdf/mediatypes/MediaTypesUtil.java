/*
 * Copyright  2002-2005 WYMIWYG (http://wymiwyg.org)
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
package org.wymiwyg.commons.rdf.mediatypes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.activation.MimeTypeParseException;

import org.wymiwyg.commons.mediatypes.MimeType;
import org.wymiwyg.commons.vocabulary.MEDIATYPES;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * @author reto
 * 
 */
public class MediaTypesUtil {

	private static MediaTypesUtil defaultInstance;

	private Map extensionCanonicalMimeMap = new HashMap();

	private Map mimeCanonicalExtensionMap = new HashMap();

	private Map mimeCanonicalMimeMap = new HashMap();

	public MediaTypesUtil() throws MimeTypeParseException {
		Model model = ModelFactory.createDefaultModel();
		model.read(getClass().getResource("mimetypes.rdf").toString());
		load(model);
	}
	public MediaTypesUtil(Model model) throws MimeTypeParseException {
		load(model);
		
	}
	
	public static MediaTypesUtil getDefaultInstance() {
		if (defaultInstance == null) {
			synchronized (MediaTypesUtil.class) {
				if (defaultInstance == null) {
					try {
						defaultInstance = new MediaTypesUtil();
					} catch (MimeTypeParseException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return defaultInstance;
	}
	
	public static void setDefaultInstance(MediaTypesUtil defaultInstance) {
		MediaTypesUtil.defaultInstance = defaultInstance;
	}
	

	/**
	 * @param model
	 * @throws MimeTypeParseException 
	 */
	private void load(Model model) throws MimeTypeParseException {
		ResIterator typesResIter = model.listSubjectsWithProperty(RDF.type,
				MEDIATYPES.Type);
		while (typesResIter.hasNext()) {
			Resource type = typesResIter.nextResource();
			processType(type);
		}
		typesResIter.close();
		
	}
	/**
	 * @param type
	 * @throws MimeTypeParseException
	 */
	private void processType(Resource type) throws MimeTypeParseException {
		String canonicalMimeTypeString = type.getRequiredProperty(
				MEDIATYPES.canonicalMimeType).getString();
		Set otherMimeTypeStrings = new HashSet();
		StmtIterator mimeTypes = type.listProperties(MEDIATYPES.mimeType);
		while (mimeTypes.hasNext()) {
			otherMimeTypeStrings.add(mimeTypes.nextStatement().getString());
		}
		mimeTypes.close();
		otherMimeTypeStrings.add(canonicalMimeTypeString);
		MimeType canonicalMimeType = new MimeType(canonicalMimeTypeString);
		String canonicalExtension;
		if (type.hasProperty(MEDIATYPES.canonicalFileExtension)) {
			canonicalExtension = type.getProperty(
					MEDIATYPES.canonicalFileExtension).getString();
		} else {
			if (type.hasProperty(MEDIATYPES.fileExtension)) {
				throw new RuntimeException("no file extension for type "
						+ canonicalMimeTypeString + " marked as canonicacl");
			}
			canonicalExtension = null;
		}
		for (Iterator iter = otherMimeTypeStrings.iterator(); iter.hasNext();) {
			String currentMimeTypeString = (String) iter.next();
			MimeType currentMimeType = new MimeType(currentMimeTypeString);
			mimeCanonicalMimeMap.put(currentMimeType, canonicalMimeType);
			if (canonicalExtension != null) {
				mimeCanonicalExtensionMap.put(currentMimeType,
						canonicalExtension);
			}
		}
		if (canonicalExtension != null) {
			Set allExtensions = new HashSet();
			StmtIterator otherExtensionsIter = type
					.listProperties(MEDIATYPES.fileExtension);
			while (otherExtensionsIter.hasNext()) {
				allExtensions.add(otherExtensionsIter.nextStatement()
						.getString());
			}
			otherExtensionsIter.close();
			allExtensions.add(canonicalExtension);
			for (Iterator iter = allExtensions.iterator(); iter.hasNext();) {
				String currentExtension = (String) iter.next();
				extensionCanonicalMimeMap.put(currentExtension,
						canonicalMimeType);
			}
		}

	}

	/**
	 * returns the canonical mime-type for the the given extension
	 * 
	 * @param extension
	 *            the extension without dots
	 * @return
	 */
	public MimeType getTypeForExtension(String extension) {
		return (MimeType) extensionCanonicalMimeMap.get(extension);
	}

	/**
	 * get the extension for a given type
	 * 
	 * @param type
	 * @return the extension without dots
	 */
	public String getExtensionForType(MimeType type) {
		return (String) mimeCanonicalExtensionMap.get(type);
	}

	public MimeType getCanonicalType(MimeType type) {
		return (MimeType) mimeCanonicalMimeMap.get(type);
	}

}
