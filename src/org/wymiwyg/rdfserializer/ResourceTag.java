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
 * modification, are permitted provided that the following conditions are met: 1.
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. 2. Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. 3. The end-user documentation
 * included with the redistribution, if any, must include the following
 * acknowlegement: "This product includes software developed by WYMIWYG."
 * Alternately, this acknowlegement may appear in the software itself, if and
 * wherever such third-party acknowlegements normally appear. 4. The name
 * "WYMIWYG" or "WYMIWYG.org" must not be used to endorse or promote products
 * derived from this software without prior written permission. For written
 * permission, please contact wymiwyg@wymiwyg.org. 5. Products derived from
 * this software may not be called "WYMIWYG" nor may "WYMIWYG" appear in their
 * names without prior written permission of WYMIWYG.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL WYMIWYG
 * OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 * 
 * This software consists of voluntary contributions made by many individuals
 * on behalf of WYMIWYG. For more information on WYMIWYG, please see
 * http://www.WYMIWYG.org/.
 * 
 * This licensed is based on The Apache Software License, Version 1.1, see
 * http://www.apache.org/.
 */

package org.wymiwyg.rdfserializer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wymiwyg.commons.util.MalformedURIException;
import org.wymiwyg.commons.util.URI;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * @author reto
 */
public class ResourceTag extends XMLTag {

	private static Log log = LogFactory.getLog(ResourceTag.class);
	private String anonID = null;
	private static int anonCounter = 0;
	/**
	 *  
	 */
	public ResourceTag(
		Resource resource,
		Style style,
		Set usedStatements,
		Map variables,
		String urlBase) {
		super(style, variables);
		variables.put(resource, this);
		StmtIterator typeIter = resource.listProperties(RDF.type);
		Set typeSet = new HashSet();
		while (typeIter.hasNext()) {
			typeSet.add(typeIter.nextStatement().getResource());
		}
		Resource mainType = null;
		TypeDescriptor[] typeDescriptors = style.getTypeDescriptors();
		for (int i = 0; i < typeDescriptors.length; i++) {
			Resource current = typeDescriptors[i].getType();
			if (typeSet.contains(current)) {
				mainType = current;
				break;
			}
		}
		if (mainType == null) {
			if (typeSet.size() > 1) {
				log.warn(
					"None of the types of resource "
						+ resource
						+ " has a type descriptor");
				/*throw new RuntimeException(
					"None of the types of resource "
						+ resource+ " has a type descriptor");*/
			}
			if (typeSet.size() == 1) {
				mainType = (Resource) typeSet.iterator().next();
			}
		}
		if (mainType != null) {
			setNameSpace(mainType.getNameSpace());
			setLocalName(mainType.getLocalName());
			usedStatements.add(
				resource.getModel().createStatement(
					resource,
					RDF.type,
					mainType));
		} else {
			setNameSpace(RDF.getURI());
			setLocalName("Description");
		}
		if (!resource.isAnon()) {
			String uri = resource.getURI();
			if ((urlBase != null) && (!urlBase.equals(""))) {
				try {
					uri =
						new URI(urlBase).relativize(
							uri,
							URI.SAMEDOCUMENT
								| URI.ABSOLUTE
								| URI.RELATIVE
								| URI.PARENT);
				} catch (MalformedURIException e) {
					throw new RuntimeException(e);
				}
			}
			addAttribute(RDF.getURI(), "about", uri);
		}
	}

	/**
	 * @return
	 */
	public String getAnonID() {
		synchronized (ResourceTag.class) {
			if (anonID == null) {
				anonID = "A" + (++anonCounter);
			}
			return anonID;
		}
	}

}
