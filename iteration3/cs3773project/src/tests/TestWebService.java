package tests;

import static org.junit.Assert.*;

import java.io.File;

import madmarcos.HTTPMethod;
import madmarcos.WSStuff;

import org.junit.Test;

public class TestWebService {

	
	/*
	 * 
	 * Testign creating a WSStuff object
	 */
	@Test
	public void testWSStuff_null(){
		String input  = null;
		String iaexception_message = "WS URL must be valid HTTP or HTTPS web resource";
		try{
			WSStuff simpleConnect = new WSStuff(input);
		}catch(IllegalArgumentException e){
			assertEquals(iaexception_message, e.getMessage());
		}
	}
	
	@Test
	public void testWSStuff_emptystring(){
		String input = "";
		String iaexception_message = "WS URL must be valid HTTP or HTTPS web resource";
		try{
			WSStuff simpleConnect = new WSStuff(input);
		}catch(IllegalArgumentException e){
			assertEquals(iaexception_message, e.getMessage());
		}
	}
	
	@Test(expected =IllegalArgumentException.class)
	public void nonurl(){
		WSStuff simpleConnect = new WSStuff("notaurl");
		String response  = simpleConnect.sendRequest(HTTPMethod.GET, "");
		assertEquals("WS URL must be valid HTTP or HTTPS web resource", response);

	}
	
	@Test
	public void invalidurl(){
		WSStuff simpleConnect = new WSStuff("http://devcloud.fulgentcorp.com/doesnotexit/test.php");
		try{
			simpleConnect.sendRequest(HTTPMethod.GET, "");
		}catch(RuntimeException e){
			assertEquals("Web resource not found!", e.getMessage());
		}
		
	}
	
	
	/*
	 * Test sendRequest
	 */
/*

	@Test(timeout = 5000)
	public void testbadSSL(){
		WSStuff simpleConnect = new WSStuff("http://devcloud.fulgentcorp.com/testbad.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		try{
			simpleConnect.sendRequest(HTTPMethod.GET, "");
		}catch(Exception e){
			assertEquals("test timed out after 5000 milliseconds", e.getMessage());
		}
		//assertEquals("this is only a test", response);
	}
	*/
	
	
	
	
	/*
	 * Test initSSLContext
	 */

	@Test
	public void testSSL(){
		WSStuff simpleConnect = new WSStuff("http://devcloud.fulgentcorp.com/test.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response  = simpleConnect.sendRequest(HTTPMethod.GET, "");
		assertEquals("this is only a test", response);
	}
	
	
	@Test
	public void testSSL_nullcert(){
		File file = null;
		WSStuff simpleConnect = new WSStuff("http://devcloud.fulgentcorp.com/test.php");
		try{
			simpleConnect.initSSLContext( file);
		}catch(IllegalArgumentException e){
			assertEquals("Invalid cert file!", e.getMessage());
		}
		
	}
	
	@Test
	public void testSSL_notcerfile(){
		String file = "notacert.txt";
		
		WSStuff simpleConnect = new WSStuff("http://devcloud.fulgentcorp.com/test.php");
		try{
			simpleConnect.initSSLContext( new File(file));
		}catch(RuntimeException e){
			assertEquals("Cannot create SSLContext: Cannot create SSLContext: initKeyStore returned false", e.getMessage());
		}
	}

	
	@Test
	public void testHTTP1(){
		WSStuff simpleConnect = new WSStuff("http://devcloud.fulgentcorp.com/test.php");
		String response  = simpleConnect.sendRequest(HTTPMethod.GET, "");
		assertEquals("this is only a test", response);
		
	}
	

	
}
