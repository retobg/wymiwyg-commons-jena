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

import com.hp.hpl.jena.rdf.model.Alt;
import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ObjectF;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceF;
import com.hp.hpl.jena.rdf.model.Seq;

/**
 * <p>ThreadSafeAlt class.</p>
 *
 * @author reto
 * @version $Id: $Id
 */
public class ThreadSafeAlt  extends ThreadSafeContainer implements Alt {

	Alt wrapped;
	/**
	 * <p>Constructor for ThreadSafeAlt.</p>
	 *
	 * @param model a {@link org.wymiwyg.commons.tsmodel.ThreadSafeModel} object.
	 * @param wrapped a {@link com.hp.hpl.jena.rdf.model.Alt} object.
	 */
	public ThreadSafeAlt(ThreadSafeModel model, Alt wrapped) {
		super(model, wrapped);
		this.wrapped =wrapped;
	}

	/**
	 * <p>setDefault.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(com.hp.hpl.jena.rdf.model.RDFNode)
	 * @param o a {@link com.hp.hpl.jena.rdf.model.RDFNode} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Alt} object.
	 */
	public Alt setDefault(RDFNode o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * <p>setDefault.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(boolean)
	 * @param o a boolean.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Alt} object.
	 */
	public Alt setDefault(boolean o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * <p>setDefault.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(long)
	 * @param o a long.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Alt} object.
	 */
	public Alt setDefault(long o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/** {@inheritDoc} */
	public Alt setDefault(char o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * <p>setDefault.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(float)
	 * @param o a float.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Alt} object.
	 */
	public Alt setDefault(float o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * <p>setDefault.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(double)
	 * @param o a double.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Alt} object.
	 */
	public Alt setDefault(double o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * <p>setDefault.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(java.lang.String)
	 * @param o a {@link java.lang.String} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Alt} object.
	 */
	public Alt setDefault(String o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/** {@inheritDoc} */
	public Alt setDefault(String o, String l) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * <p>setDefault.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(java.lang.Object)
	 * @param o a {@link java.lang.Object} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Alt} object.
	 */
	public Alt setDefault(Object o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * <p>getDefault.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefault()
	 * @return a {@link com.hp.hpl.jena.rdf.model.RDFNode} object.
	 */
	public RDFNode getDefault() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>getDefaultResource.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultResource()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Resource} object.
	 */
	public Resource getDefaultResource() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>getDefaultLiteral.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultLiteral()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Literal} object.
	 */
	public Literal getDefaultLiteral() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>getDefaultBoolean.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultBoolean()
	 * @return a boolean.
	 */
	public boolean getDefaultBoolean() {
		synchronized (model) {
			return wrapped.getDefaultBoolean();
		}
	}

	/**
	 * <p>getDefaultByte.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultByte()
	 * @return a byte.
	 */
	public byte getDefaultByte() {
		synchronized (model) {
			return wrapped.getDefaultByte();
		}
	}

	/**
	 * <p>getDefaultShort.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultShort()
	 * @return a short.
	 */
	public short getDefaultShort() {
		synchronized (model) {
			return wrapped.getDefaultShort();
		}
	}

	/**
	 * <p>getDefaultInt.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultInt()
	 * @return a int.
	 */
	public int getDefaultInt() {
		synchronized (model) {
			return wrapped.getDefaultInt();
		}
	}

	/**
	 * <p>getDefaultLong.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultLong()
	 * @return a long.
	 */
	public long getDefaultLong() {
		synchronized (model) {
			return wrapped.getDefaultLong();
		}
	}

	/**
	 * <p>getDefaultChar.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultChar()
	 * @return a char.
	 */
	public char getDefaultChar() {
		synchronized (model) {
			return wrapped.getDefaultChar();
		}
	}

	/**
	 * <p>getDefaultFloat.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultFloat()
	 * @return a float.
	 */
	public float getDefaultFloat() {
		synchronized (model) {
			return wrapped.getDefaultFloat();
		}
	}

	/**
	 * <p>getDefaultDouble.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultDouble()
	 * @return a double.
	 */
	public double getDefaultDouble() {
		synchronized (model) {
			return wrapped.getDefaultDouble();
		}
	}

	/**
	 * <p>getDefaultString.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultString()
	 * @return a {@link java.lang.String} object.
	 */
	public String getDefaultString() {
		synchronized (model) {
			return wrapped.getDefaultString();
		}
	}

	/**
	 * <p>getDefaultLanguage.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultLanguage()
	 * @return a {@link java.lang.String} object.
	 */
	public String getDefaultLanguage() {
		synchronized (model) {
			return wrapped.getDefaultLanguage();
		}
	}

	/** {@inheritDoc} */
	public Resource getDefaultResource(ResourceF f) {
		synchronized (model) {
			return new ThreadSafeResource(model, wrapped.getDefaultResource(f));
		}
	}

	/** {@inheritDoc} */
	public Object getDefaultObject(ObjectF f) {
		synchronized (model) {
			return wrapped.getDefaultObject(f);
		}
	}

	/**
	 * <p>getDefaultAlt.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultAlt()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Alt} object.
	 */
	public Alt getDefaultAlt() {
		synchronized (model) {
			return new ThreadSafeAlt(model, wrapped.getDefaultAlt());
		}
	}

	/**
	 * <p>getDefaultBag.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultBag()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Bag} object.
	 */
	public Bag getDefaultBag() {
		synchronized (model) {
			return new ThreadSafeBag(model, wrapped.getDefaultBag());
		}
	}

	/**
	 * <p>getDefaultSeq.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultSeq()
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq getDefaultSeq() {
		synchronized (model) {
			return new ThreadSafeSeq(model, wrapped.getDefaultSeq());
		}
	}

}
