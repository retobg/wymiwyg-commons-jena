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

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * <p>XMLTag class.</p>
 *
 * @author reto
 * @version $Id: $Id
 */
public class XMLTag implements ContentProducer {

	//private String nameSpace;
	private String localName;
	private String prefix;
	private Map variables;
	private Style style;
	private static ThreadLocal prefixNumber = new ThreadLocal();
	private StringBuffer attributes = new StringBuffer();
	private boolean closed = false;
	private boolean preserveSpaces = false;
	private static ThreadLocal indentationHolder = new ThreadLocal();

	/**
	 * <p>Constructor for XMLTag.</p>
	 *
	 * @param style a {@link org.wymiwyg.rdfserializer.Style} object.
	 * @param variables a {@link java.util.Map} object.
	 */
	public XMLTag(Style style, Map variables) {
		this.variables = variables;
		this.style = style;
	}

	/** {@inheritDoc} */
	public void write(Writer out) throws IOException {
		Integer indentation = (Integer) indentationHolder.get();
		if (indentation == null) {
			indentation = new Integer(0);
			indentationHolder.set(indentation);
		}
		out.write('\n');
		int indentationInt = indentation.intValue();
		for (int i = 0; i < indentationInt; i++) {
			out.write("  ");
		}
		out.write('<');
		out.write(prefix);
		out.write(':');
		out.write(localName);
		out.write(attributes.toString());
		if (closed) {
			out.write('/');
		} else {
			indentationHolder.set(new Integer(indentationInt + 1));
		}
		out.write('>');

	}

	/**
	 * <p>addAttribute.</p>
	 *
	 * @param nameSpace a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 */
	public void addAttribute(
		String nameSpace,
		String localName,
		String value) {
		String attributePrefix = getPrefix(nameSpace);
		attributes.append(' ');
		attributes.append(attributePrefix);
		attributes.append(':');
		attributes.append(localName);
		attributes.append("=\"");
		attributes.append(XMLEncoder.encode(value));
		attributes.append("\" ");
	}

	/**
	 * <p>Setter for the field <code>localName</code>.</p>
	 *
	 * @param localName
	 *                   The localName to set.
	 */
	public void setLocalName(String localName) {
		this.localName = localName;
	}

	/**
	 * <p>setNameSpace.</p>
	 *
	 * @param nameSpace
	 *                   The nameSpace to set.
	 */
	public void setNameSpace(String nameSpace) {
		//TODO check if ns-prefix is available, or make it available
		//this.nameSpace = nameSpace;
		prefix = getPrefix(nameSpace);

	}

	/**
	 * @param nameSpace
	 * @return
	 */
	private String getPrefix(String nameSpace) {
		DocumentTag documentTag =
			(DocumentTag) variables.get(DocumentTag.class);
		String result = documentTag.getPrefixForNameSpace(nameSpace);
		if (result == null) {
			result = style.getNameSpacePrefix(nameSpace);
			if (result == null) {
				Integer prefixInteger = (Integer) prefixNumber.get();
				int prefixInt;
				if (prefixInteger != null) {
					prefixInt = prefixInteger.intValue();
				} else {
					prefixInt = 0;
				}
				result = "k." + prefixInt++;
				prefixNumber.set(new Integer(prefixInt));
			}
			try {
				documentTag.addNameSpace(result, nameSpace);
			} catch (NameSpaceAlreadyBoundException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	ContentProducer getClosingTag() {
		return new ContentProducer() {

			public void write(Writer out) throws IOException {
				Integer indentation = (Integer) indentationHolder.get();
				int indentationInt = indentation.intValue();
				indentationInt--;
				if (!preserveSpaces) {
					out.write('\n');
					for (int i = 0; i < indentationInt; i++) {
						out.write("  ");
					}
				}
				indentationHolder.set(new Integer(indentationInt));
				out.write("</");
				out.write(prefix);
				out.write(':');
				out.write(localName);
				out.write('>');

			}
		};
	}

	/**
	 * <p>addXMLAttribute.</p>
	 *
	 * @param localName a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 */
	public void addXMLAttribute(String localName, String value) {
		attributes.append(" xml:");
		attributes.append(localName);
		attributes.append("=\"");
		attributes.append(value);
		attributes.append("\" ");
	}

	/**
	 * <p>close.</p>
	 */
	public void close() {
		closed = true;
	}
	/**
	 * <p>isPreserveSpaces.</p>
	 *
	 * @return Returns the preserveSpaces.
	 */
	public boolean isPreserveSpaces() {
		return preserveSpaces;
	}

	/**
	 * <p>Setter for the field <code>preserveSpaces</code>.</p>
	 *
	 * @param preserveSpaces The preserveSpaces to set.
	 */
	public void setPreserveSpaces(boolean preserveSpaces) {
		this.preserveSpaces = preserveSpaces;
	}

}
