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

/**
 * We extend ModelCom just because there probably is a bug in
 * Query using non-ModelCom models.
 *
 * @author reto
 * @version $Id: $Id
 */
public class ThreadSafeModel extends ModelCom implements Model {

	Model wrapped;

	/**
	 * <p>Constructor for ThreadSafeModel.</p>
	 *
	 * @param wrapped a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public ThreadSafeModel(Model wrapped) {
		super(wrapped.getGraph());
		this.wrapped = wrapped;
	}

	/**
	 * <p>abort.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#abort()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public Model abort() {
		wrapped.abort();
		return this;
	}

	/** {@inheritDoc} */
	public synchronized Model add(List statements) {
		wrapped.add(statements);
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#add(com.hp.hpl.jena.rdf.model.Model)
	 * @param m a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public synchronized Model add(Model m) {
		wrapped.add(m);
		return this;
	}

	/** {@inheritDoc} */
	public synchronized Model add(Model m, boolean suppressReifications) {
		wrapped.add(m, suppressReifications);
		return this;
	}

	
	/** {@inheritDoc} */
	public synchronized Model add(Resource s, Property p, RDFNode o) {
		wrapped.add(s, p, o);
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String)
	 * @param s a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 * @param p a {@link com.hp.hpl.jena.rdf.model.Property} object.
	 * @param o a {@link java.lang.String} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public synchronized Model add(Resource s, Property p, String o) {
		wrapped.add(s, p, o);
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#add(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String, boolean)
	 * @param s a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 * @param p a {@link com.hp.hpl.jena.rdf.model.Property} object.
	 * @param o a {@link java.lang.String} object.
	 * @param wellFormed a boolean.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public synchronized Model add(
		Resource s,
		Property p,
		String o,
		boolean wellFormed) {
		wrapped.add(s, p, o, wellFormed);
		return this;
	}

	/** {@inheritDoc} */
	public synchronized Model add(Resource s, Property p, String o, String l) {
		wrapped.add(s, p, o, l);
		return this;
	}

	/** {@inheritDoc} */
	public synchronized Model add(
		Resource s,
		Property p,
		String o,
		String l,
		boolean wellFormed) {
		wrapped.add(s, p, o, l, wellFormed);
		return this;
	}

	/** {@inheritDoc} */
	public synchronized Model add(Statement s) {
		wrapped.add(s);
		return this;
	}

	/** {@inheritDoc} */
	public synchronized Model add(Statement[] statements) {
		wrapped.add(statements);
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#add(com.hp.hpl.jena.rdf.model.StmtIterator)
	 * @param iter a {@link com.hp.hpl.jena.rdf.model.StmtIterator} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public synchronized Model add(StmtIterator iter) {
		wrapped.add(iter);
		return this;
	}

	/**
	 * <p>begin.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#begin()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public synchronized Model begin() {
		wrapped.begin();
		return this;
	}

	/**
	 * <p>close.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#close()
	 */
	public synchronized void close() {
		wrapped.close();
	}

	/**
	 * <p>commit.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#commit()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public Model commit() {
		wrapped.commit();
		return this;
	}

	/** {@inheritDoc} */
	public synchronized boolean contains(Resource s, Property p) {
		return wrapped.contains(s, p);
	}

	

	/** {@inheritDoc} */
	public synchronized boolean contains(Resource s, Property p, RDFNode o) {
		return wrapped.contains(s, p, o);
	}

	/** {@inheritDoc} */
	public synchronized boolean contains(Resource s, Property p, String o) {
		return wrapped.contains(s, p, o);
	}

	/** {@inheritDoc} */
	public synchronized boolean contains(
		Resource s,
		Property p,
		String o,
		String l) {
		return wrapped.contains(s, p, o, l);
	}

	/** {@inheritDoc} */
	public synchronized boolean contains(Statement s) {
		return wrapped.contains(s);
	}

	/** {@inheritDoc} */
	public synchronized boolean containsAll(Model model) {
		return wrapped.containsAll(model);
	}

	/**
	 * <p>containsAll.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#containsAll(com.hp.hpl.jena.rdf.model.StmtIterator)
	 * @param iter a {@link com.hp.hpl.jena.rdf.model.StmtIterator} object.
	 * @return a boolean.
	 */
	public synchronized boolean containsAll(StmtIterator iter) {
		return wrapped.containsAll(iter);
	}

	/** {@inheritDoc} */
	public synchronized boolean containsAny(Model model) {
		return wrapped.containsAny(model);
	}

	/**
	 * <p>containsAny.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#containsAny(com.hp.hpl.jena.rdf.model.StmtIterator)
	 * @param iter a {@link com.hp.hpl.jena.rdf.model.StmtIterator} object.
	 * @return a boolean.
	 */
	public synchronized boolean containsAny(StmtIterator iter) {
		return wrapped.containsAny(iter);
	}

	/** {@inheritDoc} */
	public synchronized boolean containsResource(RDFNode r) {
		return wrapped.containsResource(r);
	}

	/**
	 * <p>createAlt.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createAlt()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Alt} object.
	 */
	public Alt createAlt() {
		return new ThreadSafeAlt(this, wrapped.createAlt());
	}

	/** {@inheritDoc} */
	public Alt createAlt(String uri) {
		return new ThreadSafeAlt(this, wrapped.createAlt(uri));
	}

	/**
	 * <p>createBag.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createBag()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Bag} object.
	 */
	public Bag createBag() {
		return new ThreadSafeBag(this, wrapped.createBag());
	}

	/** {@inheritDoc} */
	public Bag createBag(String uri) {
		return new ThreadSafeBag(this, wrapped.createBag(uri));
	}

	/**
	 * <p>createList.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#createList()
	 * @return a {@link com.hp.hpl.jena.rdf.model.RDFList} object.
	 */
	public RDFList createList() {
		return wrapped.createList();
	}

	/** {@inheritDoc} */
	public synchronized RDFList createList(Iterator members) {
		return wrapped.createList(members);
	}

	/**
	 * <p>createList.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#createList(com.hp.hpl.jena.rdf.model.RDFNode[])
	 * @param members an array of {@link com.hp.hpl.jena.rdf.model.RDFNode} objects.
	 * @return a {@link com.hp.hpl.jena.rdf.model.RDFList} object.
	 */
	public synchronized RDFList createList(RDFNode[] members) {
		return wrapped.createList(members);
	}

	
	/** {@inheritDoc} */
	public Literal createLiteral(String v) {
		return wrapped.createLiteral(v);
	}

	/** {@inheritDoc} */
	public Literal createLiteral(String v, boolean wellFormed) {
		return wrapped.createLiteral(v, wellFormed);
	}

	/** {@inheritDoc} */
	public Literal createLiteral(String v, String language) {
		return wrapped.createLiteral(v, language);
	}



	/** {@inheritDoc} */
	public Property createProperty(String uri) {
		return new ThreadSafeProperty(this, wrapped.createProperty(uri));
	}

	/** {@inheritDoc} */
	public Property createProperty(
		String nameSpace,
		String localName) {
		return new ThreadSafeProperty(
			this,
			wrapped.createProperty(nameSpace, localName));
	}

	/** {@inheritDoc} */
	public ReifiedStatement createReifiedStatement(Statement s) {
		return new ThreadSafeReifiedStatement(
			this,
			wrapped.createReifiedStatement(s));
	}

	/** {@inheritDoc} */
	public ReifiedStatement createReifiedStatement(
		String uri,
		Statement s) {
		return new ThreadSafeReifiedStatement(
			this,
			wrapped.createReifiedStatement(uri, s));
	}

	/**
	 * <p>createResource.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#createResource()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 */
	public Resource createResource() {
		return new ThreadSafeResource(this, wrapped.createResource());
	}

	/** {@inheritDoc} */
	public Resource createResource(AnonId id) {
		return new ThreadSafeResource(this, wrapped.createResource(id));
	}

	/** {@inheritDoc} */
	public Resource createResource(Resource type) {
		return new ThreadSafeResource(this, wrapped.createResource(type));
	}

	/** {@inheritDoc} */
	public Resource createResource(ResourceF f) {
		return new ThreadSafeResource(this, wrapped.createResource(f));
	}

	/**
	 * <p>createResource.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#createResource(java.lang.String)
	 * @param uri a {@link java.lang.String} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 */
	public Resource createResource(String uri) {
		return new ThreadSafeResource(this, wrapped.createResource(uri));
	}

	/** {@inheritDoc} */
	public Resource createResource(String uri, Resource type) {
		return new ThreadSafeResource(this, wrapped.createResource(uri, type));
	}

	/** {@inheritDoc} */
	public  Resource createResource(String uri, ResourceF f) {
		return new ThreadSafeResource(this, wrapped.createResource(uri, f));
	}

	/**
	 * <p>createSeq.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createSeq()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public  Seq createSeq() {
		return new ThreadSafeSeq(this, wrapped.createSeq());
	}

	/** {@inheritDoc} */
	public synchronized Seq createSeq(String uri) {
		return new ThreadSafeSeq(this, wrapped.createSeq(uri));
	}


	/** {@inheritDoc} */
	public  Statement createStatement(
		Resource s,
		Property p,
		RDFNode o) {
		return new ThreadSafeStatement(this, wrapped.createStatement(s, p, o));
	}

	/** {@inheritDoc} */
	public  Statement createStatement(
		Resource s,
		Property p,
		String o) {
		return new ThreadSafeStatement(this, wrapped.createStatement(s, p, o));
	}

	/** {@inheritDoc} */
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
	 * <p>createStatement.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createStatement(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Property, java.lang.String, java.lang.String)
	 * @param s a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 * @param p a {@link com.hp.hpl.jena.rdf.model.Property} object.
	 * @param o a {@link java.lang.String} object.
	 * @param l a {@link java.lang.String} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
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

	/** {@inheritDoc} */
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
	 * <p>createTypedLiteral.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(boolean)
	 * @param v a boolean.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Literal} object.
	 */
	public  Literal createTypedLiteral(boolean v) {
		return wrapped.createTypedLiteral(v);
	}

	/**
	 * <p>createTypedLiteral.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(char)
	 * @param v a char.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Literal} object.
	 */
	public  Literal createTypedLiteral(char v) {
		return wrapped.createTypedLiteral(v);
	}

	/** {@inheritDoc} */
	public  Literal createTypedLiteral(double v) {
		return wrapped.createTypedLiteral(v);
	}

	/**
	 * <p>createTypedLiteral.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(float)
	 * @param v a float.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Literal} object.
	 */
	public  Literal createTypedLiteral(float v) {
		return wrapped.createTypedLiteral(v);
	}

	/** {@inheritDoc} */
	public  Literal createTypedLiteral(int v) {
		return wrapped.createTypedLiteral(v);
	}

	/**
	 * <p>createTypedLiteral.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(long)
	 * @param v a long.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Literal} object.
	 */
	public  Literal createTypedLiteral(long v) {
		return wrapped.createTypedLiteral(v);
	}

	/** {@inheritDoc} */
	public  Literal createTypedLiteral(Object value) {
		return wrapped.createTypedLiteral(value);
	}

	/** {@inheritDoc} */
	public  Literal createTypedLiteral(
		Object value,
		RDFDatatype dtype) {
		return wrapped.createTypedLiteral(value, dtype);
	}

	/** {@inheritDoc} */
	public Literal createTypedLiteral(
		Object value,
		String typeURI) {
		return wrapped.createTypedLiteral(value, typeURI);
	}

	/**
	 * <p>createTypedLiteral.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(java.lang.String)
	 * @param v a {@link java.lang.String} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Literal} object.
	 */
	public Literal createTypedLiteral(String v) {
		return wrapped.createTypedLiteral(v);
	}

	/** {@inheritDoc} */
	public Literal createTypedLiteral(
		String lex,
		RDFDatatype dtype) {
		return wrapped.createTypedLiteral(lex, dtype);
	}

	/** {@inheritDoc} */
	public Literal createTypedLiteral(
		String lex,
		String typeURI) {
		return wrapped.createTypedLiteral(lex, typeURI);
	}

	/** {@inheritDoc} */
	public synchronized Model difference(Model model) {
		return new ThreadSafeModel(wrapped.difference(model));
	}

	/**
	 * {@inheritDoc}
	 *
	 * see com.hp.hpl.jena.rdf.model.ModelLock#enterCriticalSection(boolean)
	 */
	public void enterCriticalSection(boolean readLockRequested) {
		//wrapped.enterCriticalSection(readLockRequested);
	}

	/** {@inheritDoc} */
	public synchronized Object executeInTransaction(Command cmd) {
		return wrapped.executeInTransaction(cmd);
	}

	/** {@inheritDoc} */
	public synchronized String expandPrefix(String prefixed) {
		return wrapped.expandPrefix(prefixed);
	}

	/** {@inheritDoc} */
	public Alt getAlt(Resource r) {
		return new ThreadSafeAlt(this, wrapped.getAlt(r));
	}

	/** {@inheritDoc} */
	public Alt getAlt(String uri) {
		return new ThreadSafeAlt(this, wrapped.getAlt(uri));
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	public Bag getBag(Resource r) {
		return new ThreadSafeBag(this, wrapped.getBag(r));
	}

	/** {@inheritDoc} */
	public Bag getBag(String uri) {
		return new ThreadSafeBag(this, wrapped.getBag(uri));
	}

	/**
	 * <p>getGraph.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#getGraph()
	 * @return a {@link com.hp.hpl.jena.graph.Graph} object.
	 */
	public synchronized Graph getGraph() {
		if (wrapped == null) {
			return super.getGraph();
		}
		return wrapped.getGraph();
	}


	/**
	 * <p>getNsPrefixMap.</p>
	 *
	 * @see com.hp.hpl.jena.shared.PrefixMapping#getNsPrefixMap()
	 * @return a {@link java.util.Map} object.
	 */
	public Map getNsPrefixMap() {
		//this is called by ModelCom constructor
		if (wrapped == null) {
			return super.getNsPrefixMap();
		}
		// this should return a copy, so no sync needed
		return wrapped.getNsPrefixMap();
	}

	/** {@inheritDoc} */
	public synchronized String getNsPrefixURI(String prefix) {
		return wrapped.getNsPrefixURI(prefix);
	}

	/** {@inheritDoc} */
	public synchronized String getNsURIPrefix(String uri) {
		return wrapped.getNsURIPrefix(uri);
	}

	/** {@inheritDoc} */
	public synchronized Statement getProperty(Resource s, Property p) {
		return new ThreadSafeStatement(this, wrapped.getProperty(s, p));
	}

	/** {@inheritDoc} */
	public synchronized Property getProperty(String uri) {
		return new ThreadSafeProperty(this, wrapped.getProperty(uri));
	}

	/** {@inheritDoc} */
	public synchronized Property getProperty(
		String nameSpace,
		String localName) {
		return new ThreadSafeProperty(
			this,
			wrapped.getProperty(nameSpace, localName));
	}

	/** {@inheritDoc} */
	public synchronized RDFNode getRDFNode(Node n) {
		RDFNode wrappedResult = wrapped.getRDFNode(n);
		if (wrappedResult instanceof Resource) {
			return new ThreadSafeResource(this, (Resource) wrappedResult);
		} else {
			return wrappedResult;
		}
	}

	/**
	 * <p>getReader.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.RDFReaderF#getReader()
	 * @return a {@link com.hp.hpl.jena.rdf.model.RDFReader} object.
	 */
	public synchronized RDFReader getReader() {
		return wrapped.getReader();
	}

	/** {@inheritDoc} */
	public synchronized RDFReader getReader(String lang) {
		return wrapped.getReader(lang);
	}

	/** {@inheritDoc} */
	public synchronized Statement getRequiredProperty(Resource s, Property p) {
		return new ThreadSafeStatement(this, wrapped.getRequiredProperty(s, p));
	}

	/** {@inheritDoc} */
	public Resource getResource(String uri) {
		return new ThreadSafeResource(this, wrapped.getResource(uri));
	}

	/** {@inheritDoc} */
	public Resource getResource(String uri, ResourceF f) {
		return new ThreadSafeResource(this, wrapped.getResource(uri, f));
	}

	/** {@inheritDoc} */
	public Seq getSeq(Resource r) {
		return new ThreadSafeSeq(this, wrapped.getSeq(r));
	}

	/** {@inheritDoc} */
	public Seq getSeq(String uri) {
		return new ThreadSafeSeq(this, getSeq(uri));
	}

	/**
	 * <p>getWriter.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.RDFWriterF#getWriter()
	 * @return a {@link com.hp.hpl.jena.rdf.model.RDFWriter} object.
	 */
	public RDFWriter getWriter() {
		return wrapped.getWriter();
	}

	/** {@inheritDoc} */
	public RDFWriter getWriter(String lang) {
		return wrapped.getWriter(lang);
	}

	/**
	 * <p>independent.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#independent()
	 * @return a boolean.
	 */
	public  boolean independent() {
		return wrapped.independent();
	}

	/** {@inheritDoc} */
	public synchronized Model intersection(Model model) {
		return new ThreadSafeModel(wrapped.intersection(model));
	}

	/**
	 * <p>isEmpty.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#isEmpty()
	 * @return a boolean.
	 */
	public synchronized boolean isEmpty() {
		return wrapped.isEmpty();
	}

	/** {@inheritDoc} */
	public synchronized boolean isIsomorphicWith(Model g) {
		return wrapped.isIsomorphicWith(g);
	}

	/** {@inheritDoc} */
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
	 * <p>listNameSpaces.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#listNameSpaces()
	 * @return a {@link com.hp.hpl.jena.rdf.model.NsIterator} object.
	 */
	public synchronized NsIterator listNameSpaces() {
		// TODO Auto-generated method stub
		return wrapped.listNameSpaces();
	}

	/**
	 * <p>listObjects.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#listObjects()
	 * @return a {@link com.hp.hpl.jena.rdf.model.NodeIterator} object.
	 */
	public synchronized NodeIterator listObjects() {
		return new CachingNodeIterator(wrapped.listObjects());
	}

	/** {@inheritDoc} */
	public synchronized NodeIterator listObjectsOfProperty(Property p) {
		return new CachingNodeIterator(wrapped.listObjectsOfProperty(p));
	}

	/** {@inheritDoc} */
	public synchronized NodeIterator listObjectsOfProperty(
		Resource s,
		Property p) {
		return new CachingNodeIterator(wrapped.listObjectsOfProperty(s, p));
	}

	/**
	 * <p>listReifiedStatements.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#listReifiedStatements()
	 * @return a {@link com.hp.hpl.jena.rdf.model.RSIterator} object.
	 */
	public synchronized RSIterator listReifiedStatements() {
		return new CachingRSIterator(this, wrapped.listReifiedStatements());
	}

	/** {@inheritDoc} */
	public synchronized RSIterator listReifiedStatements(Statement st) {
		return new CachingRSIterator(this, wrapped.listReifiedStatements(st));
	}

	/**
	 * <p>listStatements.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#listStatements()
	 * @return a {@link com.hp.hpl.jena.rdf.model.StmtIterator} object.
	 */
	public synchronized StmtIterator listStatements() {
		return new CachingStmtIterator(this, wrapped.listStatements());
	}

	
	/** {@inheritDoc} */
	public synchronized StmtIterator listStatements(
		Resource s,
		Property p,
		RDFNode o) {
		return new CachingStmtIterator(this, wrapped.listStatements(s, p, o));
	}

	/** {@inheritDoc} */
	public synchronized StmtIterator listStatements(
		Resource subject,
		Property predicate,
		String object) {
		return new CachingStmtIterator(
			this,
			wrapped.listStatements(subject, predicate, object));
	}

	/** {@inheritDoc} */
	public synchronized StmtIterator listStatements(
		Resource subject,
		Property predicate,
		String object,
		String lang) {
		return new CachingStmtIterator(
			this,
			wrapped.listStatements(subject, predicate, object, lang));
	}

	/** {@inheritDoc} */
	public synchronized StmtIterator listStatements(Selector s) {
		return new CachingStmtIterator(this, wrapped.listStatements(s));
	}

	/**
	 * <p>listSubjects.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#listSubjects()
	 * @return a {@link com.hp.hpl.jena.rdf.model.ResIterator} object.
	 */
	public synchronized ResIterator listSubjects() {
		return new CachingResIterator(this, wrapped.listSubjects());
	}

	/** {@inheritDoc} */
	public synchronized ResIterator listSubjectsWithProperty(Property p) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p));
	}

	
	/** {@inheritDoc} */
	public synchronized ResIterator listSubjectsWithProperty(
		Property p,
		RDFNode o) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p, o));
	}

	/** {@inheritDoc} */
	public synchronized ResIterator listSubjectsWithProperty(
		Property p,
		String o) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p, o));
	}

	/** {@inheritDoc} */
	public synchronized ResIterator listSubjectsWithProperty(
		Property p,
		String o,
		String l) {
		return new CachingResIterator(
			this,
			wrapped.listSubjectsWithProperty(p, o, l));
	}

	/**
	 * <p>lock.</p>
	 *
	 * @see com.hp.hpl.jena.shared.PrefixMapping#lock()
	 * @return a {@link com.hp.hpl.jena.shared.PrefixMapping} object.
	 */
	public synchronized PrefixMapping lock() {
		// TODO Auto-generated method stub
		return wrapped.lock();
	}

	/** {@inheritDoc} */
	public synchronized Model query(Selector s) {
		return new ThreadSafeModel(wrapped.query(s));
	}

	/**
	 * <p>queryHandler.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#queryHandler()
	 * @return a {@link com.hp.hpl.jena.graph.query.QueryHandler} object.
	 */
	public synchronized QueryHandler queryHandler() {
		// TODO Auto-generated method stub
		return wrapped.queryHandler();
	}

	/** {@inheritDoc} */
	public synchronized Model read(InputStream in, String base) {
		// TODO Auto-generated method stub
		return wrapped.read(in, base);
	}

	/** {@inheritDoc} */
	public synchronized Model read(InputStream in, String base, String lang) {
		// TODO Auto-generated method stub
		return wrapped.read(in, base, lang);
	}

	/** {@inheritDoc} */
	public synchronized Model read(Reader reader, String base) {
		// TODO Auto-generated method stub
		return wrapped.read(reader, base);
	}

	/** {@inheritDoc} */
	public synchronized Model read(Reader reader, String base, String lang) {
		// TODO Auto-generated method stub
		return wrapped.read(reader, base, lang);
	}

	/** {@inheritDoc} */
	public synchronized Model read(String url) {
		// TODO Auto-generated method stub
		return wrapped.read(url);
	}

	/** {@inheritDoc} */
	public synchronized Model read(String url, String lang) {
		// TODO Auto-generated method stub
		return wrapped.read(url, lang);
	}

	/** {@inheritDoc} */
	public synchronized Model register(ModelChangedListener listener) {
		// TODO Auto-generated method stub
		return wrapped.register(listener);
	}

	/**
	 * <p>remove.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#remove(java.util.List)
	 * @param statements a {@link java.util.List} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public synchronized Model remove(List statements) {
		// TODO Auto-generated method stub
		return wrapped.remove(statements);
	}

	/**
	 * <p>remove.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#remove(com.hp.hpl.jena.rdf.model.Model)
	 * @param m a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public synchronized Model remove(Model m) {
		// TODO Auto-generated method stub
		return wrapped.remove(m);
	}

	/** {@inheritDoc} */
	public synchronized Model remove(Model m, boolean suppressReifications) {
		// TODO Auto-generated method stub
		return wrapped.remove(m, suppressReifications);
	}

	/** {@inheritDoc} */
	public synchronized Model remove(Statement s) {
		// TODO Auto-generated method stub
		return wrapped.remove(s);
	}

	/** {@inheritDoc} */
	public synchronized Model remove(Statement[] statements) {
		// TODO Auto-generated method stub
		return wrapped.remove(statements);
	}

	/** {@inheritDoc} */
	public synchronized Model remove(StmtIterator iter) {
		// TODO Auto-generated method stub
		return wrapped.remove(iter);
	}

	/** {@inheritDoc} */
	public synchronized void removeAllReifications(Statement s) {
		// TODO Auto-generated method stub
		wrapped.removeAllReifications(s);
	}

	/** {@inheritDoc} */
	public synchronized void removeReification(ReifiedStatement rs) {
		// TODO Auto-generated method stub
		wrapped.removeReification(rs);
	}

	/** {@inheritDoc} */
	public synchronized PrefixMapping setNsPrefix(String prefix, String uri) {
		// TODO Auto-generated method stub
		return wrapped.setNsPrefix(prefix, uri);
	}

	/** {@inheritDoc} */
	public synchronized PrefixMapping setNsPrefixes(Map map) {
		// TODO Auto-generated method stub
		if (wrapped == null) {
			return super.setNsPrefixes(map);
		}
		return wrapped.setNsPrefixes(map);
	}

	/** {@inheritDoc} */
	public synchronized PrefixMapping setNsPrefixes(PrefixMapping other) {
		// TODO Auto-generated method stub
		return wrapped.setNsPrefixes(other);
	}

	/** {@inheritDoc} */
	public synchronized String setReaderClassName(
		String lang,
		String className) {
		// TODO Auto-generated method stub
		return wrapped.setReaderClassName(lang, className);
	}

	/** {@inheritDoc} */
	public synchronized String setWriterClassName(
		String lang,
		String className) {
		// TODO Auto-generated method stub
		return wrapped.setWriterClassName(lang, className);
	}

	/**
	 * <p>size.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#size()
	 * @return a long.
	 */
	public synchronized long size() {
		// TODO Auto-generated method stub
		return wrapped.size();
	}

	/**
	 * <p>supportsSetOperations.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#supportsSetOperations()
	 * @return a boolean.
	 */
	public synchronized boolean supportsSetOperations() {
		// TODO Auto-generated method stub
		return wrapped.supportsSetOperations();
	}

	/**
	 * <p>supportsTransactions.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#supportsTransactions()
	 * @return a boolean.
	 */
	public synchronized boolean supportsTransactions() {
		// TODO Auto-generated method stub
		return wrapped.supportsTransactions();
	}

	/** {@inheritDoc} */
	public synchronized Model union(Model model) {
		synchronized (model) {
			return new ThreadSafeModel(wrapped.union(model));
		}
	}

	/** {@inheritDoc} */
	public synchronized Model unregister(ModelChangedListener listener) {
		// TODO Auto-generated method stub
		return wrapped.unregister(listener);
	}



	/** {@inheritDoc} */
	public synchronized Model write(OutputStream out) {
		// TODO Auto-generated method stub
		return wrapped.write(out);
	}

	/** {@inheritDoc} */
	public synchronized Model write(OutputStream out, String lang) {
		// TODO Auto-generated method stub
		return wrapped.write(out, lang);
	}

	/** {@inheritDoc} */
	public synchronized Model write(
		OutputStream out,
		String lang,
		String base) {
		// TODO Auto-generated method stub
		return wrapped.write(out, lang, base);
	}

	/**
	 * <p>write.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#write(java.io.Writer)
	 * @param writer a {@link java.io.Writer} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public synchronized Model write(Writer writer) {
		// TODO Auto-generated method stub
		return wrapped.write(writer);
	}

	/** {@inheritDoc} */
	public synchronized Model write(Writer writer, String lang) {
		// TODO Auto-generated method stub
		return wrapped.write(writer, lang);
	}

	/** {@inheritDoc} */
	public synchronized Model write(Writer writer, String lang, String base) {
		// TODO Auto-generated method stub
		return wrapped.write(writer, lang, base);
	}

	/**
	 * <p>getReificationStyle.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Model#getReificationStyle()
	 * @return a {@link com.hp.hpl.jena.shared.ReificationStyle} object.
	 */
	public ReificationStyle getReificationStyle() {
		// TODO Auto-generated method stub
		return getGraph().getReifier().getStyle();
	}

	/**
	 * <p>createTypedLiteral.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.ModelCon#createTypedLiteral(java.util.Calendar)
	 * @param arg0 a {@link java.util.Calendar} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Literal} object.
	 */
	public Literal createTypedLiteral(Calendar arg0) {
		// TODO Auto-generated method stub
		return wrapped.createTypedLiteral(arg0);
	}

	/** {@inheritDoc} */
	public PrefixMapping removeNsPrefix(String arg0) {
		// TODO Auto-generated method stub
		return wrapped.removeNsPrefix(arg0);
	}
}
