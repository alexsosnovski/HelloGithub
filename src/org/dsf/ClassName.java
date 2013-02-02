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
		if (wordIdx >= words.size()) { //if class needs to have more words to match
			return false;
		}
		
		String word = words.get(wordIdx);
		
		if (offset >= word.length()) { //if there are more specifying chars than current word has
			return false;
		}
		
		if (pattern.length() == 0) { //nothing more to match
			return true;
		}
		
		if (Character.isUpperCase(pattern.charAt(0))) { //if current word matches
			if (offset > 0) {
				System.err.println("Offset > 0!!! Pattern: " + pattern + "; wordIdx: " + wordIdx + 
						"; offset: " + offset + "; words: " + words);
			}
			
			if (pattern.charAt(0) == word.charAt(offset)) {
				if (pattern.length() == 1) { //matched last symbol of the pattern
					return true;
				}
				
				if (Character.isUpperCase(pattern.charAt(1))) {
					return this.matches(pattern.substring(1), wordIdx + 1, 0);
				} 
				
				if (pattern.charAt(1) == ' ') { //if current word should be the last
					return isLastWord(wordIdx);
				}
				
				return this.matches(pattern.substring(1), wordIdx, offset + 1);
			} else {
				return false;
			}
		} 
		
		if (pattern.charAt(0) == ' ') {
			return isLastWord(wordIdx);
		}
		
		if (pattern.charAt(0) == '*') {
			//TODO: implement
		}
		
		if (pattern.charAt(0) == word.charAt(offset)) {
			if (pattern.length() == 1) { //matched last symbol of the pattern
				return true;
			}
			
			if (Character.isUpperCase(pattern.charAt(1))) {
				return this.matches(pattern.substring(1), wordIdx + 1, 0);
			} 
			
			if (pattern.charAt(1) == ' ') { //if current word should be the last
				return isLastWord(wordIdx);
			}
			
			return this.matches(pattern.substring(1), wordIdx, offset + 1);
		} else {
			return false;
		}
	}

	private boolean isLastWord(int wordIdx) {
		return wordIdx == words.size() - 1;
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
