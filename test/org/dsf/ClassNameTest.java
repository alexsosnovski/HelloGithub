package org.dsf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ClassNameTest {
	@Test(expected = NullPointerException.class)
	public void testConstructorNullArgument() {
		new ClassName(null);
	}
	
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
	
	@Test
	public void testNonAsciiChars() {
		ClassName className = new ClassName("моя.прелесссть.МойÜberКласс");
		assertEquals("МойÜberКласс", className.toString());
		assertEquals(3, className.getWords().length);
		assertEquals("Мой", className.getWords()[0]);
		assertEquals("Über", className.getWords()[1]);
		assertEquals("Класс", className.getWords()[2]);
	}
	
	@Test
	public void testRemoveDuplicateWildcards() {
		assertEquals("*", ClassName.removeDuplicateWildcards("****"));
		assertEquals("*A*B*", ClassName.removeDuplicateWildcards("**A**B**"));
		assertEquals("*A*B*", ClassName.removeDuplicateWildcards("*A*B*"));
	}
	
	@Test
	public void testIsLastWord() {
		ClassName className = new ClassName("MyTestClass");
		assertEquals(3, className.getWords().length);
		assertFalse(className.isLastWord(0));
		assertFalse(className.isLastWord(1));
		assertTrue(className.isLastWord(2));
		assertFalse(className.isLastWord(-1));
		assertFalse(className.isLastWord(3));
	}
}
