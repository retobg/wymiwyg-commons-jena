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
 * <p>ThreadSafeStatement class.</p>
 *
 * @author reto
 * @version $Id: $Id
 */
public class ThreadSafeStatement implements Statement {
	Statement wrapped;
	ThreadSafeModel model;
	/**
	 * <p>Constructor for ThreadSafeStatement.</p>
	 *
	 * @param model a {@link org.wymiwyg.commons.tsmodel.ThreadSafeModel} object.
	 * @param wrapped a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public ThreadSafeStatement(ThreadSafeModel model, Statement wrapped) {
		this.model = model;
		this.wrapped = wrapped;
		if (wrapped == null) {
			throw new RuntimeException("Cannot wrapp null-statement");
		}
	}

	/**
	 * <p>asTriple.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#asTriple()
	 * @return a {@link com.hp.hpl.jena.graph.Triple} object.
	 */
	public Triple asTriple() {
		// TODO Auto-generated method stub
		return wrapped.asTriple();
	}

	/**
	 * <p>changeObject.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(boolean)
	 * @param o a boolean.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeObject(boolean o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * <p>changeObject.</p>
	 *
	 * @param o a char.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeObject(char o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * <p>changeObject.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(double)
	 * @param o a double.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeObject(double o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/** {@inheritDoc} */
	public Statement changeObject(float o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * <p>changeObject.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(long)
	 * @param o a long.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeObject(long o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * <p>changeObject.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(java.lang.Object)
	 * @param o a {@link java.lang.Object} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeObject(Object o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * <p>changeObject.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(com.hp.hpl.jena.rdf.model.RDFNode)
	 * @param o a {@link com.hp.hpl.jena.rdf.model.RDFNode} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeObject(RDFNode o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/** {@inheritDoc} */
	public Statement changeObject(String o, boolean wellFormed) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o, wellFormed);
	}

	/** {@inheritDoc} */
	public Statement changeObject(String o, String l, boolean wellFormed) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o, l, wellFormed);
	}

	/**
	 * <p>changeObject.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(java.lang.String, java.lang.String)
	 * @param o a {@link java.lang.String} object.
	 * @param l a {@link java.lang.String} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeObject(String o, String l) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o, l);
	}

	/**
	 * <p>changeObject.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#changeObject(java.lang.String)
	 * @param o a {@link java.lang.String} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeObject(String o) {
		// TODO Auto-generated method stub
		return wrapped.changeObject(o);
	}

	/**
	 * <p>createReifiedStatement.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#createReifiedStatement()
	 * @return a {@link com.hp.hpl.jena.rdf.model.ReifiedStatement} object.
	 */
	public ReifiedStatement createReifiedStatement() {
		// TODO Auto-generated method stub
		return wrapped.createReifiedStatement();
	}

	/** {@inheritDoc} */
	public ReifiedStatement createReifiedStatement(String uri) {
		// TODO Auto-generated method stub
		return wrapped.createReifiedStatement(uri);
	}

	/**
	 * <p>getAlt.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getAlt()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Alt} object.
	 */
	public Alt getAlt() {
		// TODO Auto-generated method stub
		return wrapped.getAlt();
	}

	/**
	 * <p>getBag.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getBag()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Bag} object.
	 */
	public Bag getBag() {
		// TODO Auto-generated method stub
		return wrapped.getBag();
	}

	/**
	 * <p>getBoolean.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getBoolean()
	 * @return a boolean.
	 */
	public boolean getBoolean() {
		// TODO Auto-generated method stub
		return wrapped.getBoolean();
	}

	/**
	 * <p>getByte.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getByte()
	 * @return a byte.
	 */
	public byte getByte() {
		// TODO Auto-generated method stub
		return wrapped.getByte();
	}

	/**
	 * <p>getChar.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getChar()
	 * @return a char.
	 */
	public char getChar() {
		// TODO Auto-generated method stub
		return wrapped.getChar();
	}

	/**
	 * <p>getDouble.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getDouble()
	 * @return a double.
	 */
	public double getDouble() {
		// TODO Auto-generated method stub
		return wrapped.getDouble();
	}

	/**
	 * <p>getFloat.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getFloat()
	 * @return a float.
	 */
	public float getFloat() {
		// TODO Auto-generated method stub
		return wrapped.getFloat();
	}

	/**
	 * <p>getInt.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getInt()
	 * @return a int.
	 */
	public int getInt() {
		// TODO Auto-generated method stub
		return wrapped.getInt();
	}

	/**
	 * <p>getLanguage.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getLanguage()
	 * @return a {@link java.lang.String} object.
	 */
	public String getLanguage() {
		// TODO Auto-generated method stub
		return wrapped.getLanguage();
	}

	/**
	 * <p>getLiteral.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getLiteral()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Literal} object.
	 */
	public Literal getLiteral() {
		// TODO Auto-generated method stub
		return wrapped.getLiteral();
	}

	/**
	 * <p>getLong.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getLong()
	 * @return a long.
	 */
	public long getLong() {
		// TODO Auto-generated method stub
		return wrapped.getLong();
	}

	/**
	 * <p>Getter for the field <code>model</code>.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getModel()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * <p>getObject.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getObject()
	 * @return a {@link com.hp.hpl.jena.rdf.model.RDFNode} object.
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

	/** {@inheritDoc} */
	public Object getObject(ObjectF f) {
		// TODO Auto-generated method stub
		return wrapped.getObject(f);
	}

	/**
	 * <p>getPredicate.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getPredicate()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Property} object.
	 */
	public Property getPredicate() {
		return new ThreadSafeProperty(model, wrapped.getPredicate());
	}

	/** {@inheritDoc} */
	public Statement getProperty(Property p) {
		Statement wrappedResult = wrapped.getProperty(p);
		if (wrappedResult == null) {
			return null;
		}
		return new ThreadSafeStatement(model, wrappedResult);
	}

	/**
	 * <p>getResource.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getResource()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 */
	public Resource getResource() {
		return new ThreadSafeResource(model, wrapped.getResource());
	}

	/** {@inheritDoc} */
	public Resource getResource(ResourceF f) {
		return new ThreadSafeResource(model, wrapped.getResource(f));
	}

	/**
	 * <p>getSeq.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getSeq()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq getSeq() {
		Seq wrappedResult = wrapped.getSeq();
		if (wrappedResult == null) {
			return null;
		}
		return new ThreadSafeSeq(model, wrappedResult);
	}

	/**
	 * <p>getShort.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getShort()
	 * @return a short.
	 */
	public short getShort() {
		return wrapped.getShort();
	}

	/** {@inheritDoc} */
	public Statement getStatementProperty(Property p) {
		return new ThreadSafeStatement(model, wrapped.getStatementProperty(p));
	}

	/**
	 * <p>getString.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getString()
	 * @return a {@link java.lang.String} object.
	 */
	public String getString() {
		return wrapped.getString();
	}

	/**
	 * <p>getSubject.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#getSubject()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 */
	public Resource getSubject() {
		return new ThreadSafeResource(model, wrapped.getSubject());
	}



	/**
	 * <p>isReified.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#isReified()
	 * @return a boolean.
	 */
	public boolean isReified() {
		return wrapped.isReified();
	}

	/**
	 * <p>listReifiedStatements.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#listReifiedStatements()
	 * @return a {@link com.hp.hpl.jena.rdf.model.RSIterator} object.
	 */
	public RSIterator listReifiedStatements() {
		return new CachingRSIterator(model, wrapped.listReifiedStatements());
	}

	/**
	 * <p>remove.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#remove()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement remove() {
		model.remove(wrapped);
		return this;
	}

	/**
	 * <p>removeReification.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Statement#removeReification()
	 */
	public void removeReification() {
		model.removeAllReifications(wrapped);
	}

	/** {@inheritDoc} */
	public boolean equals(Object obj) {
		synchronized (model) {
			if (obj instanceof ThreadSafeStatement) {
				return wrapped.equals(((ThreadSafeStatement)obj).wrapped);
			}
			return wrapped.equals(obj);
		}
	}

	/**
	 * <p>hashCode.</p>
	 *
	 * @see java.lang.Object#hashCode()
	 * @return a int.
	 */
	public int hashCode() {
		synchronized (model) {
			return wrapped.hashCode();
		}
	}

	/**
	 * <p>toString.</p>
	 *
	 * @see java.lang.Object#toString()
	 * @return a {@link java.lang.String} object.
	 */
	public String toString() {
		return wrapped.toString();
	}

	/**
	 * <p>changeLiteralObject.</p>
	 *
	 * @param arg0 a boolean.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeLiteralObject(boolean arg0) {
		synchronized (model) {
			return wrapped.changeLiteralObject(arg0);
		}
	}

	/**
	 * <p>changeLiteralObject.</p>
	 *
	 * @param arg0 a long.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeLiteralObject(long arg0) {
		synchronized (model) {
			return wrapped.changeLiteralObject(arg0);
		}
	}

	/**
	 * <p>changeLiteralObject.</p>
	 *
	 * @param arg0 a int.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeLiteralObject(int arg0) {
		synchronized (model) {
			return wrapped.changeLiteralObject(arg0);
		}
	}

	/** {@inheritDoc} */
	public Statement changeLiteralObject(char arg0) {
		synchronized (model) {
			return wrapped.changeLiteralObject(arg0);
		}
	}

	/**
	 * <p>changeLiteralObject.</p>
	 *
	 * @param arg0 a float.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeLiteralObject(float arg0) {
		synchronized (model) {
			return wrapped.changeLiteralObject(arg0);
		}
	}

	/**
	 * <p>changeLiteralObject.</p>
	 *
	 * @param arg0 a double.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Statement} object.
	 */
	public Statement changeLiteralObject(double arg0) {
		synchronized (model) {
			return wrapped.changeLiteralObject(arg0);
		}
	}

	/**
	 * <p>hasWellFormedXML.</p>
	 *
	 * @return a boolean.
	 */
	public boolean hasWellFormedXML() {
		synchronized (model) {
			return wrapped.hasWellFormedXML();
		}
	}

	/* (non-Javadoc)
	 * @see com.hp.hpl.jena.rdf.model.Statement#hasWellFormedXML()
	 */
	/*public boolean hasWellFormedXML() {
		return wrapped.hasWellFormedXML();
	}*/

}
