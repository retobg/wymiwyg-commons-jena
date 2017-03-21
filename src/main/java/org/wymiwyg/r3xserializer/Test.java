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
import java.io.StringReader;
import java.io.StringWriter;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.vocabulary.DC;

/**
 * <p>Test class.</p>
 *
 * @author reto
 * @date Jun 1, 2004
 * @version $Id: $Id
 */
public class Test {
	/**
	 * <p>main.</p>
	 *
	 * @param args an array of {@link java.lang.String} objects.
	 * @throws java.io.IOException if any.
	 */
	public static void main(String[] args) throws IOException {
		Model model = ModelFactory.createDefaultModel();
		model.read("http://www.wasab.dk/morten/2004/03/label.rdf");
		Resource plural = model
				.createResource("http://purl.org/net/vocab/2004/03/label#plural");
		Seq seq = model.createSeq();
		seq.add('a');
		seq.add("Hallo");
		seq.add(0.8798);
		plural.addProperty(DC.contributor, seq);
		StringWriter writer = new StringWriter();
		new Serializer().serialize(model, null, writer);
		System.out.println(writer.toString());
		Model model2 = ModelFactory.createDefaultModel();
		model2.read(new StringReader(writer.toString()), "");
		System.out.println(model.isIsomorphicWith(model2));
	}

}
