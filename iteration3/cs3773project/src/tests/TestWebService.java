package tests;

import static org.junit.Assert.*;

import java.io.File;

import madmarcos.HTTPMethod;
import madmarcos.WSStuff;

import org.junit.Test;

public class TestWebService {

	@Test
	public void testHTTP1(){
		WSStuff simpleConnect = new WSStuff("http://devcloud.fulgentcorp.com/test.php");
		String response  = simpleConnect.sendRequest(HTTPMethod.GET, "");
		assertEquals("this is only a test", response);
		
	}
	
	@Test(expected =IllegalArgumentException.class)
	public void nonurl(){
		WSStuff simpleConnect = new WSStuff("notaurl");
		String response  = simpleConnect.sendRequest(HTTPMethod.GET, "");
		assertEquals("WS URL must be valid HTTP or HTTPS web resource", response);

	}
	
	@Test(expected = RuntimeException.class)
	public void invalidurl(){
		WSStuff simpleConnect = new WSStuff("http://xdevcloud.fulgentcorp.com/test.php");
		String response  = simpleConnect.sendRequest(HTTPMethod.GET, "");
		assertEquals("Web resource not found!", response);
	}
	
	@Test
	public void testSSL(){
		WSStuff simpleConnect = new WSStuff("http://devcloud.fulgentcorp.com/test.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response  = simpleConnect.sendRequest(HTTPMethod.GET, "");
		assertEquals("this is only a test", response);
	}
	
	@Test
	public void timeout_test(){
		
	}
}
