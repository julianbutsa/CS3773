package tests;

import static org.junit.Assert.*;

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
}
