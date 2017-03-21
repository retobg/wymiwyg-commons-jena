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
 * <p>ThreadSafeSeq class.</p>
 *
 * @author reto
 * @version $Id: $Id
 */
public class ThreadSafeSeq extends ThreadSafeContainer implements Seq {
	Seq wrapped;
	/**
	 * <p>Constructor for ThreadSafeSeq.</p>
	 *
	 * @param model a {@link org.wymiwyg.commons.tsmodel.ThreadSafeModel} object.
	 * @param wrapped a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public ThreadSafeSeq(ThreadSafeModel model, Seq wrapped) {
		super(model, wrapped);
		this.wrapped = wrapped;
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, com.hp.hpl.jena.rdf.model.RDFNode)
	 * @param index a int.
	 * @param o a {@link com.hp.hpl.jena.rdf.model.RDFNode} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq add(int index, RDFNode o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, boolean)
	 * @param index a int.
	 * @param o a boolean.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq add(int index, boolean o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, long)
	 * @param index a int.
	 * @param o a long.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq add(int index, long o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, char)
	 * @param index a int.
	 * @param o a char.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq add(int index, char o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, float)
	 * @param index a int.
	 * @param o a float.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq add(int index, float o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/**
	 * <p>add.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#add(int, double)
	 * @param index a int.
	 * @param o a double.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq add(int index, double o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/** {@inheritDoc} */
	public Seq add(int index, String o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/** {@inheritDoc} */
	public Seq add(int index, String o, String l) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/** {@inheritDoc} */
	public Seq add(int index, Object o) {
		synchronized (model) {
			wrapped.add(index, o);
		}
		return this;
	}

	/** {@inheritDoc} */
	public boolean getBoolean(int index) {
		synchronized (model) {
			return wrapped.getBoolean(index);
		}
	}

	/** {@inheritDoc} */
	public byte getByte(int index) {
		synchronized (model) {
			return wrapped.getByte(index);
		}
	}

	/** {@inheritDoc} */
	public short getShort(int index) {
		synchronized (model) {
			return wrapped.getShort(index);
		}
	}

	/** {@inheritDoc} */
	public int getInt(int index) {
		synchronized (model) {
			return wrapped.getInt(index);
		}
	}

	/** {@inheritDoc} */
	public long getLong(int index) {
		synchronized (model) {
			return wrapped.getLong(index);
		}
	}

	/** {@inheritDoc} */
	public char getChar(int index) {
		synchronized (model) {
			return wrapped.getChar(index);
		}
	}

	/** {@inheritDoc} */
	public float getFloat(int index) {
		synchronized (model) {
			return wrapped.getFloat(index);
		}
	}

	/** {@inheritDoc} */
	public double getDouble(int index) {
		synchronized (model) {
			return wrapped.getDouble(index);
		}
	}

	/** {@inheritDoc} */
	public String getString(int index) {
		synchronized (model) {
			return wrapped.getString(index);
		}
	}

	/** {@inheritDoc} */
	public String getLanguage(int index) {
		synchronized (model) {
			return wrapped.getLanguage(index);
		}
	}

	/** {@inheritDoc} */
	public Object getObject(int index, ObjectF f) {
		synchronized (model) {
			return wrapped.getObject(index, f);
		}
	}

	/** {@inheritDoc} */
	public Resource getResource(int index, ResourceF f) {
		synchronized (model) {
			return new ThreadSafeResource(model, wrapped.getResource(index, f));
		}
	}

	/** {@inheritDoc} */
	public Literal getLiteral(int index) {
		synchronized (model) {
			return wrapped.getLiteral(index);
		}
	}

	/** {@inheritDoc} */
	public Resource getResource(int index) {
		synchronized (model) {
			return new ThreadSafeResource(model, wrapped.getResource(index));
		}
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	public Bag getBag(int index) {
		synchronized (model) {
			return new ThreadSafeBag(model, wrapped.getBag(index));
		}
	}

	/** {@inheritDoc} */
	public Alt getAlt(int index) {
		synchronized (model) {
			return new ThreadSafeAlt(model, wrapped.getAlt(index));
		}
	}

	/** {@inheritDoc} */
	public Seq getSeq(int index) {
		synchronized (model) {
			return new ThreadSafeSeq(model, wrapped.getSeq(index));
		}
	}

	/** {@inheritDoc} */
	public Seq remove(int index) {
		synchronized (model) {
			wrapped.remove(index);
		}
		return this;
	}

	/**
	 * <p>indexOf.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(com.hp.hpl.jena.rdf.model.RDFNode)
	 * @param o a {@link com.hp.hpl.jena.rdf.model.RDFNode} object.
	 * @return a int.
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
	 * <p>indexOf.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(boolean)
	 * @param o a boolean.
	 * @return a int.
	 */
	public int indexOf(boolean o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/**
	 * <p>indexOf.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(long)
	 * @param o a long.
	 * @return a int.
	 */
	public int indexOf(long o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/**
	 * <p>indexOf.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(char)
	 * @param o a char.
	 * @return a int.
	 */
	public int indexOf(char o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/**
	 * <p>indexOf.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(float)
	 * @param o a float.
	 * @return a int.
	 */
	public int indexOf(float o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/** {@inheritDoc} */
	public int indexOf(double o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/**
	 * <p>indexOf.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(java.lang.String)
	 * @param o a {@link java.lang.String} object.
	 * @return a int.
	 */
	public int indexOf(String o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/** {@inheritDoc} */
	public int indexOf(String o, String l) {
		synchronized (model) {
			return wrapped.indexOf(o,l);
		}
	}

	/**
	 * <p>indexOf.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#indexOf(java.lang.Object)
	 * @param o a {@link java.lang.Object} object.
	 * @return a int.
	 */
	public int indexOf(Object o) {
		synchronized (model) {
			return wrapped.indexOf(o);
		}
	}

	/**
	 * <p>set.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, com.hp.hpl.jena.rdf.model.RDFNode)
	 * @param index a int.
	 * @param o a {@link com.hp.hpl.jena.rdf.model.RDFNode} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq set(int index, RDFNode o) {
		synchronized (model) {
			return wrapped.set(index,o);
		}
	}

	/**
	 * <p>set.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, boolean)
	 * @param index a int.
	 * @param o a boolean.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq set(int index, boolean o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, long)
	 * @param index a int.
	 * @param o a long.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq set(int index, long o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, char)
	 * @param index a int.
	 * @param o a char.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq set(int index, char o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, float)
	 * @param index a int.
	 * @param o a float.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq set(int index, float o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, double)
	 * @param index a int.
	 * @param o a double.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq set(int index, double o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/** {@inheritDoc} */
	public Seq set(int index, String o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/** {@inheritDoc} */
	public Seq set(int index, String o, String l) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

	/**
	 * <p>set.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.Seq#set(int, java.lang.Object)
	 * @param index a int.
	 * @param o a {@link java.lang.Object} object.
	 * @return a {@link com.hp.hpl.jena.rdf.model.Seq} object.
	 */
	public Seq set(int index, Object o) {
		synchronized (model) {
			wrapped.set(index,o);
		}
		return this;
	}

}
