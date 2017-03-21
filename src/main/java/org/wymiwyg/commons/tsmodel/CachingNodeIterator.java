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

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ClosableIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.Filter;
import com.hp.hpl.jena.util.iterator.Map1;

/**
 * <p>CachingNodeIterator class.</p>
 *
 * @author reto
 * @version $Id: $Id
 */
public class CachingNodeIterator implements NodeIterator {

	Iterator iterator;
	private List list;

	/**
	 * <p>Constructor for CachingNodeIterator.</p>
	 *
	 * @param orig a {@link com.hp.hpl.jena.rdf.model.NodeIterator} object.
	 */
	public CachingNodeIterator(NodeIterator orig) {
		list = new ArrayList();
		while (orig.hasNext()) {
			list.add(orig.nextNode());
		}
		iterator = list.iterator();
		orig.close();
	}

	/**
	 * <p>hasNext.</p>
	 *
	 * @see java.util.Iterator#hasNext()
	 * @return a boolean.
	 */
	public boolean hasNext() {
		return iterator.hasNext();
	}

	/**
	 * <p>nextNode.</p>
	 *
	 * @see com.hp.hpl.jena.rdf.model.NodeIterator#nextNode()
	 * @return a {@link com.hp.hpl.jena.rdf.model.RDFNode} object.
	 * @throws java.util.NoSuchElementException if any.
	 */
	public RDFNode nextNode() throws NoSuchElementException {
		return (RDFNode) iterator.next();
	}

	/**
	 * <p>close.</p>
	 *
	 * @see com.hp.hpl.jena.util.iterator.ClosableIterator#close()
	 */
	public void close() {
		// no need to close

	}

	/**
	 * <p>next.</p>
	 *
	 * @see java.util.Iterator#next()
	 * @return a {@link java.lang.Object} object.
	 */
	public Object next() {
		return iterator.next();
	}

	/**
	 * <p>remove.</p>
	 *
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		iterator.remove();
	}

	/**
	 * <p>removeNext.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object removeNext() {
		Object result = iterator.next();
		iterator.remove();
		return result;
	}

	/** {@inheritDoc} */
	public ExtendedIterator andThen(ClosableIterator arg0) {
		// TODO implement
		throw new RuntimeException("Not yet implemented");
	}

	/** {@inheritDoc} */
	public ExtendedIterator filterKeep(Filter arg0) {
		// TODO implement
		throw new RuntimeException("Not yet implemented");
	}

	/** {@inheritDoc} */
	public ExtendedIterator filterDrop(Filter arg0) {
		// TODO implement
		throw new RuntimeException("Not yet implemented");
	}

	/** {@inheritDoc} */
	public ExtendedIterator mapWith(Map1 arg0) {
		// TODO implement
		throw new RuntimeException("Not yet implemented");
	}

	/* (non-Javadoc)
	 * @see com.hp.hpl.jena.util.iterator.ExtendedIterator#toList()
	 */
	/**
	 * <p>toList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List toList() {
		return list;
	}


	/**
	 * <p>toSet.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set toSet() {
		return new AbstractSet() {


			@Override
			public int size() {
				return list.size();
			}

			@Override
			public Iterator iterator() {
				return CachingNodeIterator.this;
			}
			
		};
	}

}
