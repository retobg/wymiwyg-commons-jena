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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.wymiwyg.commons.vocabulary.MEDIATYPES;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

/** A class to parse files like /etc/mime.types
 * Any line that doesn't start with # containst a mime-type followed by optional extensions, 
 * of which the first is the canonical one
 * 
 * @author reto
 *
 */
//additional file with: canonical alternative* mime-types?
public class MimeTypeParser {

	/**
	 * 
	 */
	public MimeTypeParser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String filename;
		if (args.length != 1) {
			filename = MimeTypeParser.class.getResource("mime.types").getFile();
		} else {
			filename = args[0];
		}
		FileReader in = new FileReader(filename);
		BufferedReader reader = new BufferedReader(in);
		Model model = ModelFactory.createDefaultModel();
		for (String line = reader.readLine(); line != null; line = reader.readLine()) {
			if (line.length() == 0) {
				continue;
			}
			if (line.charAt(0) == '#') {
				continue;
			}
			StringTokenizer tokens = new StringTokenizer(line);
			String mimeTypeString = tokens.nextToken();
			if (tokens.hasMoreTokens()) {
				Resource typeRes = model.createResource(MEDIATYPES.Type);
				typeRes.addProperty(MEDIATYPES.canonicalMimeType, mimeTypeString);
				typeRes.addProperty(MEDIATYPES.canonicalFileExtension, tokens.nextToken());
				while (tokens.hasMoreTokens()) {
					String extension = tokens.nextToken();
					typeRes.addProperty(MEDIATYPES.fileExtension, extension);
				}
			}
		}
		model.write(System.out);
	}

}
