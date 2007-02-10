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
 * @author reto
 */
public class ThreadSafeAlt  extends ThreadSafeContainer implements Alt {

	Alt wrapped;
	/**
	 * 
	 */
	public ThreadSafeAlt(ThreadSafeModel model, Alt wrapped) {
		super(model, wrapped);
		this.wrapped =wrapped;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public Alt setDefault(RDFNode o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(boolean)
	 */
	public Alt setDefault(boolean o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(long)
	 */
	public Alt setDefault(long o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(char)
	 */
	public Alt setDefault(char o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(float)
	 */
	public Alt setDefault(float o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(double)
	 */
	public Alt setDefault(double o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(java.lang.String)
	 */
	public Alt setDefault(String o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(java.lang.String, java.lang.String)
	 */
	public Alt setDefault(String o, String l) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#setDefault(java.lang.Object)
	 */
	public Alt setDefault(Object o) {
		synchronized (model) {
			wrapped.setDefault(o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefault()
	 */
	public RDFNode getDefault() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultResource()
	 */
	public Resource getDefaultResource() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultLiteral()
	 */
	public Literal getDefaultLiteral() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultBoolean()
	 */
	public boolean getDefaultBoolean() {
		synchronized (model) {
			return wrapped.getDefaultBoolean();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultByte()
	 */
	public byte getDefaultByte() {
		synchronized (model) {
			return wrapped.getDefaultByte();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultShort()
	 */
	public short getDefaultShort() {
		synchronized (model) {
			return wrapped.getDefaultShort();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultInt()
	 */
	public int getDefaultInt() {
		synchronized (model) {
			return wrapped.getDefaultInt();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultLong()
	 */
	public long getDefaultLong() {
		synchronized (model) {
			return wrapped.getDefaultLong();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultChar()
	 */
	public char getDefaultChar() {
		synchronized (model) {
			return wrapped.getDefaultChar();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultFloat()
	 */
	public float getDefaultFloat() {
		synchronized (model) {
			return wrapped.getDefaultFloat();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultDouble()
	 */
	public double getDefaultDouble() {
		synchronized (model) {
			return wrapped.getDefaultDouble();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultString()
	 */
	public String getDefaultString() {
		synchronized (model) {
			return wrapped.getDefaultString();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultLanguage()
	 */
	public String getDefaultLanguage() {
		synchronized (model) {
			return wrapped.getDefaultLanguage();
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultResource(com.hp.hpl.jena.rdf.model.ResourceF)
	 */
	public Resource getDefaultResource(ResourceF f) {
		synchronized (model) {
			return new ThreadSafeResource(model, wrapped.getDefaultResource(f));
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultObject(com.hp.hpl.jena.rdf.model.ObjectF)
	 */
	public Object getDefaultObject(ObjectF f) {
		synchronized (model) {
			return wrapped.getDefaultObject(f);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultAlt()
	 */
	public Alt getDefaultAlt() {
		synchronized (model) {
			return new ThreadSafeAlt(model, wrapped.getDefaultAlt());
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultBag()
	 */
	public Bag getDefaultBag() {
		synchronized (model) {
			return new ThreadSafeBag(model, wrapped.getDefaultBag());
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Alt#getDefaultSeq()
	 */
	public Seq getDefaultSeq() {
		synchronized (model) {
			return new ThreadSafeSeq(model, wrapped.getDefaultSeq());
		}
	}

}
