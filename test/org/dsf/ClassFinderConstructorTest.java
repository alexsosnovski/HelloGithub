package org.dsf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ClassFinderConstructorTest {
	final String encoding = ClassFinder.SUPPORTED_ENCODING;
	
	@Test
	public void testEmptyStream() throws Exception {
		InputStream in = new ByteArrayInputStream("".getBytes(encoding));
		ClassFinder finder = new ClassFinder(in);
		assertEquals(0, finder.getClasses().length);
	}
	
	@Test
	public void testSingleClass() throws Exception {
		InputStream in = new ByteArrayInputStream("my.MyClass".getBytes(encoding));
		ClassFinder finder = new ClassFinder(in);
		assertEquals(1, finder.getClasses().length);
		assertEquals("MyClass", finder.getClasses()[0].toString());
	}
	
	@Test
	public void testMultipleClasses() throws Exception {
		InputStream in = new ByteArrayInputStream("my.MyClass\nmy.MySecondClass".getBytes(encoding));
		ClassFinder finder = new ClassFinder(in);
		assertEquals(2, finder.getClasses().length);
		assertEquals("MyClass", finder.getClasses()[0].toString());
		assertEquals("MySecondClass", finder.getClasses()[1].toString());
	}
	
	@Test
	public void testEmptyLinesIgnored() throws Exception {
		InputStream in = new ByteArrayInputStream("\n\nmy.MyClass\n\n".getBytes(encoding));
		ClassFinder finder = new ClassFinder(in);
		assertEquals(1, finder.getClasses().length);
		assertEquals("MyClass", finder.getClasses()[0].toString());
	}
	
	@Test
	public void testWhitespacesIgnored() throws Exception {
		InputStream in = new ByteArrayInputStream("  my . My Class  ".getBytes(encoding));
		ClassFinder finder = new ClassFinder(in);
		assertEquals(1, finder.getClasses().length);
		assertEquals("MyClass", finder.getClasses()[0].toString());
	}
	
	@Test
	public void testTabsIgnored() throws Exception {
		InputStream in = new ByteArrayInputStream("\t\tmy.My\tClass\t\t".getBytes(encoding));
		ClassFinder finder = new ClassFinder(in);
		assertEquals(1, finder.getClasses().length);
		assertEquals("MyClass", finder.getClasses()[0].toString());
	}
	
	@Test
	public void testCarriageReturnsIgnored() throws Exception {
		InputStream in = new ByteArrayInputStream("\r\rmy.My\rClass\r\r".getBytes(encoding));
		ClassFinder finder = new ClassFinder(in);
		assertEquals(1, finder.getClasses().length);
		assertEquals("MyClass", finder.getClasses()[0].toString());
	}
	
	@Test
	public void testOftenUsedSpecialCharsNotIgnored() throws Exception {
		InputStream in = new ByteArrayInputStream("my.$Proxy1_MyClass$1".getBytes(encoding));
		ClassFinder finder = new ClassFinder(in);
		assertEquals(1, finder.getClasses().length);
		assertEquals("$Proxy1_MyClass$1", finder.getClasses()[0].toString());
	}
	
	@Test
	public void testNonAsciiChars() throws Exception {
		InputStream in = new ByteArrayInputStream("my.МойÜberКласс".getBytes(encoding));
		ClassFinder finder = new ClassFinder(in);
		assertEquals(1, finder.getClasses().length);
		assertEquals("МойÜberКласс", finder.getClasses()[0].toString());
	}
}
