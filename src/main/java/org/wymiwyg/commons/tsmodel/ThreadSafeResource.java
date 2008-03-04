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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.enhanced.EnhGraph;
import com.hp.hpl.jena.enhanced.EnhNode;
import com.hp.hpl.jena.enhanced.GraphPersonality;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.RDFVisitor;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * @author reto
 */
public class ThreadSafeResource extends EnhNode implements Resource {

	private static final Log logger =
		LogFactory.getLog(ThreadSafeResource.class);
	protected ThreadSafeModel model;
	private Resource wrapped;

	/**
	 * @param model
	 * @param wrapped
	 */
	public ThreadSafeResource(ThreadSafeModel model, Resource wrapped) {
		//actually I don't want to subclass EnhNode!
		super(
			wrapped.getNode(),
				new EnhGraph(model.getGraph(), new GraphPersonality()));
		this.model = model;
		this.wrapped = wrapped;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#abort()
	 */
	public Resource abort() {
		wrapped.abort();
		return this;
	}


	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#addProperty(com.hp.hpl.jena.rdf.model.Property, com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public Resource addProperty(Property p, RDFNode o) {
		model.add(this, p, o);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#addProperty(com.hp.hpl.jena.rdf.model.Property, java.lang.String)
	 */
	public Resource addProperty(Property p, String o) {
		model.add(this, p, o);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#addProperty(com.hp.hpl.jena.rdf.model.Property, java.lang.String, java.lang.String)
	 */
	public Resource addProperty(Property p, String o, String l) {
		model.add(this, p, o, l);
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.RDFNode#as(java.lang.Class)
	 */
	public RDFNode as(Class view) {
		// TODO implement
		logger.warn("Synchronization not implemented");
		return wrapped.as(view);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.RDFNode#asNode()
	 */
	public Node asNode() {
		// TODO is graph level sync needed?
		//logger.warn("Synchronization not implemented");
		return wrapped.asNode();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#begin()
	 */
	public Resource begin() {
		synchronized (model) {
			wrapped.begin();
		}
		return this;
	}

	/**
	 * Since multiple threads may access the model just because
	 * canAs returns true doesn't mean .as will work. 
	 * 
	 * @see com.hp.hpl.jena.rdf.model.RDFNode#canAs(java.lang.Class)
	 */
	public boolean canAs(Class view) {
		synchronized (model) {
			return wrapped.canAs(view);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#commit()
	 */
	public Resource commit() {
		synchronized (model) {
			wrapped.commit();
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#getId()
	 */
	public AnonId getId() {
		synchronized (model) {
			return wrapped.getId();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#getLocalName()
	 */
	public String getLocalName() {
		synchronized (model) {
			return wrapped.getLocalName();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#getModel()
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#getNameSpace()
	 */
	public String getNameSpace() {
		synchronized (model) {
			return wrapped.getNameSpace();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#getNode()
	 */
	public Node getNode() {
		// TODO implement
		logger.warn("Synchronization not implemented");
		return wrapped.getNode();
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#getProperty(com.hp.hpl.jena.rdf.model.Property)
	 */
	public Statement getProperty(Property p) {
		synchronized (model) {
			Statement wrappedResult = wrapped.getProperty(p);
			if (wrappedResult == null) {
				return null;
			}
			return new ThreadSafeStatement(model, wrappedResult);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#getRequiredProperty(com.hp.hpl.jena.rdf.model.Property)
	 */
	public Statement getRequiredProperty(Property p) {
		synchronized (model) {
			return new ThreadSafeStatement(
				model,
				wrapped.getRequiredProperty(p));
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#getURI()
	 */
	public String getURI() {
		synchronized (model) {
			return wrapped.getURI();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#hasProperty(com.hp.hpl.jena.rdf.model.Property)
	 */
	public boolean hasProperty(Property p) {
		synchronized (model) {
			return wrapped.hasProperty(p);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#hasProperty(com.hp.hpl.jena.rdf.model.Property, com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public boolean hasProperty(Property p, RDFNode o) {
		synchronized (model) {
			return wrapped.hasProperty(p, o);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#hasProperty(com.hp.hpl.jena.rdf.model.Property, java.lang.String)
	 */
	public boolean hasProperty(Property p, String o) {
		synchronized (model) {
			return wrapped.hasProperty(p, o);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#hasProperty(com.hp.hpl.jena.rdf.model.Property, java.lang.String, java.lang.String)
	 */
	public boolean hasProperty(Property p, String o, String l) {
		synchronized (model) {
			return wrapped.hasProperty(p, o, l);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.RDFNode#inModel(com.hp.hpl.jena.rdf.model.Model)
	 */
	public RDFNode inModel(Model m) {
		return wrapped.inModel(m);
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#isAnon()
	 */
	//TODO does it work?
	/*public boolean isAnon() {
		synchronized (model) {
			return wrapped.isAnon();
		}
	}*/

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#listProperties()
	 */
	public StmtIterator listProperties() {
		synchronized (model) {
			return new CachingStmtIterator(model, wrapped.listProperties());
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#listProperties(com.hp.hpl.jena.rdf.model.Property)
	 */
	public StmtIterator listProperties(Property p) {
		synchronized (model) {
			return new CachingStmtIterator(model, wrapped.listProperties(p));
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#removeAll(com.hp.hpl.jena.rdf.model.Property)
	 */
	public Resource removeAll(Property p) {
		synchronized (model) {
			wrapped.removeAll(p);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#removeProperties()
	 */
	public Resource removeProperties() {
		synchronized (model) {
			wrapped.removeProperties();
			return this;
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.RDFNode#visitWith(com.hp.hpl.jena.rdf.model.RDFVisitor)
	 */
	public Object visitWith(RDFVisitor rv) {
		synchronized (model) {
			return wrapped.visitWith(rv);
		}
	}
	
	Resource getWrapped() {
		return wrapped;
	}
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/*public boolean equals(Object other) {
		if (other instanceof Resource) {
			Resource otherRes = (Resource)other;
			if (isAnon() && otherRes.isAnon()) {
				return getId().equals(otherRes.getId());
			} else {
				return getURI().equals(otherRes.getURI());
			}
		}
		return false;
	}*/

	/**
	 * @see java.lang.Object#hashCode()
	 */
	/*public int hashCode() {
		return wrapped.hashCode();
	}*/

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return wrapped.toString();
	}

	public boolean hasURI(String arg0) {
		return !isAnon();
	}

	public Resource addLiteral(Property arg0, boolean arg1) {
		synchronized (model) {
			return wrapped.addLiteral(arg0, arg1);
		}
	}

	public Resource addLiteral(Property arg0, long arg1) {
		synchronized (model) {
			return wrapped.addLiteral(arg0, arg1);
		}
	}

	public Resource addLiteral(Property arg0, char arg1) {
		synchronized (model) {
			return wrapped.addLiteral(arg0, arg1);
		}
	}

	public Resource addLiteral(Property arg0, double arg1) {
		synchronized (model) {
			return wrapped.addLiteral(arg0, arg1);
		}
	}

	public Resource addLiteral(Property arg0, float arg1) {
		synchronized (model) {
			return wrapped.addLiteral(arg0, arg1);
		}
	}

	public Resource addLiteral(Property arg0, Object arg1) {
		synchronized (model) {
			return wrapped.addLiteral(arg0, arg1);
		}
	}

	public Resource addProperty(Property arg0, String arg1, RDFDatatype arg2) {
		synchronized (model) {
			return wrapped.addLiteral(arg0, arg1);
		}
	}

	public boolean hasLiteral(Property arg0, boolean arg1) {
		synchronized (model) {
			return wrapped.hasLiteral(arg0, arg1);
		}
	}

	public boolean hasLiteral(Property arg0, long arg1) {
		synchronized (model) {
			return wrapped.hasLiteral(arg0, arg1);
		}
	}

	public boolean hasLiteral(Property arg0, char arg1) {
		synchronized (model) {
			return wrapped.hasLiteral(arg0, arg1);
		}
	}

	public boolean hasLiteral(Property arg0, double arg1) {
		synchronized (model) {
			return wrapped.hasLiteral(arg0, arg1);
		}
	}

	public boolean hasLiteral(Property arg0, float arg1) {
		synchronized (model) {
			return wrapped.hasLiteral(arg0, arg1);
		}
	}

	public boolean hasLiteral(Property arg0, Object arg1) {
		synchronized (model) {
			return wrapped.hasLiteral(arg0, arg1);
		}
	}

	/* (non-Javadoc)
	 * @see com.hp.hpl.jena.rdf.model.Resource#addProperty(com.hp.hpl.jena.rdf.model.Property, java.lang.String, com.hp.hpl.jena.datatypes.RDFDatatype)
	 */
/*	public Resource addProperty(Property arg0, String arg1, RDFDatatype arg2) {
		synchronized (model) {
			return wrapped.addProperty(arg0, arg1, arg2);
		}
	}*/

	/**
	 * @see com.hp.hpl.jena.rdf.model.Resource#hasURI(java.lang.String)
	 */
	/*public boolean hasURI(String arg0) {
		return wrapped.hasURI(arg0);
	}*/

}
