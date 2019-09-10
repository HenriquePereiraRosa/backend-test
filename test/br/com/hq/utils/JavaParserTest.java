package br.com.hq.utils;


import org.junit.*;

import java.time.MonthDay;

import static org.junit.Assert.assertEquals;

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
		assertEquals(140.00, parser.parseFloat("140,00"), 0.0001);
		assertEquals(140.00, parser.parseFloat(" 140, 00"), 0.0001);
		assertEquals(140.00, parser.parseFloat(" 140,00 "), 0.0001);
		assertEquals(-140.00, parser.parseFloat("-140,00"), 0.0001);
		assertEquals(140.00, parser.parseFloat("14 0,00 "), 0.0001);
		assertEquals(140.00, parser.parseFloat("14 0,00"), 0.0001);
		assertEquals(140.00, parser.parseFloat("1 40,00"), 0.0001);
		assertEquals(1040.00, parser.parseFloat("1. 040,00"), 0.0001);
		assertEquals(1040.00, parser.parseFloat("1 040,00"), 0.0001);
		assertEquals(-1040.00, parser.parseFloat("-1 040,00"), 0.0001);
		assertEquals(-1.00000000376832E14, parser.parseFloat("-100 .00 0..0 00.000.040,00"), 0.01);
		

		assertEquals(MonthDay.of(07, 31), parser.parseData("31/jul", '/'));
		assertEquals(MonthDay.of(07, 31), parser.parseData("31/ jul", '/'));
		assertEquals(MonthDay.of(07, 31), parser.parseData(" 31/ jul ", '/'));
		assertEquals(MonthDay.of(07, 31), parser.parseData(" 31_ jul ", '_'));
		assertEquals(MonthDay.of(07, 31), parser.parseData(" 31. jul ", '.'));
	}

}
