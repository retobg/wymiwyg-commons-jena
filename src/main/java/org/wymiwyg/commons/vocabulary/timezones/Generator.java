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
package org.wymiwyg.commons.vocabulary.timezones;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.wymiwyg.commons.jena.JenaUtil;



import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * <p>Generator class.</p>
 *
 * @author reto
 * @version $Id: $Id
 */
public class Generator {

	/**
	 * <p>Constructor for Generator.</p>
	 */
	public Generator() {
		super();
	}
	
	/**
	 * <p>main.</p>
	 *
	 * @param args an array of {@link java.lang.String} objects.
	 * @throws java.net.URISyntaxException if any.
	 * @throws java.io.IOException if any.
	 */
	public static void main(String[] args) throws URISyntaxException, IOException {
		Model tzData = ModelFactory.createDefaultModel();
		Model tzList = ModelFactory.createDefaultModel();
		tzList.read("http://www.w3.org/2002/12/cal/tzd/ifps.n3", "", "N3");
		ResIterator zoneIter = tzList.listSubjects(); 
		while (zoneIter.hasNext()) {
			Resource zone =  zoneIter.nextResource();
			Model currentZoneModel = ModelFactory.createDefaultModel();
			try {
				currentZoneModel.read(zone.getURI().toString());
			} catch (Exception e) {
				System.err.println(e.toString());
				continue;
			}
			zone = (Resource) zone.inModel(currentZoneModel);
			tzData.add(JenaUtil.getExpandedResource(zone, 6));
		}
		zoneIter.close();
		/*URL thisClass = ClassLoader.getSystemResource("org/wymiwyg/commons/vocabulary/timezones/Generator.class");
		File thisClassFile = new File(new URI(thisClass.toString()));
		File outFile = new File(thisClassFile.getParent(), "time-zone-data.rdf");*/
		File outFile = new File("time-zone-data.rdf");
		FileOutputStream outFileOut = new FileOutputStream(outFile);
		tzData.write(outFileOut);
		outFileOut.close();
	}

}
