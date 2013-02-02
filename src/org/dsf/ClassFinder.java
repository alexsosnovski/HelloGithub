package org.dsf;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ClassFinder {
	public static final String SUPPORTED_ENCODING = "UTF-8";
	
	List<ClassName> classes = new LinkedList<>();
	
	/**
	 * Reads class names from input stream. <code>SUPPORTED_ENCODING</code> 
	 * will be used to decode bytes into characters. This method DOES NOT perform any validation.
	 * Whitespaces, tabs and carriage return symbols will be totally ignored. 
	 * Class names should be separated by '\n' symbols. "Empty lines" will be ignored.
	 * 
	 * @throws RuntimeException if some error occurs.
	 */
	public ClassFinder(InputStream classNamesStream) {
		try (InputStreamReader in = new InputStreamReader(classNamesStream, SUPPORTED_ENCODING)) {
			int i;
			StringBuilder buff = new StringBuilder(256);
			
			while ((i = in.read()) != -1) {
				char c = (char) i;
				
				switch (c) {
					case ' ': break;
					case '\t': break;
					case '\r': break;
					case '\n':
						createClassName(buff.toString());
						buff = new StringBuilder(256);
						break;
					default:
						buff.append(c);
						break;
				}
			}
			
			createClassName(buff.toString());
		} catch (IOException e) {
			//I would have thrown IOException but method signature doesn't allow that :(
			throw new RuntimeException("Could not read class names from input stream", e);
		}
	}

	public Collection<String> findMatching(String pattern) {
		return new LinkedList<>();
	}
	
	/** This method is intended for unit tests */
	protected ClassName[] getClasses() {
		return classes.toArray(new ClassName[classes.size()]);
	}
	
	private void createClassName(String name) {
		if (name.length() == 0) {
			return;
		} else {
			classes.add(new ClassName(name));
		}
	}
}
