/*
 * Created on May 4, 2004
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
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any, must
 * include the following acknowlegement: "This product includes software
 * developed by WYMIWYG." Alternately, this acknowlegement may appear in the
 * software itself, if and wherever such third-party acknowlegements normally
 * appear.
 * 
 * 4. The name "WYMIWYG" or "WYMIWYG.org" must not be used to endorse or promote
 * products derived from this software without prior written permission. For
 * written permission, please contact wymiwyg@wymiwyg.org.
 * 
 * 5. Products derived from this software may not be called "WYMIWYG" nor may
 * "WYMIWYG" appear in their names without prior written permission of WYMIWYG.
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
package org.wymiwyg.commons.jena;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author reto
 */
public class NamedResourceGenerator {
	public static final int MAX_NAME_LENGTH = 30;

	public static final int PREFFERRED_NAME_LENGTH = 12;

	/**
	 * This generates a relative URL starting with / represing a Resoure that
	 * has no properties in the model yet and that starts with
	 * /year/month/name[-ordinal].
	 * 
	 * The name is generated from the title attribute as follows: - the part of
	 * the title starting at the first dot is removed - all characters are
	 * changed to lowercase - whitesaces are replaced with dash - ...
	 * 
	 * @author reto
	 */
	public static String createURLPart(String title, Date date) {
		StringBuffer out = new StringBuffer();
		out.append(new SimpleDateFormat("yyyy/MM/dd/").format(date));
		try {
			out.append(URLEncoder.encode(title, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException();
		}
		return out.toString();
	}

	/*
	 * Calendar now = Calendar.getInstance();
	 * out.append(now.get(Calendar.YEAR)); out.append('/');
	 * out.append(now.get(Calendar.MONT)+1); out.append('/');
	 */
	private static String getName(String title) {
		StringBuffer out = new StringBuffer();
		int dotPos = title.indexOf('.');
		StringReader in;
		if (dotPos != -1) {
			in = new StringReader(title.substring(0, dotPos).toLowerCase());
		} else {
			in = new StringReader(title.toLowerCase());
		}
		StringWriter wordBuffer = null;
		try {
			for (int ch = in.read(); true; ch = in.read()) {
				if (ch == -1) {
					if ((wordBuffer != null)
							&& (out.length() + wordBuffer.toString().length() <= MAX_NAME_LENGTH)) {
						out.append(wordBuffer.toString());
					}
					break;
				}
				if (out.length() >= MAX_NAME_LENGTH) {
					break;
				}
				if (ch == ' ') {
					ch = '-';
				}
				if (ch == '-') {
					if (out.length() < PREFFERRED_NAME_LENGTH) {
						out.append((char) ch);
					} else {
						if (wordBuffer != null) {
							String wordBufferString = wordBuffer.toString();
							if (out.length() + wordBufferString.length() > MAX_NAME_LENGTH) {
								break;
							} else {
								out.append(wordBufferString);
								if (out.length() > PREFFERRED_NAME_LENGTH) {
									break;
								}
							}
						}
						wordBuffer = new StringWriter();
						wordBuffer.write(ch); //start with dash
					}
				} else {
					/*
					 * if (!Character.isLetterOrDigit((char) ch)) { continue; }
					 */
					if (!URLEncoder.encode(Character.toString((char) ch),
							"utf-8").equals(Character.toString((char) ch))) {
						continue;
					}
					if (wordBuffer != null) {
						wordBuffer.write(ch);
					} else {
						out.append((char) ch);
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return out.toString();
	}

	public static Resource createNewResource(Model model, URL urlBase,
			String title) {
		return createNewResourceFromSuffix(model, urlBase, getName(title));
	}
	public static Resource createNewResource(Model model, URL urlBase,
			String title, Date date) {
		return createNewResourceFromSuffix(model, urlBase, getName(title), date);
	}

	public static Resource createNewResourceFromSuffix(Model model,
			URL urlBase, String suffix) {
		return createNewResourceFromSuffix(model, urlBase, suffix,
				new Date());
	}

	public static Resource createNewResourceFromSuffix(Model model,
			URL urlBase, String suffix, Date date) {
		if ((suffix.length() > 0) && (suffix.charAt(0) == '/')) {
			suffix = suffix.substring(1);
		}
		StringBuffer resourceURL = new StringBuffer(urlBase.toString());
		if (!(resourceURL.charAt(resourceURL.length() - 1) == '/')) {
			resourceURL.append('/');
		}
		resourceURL.append(createURLPart(suffix, date));
		Resource resource = model.createResource(resourceURL.toString());
		int pos = 1;
		while (resource.listProperties().hasNext()) {
			resource = model.createResource(resourceURL.toString() + '-'
					+ pos++);
		}
		return resource;
	}

}