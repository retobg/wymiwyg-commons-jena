/*
 * Created on Nov 10, 2003
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

package org.wymiwyg.commons.tsmodel;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.query.QueryHandler;
import com.hp.hpl.jena.rdf.model.Alt;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelChangedListener;
import com.hp.hpl.jena.rdf.model.ModelLock;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.NsIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.RSIterator;
import com.hp.hpl.jena.rdf.model.ReifiedStatement;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceF;
import com.hp.hpl.jena.rdf.model.Selector;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import com.hp.hpl.jena.shared.Command;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.shared.ReificationStyle;

/** We extend ModelCom just because there probably is a bug in 
 * Query using non-ModelCom models.
 * @author reto
 */
public class ThreadSafeModel extends ModelCom implements Model {

	Model wrapped;

	/**
	 * @param wrapped
	 */
	public ThreadSafeModel(Model wrapped) {
		super(wrapped.getGraph());
		this.wrapped = wrapped;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#abort()
	 */
	public Model abort() {
		wrapped.abort();
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#add(java.util.List)
	 */
	public synchronized Model add(List statements) {
		wrapped.add(statements);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#add(com.hp.hpl.jena.rdf.model.Model)
	 */
	public synchronized Model add(Model m) {
		wrapped.add(m);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#add(com.hp.hpl.jena.rdf.model.Model, boolean)
	 */
	public synchronized Model add(Model m, boolean suppressReifications) {
		wrapped.add(m, suppressReifications);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, boolean)
	 */
	public synchronized Model add(Resource s, Property p, boolean o) {
		wrapped.add(s, p, o);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, char)
	 */
	public synchronized Model add(Resource s, Property p, char o) {
		wrapped.add(s, p, o);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, double)
	 */
	public synchronized Model add(Resource s, Property p, double o) {
		wrapped.add(s, p, o);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, float)
	 */
	public synchronized Model add(Resource s, Property p, float o) {
		wrapped.add(s, p, o);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, long)
	 */
	public synchronized Model add(Resource s, Property p, long o) {
		wrapped.add(s, p, o);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.Object)
	 */
	public synchronized Model add(Resource s, Property p, Object o) {
		wrapped.add(s, p, o);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public synchronized Model add(Resource s, Property p, RDFNode o) {
		wrapped.add(s, p, o);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String)
	 */
	public synchronized Model add(Resource s, Property p, String o) {
		wrapped.add(s, p, o);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String, boolean)
	 */
	public synchronized Model add(
		Resource s,
		Property p,
		String o,
		boolean wellFormed) {
		wrapped.add(s, p, o, wellFormed);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String, java.lang.String)
	 */
	public synchronized Model add(Resource s, Property p, String o, String l) {
		wrapped.add(s, p, o, l);
		return this;
	}

	/**
	 * @deprecated
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String, java.lang.String, boolean)
	 */
	public synchronized Model add(
		Resource s,
		Property p,
		String o,
		String l,
		boolean wellFormed) {
		wrapped.add(s, p, o, l, wellFormed);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#add(com.hp.hpl.jena.rdf.model.Statement)
	 */
	public synchronized Model add(Statement s) {
		wrapped.add(s);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#add(com.hp.hpl.jena.rdf.model.Statement[])
	 */
	public synchronized Model add(Statement[] statements) {
		wrapped.add(statements);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#add(com.hp.hpl.jena.rdf.model.StmtIterator)
	 */
	public synchronized Model add(StmtIterator iter) {
		wrapped.add(iter);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#begin()
	 */
	public synchronized Model begin() {
		wrapped.begin();
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#close()
	 */
	public synchronized void close() {
		wrapped.close();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#commit()
	 */
	public Model commit() {
		wrapped.commit();
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#contains(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property)
	 */
	public synchronized boolean contains(Resource s, Property p) {
		return wrapped.contains(s, p);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#contains(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, boolean)
	 */
	public synchronized boolean contains(Resource s, Property p, boolean o) {
		return wrapped.contains(s, p, o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#contains(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, char)
	 */
	public synchronized boolean contains(Resource s, Property p, char o) {
		return wrapped.contains(s, p, o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#contains(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, double)
	 */
	public synchronized boolean contains(Resource s, Property p, double o) {
		return wrapped.contains(s, p, o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#contains(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, float)
	 */
	public synchronized boolean contains(Resource s, Property p, float o) {
		return wrapped.contains(s, p, o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#contains(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, long)
	 */
	public synchronized boolean contains(Resource s, Property p, long o) {
		return wrapped.contains(s, p, o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#contains(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.Object)
	 */
	public synchronized boolean contains(Resource s, Property p, Object o) {
		return wrapped.contains(s, p, o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#contains(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public synchronized boolean contains(Resource s, Property p, RDFNode o) {
		return wrapped.contains(s, p, o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#contains(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String)
	 */
	public synchronized boolean contains(Resource s, Property p, String o) {
		return wrapped.contains(s, p, o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#contains(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String, java.lang.String)
	 */
	public synchronized boolean contains(
		Resource s,
		Property p,
		String o,
		String l) {
		return wrapped.contains(s, p, o, l);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#contains(com.hp.hpl.jena.rdf.model.Statement)
	 */
	public synchronized boolean contains(Statement s) {
		return wrapped.contains(s);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#containsAll(com.hp.hpl.jena.rdf.model.Model)
	 */
	public synchronized boolean containsAll(Model model) {
		return wrapped.containsAll(model);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#containsAll(com.hp.hpl.jena.rdf.model.StmtIterator)
	 */
	public synchronized boolean containsAll(StmtIterator iter) {
		return wrapped.containsAll(iter);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#containsAny(com.hp.hpl.jena.rdf.model.Model)
	 */
	public synchronized boolean containsAny(Model model) {
		return wrapped.containsAny(model);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#containsAny(com.hp.hpl.jena.rdf.model.StmtIterator)
	 */
	public synchronized boolean containsAny(StmtIterator iter) {
		return wrapped.containsAny(iter);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#containsResource(com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public synchronized boolean containsResource(RDFNode r) {
		return wrapped.containsResource(r);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createAlt()
	 */
	public Alt createAlt() {
		return new ThreadSafeAlt(this, wrapped.createAlt());
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createAlt(java.lang.String)
	 */
	public Alt createAlt(String uri) {
		return new ThreadSafeAlt(this, wrapped.createAlt(uri));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createBag()
	 */
	public Bag createBag() {
		return new ThreadSafeBag(this, wrapped.createBag());
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createBag(java.lang.String)
	 */
	public Bag createBag(String uri) {
		return new ThreadSafeBag(this, wrapped.createBag(uri));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createList()
	 */
	public RDFList createList() {
		return wrapped.createList();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createList(java.util.Iterator)
	 */
	public synchronized RDFList createList(Iterator members) {
		return wrapped.createList(members);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createList(com.hp.hpl.jena.rdf.model.RDFNode[])
	 */
	public synchronized RDFList createList(RDFNode[] members) {
		return wrapped.createList(members);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createLiteral(boolean)
	 */
	public Literal createLiteral(boolean v) {
		return wrapped.createLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createLiteral(char)
	 */
	public Literal createLiteral(char v) {
		return wrapped.createLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createLiteral(double)
	 */
	public Literal createLiteral(double v) {
		return wrapped.createLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createLiteral(float)
	 */
	public Literal createLiteral(float v) {
		return wrapped.createLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createLiteral(long)
	 */
	public Literal createLiteral(long v) {
		return wrapped.createLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createLiteral(java.lang.Object)
	 */
	public Literal createLiteral(Object v) {
		return wrapped.createLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createLiteral(java.lang.String)
	 */
	public Literal createLiteral(String v) {
		return wrapped.createLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createLiteral(java.lang.String, boolean)
	 */
	public Literal createLiteral(String v, boolean wellFormed) {
		return wrapped.createLiteral(v, wellFormed);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createLiteral(java.lang.String, java.lang.String)
	 */
	public Literal createLiteral(String v, String language) {
		return wrapped.createLiteral(v, language);
	}

	/**
	 * @deprecated
	 * @see com.hp.hpl.jena.rdf.model.Model#createLiteral(java.lang.String, java.lang.String, boolean)
	 */
	public Literal createLiteral(
		String v,
		String language,
		boolean wellFormed) {
		return wrapped.createLiteral(v, language, wellFormed);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createProperty(java.lang.String)
	 */
	public Property createProperty(String uri) {
		return new ThreadSafeProperty(this, wrapped.createProperty(uri));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createProperty(java.lang.String, java.lang.String)
	 */
	public Property createProperty(
		String nameSpace,
		String localName) {
		return new ThreadSafeProperty(
			this,
			wrapped.createProperty(nameSpace, localName));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createReifiedStatement(com.hp.hpl.jena.rdf.model.Statement)
	 */
	public ReifiedStatement createReifiedStatement(Statement s) {
		return new ThreadSafeReifiedStatement(
			this,
			wrapped.createReifiedStatement(s));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createReifiedStatement(java.lang.String, com.hp.hpl.jena.rdf.model.Statement)
	 */
	public ReifiedStatement createReifiedStatement(
		String uri,
		Statement s) {
		return new ThreadSafeReifiedStatement(
			this,
			wrapped.createReifiedStatement(uri, s));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createResource()
	 */
	public Resource createResource() {
		return new ThreadSafeResource(this, wrapped.createResource());
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createResource(com.hp.hpl.jena.rdf.model.AnonId)
	 */
	public Resource createResource(AnonId id) {
		return new ThreadSafeResource(this, wrapped.createResource(id));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createResource(com.hp.hpl.jena.rdf.model.Resource)
	 */
	public Resource createResource(Resource type) {
		return new ThreadSafeResource(this, wrapped.createResource(type));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createResource(com.hp.hpl.jena.rdf.model.ResourceF)
	 */
	public Resource createResource(ResourceF f) {
		return new ThreadSafeResource(this, wrapped.createResource(f));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createResource(java.lang.String)
	 */
	public Resource createResource(String uri) {
		return new ThreadSafeResource(this, wrapped.createResource(uri));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createResource(java.lang.String, com.hp.hpl.jena.rdf.model.Resource)
	 */
	public Resource createResource(String uri, Resource type) {
		return new ThreadSafeResource(this, wrapped.createResource(uri, type));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createResource(java.lang.String, com.hp.hpl.jena.rdf.model.ResourceF)
	 */
	public  Resource createResource(String uri, ResourceF f) {
		return new ThreadSafeResource(this, wrapped.createResource(uri, f));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createSeq()
	 */
	public  Seq createSeq() {
		return new ThreadSafeSeq(this, wrapped.createSeq());
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createSeq(java.lang.String)
	 */
	public synchronized Seq createSeq(String uri) {
		return new ThreadSafeSeq(this, wrapped.createSeq(uri));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createStatement(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, boolean)
	 */
	public Statement createStatement(
		Resource s,
		Property p,
		boolean o) {
		return new ThreadSafeStatement(this, wrapped.createStatement(s, p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createStatement(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, char)
	 */
	public Statement createStatement(
		Resource s,
		Property p,
		char o) {
		return new ThreadSafeStatement(this, wrapped.createStatement(s, p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createStatement(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, double)
	 */
	public Statement createStatement(
		Resource s,
		Property p,
		double o) {
		return new ThreadSafeStatement(this, wrapped.createStatement(s, p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createStatement(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, float)
	 */
	public Statement createStatement(
		Resource s,
		Property p,
		float o) {
		return new ThreadSafeStatement(this, wrapped.createStatement(s, p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createStatement(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, long)
	 */
	public  Statement createStatement(
		Resource s,
		Property p,
		long o) {
		return new ThreadSafeStatement(this, wrapped.createStatement(s, p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createStatement(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.Object)
	 */
	public  Statement createStatement(
		Resource s,
		Property p,
		Object o) {
		return new ThreadSafeStatement(this, wrapped.createStatement(s, p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createStatement(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public  Statement createStatement(
		Resource s,
		Property p,
		RDFNode o) {
		return new ThreadSafeStatement(this, wrapped.createStatement(s, p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createStatement(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String)
	 */
	public  Statement createStatement(
		Resource s,
		Property p,
		String o) {
		return new ThreadSafeStatement(this, wrapped.createStatement(s, p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createStatement(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String, boolean)
	 */
	public  Statement createStatement(
		Resource s,
		Property p,
		String o,
		boolean wellFormed) {
		return new ThreadSafeStatement(
			this,
			wrapped.createStatement(s, p, o, wellFormed));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createStatement(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String, java.lang.String)
	 */
	public  Statement createStatement(
		Resource s,
		Property p,
		String o,
		String l) {
		return new ThreadSafeStatement(
			this,
			wrapped.createStatement(s, p, o, l));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createStatement(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String, java.lang.String, boolean)
	 */
	public  Statement createStatement(
		Resource s,
		Property p,
		String o,
		String l,
		boolean wellFormed) {
		return new ThreadSafeStatement(
			this,
			wrapped.createStatement(s, p, o, l, wellFormed));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(boolean)
	 */
	public  Literal createTypedLiteral(boolean v) {
		return wrapped.createTypedLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(char)
	 */
	public  Literal createTypedLiteral(char v) {
		return wrapped.createTypedLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(double)
	 */
	public  Literal createTypedLiteral(double v) {
		return wrapped.createTypedLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(float)
	 */
	public  Literal createTypedLiteral(float v) {
		return wrapped.createTypedLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(int)
	 */
	public  Literal createTypedLiteral(int v) {
		return wrapped.createTypedLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(long)
	 */
	public  Literal createTypedLiteral(long v) {
		return wrapped.createTypedLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(java.lang.Object)
	 */
	public  Literal createTypedLiteral(Object value) {
		return wrapped.createTypedLiteral(value);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createTypedLiteral(java.lang.Object, com.hp.hpl.jena.datatypes.RDFDatatype)
	 */
	public  Literal createTypedLiteral(
		Object value,
		RDFDatatype dtype) {
		return wrapped.createTypedLiteral(value, dtype);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(java.lang.Object, java.lang.String)
	 */
	public Literal createTypedLiteral(
		Object value,
		String typeURI) {
		return wrapped.createTypedLiteral(value, typeURI);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(java.lang.String)
	 */
	public Literal createTypedLiteral(String v) {
		return wrapped.createTypedLiteral(v);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#createTypedLiteral(java.lang.String, com.hp.hpl.jena.datatypes.RDFDatatype)
	 */
	public Literal createTypedLiteral(
		String lex,
		RDFDatatype dtype) {
		return wrapped.createTypedLiteral(lex, dtype);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(java.lang.String, java.lang.String)
	 */
	public Literal createTypedLiteral(
		String lex,
		String typeURI) {
		return wrapped.createTypedLiteral(lex, typeURI);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#difference(com.hp.hpl.jena.rdf.model.Model)
	 */
	public synchronized Model difference(Model model) {
		return new ThreadSafeModel(wrapped.difference(model));
	}

	/**
	 * see com.hp.hpl.jena.rdf.model.ModelLock#enterCriticalSection(boolean)
	 */
	public void enterCriticalSection(boolean readLockRequested) {
		//wrapped.enterCriticalSection(readLockRequested);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#executeInTransaction(com.hp.hpl.jena.shared.Command)
	 */
	public synchronized Object executeInTransaction(Command cmd) {
		return wrapped.executeInTransaction(cmd);
	}

	/**
	 * @see com.hp.hpl.jena.shared.PrefixMapping#expandPrefix(java.lang.String)
	 */
	public synchronized String expandPrefix(String prefixed) {
		return wrapped.expandPrefix(prefixed);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#getAlt(com.hp.hpl.jena.rdf.model.Resource)
	 */
	public Alt getAlt(Resource r) {
		return new ThreadSafeAlt(this, wrapped.getAlt(r));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#getAlt(java.lang.String)
	 */
	public Alt getAlt(String uri) {
		return new ThreadSafeAlt(this, wrapped.getAlt(uri));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#getAnyReifiedStatement(com.hp.hpl.jena.rdf.model.Statement)
	 */
	public synchronized Resource getAnyReifiedStatement(Statement s) {
		Resource wrappedResult = wrapped.getAnyReifiedStatement(s);
		if (wrappedResult instanceof ReifiedStatement) {
			return new ThreadSafeReifiedStatement(
				this,
				(ReifiedStatement) wrappedResult);
		} else {
			return new ThreadSafeResource(this, wrappedResult);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#getBag(com.hp.hpl.jena.rdf.model.Resource)
	 */
	public Bag getBag(Resource r) {
		return new ThreadSafeBag(this, wrapped.getBag(r));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#getBag(java.lang.String)
	 */
	public Bag getBag(String uri) {
		return new ThreadSafeBag(this, wrapped.getBag(uri));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#getGraph()
	 */
	public synchronized Graph getGraph() {
		if (wrapped == null) {
			return super.getGraph();
		}
		return wrapped.getGraph();
	}

	/**
	 * @deprecated
	 * @see com.hp.hpl.jena.rdf.model.Model#getModelLock()
	 */
	public ModelLock getModelLock() {
		return wrapped.getModelLock();
	}

	/**
	 * @see com.hp.hpl.jena.shared.PrefixMapping#getNsPrefixMap()
	 */
	public Map getNsPrefixMap() {
		//this is called by ModelCom constructor
		if (wrapped == null) {
			return super.getNsPrefixMap();
		}
		// this should return a copy, so no sync needed
		return wrapped.getNsPrefixMap();
	}

	/**
	 * @see com.hp.hpl.jena.shared.PrefixMapping#getNsPrefixURI(java.lang.String)
	 */
	public synchronized String getNsPrefixURI(String prefix) {
		return wrapped.getNsPrefixURI(prefix);
	}

	/**
	 * @see com.hp.hpl.jena.shared.PrefixMapping#getNsURIPrefix(java.lang.String)
	 */
	public synchronized String getNsURIPrefix(String uri) {
		return wrapped.getNsURIPrefix(uri);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#getProperty(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property)
	 */
	public synchronized Statement getProperty(Resource s, Property p) {
		return new ThreadSafeStatement(this, wrapped.getProperty(s, p));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#getProperty(java.lang.String)
	 */
	public synchronized Property getProperty(String uri) {
		return new ThreadSafeProperty(this, wrapped.getProperty(uri));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#getProperty(java.lang.String, java.lang.String)
	 */
	public synchronized Property getProperty(
		String nameSpace,
		String localName) {
		return new ThreadSafeProperty(
			this,
			wrapped.getProperty(nameSpace, localName));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#getRDFNode(com.hp.hpl.jena.graph.Node)
	 */
	public synchronized RDFNode getRDFNode(Node n) {
		RDFNode wrappedResult = wrapped.getRDFNode(n);
		if (wrappedResult instanceof Resource) {
			return new ThreadSafeResource(this, (Resource) wrappedResult);
		} else {
			return wrappedResult;
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.RDFReaderF#getReader()
	 */
	public synchronized RDFReader getReader() {
		return wrapped.getReader();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.RDFReaderF#getReader(java.lang.String)
	 */
	public synchronized RDFReader getReader(String lang) {
		return wrapped.getReader(lang);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#getRequiredProperty(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property)
	 */
	public synchronized Statement getRequiredProperty(Resource s, Property p) {
		return new ThreadSafeStatement(this, wrapped.getRequiredProperty(s, p));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#getResource(java.lang.String)
	 */
	public Resource getResource(String uri) {
		return new ThreadSafeResource(this, wrapped.getResource(uri));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#getResource(java.lang.String, com.hp.hpl.jena.rdf.model.ResourceF)
	 */
	public Resource getResource(String uri, ResourceF f) {
		return new ThreadSafeResource(this, wrapped.getResource(uri, f));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#getSeq(com.hp.hpl.jena.rdf.model.Resource)
	 */
	public Seq getSeq(Resource r) {
		return new ThreadSafeSeq(this, wrapped.getSeq(r));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#getSeq(java.lang.String)
	 */
	public Seq getSeq(String uri) {
		return new ThreadSafeSeq(this, getSeq(uri));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.RDFWriterF#getWriter()
	 */
	public RDFWriter getWriter() {
		return wrapped.getWriter();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.RDFWriterF#getWriter(java.lang.String)
	 */
	public RDFWriter getWriter(String lang) {
		return wrapped.getWriter(lang);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#independent()
	 */
	public  boolean independent() {
		return wrapped.independent();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#intersection(com.hp.hpl.jena.rdf.model.Model)
	 */
	public synchronized Model intersection(Model model) {
		return new ThreadSafeModel(wrapped.intersection(model));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#isEmpty()
	 */
	public synchronized boolean isEmpty() {
		return wrapped.isEmpty();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#isIsomorphicWith(com.hp.hpl.jena.rdf.model.Model)
	 */
	public synchronized boolean isIsomorphicWith(Model g) {
		return wrapped.isIsomorphicWith(g);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#isReified(com.hp.hpl.jena.rdf.model.Statement)
	 */
	public synchronized boolean isReified(Statement s) {
		return wrapped.isReified(s);
	}

	/**
	 * see com.hp.hpl.jena.rdf.model.ModelLock#leaveCriticalSection()
	 */
	public void leaveCriticalSection() {
		//wrapped.leaveCriticalSection();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#listNameSpaces()
	 */
	public synchronized NsIterator listNameSpaces() {
		// TODO Auto-generated method stub
		return wrapped.listNameSpaces();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#listObjects()
	 */
	public synchronized NodeIterator listObjects() {
		return new CachingNodeIterator(wrapped.listObjects());
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#listObjectsOfProperty(com.hp.hpl.jena.rdf.model.Property)
	 */
	public synchronized NodeIterator listObjectsOfProperty(Property p) {
		return new CachingNodeIterator(wrapped.listObjectsOfProperty(p));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#listObjectsOfProperty(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property)
	 */
	public synchronized NodeIterator listObjectsOfProperty(
		Resource s,
		Property p) {
		return new CachingNodeIterator(wrapped.listObjectsOfProperty(s, p));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#listReifiedStatements()
	 */
	public synchronized RSIterator listReifiedStatements() {
		return new CachingRSIterator(this, wrapped.listReifiedStatements());
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#listReifiedStatements(com.hp.hpl.jena.rdf.model.Statement)
	 */
	public synchronized RSIterator listReifiedStatements(Statement st) {
		return new CachingRSIterator(this, wrapped.listReifiedStatements(st));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#listStatements()
	 */
	public synchronized StmtIterator listStatements() {
		return new CachingStmtIterator(this, wrapped.listStatements());
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listStatements(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, boolean)
	 */
	public synchronized StmtIterator listStatements(
		Resource subject,
		Property predicate,
		boolean object) {
		return new CachingStmtIterator(
			this,
			wrapped.listStatements(subject, predicate, object));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listStatements(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, char)
	 */
	public synchronized StmtIterator listStatements(
		Resource subject,
		Property predicate,
		char object) {
		return new CachingStmtIterator(
			this,
			wrapped.listStatements(subject, predicate, object));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listStatements(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, double)
	 */
	public synchronized StmtIterator listStatements(
		Resource subject,
		Property predicate,
		double object) {
		return new CachingStmtIterator(
			this,
			wrapped.listStatements(subject, predicate, object));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listStatements(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, float)
	 */
	public synchronized StmtIterator listStatements(
		Resource subject,
		Property predicate,
		float object) {
		return new CachingStmtIterator(
			this,
			wrapped.listStatements(subject, predicate, object));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listStatements(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, long)
	 */
	public synchronized StmtIterator listStatements(
		Resource subject,
		Property predicate,
		long object) {
		return new CachingStmtIterator(
			this,
			wrapped.listStatements(subject, predicate, object));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#listStatements(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public synchronized StmtIterator listStatements(
		Resource s,
		Property p,
		RDFNode o) {
		return new CachingStmtIterator(this, wrapped.listStatements(s, p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listStatements(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String)
	 */
	public synchronized StmtIterator listStatements(
		Resource subject,
		Property predicate,
		String object) {
		return new CachingStmtIterator(
			this,
			wrapped.listStatements(subject, predicate, object));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listStatements(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String, java.lang.String)
	 */
	public synchronized StmtIterator listStatements(
		Resource subject,
		Property predicate,
		String object,
		String lang) {
		return new CachingStmtIterator(
			this,
			wrapped.listStatements(subject, predicate, object, lang));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#listStatements(com.hp.hpl.jena.rdf.model.Selector)
	 */
	public synchronized StmtIterator listStatements(Selector s) {
		return new CachingStmtIterator(this, wrapped.listStatements(s));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#listSubjects()
	 */
	public synchronized ResIterator listSubjects() {
		return new CachingResIterator(this, wrapped.listSubjects());
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#listSubjectsWithProperty(com.hp.hpl.jena.rdf.model.Property)
	 */
	public synchronized ResIterator listSubjectsWithProperty(Property p) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listSubjectsWithProperty(com.hp.hpl.jena.rdf.model.Property, boolean)
	 */
	public synchronized ResIterator listSubjectsWithProperty(
		Property p,
		boolean o) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listSubjectsWithProperty(com.hp.hpl.jena.rdf.model.Property, char)
	 */
	public synchronized ResIterator listSubjectsWithProperty(
		Property p,
		char o) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listSubjectsWithProperty(com.hp.hpl.jena.rdf.model.Property, double)
	 */
	public synchronized ResIterator listSubjectsWithProperty(
		Property p,
		double o) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listSubjectsWithProperty(com.hp.hpl.jena.rdf.model.Property, float)
	 */
	public synchronized ResIterator listSubjectsWithProperty(
		Property p,
		float o) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listSubjectsWithProperty(com.hp.hpl.jena.rdf.model.Property, long)
	 */
	public synchronized ResIterator listSubjectsWithProperty(
		Property p,
		long o) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listSubjectsWithProperty(com.hp.hpl.jena.rdf.model.Property, java.lang.Object)
	 */
	public synchronized ResIterator listSubjectsWithProperty(
		Property p,
		Object o) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#listSubjectsWithProperty(com.hp.hpl.jena.rdf.model.Property, com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public synchronized ResIterator listSubjectsWithProperty(
		Property p,
		RDFNode o) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listSubjectsWithProperty(com.hp.hpl.jena.rdf.model.Property, java.lang.String)
	 */
	public synchronized ResIterator listSubjectsWithProperty(
		Property p,
		String o) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p, o));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#listSubjectsWithProperty(com.hp.hpl.jena.rdf.model.Property, java.lang.String, java.lang.String)
	 */
	public synchronized ResIterator listSubjectsWithProperty(
		Property p,
		String o,
		String l) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p, o, l));
	}

	/**
	 * @see com.hp.hpl.jena.shared.PrefixMapping#lock()
	 */
	public synchronized PrefixMapping lock() {
		// TODO Auto-generated method stub
		return wrapped.lock();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#query(com.hp.hpl.jena.rdf.model.Selector)
	 */
	public synchronized Model query(Selector s) {
		return new ThreadSafeModel(wrapped.query(s));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#queryHandler()
	 */
	public synchronized QueryHandler queryHandler() {
		// TODO Auto-generated method stub
		return wrapped.queryHandler();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#read(java.io.InputStream, java.lang.String)
	 */
	public synchronized Model read(InputStream in, String base) {
		// TODO Auto-generated method stub
		return wrapped.read(in, base);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#read(java.io.InputStream, java.lang.String, java.lang.String)
	 */
	public synchronized Model read(InputStream in, String base, String lang) {
		// TODO Auto-generated method stub
		return wrapped.read(in, base, lang);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#read(java.io.Reader, java.lang.String)
	 */
	public synchronized Model read(Reader reader, String base) {
		// TODO Auto-generated method stub
		return wrapped.read(reader, base);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#read(java.io.Reader, java.lang.String, java.lang.String)
	 */
	public synchronized Model read(Reader reader, String base, String lang) {
		// TODO Auto-generated method stub
		return wrapped.read(reader, base, lang);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#read(java.lang.String)
	 */
	public synchronized Model read(String url) {
		// TODO Auto-generated method stub
		return wrapped.read(url);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#read(java.lang.String, java.lang.String)
	 */
	public synchronized Model read(String url, String lang) {
		// TODO Auto-generated method stub
		return wrapped.read(url, lang);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#register(com.hp.hpl.jena.rdf.model.ModelChangedListener)
	 */
	public synchronized Model register(ModelChangedListener listener) {
		// TODO Auto-generated method stub
		return wrapped.register(listener);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#remove(java.util.List)
	 */
	public synchronized Model remove(List statements) {
		// TODO Auto-generated method stub
		return wrapped.remove(statements);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#remove(com.hp.hpl.jena.rdf.model.Model)
	 */
	public synchronized Model remove(Model m) {
		// TODO Auto-generated method stub
		return wrapped.remove(m);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#remove(com.hp.hpl.jena.rdf.model.Model, boolean)
	 */
	public synchronized Model remove(Model m, boolean suppressReifications) {
		// TODO Auto-generated method stub
		return wrapped.remove(m, suppressReifications);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#remove(com.hp.hpl.jena.rdf.model.Statement)
	 */
	public synchronized Model remove(Statement s) {
		// TODO Auto-generated method stub
		return wrapped.remove(s);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#remove(com.hp.hpl.jena.rdf.model.Statement[])
	 */
	public synchronized Model remove(Statement[] statements) {
		// TODO Auto-generated method stub
		return wrapped.remove(statements);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#remove(com.hp.hpl.jena.rdf.model.StmtIterator)
	 */
	public synchronized Model remove(StmtIterator iter) {
		// TODO Auto-generated method stub
		return wrapped.remove(iter);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#removeAllReifications(com.hp.hpl.jena.rdf.model.Statement)
	 */
	public synchronized void removeAllReifications(Statement s) {
		// TODO Auto-generated method stub
		wrapped.removeAllReifications(s);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#removeReification(com.hp.hpl.jena.rdf.model.ReifiedStatement)
	 */
	public synchronized void removeReification(ReifiedStatement rs) {
		// TODO Auto-generated method stub
		wrapped.removeReification(rs);
	}

	/**
	 * @see com.hp.hpl.jena.shared.PrefixMapping#setNsPrefix(java.lang.String, java.lang.String)
	 */
	public synchronized PrefixMapping setNsPrefix(String prefix, String uri) {
		// TODO Auto-generated method stub
		return wrapped.setNsPrefix(prefix, uri);
	}

	/**
	 * @see com.hp.hpl.jena.shared.PrefixMapping#setNsPrefixes(java.util.Map)
	 */
	public synchronized PrefixMapping setNsPrefixes(Map map) {
		// TODO Auto-generated method stub
		if (wrapped == null) {
			return super.setNsPrefixes(map);
		}
		return wrapped.setNsPrefixes(map);
	}

	/**
	 * @see com.hp.hpl.jena.shared.PrefixMapping#setNsPrefixes(com.hp.hpl.jena.shared.PrefixMapping)
	 */
	public synchronized PrefixMapping setNsPrefixes(PrefixMapping other) {
		// TODO Auto-generated method stub
		return wrapped.setNsPrefixes(other);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.RDFReaderF#setReaderClassName(java.lang.String, java.lang.String)
	 */
	public synchronized String setReaderClassName(
		String lang,
		String className) {
		// TODO Auto-generated method stub
		return wrapped.setReaderClassName(lang, className);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.RDFWriterF#setWriterClassName(java.lang.String, java.lang.String)
	 */
	public synchronized String setWriterClassName(
		String lang,
		String className) {
		// TODO Auto-generated method stub
		return wrapped.setWriterClassName(lang, className);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#size()
	 */
	public synchronized long size() {
		// TODO Auto-generated method stub
		return wrapped.size();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#supportsSetOperations()
	 */
	public synchronized boolean supportsSetOperations() {
		// TODO Auto-generated method stub
		return wrapped.supportsSetOperations();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#supportsTransactions()
	 */
	public synchronized boolean supportsTransactions() {
		// TODO Auto-generated method stub
		return wrapped.supportsTransactions();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#union(com.hp.hpl.jena.rdf.model.Model)
	 */
	public synchronized Model union(Model model) {
		synchronized (model) {
			return new ThreadSafeModel(wrapped.union(model));
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#unregister(com.hp.hpl.jena.rdf.model.ModelChangedListener)
	 */
	public synchronized Model unregister(ModelChangedListener listener) {
		// TODO Auto-generated method stub
		return wrapped.unregister(listener);
	}

	/**
	 * @deprecated
	 * @see com.hp.hpl.jena.shared.PrefixMapping#usePrefix(java.lang.String)
	 */
	public synchronized String usePrefix(String uri) {
		// TODO Auto-generated method stub
		return wrapped.usePrefix(uri);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#write(java.io.OutputStream)
	 */
	public synchronized Model write(OutputStream out) {
		// TODO Auto-generated method stub
		return wrapped.write(out);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#write(java.io.OutputStream, java.lang.String)
	 */
	public synchronized Model write(OutputStream out, String lang) {
		// TODO Auto-generated method stub
		return wrapped.write(out, lang);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#write(java.io.OutputStream, java.lang.String, java.lang.String)
	 */
	public synchronized Model write(
		OutputStream out,
		String lang,
		String base) {
		// TODO Auto-generated method stub
		return wrapped.write(out, lang, base);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#write(java.io.Writer)
	 */
	public synchronized Model write(Writer writer) {
		// TODO Auto-generated method stub
		return wrapped.write(writer);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#write(java.io.Writer, java.lang.String)
	 */
	public synchronized Model write(Writer writer, String lang) {
		// TODO Auto-generated method stub
		return wrapped.write(writer, lang);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#write(java.io.Writer, java.lang.String, java.lang.String)
	 */
	public synchronized Model write(Writer writer, String lang, String base) {
		// TODO Auto-generated method stub
		return wrapped.write(writer, lang, base);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Model#getReificationStyle()
	 */
	public ReificationStyle getReificationStyle() {
		// TODO Auto-generated method stub
		return getGraph().getReifier().getStyle();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(java.util.Calendar)
	 */
	public Literal createTypedLiteral(Calendar arg0) {
		// TODO Auto-generated method stub
		return wrapped.createTypedLiteral(arg0);
	}

	/**
	 * @see com.hp.hpl.jena.shared.PrefixMapping#removeNsPrefix(java.lang.String)
	 */
	public PrefixMapping removeNsPrefix(String arg0) {
		// TODO Auto-generated method stub
		return wrapped.removeNsPrefix(arg0);
	}
}
