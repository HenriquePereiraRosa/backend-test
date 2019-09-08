package br.com.hq.utils;

import static org.junit.Assert.*;

import java.time.DateTimeException;
import java.time.MonthDay;
import java.time.format.DateTimeParseException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JavaParserTest {

	private JavaParser parser;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		parser = new JavaParser();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals(140.00, parser.parseFloat("140.00"), 0.0001);
		assertEquals(140.00, parser.parseFloat(" 140. 00"), 0.0001);
		assertEquals(140.00, parser.parseFloat(" 140.00 "), 0.0001);
		assertEquals(-140.00, parser.parseFloat("-140.00"), 0.0001);
		assertEquals(140.00, parser.parseFloat("14 0.00 "), 0.0001);
		assertEquals(140.00, parser.parseFloat("14 0.00"), 0.0001);
		assertEquals(140.00, parser.parseFloat("1 40,00"), 0.0001);

		

		assertEquals(MonthDay.of(07, 31), parser.parseData("31/jul", '/'));
		assertEquals(MonthDay.of(07, 31), parser.parseData("31/ jul", '/'));
		assertEquals(MonthDay.of(07, 31), parser.parseData(" 31/ jul ", '/'));
		assertEquals(MonthDay.of(07, 31), parser.parseData(" 31_ jul ", '_'));
		assertEquals(MonthDay.of(07, 31), parser.parseData(" 31. jul ", '.'));
	}

}
