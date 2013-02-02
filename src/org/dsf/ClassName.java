package org.dsf;

import java.util.LinkedList;

public class ClassName implements Comparable<ClassName> {
	private String className;
	private LinkedList<String> words = new LinkedList<>();
	
	public ClassName(String qualifiedName) {
		int idx = qualifiedName.lastIndexOf('.');
		this.className = (idx < 0) ? qualifiedName : qualifiedName.substring(idx + 1);
		
		char[] chars = className.toCharArray();
		int startIdx = 0;
		
		for (int endIdx = 1; endIdx < chars.length; endIdx++) {
			if (chars[endIdx] >= (int) 'A' && chars[endIdx] <= (int) 'Z') {
				words.add(className.substring(startIdx, endIdx));
				startIdx = endIdx;
			}
		}
		
		words.add(className.substring(startIdx));
	}
	
	@Override
	public String toString() {
		return className;
	}
	
	@Override
	public int compareTo(ClassName other) {
		return this.className.compareTo(other.className);
	}
	
	@Override
	public int hashCode() {
		return this.className.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ClassName)) {
			return false;
		}
		
		return this.className.equals(((ClassName) o).className);
	}

	public String getClassName() {
		return className;
	}

	public String[] getWords() {
		return words.toArray(new String[words.size()]);
	}
}
