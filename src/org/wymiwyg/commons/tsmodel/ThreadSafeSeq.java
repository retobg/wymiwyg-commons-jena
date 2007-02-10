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
public class ThreadSafeSeq extends ThreadSafeContainer implements Seq {
	Seq wrapped;
	/**
	 * @param model
	 * @param wrapped
	 */
	public ThreadSafeSeq(ThreadSafeModel model, Seq wrapped) {
		super(model, wrapped);
		this.wrapped = wrapped;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public Seq add(int index, RDFNode o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, boolean)
	 */
	public Seq add(int index, boolean o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, long)
	 */
	public Seq add(int index, long o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, char)
	 */
	public Seq add(int index, char o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, float)
	 */
	public Seq add(int index, float o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, double)
	 */
	public Seq add(int index, double o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, java.lang.String)
	 */
	public Seq add(int index, String o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, java.lang.String, java.lang.String)
	 */
	public Seq add(int index, String o, String l) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, java.lang.Object)
	 */
	public Seq add(int index, Object o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getBoolean(int)
	 */
	public boolean getBoolean(int index) {
		synchronized (model) {
			return wrapped.getBoolean(index);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getByte(int)
	 */
	public byte getByte(int index) {
		synchronized (model) {
			return wrapped.getByte(index);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getShort(int)
	 */
	public short getShort(int index) {
		synchronized (model) {
			return wrapped.getShort(index);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getInt(int)
	 */
	public int getInt(int index) {
		synchronized (model) {
			return wrapped.getInt(index);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getLong(int)
	 */
	public long getLong(int index) {
		synchronized (model) {
			return wrapped.getLong(index);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getChar(int)
	 */
	public char getChar(int index) {
		synchronized (model) {
			return wrapped.getChar(index);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getFloat(int)
	 */
	public float getFloat(int index) {
		synchronized (model) {
			return wrapped.getFloat(index);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getDouble(int)
	 */
	public double getDouble(int index) {
		synchronized (model) {
			return wrapped.getDouble(index);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getString(int)
	 */
	public String getString(int index) {
		synchronized (model) {
			return wrapped.getString(index);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getLanguage(int)
	 */
	public String getLanguage(int index) {
		synchronized (model) {
			return wrapped.getLanguage(index);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getObject(int, com.hp.hpl.jena.rdf.model.ObjectF)
	 */
	public Object getObject(int index, ObjectF f) {
		synchronized (model) {
			return wrapped.getObject(index, f);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getResource(int, com.hp.hpl.jena.rdf.model.ResourceF)
	 */
	public Resource getResource(int index, ResourceF f) {
		synchronized (model) {
			return new ThreadSafeResource(model, wrapped.getResource(index, f));
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getLiteral(int)
	 */
	public Literal getLiteral(int index) {
		synchronized (model) {
			return wrapped.getLiteral(index);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getResource(int)
	 */
	public Resource getResource(int index) {
		synchronized (model) {
			return new ThreadSafeResource(model, wrapped.getResource(index));
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getObject(int)
	 */
	public RDFNode getObject(int index) {
		synchronized (model) {
			RDFNode wrappedResult = wrapped.getObject(index);
			if (wrappedResult instanceof Resource) {
				return new ThreadSafeResource(model, (Resource)wrappedResult);
			} else {
				return wrappedResult;
			}
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getBag(int)
	 */
	public Bag getBag(int index) {
		synchronized (model) {
			return new ThreadSafeBag(model, wrapped.getBag(index));
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getAlt(int)
	 */
	public Alt getAlt(int index) {
		synchronized (model) {
			return new ThreadSafeAlt(model, wrapped.getAlt(index));
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#getSeq(int)
	 */
	public Seq getSeq(int index) {
		synchronized (model) {
			return new ThreadSafeSeq(model, wrapped.getSeq(index));
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#remove(int)
	 */
	public Seq remove(int index) {
		synchronized (model) {
			wrapped.remove(index);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public int indexOf(RDFNode o) {
		synchronized (model) {
			//Since a ThreadSafeResource is equals to its wrapped Resource
			//this may not be necessary according to the spec
			/*if (o instanceof ThreadSafeResource) {
				o = ((ThreadSafeResource)o).getWrapped();
			}*/
			return wrapped.indexOf(o);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(boolean)
	 */
	public int indexOf(boolean o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(long)
	 */
	public int indexOf(long o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(char)
	 */
	public int indexOf(char o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(float)
	 */
	public int indexOf(float o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(double)
	 */
	public int indexOf(double o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(java.lang.String)
	 */
	public int indexOf(String o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(java.lang.String, java.lang.String)
	 */
	public int indexOf(String o, String l) {
		synchronized (model) {
			return wrapped.indexOf(o,l);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, com.hp.hpl.jena.rdf.model.RDFNode)
	 */
	public Seq set(int index, RDFNode o) {
		synchronized (model) {
			return wrapped.set(index,o);
		}
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, boolean)
	 */
	public Seq set(int index, boolean o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, long)
	 */
	public Seq set(int index, long o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, char)
	 */
	public Seq set(int index, char o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, float)
	 */
	public Seq set(int index, float o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, double)
	 */
	public Seq set(int index, double o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, java.lang.String)
	 */
	public Seq set(int index, String o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, java.lang.String, java.lang.String)
	 */
	public Seq set(int index, String o, String l) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/**
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, java.lang.Object)
	 */
	public Seq set(int index, Object o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

}
