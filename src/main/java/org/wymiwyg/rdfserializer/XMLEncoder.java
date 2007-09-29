/*
 * Created on Jan 16, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.wymiwyg.rdfserializer;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author reto
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class XMLEncoder {
	public static String encode(String string) {
		StringReader in = new StringReader(string);
		StringWriter out = new StringWriter();
		try {
			for (int ch = in.read(); ch != -1; ch = in.read()) {
				if (ch == '<') {
					out.write("&lt;");
				} else {
					//so there cannot be a ]]>
					if (ch == '>') {
						out.write("&gt;");
					} else {
					if (ch == '&') {
						out.write("&amp;");
					} else {
						out.write(ch);
					}
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return out.toString();
	}

}
