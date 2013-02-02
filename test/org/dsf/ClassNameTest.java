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
		assertEquals(1, className.getWordsArr().length);
		assertEquals("Test", className.getWordsArr()[0]);
	}
	
	@Test
	public void testDefaultPackageMultiWordClass() {
		ClassName className = new ClassName("MyTest");
		assertEquals("MyTest", className.toString());
		assertEquals(2, className.getWordsArr().length);
		assertEquals("My", className.getWordsArr()[0]);
		assertEquals("Test", className.getWordsArr()[1]);
	}
	
	@Test
	public void testMultiWordClass() {
		ClassName className = new ClassName("foo.bar.MyTest");
		assertEquals("MyTest", className.toString());
		assertEquals(2, className.getWordsArr().length);
		assertEquals("My", className.getWordsArr()[0]);
		assertEquals("Test", className.getWordsArr()[1]);
	}
	
	@Test
	public void testNestedClassName() {
		ClassName className = new ClassName("foo.bar.My$Test");
		assertEquals("My$Test", className.toString());
		assertEquals(2, className.getWordsArr().length);
		assertEquals("My$", className.getWordsArr()[0]);
		assertEquals("Test", className.getWordsArr()[1]);
	}
	
	@Test
	public void testLowercaseFirstChar() {
		ClassName className = new ClassName("foo.bar.myTest");
		assertEquals("myTest", className.toString());
		assertEquals(2, className.getWordsArr().length);
		assertEquals("my", className.getWordsArr()[0]);
		assertEquals("Test", className.getWordsArr()[1]);
	}
	
	@Test
	public void testProxyClassName() {
		ClassName className = new ClassName("foo.bar.$Proxy1");
		assertEquals("$Proxy1", className.toString());
		assertEquals(2, className.getWordsArr().length);
		assertEquals("$", className.getWordsArr()[0]);
		assertEquals("Proxy1", className.getWordsArr()[1]);
	}
	
	@Test
	public void testNonAsciiChars() {
		ClassName className = new ClassName("моя.прелесссть.МойÜberКласс");
		assertEquals("МойÜberКласс", className.toString());
		assertEquals(3, className.getWordsArr().length);
		assertEquals("Мой", className.getWordsArr()[0]);
		assertEquals("Über", className.getWordsArr()[1]);
		assertEquals("Класс", className.getWordsArr()[2]);
	}
	
	@Test
	public void testHashCode() {
		ClassName className = new ClassName("foo.bar.$Proxy1");
		assertEquals("$Proxy1".hashCode(), className.hashCode());
		
		className = new ClassName("SomeClass");
		assertEquals("SomeClass".hashCode(), className.hashCode());
	}
	
	@Test
	public void testEquals() {
		ClassName className1 = new ClassName("foo.bar.$Proxy1");
		ClassName className2 = new ClassName("$Proxy1");
		ClassName className3 = new ClassName("$Proxy2");
		
		assertFalse(className1.equals(null));
		assertFalse(className1.equals("$Proxy1"));
		assertTrue(className1.equals(className2));
		assertFalse(className1.equals(className3));
	}
	
	@Test
	public void testCompareTo() {
		ClassName className1 = new ClassName("foo.bar.$Proxy1");
		ClassName className2 = new ClassName("$Proxy1");
		ClassName className3 = new ClassName("$Proxy2");
		
		assertEquals(0, className1.compareTo(className2));
		assertTrue(className1.compareTo(className3) < 0);
		assertTrue(className3.compareTo(className2) > 0);
	}
}
