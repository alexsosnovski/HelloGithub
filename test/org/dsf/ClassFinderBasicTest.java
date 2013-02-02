package org.dsf;

import java.io.ByteArrayInputStream;
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
	final String encoding = ClassFinder.SUPPORTED_ENCODING;
	
	private InputStream testStream;
	private ClassFinder classFinder;
	
	@Test
	public void basicEmptyPattern() {
		Collection<String> result = classFinder.findMatching("");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(0, arr.length);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void basicNullPattern() {
		classFinder.findMatching(null);
	}
	
	@Test
	public void basicOneCapitalChar() {
		Collection<String> result = classFinder.findMatching("T");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(3, arr.length);
		assertEquals("TestClass", arr[0]);
		assertEquals("TestClassSecond", arr[1]);
		assertEquals("TestClassThird", arr[2]);
	}
	
	@Test
	public void basicTwoCapitalChars() {
		Collection<String> result = classFinder.findMatching("TC");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(3, arr.length);
		assertEquals("TestClass", arr[0]);
		assertEquals("TestClassSecond", arr[1]);
		assertEquals("TestClassThird", arr[2]);
	}
	
	@Test
	public void basicSpecifyingChars() {
		Collection<String> result = classFinder.findMatching("TeCla");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(3, arr.length);
		assertEquals("TestClass", arr[0]);
		assertEquals("TestClassSecond", arr[1]);
		assertEquals("TestClassThird", arr[2]);
	}
	
	@Test
	public void basicSpecifyingCharsNoResult() {
		Collection<String> result = classFinder.findMatching("TeCzz");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(0, arr.length);
	}
	
	@Test
	public void basicSpecifyingCharsNoResult2() {
		Collection<String> result = classFinder.findMatching("TeClasss");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(0, arr.length);
	}
	
	@Test
	public void basicTrailingWhitespace() {
		Collection<String> result = classFinder.findMatching("TC ");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(1, arr.length);
		assertEquals("TestClass", arr[0]);
	}
	
	@Test
	public void basicTrailingWhitespaceSpecifyingChars() {
		Collection<String> result = classFinder.findMatching("TCla ");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(1, arr.length);
		assertEquals("TestClass", arr[0]);
	}
	
	@Test
	public void basicWildcardInBetween() {
		Collection<String> result = classFinder.findMatching("M*P");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(2, arr.length);
		assertEquals("MyPrecious", arr[0]);
		assertEquals("MySuperPrecious", arr[1]);
	}
	
	@Test
	public void basicWildcardInBetween2() {
		Collection<String> result = classFinder.findMatching("M*SP");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(1, arr.length);
		assertEquals("MySuperPrecious", arr[0]);
	}
	
	@Test
	public void basicWildcardInBetweenWithSpecifyingChars() {
		Collection<String> result = classFinder.findMatching("M*P*o");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(2, arr.length);
		assertEquals("MyPrecious", arr[0]);
		assertEquals("MySuperPrecious", arr[1]);
	}
	
	@Test
	public void basicLeadingWildcard() {
		Collection<String> result = classFinder.findMatching("*B");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(2, arr.length);
		assertEquals("FooBar", arr[0]);
		assertEquals("FooBarBaz", arr[0]);
	}
	
	@Test
	public void basicTrailingWildcard() {
		Collection<String> result = classFinder.findMatching("TC*");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(3, arr.length);
		assertEquals("TestClass", arr[0]);
		assertEquals("TestClassSecond", arr[1]);
		assertEquals("TestClassThird", arr[2]);
	}
	
	@Test
	public void testOrdering() throws Exception {
		InputStream in = new ByteArrayInputStream("zy.MyClass\nmy.MyClass2\nmy.MyClassa".getBytes(encoding));
		ClassFinder finder = new ClassFinder(in);
		Collection<String> result = finder.findMatching("MC");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(3, arr.length);
		assertEquals("MyClass", arr[0]);
		assertEquals("MyClass2", arr[1]);
		assertEquals("MyClassa", arr[2]);	
	}
	
	@Test
	public void lowercaseFirstChar() {
		Collection<String> result = classFinder.findMatching("kC");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(1, arr.length);
		assertEquals("kneeCop", arr[0]);
	}
	
	@Test
	public void lowercaseFirstCharWithSpecifyingChar() {
		Collection<String> result = classFinder.findMatching("knC");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(1, arr.length);
		assertEquals("kneeCop", arr[0]);
	}
	
	@Test
	public void lowercaseFirstCharNoResult() {
		Collection<String> result = classFinder.findMatching("fB");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(0, arr.length);
	}
	
	@Test
	public void lowercaseFirstCharWithSpecifyingCharNoResult() {
		Collection<String> result = classFinder.findMatching("kCr");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(0, arr.length);
	}
	
	@Ignore
	@Test
	public void advancedTrailingWhitespaceNoResult() {
		Collection<String> result = classFinder.findMatching("TCl ");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(0, arr.length);
	}
	
	@Ignore
	@Test
	public void advancedLowercaseChars() {
		Collection<String> result = classFinder.findMatching("tct");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(1, arr.length);
		assertEquals("TestClassThird", arr[0]);
	}
	
	@Ignore
	@Test
	public void advancedLowercaseCharInBetween() {
		Collection<String> result = classFinder.findMatching("TcT");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(1, arr.length);
		assertEquals("TestClassThird", arr[0]);
	}
	
	@Ignore
	@Test
	public void advancedLowercaseCharsNoResult() {
		Collection<String> result = classFinder.findMatching("tct ");
		String[] arr = result.toArray(new String[result.size()]);
		assertEquals(1, arr.length);
		assertEquals("TestClassThird", arr[0]);
	}
	
	@Before
	public void setUp() {
		testStream = this.getClass().getClassLoader().getResourceAsStream("classNames1.txt");
		classFinder = new ClassFinder(testStream);
	}
	
	@After
	public void tearDown() throws Exception {
		if (testStream != null) {
			testStream.close();
		}
	}
}
