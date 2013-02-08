package org.dsf;

import java.io.InputStream;

public class PerformanceTest {
	public static void main(String[] args) throws Exception {
		int startIdx = (int) 'A';
		int endIdx = (int) 'Z';
		ClassFinder classFinder;
		
		try (InputStream testStream = PerformanceTest.class.getClassLoader().getResourceAsStream("GF_classNames.txt")) {	
			classFinder = new ClassFinder(testStream);	
		} 
		
		long time = performTest(classFinder, startIdx, endIdx, "");
		System.out.println("Simple pattern search took on average " + time + " milliseconds");
		
		time = performTest(classFinder, startIdx, endIdx, "*");
		System.out.println("Wildcard pattern search took on average " + time + " milliseconds");
	}
	
	private static long performTest(ClassFinder classFinder, int startIdx, int endIdx, String prefix) {
		long startTime = System.currentTimeMillis();
		
		for (int i = startIdx; i <= endIdx; i++) {
			StringBuilder buff = new StringBuilder();
			buff.append(prefix).append((char) i);
			
			System.err.println(buff.toString());
			
			System.err.println(classFinder.findMatching(buff.toString()));
		}
		
		long endTime = System.currentTimeMillis();
		
		return (endTime - startTime) / (endIdx - startIdx + 1);
	}
}
