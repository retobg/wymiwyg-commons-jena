/*
 * Created on Mar 19, 2004
 * 
 * 
 * ====================================================================
 *
 * The WYMIWYG Software License, Version 1.0
 *
 * Copyright (c) 2002-2003 WYMIWYG  
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by WYMIWYG."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The name "WYMIWYG" or "WYMIWYG.org" must not be used to endorse 
 *    or promote products derived from this software without prior written 
 *    permission. For written permission, please contact wymiwyg@wymiwyg.org.
 *
 * 5. Products derived from this software may not be called  
 *    "WYMIWYG" nor may "WYMIWYG" appear in their names 
 *    without prior written permission of WYMIWYG.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL WYMIWYG OR ITS CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF 
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of WYMIWYG.  For more
 * information on WYMIWYG, please see http://www.WYMIWYG.org/.
 *
 * This licensed is based on The Apache Software License, Version 1.1,
 * see http://www.apache.org/.
 */
package org.wymiwyg.trixserializer;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.NoSuchElementException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wymiwyg.commons.util.MalformedURIException;
import org.wymiwyg.commons.util.URI;
import org.wymiwyg.rdfserializer.XMLEncoder;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
/**
 * <p>Serializer class.</p>
 *
 * @author reto
 * @version $Id: $Id
 */
public class Serializer {
	Log log = LogFactory.getLog(Serializer.class);
	/**
	 * <p>Constructor for Serializer.</p>
	 */
	public Serializer() {
		super();
	}
	/**
	 * <p>serialize.</p>
	 *
	 * @param model a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 * @param base a {@link java.lang.String} object.
	 * @param out a {@link java.io.Writer} object.
	 * @throws java.util.NoSuchElementException if any.
	 * @throws java.io.IOException if any.
	 */
	public void serialize(Model model, String base, Writer out)
			throws NoSuchElementException, IOException {
		PrintWriter pOut = new PrintWriter(out);
		printHeader(pOut);
		StmtIterator statements = model.listStatements();
		while (statements.hasNext()) {
			printStmt(statements.nextStatement(), base, pOut);
		}
		printFooter(pOut);
		pOut.flush();
	}
	/**
	 * @param statement
	 * @param out
	 */
	private void printStmt(Statement statement, String base, PrintWriter out)
			throws IOException {
		out.println("      <triple>");
		printResource(statement.getSubject(), base, out);
		printResource(statement.getPredicate(), base, out);
		printNode(statement.getObject(), base, out);
		out.println("      </triple>");
	}
	/**
	 * @param node
	 * @param out
	 */
	private void printNode(RDFNode node, String base, PrintWriter out)
			throws IOException {
		if (node instanceof Resource) {
			printResource((Resource) node, base, out);
		} else {
			Literal literal = (Literal) node;
			printLiteral(literal, out);
		}
	}
	/**
	 * @param literal
	 * @param out
	 */
	private void printLiteral(Literal literal, PrintWriter out) {
		String dataTypeURI = literal.getDatatypeURI();
		if (dataTypeURI == null) {
			printPlainLiteral(literal, out);
		} else {
			out.print("        <typedLiteral datatype=\"");
			out.print(dataTypeURI);
			out.print('\"');
			addLanguage(literal, out);
			out.print(">");
			out.print(XMLEncoder.encode(literal.getLexicalForm()));
			out.println("</typedLiteral>");
		}
	}
	/**
	 * @param literal
	 * @param out
	 */
	private void printPlainLiteral(Literal literal, PrintWriter out) {
		out.print("        <plainLiteral");
		addLanguage(literal, out);
		out.print(">");
		out.print(XMLEncoder.encode(literal.getLexicalForm()));
		out.println("</plainLiteral>");
	}
	/**
	 * @param literal
	 * @param out
	 */
	private void addLanguage(Literal literal, PrintWriter out) {
		String language = literal.getLanguage();
		if ((language != null) && (!language.equals(""))) {
			out.print(" xml:lang=\"" + language + "\"");
		}
	}
	/**
	 * @param resource
	 * @param out
	 */
	private void printResource(Resource resource, String base, PrintWriter out)
			throws IOException {
		if (resource.isAnon()) {
			out.print("        <id>");
			out.print(resource.getId());
			out.println("</id>");
		} else {
			out.print("        <uri>");
			if (base == null) {
				out.print(XMLEncoder.encode(resource.getURI()));
			} else {
				try {
					out.print(XMLEncoder.encode(new URI(base).relativize(
							resource.getURI(), URI.SAMEDOCUMENT | URI.ABSOLUTE
									| URI.RELATIVE | URI.PARENT)));
				} catch (MalformedURIException e) {
					log.warn("Exception relativizing URI " + resource.getURI(),
							e);
					out.print(resource.getURI());
				}
			}
			out.println("</uri>");
		}
	}
	/**
	 * @param out
	 */
	private void printHeader(PrintWriter out) {
		out.println("<?xml version=\"1.0\"?>");
		out.println("  <graphset xmlns=\"http://www.w3.org/2004/03/trix/trix-1/\">");
		out.println("    <graph>");
	}
	/**
	 * @param out
	 */
	private void printFooter(PrintWriter out) {
		out.println("    </graph>");
		out.println("  </graphset>");
	}
}
