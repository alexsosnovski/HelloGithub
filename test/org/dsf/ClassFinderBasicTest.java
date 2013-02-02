package org.dsf;

import java.io.InputStream;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ClassFinderBasicTest {
	private InputStream testStream;
	private ClassFinder classFinder;
	
	@Test
	private void basicOneCapitalChar() {
		Collection<String> result = classFinder.findMatching("T");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(3, arr.length);
		assertEquals("TestClass", arr[0]);
		assertEquals("TestClassSecond", arr[1]);
		assertEquals("TestClassThird", arr[2]);
	}
	
	@Test
	private void basicTwoCapitalChars() {
		Collection<String> result = classFinder.findMatching("TC");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(3, arr.length);
		assertEquals("TestClass", arr[0]);
		assertEquals("TestClassSecond", arr[1]);
		assertEquals("TestClassThird", arr[2]);
	}
	
	@Test
	private void basicSpecifyingChars() {
		Collection<String> result = classFinder.findMatching("TeCla");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(3, arr.length);
		assertEquals("TestClass", arr[0]);
		assertEquals("TestClassSecond", arr[1]);
		assertEquals("TestClassThird", arr[2]);
	}
	
	@Test
	private void basicSpecifyingCharsNoResult() {
		Collection<String> result = classFinder.findMatching("TeCzz");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(0, arr.length);
	}
	
	@Test
	private void basicTrailingWhitespace() {
		Collection<String> result = classFinder.findMatching("TC ");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(1, arr.length);
		assertEquals("TestClass", arr[0]);
	}
	
	@Test
	private void basicWildcardInBetween() {
		Collection<String> result = classFinder.findMatching("F*B");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(2, arr.length);
		assertEquals("FooBar", arr[0]);
		assertEquals("FooBarBaz", arr[1]);
	}
	
	@Test
	private void basicLeadingWildcard() {
		Collection<String> result = classFinder.findMatching("*B");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(2, arr.length);
		assertEquals("FooBar", arr[0]);
		assertEquals("FooBarBaz", arr[0]);
	}
	
	@Ignore
	@Test
	private void advancedTrailingWhitespaceNoResult() {
		Collection<String> result = classFinder.findMatching("TCl ");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(0, arr.length);
	}
	
	@Ignore
	@Test
	private void advancedLowercaseChars() {
		Collection<String> result = classFinder.findMatching("tct");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(1, arr.length);
		assertEquals("TestClassThird", arr[0]);
	}
	
	@Ignore
	@Test
	private void advancedLowercaseCharInBetween() {
		Collection<String> result = classFinder.findMatching("TcT");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(1, arr.length);
		assertEquals("TestClassThird", arr[0]);
	}
	
	@Ignore
	@Test
	private void advancedLowercaseCharsNoResult() {
		Collection<String> result = classFinder.findMatching("tct ");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(1, arr.length);
		assertEquals("TestClassThird", arr[0]);
	}
	
	@Before
	private void setUp() {
		testStream = this.getClass().getClassLoader().getResourceAsStream("classNames1.txt");
		classFinder = new ClassFinder(testStream);
	}
	
	@After
	private void tearDown() throws Exception {
		if (testStream != null) {
			testStream.close();
		}
	}
}
