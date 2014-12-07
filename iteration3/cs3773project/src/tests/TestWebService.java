package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import madmarcos.HTTPMethod;
import madmarcos.WSStuff;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import source.MyUtilities;

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
	
	//Successful test without initializing a certificate
	
	@Test
	public void testHTTP1(){
		WSStuff simpleConnect = new WSStuff("http://devcloud.fulgentcorp.com/test.php");
		String response  = simpleConnect.sendRequest(HTTPMethod.GET, "");
		assertEquals("this is only a test", response);
		
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

	
	
	/*
	 * 
	 * Testing hello1 service
	 * 
	 */
	
	@Test
	public void test_service_hello1(){
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello1.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET, "");
		assertEquals( response, "hello yourself");
	}
	
	

/*
 * Testing hello2 service
 * 
 */
	

	@Test
	public void test_service_hello2_success(){
		
		//make an array and put an object in it
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		testobject.put("action", "hello2");
		testarray.put(testobject);
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello2.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET, testarray.toString());
		assertEquals( "hello again", response);
	}
	
	@Test
	public void test_service_hello2_noparameter(){

		
 		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello2.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET, "");
		assertEquals( response, "JSON argument is missing!");
	}
	
	
	@Test
	public void test_service_hello2_noaction(){
		
		//make an array and put an object in it
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		testobject.put("noaction", "hello2");
		testarray.put(testobject);
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello2.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET, testarray.toString());
		assertEquals( "Could not locate \"action\" key in JSON array argument!", response);
	}
	
	@Test
	public void test_service_hello2_invalidaction(){
		
		//make an array and put an object in it
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		testobject.put("action", "nothello2");
		testarray.put(testobject);
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello2.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET, testarray.toString());
		assertEquals( "Invalid action value!", response);
	}
	
	@Test
	public void test_service_hello2_invalidparam(){
		

		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello2.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET, "not a json array");
		assertEquals( "Invalid JSON array argument!", response);
	}
	
	
	/*
	 * Test hello3
	 */
	
	@Test
	public void test_service_hello3_success(){
		
		//make an array and put an object in it
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		JSONObject testobject2 = new JSONObject();
		testobject.put("action", "hello3");
		testobject2.put("my_name", "name");
		testarray.put(testobject);
		testarray.put(testobject2);
		
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello3.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET, testarray.toString());
		JSONArray responsearray = new JSONArray(response);
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("success", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("hello name", obj.get("message"));

	}
	
	
	@Test
	public void test_service_hello3_missingargument(){
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello3.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,"");
		JSONArray responsearray = new JSONArray(response);
		
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("error", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("JSON argument is missing!", obj.get("message"));
	}
	
	
	@Test
	public void test_service_hello3_invalidargument(){
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello3.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,"notajsonarray");
		JSONArray responsearray = new JSONArray(response);
		
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("error", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("Invalid JSON array argument!", obj.get("message"));
	}
	
	
	@Test
	public void test_service_hello3_noactionkey(){
		
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		JSONObject testobject2 = new JSONObject();
		testobject.put("notaction", "hello3");
		testobject2.put("my_name", "name");
		testarray.put(testobject);
		testarray.put(testobject2);
		
		
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello3.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,testarray.toString());
		JSONArray responsearray = new JSONArray(response);
		
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("error", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("Could not locate \"action\" key in JSON array argument!", obj.get("message"));
	}
	
	
	
	@Test
	public void test_service_hello3_nomyname(){
		
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		JSONObject testobject2 = new JSONObject();
		testobject.put("action", "hello3");
		testobject2.put("notmyname", "name");
		testarray.put(testobject);
		testarray.put(testobject2);
		
		
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello3.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,testarray.toString());
		JSONArray responsearray = new JSONArray(response);
		
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("error", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("Could not locate \"my_name\" key in JSON array argument!", obj.get("message"));
	}
	
	
	
	@Test
	public void test_service_hello3_invalidaction(){
		
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		JSONObject testobject2 = new JSONObject();
		testobject.put("action", "hello400000");
		testobject2.put("my_name", "name");
		testarray.put(testobject);
		testarray.put(testobject2);
		
		
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello3.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,testarray.toString());
		JSONArray responsearray = new JSONArray(response);
		
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("error", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("Invalid action value!", obj.get("message"));
	}
	
	/*
	 * Test hello4 service
	 */
	
	@Test
	public void test_service_hello4_success(){
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		JSONObject testobject2 = new JSONObject();
		JSONObject testchecksum  = new JSONObject();
		testobject.put("action", "hello4");
		testobject2.put("my_name", "name");
		
		testarray.put(testobject);
		testarray.put(testobject2);
		
		JSONArray checksumadded = new JSONArray(MyUtilities.sha256adder(testarray, ""));
		testchecksum.put("checksum", MyUtilities.getJSONObject("checksum",checksumadded).get("checksum"));
		
		testarray.put(testchecksum);
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello4.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,testarray.toString());
		JSONArray responsearray = new JSONArray(response);
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("success", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("hello name", obj.get("message"));
		
		
		JSONArray strippedresponse = new JSONArray();
		strippedresponse.put(MyUtilities.getJSONObject("result", responsearray));
		strippedresponse.put(MyUtilities.getJSONObject("message", responsearray));
		
		JSONArray srwithhash = new JSONArray(MyUtilities.sha256adder(strippedresponse, ""));
		
		obj = MyUtilities.getJSONObject("checksum", srwithhash);
		assertNotNull(obj);
		assertEquals( MyUtilities.getJSONObject("checksum", responsearray).get("checksum"), obj.get("checksum"));
		
	}
	
	
	@Test
	public void test_service_hello4_missingargument(){

		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello4.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,"");
		JSONArray responsearray = new JSONArray(response);
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("error", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("JSON argument is missing!", obj.get("message"));
		
		
		JSONArray strippedresponse = new JSONArray();
		strippedresponse.put(MyUtilities.getJSONObject("result", responsearray));
		strippedresponse.put(MyUtilities.getJSONObject("message", responsearray));
		
		JSONArray srwithhash = new JSONArray(MyUtilities.sha256adder(strippedresponse, ""));
		
		obj = MyUtilities.getJSONObject("checksum", srwithhash);
		assertNotNull(obj);
		assertEquals( MyUtilities.getJSONObject("checksum", responsearray).get("checksum"), obj.get("checksum"));
		
	}

	@Test
	public void test_service_hello4_InvalidJSONArgument(){

		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello4.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,"notajsonarray");
		JSONArray responsearray = new JSONArray(response);
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("error", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("Invalid JSON array argument!", obj.get("message"));
		
		
		JSONArray strippedresponse = new JSONArray();
		strippedresponse.put(MyUtilities.getJSONObject("result", responsearray));
		strippedresponse.put(MyUtilities.getJSONObject("message", responsearray));
		
		JSONArray srwithhash = new JSONArray(MyUtilities.sha256adder(strippedresponse, ""));
		
		obj = MyUtilities.getJSONObject("checksum", srwithhash);
		assertNotNull(obj);
		assertEquals( MyUtilities.getJSONObject("checksum", responsearray).get("checksum"), obj.get("checksum"));
		
	}
	
	@Test
	public void test_service_hello4_noaction(){
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		JSONObject testobject2 = new JSONObject();
		JSONObject testchecksum  = new JSONObject();
		testobject.put("noaction", "hello4");
		testobject2.put("my_name", "name");
		
		testarray.put(testobject);
		testarray.put(testobject2);
		
		JSONArray checksumadded = new JSONArray(MyUtilities.sha256adder(testarray, "'"));
		testchecksum.put("checksum", MyUtilities.getJSONObject("checksum",checksumadded).get("checksum"));
		
		testarray.put(testchecksum);
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello4.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,testarray.toString());
		JSONArray responsearray = new JSONArray(response);
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("error", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("Could not locate \"action\" key in JSON array argument!", obj.get("message"));
		
		
		JSONArray strippedresponse = new JSONArray();
		strippedresponse.put(MyUtilities.getJSONObject("result", responsearray));
		strippedresponse.put(MyUtilities.getJSONObject("message", responsearray));
		
		JSONArray srwithhash = new JSONArray(MyUtilities.sha256adder(strippedresponse, ""));
		
		obj = MyUtilities.getJSONObject("checksum", srwithhash);
		assertNotNull(obj);
		assertEquals( MyUtilities.getJSONObject("checksum", responsearray).get("checksum"), obj.get("checksum"));
		
	}
	
	
	public void test_service_hello4_nomyname(){
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		JSONObject testobject2 = new JSONObject();
		JSONObject testchecksum  = new JSONObject();
		testobject.put("action", "hello4");
		testobject2.put("noname", "name");
		
		testarray.put(testobject);
		testarray.put(testobject2);
		
		JSONArray checksumadded = new JSONArray(MyUtilities.sha256adder(testarray, ""));
		testchecksum.put("checksum", MyUtilities.getJSONObject("checksum",checksumadded).get("checksum"));
		
		testarray.put(testchecksum);
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello4.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,testarray.toString());
		JSONArray responsearray = new JSONArray(response);
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("error", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("Could not locate \"my_name\" key in JSON array argument!", obj.get("message"));
		
		
		JSONArray strippedresponse = new JSONArray();
		strippedresponse.put(MyUtilities.getJSONObject("result", responsearray));
		strippedresponse.put(MyUtilities.getJSONObject("message", responsearray));
		
		JSONArray srwithhash = new JSONArray(MyUtilities.sha256adder(strippedresponse, ""));
		
		obj = MyUtilities.getJSONObject("checksum", srwithhash);
		assertNotNull(obj);
		assertEquals( MyUtilities.getJSONObject("checksum", responsearray).get("checksum"), obj.get("checksum"));
		
	}
	
	
	public void test_service_hello4_nochecksum(){
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		JSONObject testobject2 = new JSONObject();
		JSONObject testchecksum  = new JSONObject();
		testobject.put("action", "hello4");
		testobject2.put("name", "name");
		
		testarray.put(testobject);
		testarray.put(testobject2);
		
		JSONArray checksumadded = new JSONArray(MyUtilities.sha256adder(testarray, ""));
		testchecksum.put("notchecksum", MyUtilities.getJSONObject("checksum",checksumadded).get("checksum"));
		
		testarray.put(testchecksum);
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello4.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,testarray.toString());
		JSONArray responsearray = new JSONArray(response);
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("error", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("Could not locate \"checksum\" key in JSON array argument!", obj.get("message"));
		
		
		JSONArray strippedresponse = new JSONArray();
		strippedresponse.put(MyUtilities.getJSONObject("result", responsearray));
		strippedresponse.put(MyUtilities.getJSONObject("message", responsearray));
		
		JSONArray srwithhash = new JSONArray(MyUtilities.sha256adder(strippedresponse, ""));
		
		obj = MyUtilities.getJSONObject("checksum", srwithhash);
		assertNotNull(obj);
		assertEquals( MyUtilities.getJSONObject("checksum", responsearray).get("checksum"), obj.get("checksum"));
		
	}
	
	
	public void test_service_hello4_invalidaction(){
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		JSONObject testobject2 = new JSONObject();
		JSONObject testchecksum  = new JSONObject();
		testobject.put("action", "hello502349801234");
		testobject2.put("name", "name");
		
		testarray.put(testobject);
		testarray.put(testobject2);
		
		JSONArray checksumadded = new JSONArray(MyUtilities.sha256adder(testarray, ""));
		testchecksum.put("checksum", MyUtilities.getJSONObject("checksum",checksumadded).get("checksum"));
		
		testarray.put(testchecksum);
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello4.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,testarray.toString());
		JSONArray responsearray = new JSONArray(response);
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("error", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("Invalid action value!", obj.get("message"));
		
		
		JSONArray strippedresponse = new JSONArray();
		strippedresponse.put(MyUtilities.getJSONObject("result", responsearray));
		strippedresponse.put(MyUtilities.getJSONObject("message", responsearray));
		
		JSONArray srwithhash = new JSONArray(MyUtilities.sha256adder(strippedresponse, ""));
		
		obj = MyUtilities.getJSONObject("checksum", srwithhash);
		assertNotNull(obj);
		assertEquals( MyUtilities.getJSONObject("checksum", responsearray).get("checksum"), obj.get("checksum"));
		
	}
	
	
	public void test_service_hello4_invalidchecksum(){
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		JSONObject testobject2 = new JSONObject();
		JSONObject testchecksum  = new JSONObject();
		testobject.put("action", "hello502349801234");
		testobject2.put("name", "name");
		
		testarray.put(testobject);
		testarray.put(testobject2);
		
		testchecksum.put("checksum", "notachecksum");
		
		testarray.put(testchecksum);
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/hello4.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		String response = simpleConnect.sendRequest(HTTPMethod.GET,testarray.toString());
		JSONArray responsearray = new JSONArray(response);
		
		JSONObject obj = MyUtilities.getJSONObject("result", responsearray);
		assertNotNull(obj);
		assertEquals("error", obj.get("result"));
		
		obj = MyUtilities.getJSONObject("message", responsearray);
		assertNotNull(obj);
		assertEquals("Invalid checksum!", obj.get("message"));
		
		
		JSONArray strippedresponse = new JSONArray();
		strippedresponse.put(MyUtilities.getJSONObject("result", responsearray));
		strippedresponse.put(MyUtilities.getJSONObject("message", responsearray));
		
		JSONArray srwithhash = new JSONArray(MyUtilities.sha256adder(strippedresponse, ""));
		
		obj = MyUtilities.getJSONObject("checksum", srwithhash);
		assertNotNull(obj);
		assertEquals( MyUtilities.getJSONObject("checksum", responsearray).get("checksum"), obj.get("checksum"));
		
	}
}
