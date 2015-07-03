package TenPines.SecretPal;

import static org.junit.Assert.*;

import org.junit.Test;


public class AppTest {
	
	@Test
	public void helloWorldTest(){
		App app = new App();
		assertEquals(app.helloWorld(), "Hello world!");
	}

}
