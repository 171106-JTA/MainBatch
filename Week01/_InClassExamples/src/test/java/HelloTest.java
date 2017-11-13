import static org.junit.Assert.*;

import org.junit.Test;

import com.revature.example.HelloWorld;

public class HelloTest {

	@Test
	public void test() {
		HelloWorld hw = new HelloWorld();

		assertEquals("Hello world", hw.returnHello());
	}

}
