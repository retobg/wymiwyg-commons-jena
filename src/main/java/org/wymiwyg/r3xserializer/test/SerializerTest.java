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
package org.wymiwyg.r3xserializer.test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;

import org.wymiwyg.commons.jena.UnavailableLocalisationHandler;
import org.wymiwyg.commons.util.LanguageUnavailableException;
import org.wymiwyg.commons.util.text.W3CDateFormat;
import org.wymiwyg.r3xserializer.Serializer;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * @author reto
 *
 */
public class SerializerTest extends TestCase {

	public void testSerializer() throws IOException, LanguageUnavailableException {
		Model model = ModelFactory.createDefaultModel();
		Resource resource = model.createResource();
		resource.addProperty(RDFS.label, "Italiano", "it");
		resource.addProperty(RDFS.label, "Italian", "en");
		resource.addProperty(RDFS.label, "Italian");
		model.createResource().addProperty(RDFS.seeAlso, resource);
		model.createResource().addProperty(RDFS.seeAlso, resource);
		Literal l = model.createTypedLiteral(new W3CDateFormat()
                .format(new Date()), XSDDatatype.XSDdateTime);
		model.createResource().addProperty(DC.date, l);
		new Serializer().serialize(model, "",  new OutputStreamWriter(System.out), new Locale[0], new UnavailableLocalisationHandler() {

			public Literal[] getReplacement(Literal[] availableLiterals) throws LanguageUnavailableException {
				throw new LanguageUnavailableException();
				//return new Literal[0];
			}
			
		}, true);
		model.write(System.out,"RDF/XML");
	}
	
	public void testSerializeDeserialize() throws IOException {
		Model model = ModelFactory.createDefaultModel();
		model.read(getClass().getResource("test.rdf").toString());
		StringWriter writer = new StringWriter();
		new Serializer().serialize(model, "", writer);
		StringReader reader = new StringReader(writer.toString());
		Model reconstructed = ModelFactory.createDefaultModel();
		reconstructed.read(reader,"");
		assertTrue(model.isIsomorphicWith(reconstructed));
	}
}
