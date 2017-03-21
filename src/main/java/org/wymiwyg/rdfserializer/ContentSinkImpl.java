/*
 * Created on Dec 19, 2003
 * 
 * 
 * ====================================================================
 * 
 * The WYMIWYG Software License, Version 1.0
 * 
 * Copyright (c) 2002-2003 WYMIWYG All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *  3. The end-user documentation included with the redistribution, if any,
 * must include the following acknowlegement: "This product includes software
 * developed by WYMIWYG." Alternately, this acknowlegement may appear in the
 * software itself, if and wherever such third-party acknowlegements normally
 * appear.
 *  4. The name "WYMIWYG" or "WYMIWYG.org" must not be used to endorse or
 * promote products derived from this software without prior written
 * permission. For written permission, please contact wymiwyg@wymiwyg.org.
 *  5. Products derived from this software may not be called "WYMIWYG" nor may
 * "WYMIWYG" appear in their names without prior written permission of WYMIWYG.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL WYMIWYG
 * OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 * 
 * This software consists of voluntary contributions made by many individuals
 * on behalf of WYMIWYG. For more information on WYMIWYG, please see
 * http://www.WYMIWYG.org/.
 * 
 * This licensed is based on The Apache Software License, Version 1.1, see
 * http://www.apache.org/.
 */

package org.wymiwyg.rdfserializer;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>ContentSinkImpl class.</p>
 *
 * @author reto
 * @version $Id: $Id
 */
public class ContentSinkImpl implements ContentSink {

	List producers = new ArrayList();
	int readingPos = 0;
	/**
	 * <p>Constructor for ContentSinkImpl.</p>
	 */
	public ContentSinkImpl() {
		super();
	}

	/** {@inheritDoc} */
	public void append(ContentProducer producer) {
		producers.add(producer);
	}

	/**
	 * <p>nextProducer.</p>
	 *
	 * @return the next producer if this is ready, the method blocks till a
	 *               producer is ready, or null if the sink is closed
	 */
	public ContentProducer nextProducer() {
		if (readingPos < producers.size()) {
			return (ContentProducer) producers.get(readingPos++);
		} else {
			return null;
		}
	}

	/**
	 * <p>close.</p>
	 *
	 * @see org.wymiwyg.rdfserializer.ContentSink#close()
	 */
	public void close() {
		// TODO Auto-generated method stub

	}

}
