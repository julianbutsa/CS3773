package tests;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestJSON {

	private static JSONObject obj1;
	private static String obj1Encoded;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		obj1 = new JSONObject();
		obj1.put("testkeystring", "testvalue");
		obj1Encoded = "{\"testkeystring\":\"testvalue\"}";
		
	}
	
	@Test
	public void testValidObjectEncoding() {
		assertEquals(obj1Encoded, obj1.toString());
	}
	
	@Test
	public void testValidObjectDecoding1(){
		JSONObject o = new JSONObject(obj1Encoded);
		assertEquals("testvalue", o.get("testkeystring"));
		
	}

	
	@Test
	public void testArrayOfObjects(){
		JSONArray testarray = new JSONArray();
		JSONObject testobj1 = new JSONObject();
		testobj1.put("key1", "value1");
		JSONObject testobj2 = new JSONObject();
		testobj2.put("key2",  "value2");
		testarray.put(testobj1);
		testarray.put(testobj2);
		
		assertEquals(testobj1, testarray.get(0));
		assertEquals(testobj2, testarray.get(1));	
		
	}
	
	@Test
	public void testArray_returnval(){
		JSONArray testarray = new JSONArray();
		JSONObject testobj1 = new JSONObject();
		testobj1.put("key1", "value1");
		JSONObject testobj2 = new JSONObject();
		testobj2.put("key2",  "value2");
		testarray.put(testobj1);
		testarray.put(testobj2);
		
		JSONObject testresult = (JSONObject) testarray.get(0);
		assertEquals(testresult.get("key1"), "value1");
		
	}
}
