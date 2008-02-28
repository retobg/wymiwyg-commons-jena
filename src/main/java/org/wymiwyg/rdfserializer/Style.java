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

import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * @author reto
 */
public class Style {

	Map prefixMap = new HashMap();
	/**
	 *  
	 */
	public Style() {
		super();
		// TODO Auto-generated constructor stub
	}
	Resource styleRes;
	/**
	 * @param styleRes
	 */
	public Style(Resource styleRes) {
		if (!styleRes.hasProperty(RDF.type, RDFSERIALIZER.Style)) {
			throw new RuntimeException(
				"the styleRes "
					+ styleRes
					+ " is not of type "
					+ RDFSERIALIZER.Style);
		}
		this.styleRes = styleRes;
		loadPrefixMap();
	}

	/**
	 *  
	 */
	private void loadPrefixMap() {
		StmtIterator propIter =
			styleRes.listProperties(RDFSERIALIZER.nameSpacePrefix);
		while (propIter.hasNext()) {
			Resource nsPrefix = propIter.nextStatement().getResource();
			String prefix =
				nsPrefix.getProperty(RDFSERIALIZER.prefix).getString();
			String url = nsPrefix.getProperty(RDFSERIALIZER.url).getString();
			prefixMap.put(url, prefix);
		}
	}

	public TypeDescriptor[] getTypeDescriptors() {
		Statement typeDescriptorStmt =
		styleRes.getProperty(RDFSERIALIZER.typeDescriptors);
		if (typeDescriptorStmt == null) {
			return new TypeDescriptor[0];
		}
		Seq descriptors = typeDescriptorStmt.getSeq();
		TypeDescriptor[] result =
		new TypeDescriptor[descriptors.size()];
		for (int i = 0; i < result.length; i++) {
			Resource current = descriptors.getResource(i + 1);
			if (!current
					.hasProperty(RDF.type, RDFSERIALIZER.TypeDescriptor)) {
				throw new StyleException(
						"Resource "
						+ current
						+ " is not of type TypeDescriptor");
			}
			result[i] = new TypeDescriptorImpl(this, current);
		}
		return result;
	}

	public PropertyDescriptor[] getPropertyDescriptors() {
		Statement propertyDescriptorStmt =
			styleRes.getProperty(RDFSERIALIZER.propertyDescriptors);
		if (propertyDescriptorStmt == null) {
			return new PropertyDescriptor[0];
		}
		Seq descriptors = propertyDescriptorStmt.getSeq();
		PropertyDescriptor[] result =
			new PropertyDescriptor[descriptors.size()];
		for (int i = 0; i < result.length; i++) {
			Resource current = descriptors.getResource(i + 1);
			if (!current
				.hasProperty(RDF.type, RDFSERIALIZER.PropertyDescriptor)) {
				throw new StyleException(
					"Resource "
						+ current
						+ " is not of type PropertyDescriptor");
			}
			result[i] = new PropertyDescriptorImpl(this, current);
		}
		return result;
	}

	public String getNameSpacePrefix(String nameSpace) {
		return (String) prefixMap.get(nameSpace);
	}

	/**
	 * @return
	 */
	public int getAnonymousDeepness() {
		return styleRes.getProperty(RDFSERIALIZER.anonymousDeepness).getInt();
	}

	/**
	 * @return
	 */
	public int getNonAnonymousDeepness() {
		return styleRes
			.getProperty(RDFSERIALIZER.nonAnonymousDeepness)
			.getInt();
	}

}
