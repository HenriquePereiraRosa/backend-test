package test.br.com.hq.utils;

import br.com.hq.utils.JavaParser;
import org.junit.*;

import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;

public class JavaParserDateTimeParserTest {

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

	@Test(expected = DateTimeParseException.class)
	public void test() {
		parser.parseData(" 31. jul ", '/');
	}

}
