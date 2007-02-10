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

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Alt;
import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ObjectF;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.RSIterator;
import com.hp.hpl.jena.rdf.model.ReifiedStatement;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceF;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdf.model.Statement;

/**
 * @author reto
 */
public class ThreadSafeStatement implements Statement {
	Statement wrapped;
	ThreadSafeModel model;
	/**
	 * @param model
	 * @param wrapped
	 */
	public ThreadSafeStatement(ThreadSafeModel model, Statement wrapped) {
		this.model = model;
		this.wrapped = wrapped;
		if (wrapped == null) {
			throw new RuntimeException("Cannot wrapp null-statement");
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#asTriple()
	 */
	public Triple asTriple() {
		// TODO Auto-generated method stub
		return wrapped.asTriple();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(boolean)
	 */
	public Statement changeObject(boolean o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(char)
	 */
	public Statement changeObject(char o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(double)
	 */
	public Statement changeObject(double o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(float)
	 */
	public Statement changeObject(float o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(long)
	 */
	public Statement changeObject(long o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(java.lang.Object)
	 */
	public Statement changeObject(Object o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public Statement changeObject(RDFNode o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(java.lang.String, boolean)
	 */
	public Statement changeObject(String o, boolean wellFormed) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o, wellFormed);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(java.lang.String, java.lang.String, boolean)
	 */
	public Statement changeObject(String o, String l, boolean wellFormed) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o, l, wellFormed);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(java.lang.String, java.lang.String)
	 */
	public Statement changeObject(String o, String l) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o, l);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(java.lang.String)
	 */
	public Statement changeObject(String o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#createReifiedStatement()
	 */
	public ReifiedStatement createReifiedStatement() {
		// TODO Auto-generated method stub
		return wrapped.createReifiedStatement();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#createReifiedStatement(java.lang.String)
	 */
	public ReifiedStatement createReifiedStatement(String uri) {
		// TODO Auto-generated method stub
		return wrapped.createReifiedStatement(uri);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getAlt()
	 */
	public Alt getAlt() {
		// TODO Auto-generated method stub
		return wrapped.getAlt();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getBag()
	 */
	public Bag getBag() {
		// TODO Auto-generated method stub
		return wrapped.getBag();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getBoolean()
	 */
	public boolean getBoolean() {
		// TODO Auto-generated method stub
		return wrapped.getBoolean();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getByte()
	 */
	public byte getByte() {
		// TODO Auto-generated method stub
		return wrapped.getByte();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getChar()
	 */
	public char getChar() {
		// TODO Auto-generated method stub
		return wrapped.getChar();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getDouble()
	 */
	public double getDouble() {
		// TODO Auto-generated method stub
		return wrapped.getDouble();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getFloat()
	 */
	public float getFloat() {
		// TODO Auto-generated method stub
		return wrapped.getFloat();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getInt()
	 */
	public int getInt() {
		// TODO Auto-generated method stub
		return wrapped.getInt();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getLanguage()
	 */
	public String getLanguage() {
		// TODO Auto-generated method stub
		return wrapped.getLanguage();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getLiteral()
	 */
	public Literal getLiteral() {
		// TODO Auto-generated method stub
		return wrapped.getLiteral();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getLong()
	 */
	public long getLong() {
		// TODO Auto-generated method stub
		return wrapped.getLong();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getModel()
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getObject()
	 */
	public RDFNode getObject() {
		synchronized (model) {
			RDFNode wrappedResult = wrapped.getObject();
			if (wrappedResult instanceof Resource) {
				return new ThreadSafeResource(model, (Resource) wrappedResult);
			} else {
				return wrappedResult;
			}
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getObject(com.hp.hpl.jena.rdf.model.ObjectF)
	 */
	public Object getObject(ObjectF f) {
		// TODO Auto-generated method stub
		return wrapped.getObject(f);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getPredicate()
	 */
	public Property getPredicate() {
		return new ThreadSafeProperty(model, wrapped.getPredicate());
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getProperty(com.hp.hpl.jena.rdf.model.Property)
	 */
	public Statement getProperty(Property p) {
		Statement wrappedResult = wrapped.getProperty(p);
		if (wrappedResult == null) {
			return null;
		}
		return new ThreadSafeStatement(model, wrappedResult);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getResource()
	 */
	public Resource getResource() {
		return new ThreadSafeResource(model, wrapped.getResource());
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getResource(com.hp.hpl.jena.rdf.model.ResourceF)
	 */
	public Resource getResource(ResourceF f) {
		return new ThreadSafeResource(model, wrapped.getResource(f));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getSeq()
	 */
	public Seq getSeq() {
		Seq wrappedResult = wrapped.getSeq();
		if (wrappedResult == null) {
			return null;
		}
		return new ThreadSafeSeq(model, wrappedResult);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getShort()
	 */
	public short getShort() {
		return wrapped.getShort();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getStatementProperty(com.hp.hpl.jena.rdf.model.Property)
	 */
	public Statement getStatementProperty(Property p) {
		return new ThreadSafeStatement(model, wrapped.getStatementProperty(p));
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getString()
	 */
	public String getString() {
		return wrapped.getString();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getSubject()
	 */
	public Resource getSubject() {
		return new ThreadSafeResource(model, wrapped.getSubject());
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#getWellFormed()
	 */
	public boolean getWellFormed() {
		return wrapped.getWellFormed();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#isReified()
	 */
	public boolean isReified() {
		return wrapped.isReified();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#listReifiedStatements()
	 */
	public RSIterator listReifiedStatements() {
		return new CachingRSIterator(model, wrapped.listReifiedStatements());
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#remove()
	 */
	public Statement remove() {
		model.remove(wrapped);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Statement#removeReification()
	 */
	public void removeReification() {
		model.removeAllReifications(wrapped);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		synchronized (model) {
			if (obj instanceof ThreadSafeStatement) {
				return wrapped.equals(((ThreadSafeStatement)obj).wrapped);
			}
			return wrapped.equals(obj);
		}
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		synchronized (model) {
			return wrapped.hashCode();
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return wrapped.toString();
	}

	/* (non-Javadoc)
	 * @see com.hp.hpl.jena.rdf.model.Statement#hasWellFormedXML()
	 */
	public boolean hasWellFormedXML() {
		return wrapped.hasWellFormedXML();
	}

}
