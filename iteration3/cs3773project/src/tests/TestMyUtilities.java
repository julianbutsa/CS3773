package tests;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import madmarcos.CryptoStuff;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import source.MyUtilities;

public class TestMyUtilities {


	@Test
	public void test_getJSONObject(){
		//valid test.  This is normal input / behavior
		String testname = "name";
		String testvalue = "John";
		JSONObject testobject = new JSONObject();
		testobject.put(testname, testvalue);
		
		JSONArray testarray = new JSONArray();
		testarray.put(testobject);
		
		assertEquals(MyUtilities.getJSONObject(testname, testarray), testobject);
	}
	
	@Test
	public void test_getJSONObject_objectdat(){
		//valid test.  This is normal input / behavior
		String testname = "name";
		String testvalue = "John";
		JSONObject testobject = new JSONObject();
		testobject.put(testname, testvalue);
		
		JSONArray testarray = new JSONArray();
		testarray.put(testobject);
		JSONObject returnobj = MyUtilities.getJSONObject(testname, testarray);
		assertEquals(returnobj.get(testname), testvalue);
		
	}
	
	@Test
	public void test_getJSONObject_ints(){
		String testname = "number" ;
		int testvalue = 1234;
		JSONObject testobject = new JSONObject();
		testobject.put(testname,  testvalue);
		
		JSONArray testarray = new JSONArray();
		testarray.put(testobject);
		
		JSONObject returnobj = MyUtilities.getJSONObject(testname,  testarray);
		assertEquals(returnobj.get(testname), testvalue);
	}
	
	@Test
	public void test_getJSONObject_bools(){
		String testname = "number" ;
		boolean testvalue = true;
		JSONObject testobject = new JSONObject();
		testobject.put(testname,  testvalue);
		
		JSONArray testarray = new JSONArray();
		testarray.put(testobject);
		
		JSONObject returnobj = MyUtilities.getJSONObject(testname,  testarray);
		assertEquals(returnobj.get(testname), testvalue);
	}
	
	
	@Test
	public void test_getJSONObject_notfound(){
		String testwrongname = "age";
		String testname = "name";
		String testvalue = "John";
		JSONObject testobject = new JSONObject();
		testobject.put(testname, testvalue);
		
		JSONArray testarray = new JSONArray();
		testarray.put(testobject);
		
		assertNull(MyUtilities.getJSONObject(testwrongname, testarray));
	}
	
	@Test
	public void test_getJSONObject_nullkey(){
		String testnull = null;

		String testname = "name";
		String testvalue = "John";
		JSONObject testobject = new JSONObject();
		testobject.put(testname, testvalue);
		
		JSONArray testarray = new JSONArray();
		testarray.put(testobject);
		
		assertNull(MyUtilities.getJSONObject(testnull, testarray));
	}
	
	
	@Test
	public void test_getJSONObject_nullarray(){
		String testname = "name";
		JSONArray testarray = null;
		assertNull(MyUtilities.getJSONObject(testname, testarray));
	}
	
	@Test
	public void test_getJSONObject_mixedarray(){
		JSONArray testarray = new JSONArray();
		testarray.put(410);
		testarray.put(true);
		String testname = "name";
		String testvalue = "John";
		JSONObject testobject = new JSONObject();
		testobject.put(testname, testvalue);
		
		testarray.put(testobject);
		
		assertEquals( testobject , MyUtilities.getJSONObject(testname, testarray));
	}
	/*
	@Test	25530 28707
	
	public void test() {
		fail("Not yet implemented");
	}
*/
	
	
	/*
	 * Test sha256hasher
	 */
	@Test
	public void test_sha256jarray_nullarray(){
		try{
			MyUtilities.sha256adder(null);
		}catch(InvalidParameterException e ){
			assertEquals("Cannot sha256 a null JSONArray.", e.getMessage());
		}
	}
	
	@Test
	public void test_sha256jarray_success(){
		JSONArray testarray = new JSONArray();
		JSONObject testobject = new JSONObject();
		testobject.put("key",  "value");
		JSONObject testobject2= new JSONObject();
		testobject2.put("key2",  "value2");
		testarray.put(testobject);
		testarray.put(testobject2);
		String hash = CryptoStuff.hashSha256(testarray.toString());
		JSONArray returnarray = new JSONArray(MyUtilities.sha256adder(testarray));
		assertEquals(hash, MyUtilities.getJSONObject("checksum", returnarray).get("checksum"));
		
	}
}
