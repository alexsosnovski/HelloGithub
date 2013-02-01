package org.dsf;

import java.io.InputStream;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TestRunner {
	private InputStream testStream;
	private ClassFinder classFinder;
	
	@Test
	private void testOneCapitalChar() {
		Collection<String> result = classFinder.findMatching("T");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(3, arr.length);
		assertEquals("foo.bar.TestClass", arr[0]);
		assertEquals("foo.bar.TestClassSecond", arr[1]);
		assertEquals("foo.bar.TestClassThird", arr[2]);
	}
	
	@Test
	private void testTwoCapitalChars() {
		Collection<String> result = classFinder.findMatching("TC");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(3, arr.length);
		assertEquals("foo.bar.TestClass", arr[0]);
		assertEquals("foo.bar.TestClassSecond", arr[1]);
		assertEquals("foo.bar.TestClassThird", arr[2]);
	}
	
	@Test
	private void testSpecifyingChars() {
		Collection<String> result = classFinder.findMatching("TeCla");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(3, arr.length);
		assertEquals("foo.bar.TestClass", arr[0]);
		assertEquals("foo.bar.TestClassSecond", arr[1]);
		assertEquals("foo.bar.TestClassThird", arr[2]);
	}
	
	@Test
	private void testSpecifyingCharsNoResult() {
		Collection<String> result = classFinder.findMatching("TeCzz");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(0, arr.length);
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
