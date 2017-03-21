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

import com.hp.hpl.jena.rdf.model.Container;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;

/**
 * <p>ThreadSafeContainer class.</p>
 *
 * @author reto
 * @version $Id: $Id
 */
public class ThreadSafeContainer
	extends ThreadSafeResource
	implements Container {

	Container wrapped;
	/**
	 * <p>Constructor for ThreadSafeContainer.</p>
	 *
	 * @param model a {@link org.wymiwyg.commons.tsmodel.ThreadSafeModel} object.
	 * @param wrapped a {@link com.hp.hpl.jena.rdf.model.Container} object.
	 */
	public ThreadSafeContainer(ThreadSafeModel model, Container wrapped) {
		super(model, wrapped);
		this.wrapped = wrapped;
	}

	/**
	 * <p>isAlt.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#isAlt()
	 * @return a boolean.
	 */
	public boolean isAlt() {
		synchronized (model) {
			return wrapped.isAlt();
		}
	}

	/**
	 * <p>isSeq.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#isSeq()
	 * @return a boolean.
	 */
	public boolean isSeq() {
		synchronized (model) {
			return wrapped.isSeq();
		}
	}

	/**
	 * <p>isBag.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#isBag()
	 * @return a boolean.
	 */
	public boolean isBag() {
		synchronized (model) {
			return wrapped.isBag();
		}
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#add(com.hp.hpl.jena.rdf.model.RDFNode)
	 * @param o a {@link com.hp.hpl.jena.rdf.model.RDFNode} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Container} object.
	 */
	public Container add(RDFNode o) {
		synchronized (model) {
			return wrapped.add(o);
		}
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#add(boolean)
	 * @param o a boolean.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Container} object.
	 */
	public Container add(boolean o) {
		synchronized (model) {
			return wrapped.add(o);
		}
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#add(long)
	 * @param o a long.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Container} object.
	 */
	public Container add(long o) {
		synchronized (model) {
			return wrapped.add(o);
		}
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#add(char)
	 * @param o a char.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Container} object.
	 */
	public Container add(char o) {
		synchronized (model) {
			return wrapped.add(o);
		}
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#add(float)
	 * @param o a float.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Container} object.
	 */
	public Container add(float o) {
		synchronized (model) {
			return wrapped.add(o);
		}
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#add(double)
	 * @param o a double.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Container} object.
	 */
	public Container add(double o) {
		synchronized (model) {
			return wrapped.add(o);
		}
	}

	/** {@inheritDoc} */
	public Container add(String o) {
		synchronized (model) {
			return wrapped.add(o);
		}
	}

	/** {@inheritDoc} */
	public Container add(String o, String l) {
		synchronized (model) {
			return wrapped.add(o);
		}
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#add(java.lang.Object)
	 * @param o a {@link java.lang.Object} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Container} object.
	 */
	public Container add(Object o) {
		synchronized (model) {
			return wrapped.add(o);
		}
	}

	/**
	 * <p>contains.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#contains(com.hp.hpl.jena.rdf.model.RDFNode)
	 * @param o a {@link com.hp.hpl.jena.rdf.model.RDFNode} object.
	 * @return a boolean.
	 */
	public boolean contains(RDFNode o) {
		synchronized (model) {
			return wrapped.contains(o);
		}
	}

	/**
	 * <p>contains.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#contains(boolean)
	 * @param o a boolean.
	 * @return a boolean.
	 */
	public boolean contains(boolean o) {
		synchronized (model) {
			return wrapped.contains(o);
		}
	}

	/**
	 * <p>contains.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#contains(long)
	 * @param o a long.
	 * @return a boolean.
	 */
	public boolean contains(long o) {
		synchronized (model) {
			return wrapped.contains(o);
		}
	}

	/**
	 * <p>contains.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#contains(char)
	 * @param o a char.
	 * @return a boolean.
	 */
	public boolean contains(char o) {
		synchronized (model) {
			return wrapped.contains(o);
		}
	}

	/**
	 * <p>contains.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#contains(float)
	 * @param o a float.
	 * @return a boolean.
	 */
	public boolean contains(float o) {
		synchronized (model) {
			return wrapped.contains(o);
		}
	}

	/** {@inheritDoc} */
	public boolean contains(double o) {
		synchronized (model) {
			return wrapped.contains(o);
		}
	}

	/**
	 * <p>contains.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#contains(java.lang.String)
	 * @param o a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean contains(String o) {
		synchronized (model) {
			return wrapped.contains(o);
		}
	}

	/** {@inheritDoc} */
	public boolean contains(String o, String l) {
		synchronized (model) {
			return wrapped.contains(o);
		}
	}

	/**
	 * <p>contains.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#contains(java.lang.Object)
	 * @param o a {@link java.lang.Object} object.
	 * @return a boolean.
	 */
	public boolean contains(Object o) {
		synchronized (model) {
			return wrapped.contains(o);
		}
	}

	/** {@inheritDoc} */
	public Container remove(Statement s) {
		synchronized (model) {
			return wrapped.remove(s);
		}
	}

	/**
	 * <p>iterator.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#iterator()
	 * @return a {@link com.hp.hpl.jena.rdf.model.NodeIterator} object.
	 */
	public NodeIterator iterator() {
		synchronized (model) {
		return new CachingNodeIterator(wrapped.iterator());}
	}

	/**
	 * <p>size.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Container#size()
	 * @return a int.
	 */
	public int size() {
		synchronized (model) {
		return wrapped.size();
		}
	}

}
