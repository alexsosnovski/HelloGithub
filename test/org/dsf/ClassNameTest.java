package org.dsf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ClassNameTest {
	@Test
	public void testDefaultPackageSingleWordClass() {
		ClassName className = new ClassName("Test");
		assertEquals("Test", className.toString());
		assertEquals(1, className.getWords().length);
		assertEquals("Test", className.getWords()[0]);
	}
	
	@Test
	public void testDefaultPackageMultiWordClass() {
		ClassName className = new ClassName("MyTest");
		assertEquals("MyTest", className.toString());
		assertEquals(2, className.getWords().length);
		assertEquals("My", className.getWords()[0]);
		assertEquals("Test", className.getWords()[1]);
	}
	
	@Test
	public void testMultiWordClass() {
		ClassName className = new ClassName("foo.bar.MyTest");
		assertEquals("MyTest", className.toString());
		assertEquals(2, className.getWords().length);
		assertEquals("My", className.getWords()[0]);
		assertEquals("Test", className.getWords()[1]);
	}
	
	@Test
	public void testNestedClassName() {
		ClassName className = new ClassName("foo.bar.My$Test");
		assertEquals("My$Test", className.toString());
		assertEquals(2, className.getWords().length);
		assertEquals("My$", className.getWords()[0]);
		assertEquals("Test", className.getWords()[1]);
	}
	
	@Test
	public void testLowercaseFirstChar() {
		ClassName className = new ClassName("foo.bar.myTest");
		assertEquals("myTest", className.toString());
		assertEquals(2, className.getWords().length);
		assertEquals("my", className.getWords()[0]);
		assertEquals("Test", className.getWords()[1]);
	}
	
	@Test
	public void testProxyClassName() {
		ClassName className = new ClassName("foo.bar.$Proxy1");
		assertEquals("$Proxy1", className.toString());
		assertEquals(2, className.getWords().length);
		assertEquals("$", className.getWords()[0]);
		assertEquals("Proxy1", className.getWords()[1]);
	}
}
