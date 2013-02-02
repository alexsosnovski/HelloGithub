package org.dsf;

import java.util.ArrayList;

class ClassName implements Comparable<ClassName> {
	private String className;
	private ArrayList<String> words = new ArrayList<>();
	
	public ClassName(String qualifiedName) {
		int idx = qualifiedName.lastIndexOf('.');
		this.className = (idx < 0) ? qualifiedName : qualifiedName.substring(idx + 1);
		
		char[] chars = className.toCharArray();
		int startIdx = 0;
		
		for (int endIdx = 1; endIdx < chars.length; endIdx++) {
			if (Character.isUpperCase(chars[endIdx])) {
				words.add(className.substring(startIdx, endIdx));
				startIdx = endIdx;
			}
		}
		
		words.add(className.substring(startIdx));
	}
	
	public boolean matches(String pattern, int wordIdx, int offset) {
		return true;
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

	/** This method is intended for unit tests */
	protected String[] getWordsArr() {
		return words.toArray(new String[words.size()]);
	}
}
