/*
 * Created on 1-lug-03
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
package org.wymiwyg.commons.prevamodel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.graph.BulkUpdateHandler;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.TransactionHandler;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.impl.SimpleBulkUpdateHandler;
import com.hp.hpl.jena.graph.impl.SimpleTransactionHandler;
import com.hp.hpl.jena.mem.GraphMem;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.shared.ReificationStyle;
/**
 * <p>PrevaGraph class.</p>
 *
 * @author reto
 * @version $Id: $Id
 */
public class PrevaGraph extends GraphMem {
	//private ModelCon modelCon = new ModelMem();
	private File directory;
	private int counter = 1;
	private static Log logger = LogFactory.getLog(PrevaGraph.class);
	/**
	 * <p>Constructor for PrevaGraph.</p>
	 *
	 * @param directoryPath a {@link java.lang.String} object.
	 * @param storeInterval a int.
	 * @throws java.io.IOException if any.
	 */
	public PrevaGraph(String directoryPath, int storeInterval)
			throws IOException {
		super(ReificationStyle.Standard);
		directory = new File(directoryPath);
		directory.mkdirs();
		long storeIntervalMilis = 60 * 1000 * storeInterval;
		//load previous stored model
		try {
			read();
		} catch (Exception e) {
			logger.error("restoring PrevaModel", e);
			throw new RuntimeException(e);
		}
		store();
		if (storeInterval > 0) {
			Thread storingThread = new StroringThread(this, storeIntervalMilis);
			storingThread.setDaemon(true);
			storingThread.start();
		}
	}
	/**
	 * @param string
	 * @param statement
	 */
	private void log(String command, Triple triple) {
		Writer writer;
		synchronized (this) {
			try {
				writer = new FileWriter(new File(directory, "log-" + counter++
						+ "-" + command));
				writeStatement(triple, writer);
				writer.close();
			} catch (Exception e) {
				logger.error("Writing log " + e);
				throw new RuntimeException(e.toString());
			}
		}
	}
	/**
	 * @param statement
	 * @param writer
	 */
	private void writeStatement(Triple triple, Writer writer)
			throws IOException {
		Graph stmtGraph = new GraphMem();
		stmtGraph.add(triple);
		Model stmtModel = ModelFactory.createModelForGraph(stmtGraph);
		stmtModel.write(writer, "N-TRIPLE");
	}
	/**
	 * <p>removeLogs.</p>
	 */
	public synchronized void removeLogs() {
		File[] logFiles = directory.listFiles(new FilenameFilter() {
			/**
			 * @see java.io.FilenameFilter#accept(java.io.File,
			 *          java.lang.String)
			 */
			public boolean accept(File dir, String name) {
				return name.startsWith("log-");
			}
		});
		for (int i = 0; i < logFiles.length; i++) {
			logFiles[i].delete();
		}
		counter = 1;
	}
	/** {@inheritDoc} */
	public synchronized void performAdd(Triple triple) {
		super.performAdd(triple);
		if (logger.isDebugEnabled()) {
			try {
				throw new Exception("harmless debug-exception");
			} catch (Exception ex) {
				logger.debug("writing triple: "+triple, ex);
			}
		}
		log("add", triple);
	}
	/** {@inheritDoc} */
	public synchronized void performDelete(Triple triple) {
		super.performDelete(triple);
		log("delete", triple);
	}
	/**
	 *  
	 */
	private void read() throws IOException {
		final Set triples = new HashSet();
		File defaultStore = new File(directory, "store.rdf");
		File newStore = new File(directory, "store-new.rdf");
		if (defaultStore.exists()) {
			if (newStore.exists()) {
				newStore.delete();
			}
			BufferedReader in = new BufferedReader(new FileReader(defaultStore));
			String line = in.readLine();
			while (line != null) {
				triples.add(line);
				line = in.readLine();
			}
			in.close();
		} else {
			if (newStore.exists()) {
				newStore.renameTo(defaultStore);
			}
		}
		String[] logFiles = directory.list(new FilenameFilter() {
			/**
			 * @see java.io.FilenameFilter#accept(java.io.File,
			 *          java.lang.String)
			 */
			public boolean accept(File dir, String name) {
				return name.startsWith("log-");
			}
		});
		Arrays.sort(logFiles, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				return getLogNumber((String) arg0)
						- getLogNumber((String) arg1);
			}
			private int getLogNumber(String string) {
				String substring = string.substring(4);
				int endOfNumer = substring.indexOf('-');
				String numberString = substring.substring(0, endOfNumer);
				return Integer.parseInt(numberString);
			}
		});
		for (int i = 0; i < logFiles.length; i++) {
			File log = new File(directory, logFiles[i]);
			BufferedReader logIn = new BufferedReader(new FileReader(log));
			String entry = logIn.readLine();
			String nextLine = logIn.readLine();
			if (nextLine != null) {
				throw new RuntimeException(log+" is multiline");
			}
			if (entry == null) {
				logger.warn(log+" is empty, ignoring");
				continue;
			}
			logIn.close();
			if (logFiles[i].indexOf("add") != -1) {
				triples.add(entry);
			} else {
				triples.remove(entry);
			}
		}
		//Model model = ModelFactory.createModelForGraph(this);
		PipedReader pipedIn = new PipedReader();
		final PipedWriter pipedOut = new PipedWriter(pipedIn);
		new Thread() {
			public void run() {
				PrintWriter printOut = new PrintWriter(new BufferedWriter(pipedOut));
				Iterator iter = triples.iterator();
				while (iter.hasNext()) {
					String element = (String) iter.next();
					printOut.println(element);
				}
				printOut.close();
			}
		}.start();
		ModelFactory.createModelForGraph(this).read(pipedIn, "", "N-TRIPLE");
	}
	/**
	 * store model and delete logs
	 *
	 * @author reto
	 * @throws java.io.IOException if any.
	 */
	public synchronized void store() throws IOException {
		long start = System.currentTimeMillis();
		synchronized (this) {		
			//TODO set variable when file created instead of looking in dir
			String[] logFiles = directory.list(new FilenameFilter() {
				/**
				 * @see java.io.FilenameFilter#accept(java.io.File,
				 *          java.lang.String)
				 */
				public boolean accept(File dir, String name) {
					return name.startsWith("log-");
				}
			});
			if (logFiles.length > 0) {
				logger.info("reducing disk representation of prevagraph to one file");
				Model model = ModelFactory.createModelForGraph(this);
				File newStore = new File(directory, "store-new.rdf");
				Writer writer = new FileWriter(newStore);
				model.write(writer, "N-TRIPLE");
				writer.close();
				File defaultStore = new File(directory, "store.rdf");
				if (defaultStore.exists()) {
					defaultStore.delete();
				}
				newStore.renameTo(defaultStore);
				removeLogs();
				
			}
		}
		logger.info("log maintance too "+(System.currentTimeMillis() -start)+" ms");
		
	}
	/**
	 * <p>getBulkUpdateHandler.</p>
	 *
	 * @see com.hp.hpl.jena.graph.Graph#getBulkUpdateHandler()
	 * @return a {@link com.hp.hpl.jena.graph.BulkUpdateHandler} object.
	 */
	public BulkUpdateHandler getBulkUpdateHandler() {
		return new SimpleBulkUpdateHandler(this);
		//return super.getBulkUpdateHandler();
	}
	/**
	 * <p>getTransactionHandler.</p>
	 *
	 * @see com.hp.hpl.jena.graph.Graph#getTransactionHandler()
	 * @return a {@link com.hp.hpl.jena.graph.TransactionHandler} object.
	 */
	public TransactionHandler getTransactionHandler() {
		return new SimpleTransactionHandler();
	}
}
class StroringThread extends Thread {
	private static Log logger = LogFactory.getLog(StroringThread.class);
	WeakReference modelRef;
	long storeIntervalMilis;
	/**
	 * <p>Constructor for StroringThread.</p>
	 *
	 * @param graph a {@link org.wymiwyg.commons.prevamodel.PrevaGraph} object.
	 * @param storeIntervalMilis a long.
	 */
	public StroringThread(PrevaGraph graph, long storeIntervalMilis) {
		modelRef = new WeakReference(graph);
		this.storeIntervalMilis = storeIntervalMilis;
	}
	/**
	 * <p>run.</p>
	 *
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(storeIntervalMilis);
			} catch (InterruptedException e) {
			}
			PrevaGraph graph = (PrevaGraph) modelRef.get();
			if (graph == null)
				return;
			try {
				graph.store();
			} catch (IOException ex) {
				logger.error("storing: ", ex);
			}
			graph = null;
		}
	}
}
