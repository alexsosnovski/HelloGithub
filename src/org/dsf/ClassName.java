package org.dsf;

import java.util.ArrayList;

class ClassName {
	private String className;
	private ArrayList<String> words = new ArrayList<>();
	
	public ClassName(String qualifiedName) {
		int idx = qualifiedName.lastIndexOf('.');
		className = (idx < 0) ? qualifiedName : qualifiedName.substring(idx + 1);
		
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
	
	public boolean matches(String pattern) {
		if (pattern == null || pattern.trim().length() == 0) {
			return false;
		}
		
		pattern = removeDuplicateWildcards(pattern);
		
		return matches(pattern, 0, 0);
	}
	
	private boolean matches(String pattern, int wordIdx, int offset) {
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
		
		//TODO: it's just for tests, remove later
		/*if (Character.isUpperCase(pattern.charAt(0))) {
			if (offset > 0) {
				System.err.println("Offset > 0!!! Pattern: " + pattern + "; wordIdx: " + wordIdx + 
						"; offset: " + offset + "; words: " + words);
			}
		}*/
		
		if (pattern.charAt(0) == ' ') {
			return isLastWord(wordIdx);
		}
		
		if (pattern.charAt(0) == '*') {
			return handleWildcard(pattern, wordIdx, offset);
		}
		
		if (pattern.charAt(0) == word.charAt(offset)) {
			if (pattern.length() == 1) { //matched last symbol of the pattern
				return true;
			}
			
			if (pattern.charAt(1) == ' ') { //if current word should be the last
				return isLastWord(wordIdx);
			}
			
			if (Character.isUpperCase(pattern.charAt(1))) {
				return matches(pattern.substring(1), wordIdx + 1, 0);
			} else {
				return matches(pattern.substring(1), wordIdx, offset + 1);
			}
		} else {
			return false;
		}
	}
	
	private boolean handleWildcard(String pattern, int wordIdx, int offset) {
		if (pattern.length() == 1) { //matched last symbol of the pattern
			return true;
		}
		
		String newPattern = pattern.substring(1);
		
		for (int newWordIdx = wordIdx; newWordIdx < words.size(); newWordIdx++) {
			int maxOffset = 0;
			
			if (!Character.isUpperCase(newPattern.charAt(0))) {
				//offset = 0;
				maxOffset = words.get(newWordIdx).length() - 1;
			}
			
			for (int newOffset = offset; newOffset <= maxOffset; newOffset++) {
				//System.err.println("newPattern: " + newPattern + "; newWordIdx: " + newWordIdx + "; newOffset: " + 
				//		newOffset + "; words: " + words);
				
				if (matches(newPattern, newWordIdx, newOffset)) {
					return true;
				}
			}
			
			offset = 0;
		}
		
		return false;
	}
	
	protected static String removeDuplicateWildcards(String pattern) {
		StringBuilder buff = new StringBuilder();
		boolean wildcardCopied = false;
		
		for (char c : pattern.toCharArray()) {
			if (c == '*') {
				if (wildcardCopied) {
					continue;
				} else {
					buff.append(c);
					wildcardCopied = true;
				}
			} else {
				buff.append(c);
				wildcardCopied = false;
			}
		}
		
		return buff.toString();
	}

	protected boolean isLastWord(int wordIdx) {
		return wordIdx == words.size() - 1;
	}

	@Override
	public String toString() {
		return className;
	}

	/** This method is intended for unit tests */
	protected String[] getWords() {
		return words.toArray(new String[words.size()]);
	}
}
