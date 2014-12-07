package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import source.MyUtilities;
import madmarcos.*;

import org.json.*;


public class TestAuthentication {

	public static String login;
	public static String password;
	public static String passwordhash;
	
	public static JSONArray wsrequest;
	
	@BeforeClass
	public static void setupbeforeclass(){
		TestAuthentication.login = "jbc878Bruneault";
		TestAuthentication.password = "qcGQp8Z5tzahZXk";
		TestAuthentication.passwordhash = CryptoStuff.hashSha256(TestAuthentication.password);
		
		JSONArray loginrequest = new JSONArray();
		
		JSONObject type = new JSONObject();
		type.put("action", "login");
		loginrequest.put(type);
		
		JSONObject login  = new JSONObject();
		login.put("login",  TestAuthentication.login);
		loginrequest.put(login);
		
		JSONObject pwhash = new JSONObject();
		pwhash.put("password", TestAuthentication.passwordhash);
		loginrequest.put(pwhash);
		
		TestAuthentication.wsrequest = new JSONArray(MyUtilities.sha256adder(loginrequest, ""));
		
	}
	
	
	
	/* Test wsstuff login*/
	
	
	@Test
	public void ws_success() {
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/ws.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		JSONArray response  = new JSONArray(simpleConnect.sendRequest(HTTPMethod.GET, TestAuthentication.wsrequest.toString()));
		assertNotNull(response);
		assertEquals("success", MyUtilities.getJSONObject("result", response).get("result"));
		assertEquals("hello Julian Bruneault", MyUtilities.getJSONObject("message", response).get("message"));
	}
	
	@Test
	public void ws_nojsonargument() {
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/ws.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		JSONArray response  = new JSONArray(simpleConnect.sendRequest(HTTPMethod.GET, ""));
		assertNotNull(response);
		assertEquals("error", MyUtilities.getJSONObject("result", response).get("result"));
		assertEquals("JSON argument is missing!", MyUtilities.getJSONObject("message", response).get("message"));
	}
	
	@Test
	public void ws_invalidjsonargument() {
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/ws.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		JSONArray response  = new JSONArray(simpleConnect.sendRequest(HTTPMethod.GET, "noargument"));
		assertNotNull(response);
		assertEquals("error", MyUtilities.getJSONObject("result", response).get("result"));
		assertEquals("Invalid JSON array argument!", MyUtilities.getJSONObject("message", response).get("message"));
	}
	
	@Test
	public void ws_noaction() {
		JSONArray noactionarray = new JSONArray(TestAuthentication.wsrequest.toString());
		noactionarray.remove(0);
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/ws.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		JSONArray response  = new JSONArray(simpleConnect.sendRequest(HTTPMethod.GET, noactionarray.toString()));
		assertNotNull(response);
		assertEquals("error", MyUtilities.getJSONObject("result", response).get("result"));
		assertEquals("Could not locate \"action\" key in JSON array argument!", MyUtilities.getJSONObject("message", response).get("message"));
	}
	
	
	
	@Test
	public void ws_nologin() {
		JSONArray noaction = new JSONArray();
		
		JSONObject type = new JSONObject();
		type.put("action", "login");
		noaction.put(type);
		
		JSONObject pwhash = new JSONObject();
		pwhash.put("password", TestAuthentication.passwordhash);
		noaction.put(pwhash);

		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/ws.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		JSONArray response  = new JSONArray(simpleConnect.sendRequest(HTTPMethod.GET, MyUtilities.sha256adder(noaction, "")));
		assertNotNull(response);
		assertEquals("error", MyUtilities.getJSONObject("result", response).get("result"));
		assertEquals("Could not locate \"login\" key in JSON array argument!", MyUtilities.getJSONObject("message", response).get("message"));
	}
	

	@Test
	public void ws_nopassword() {
		JSONArray nopassword = new JSONArray();
		JSONObject type = new JSONObject();
		type.put("action", "login");
		nopassword.put(type);
		
		JSONObject login  = new JSONObject();
		login.put("login",  TestAuthentication.login);
		nopassword.put(login);
		
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/ws.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		JSONArray response  = new JSONArray(simpleConnect.sendRequest(HTTPMethod.GET,MyUtilities.sha256adder(nopassword, "")));
		assertNotNull(response);
		assertEquals("error", MyUtilities.getJSONObject("result", response).get("result"));
		assertEquals("Could not locate \"password\" key in JSON array argument!", MyUtilities.getJSONObject("message", response).get("message"));
	}

	/*
	 * Returns the error about not finding session salt
	@Test
	public void ws_invalidAction() {
		JSONArray loginrequest = new JSONArray();
		
		JSONObject type = new JSONObject();
		type.put("action", "noaction");
		loginrequest.put(type);
		
		JSONObject login  = new JSONObject();
		login.put("login",  TestAuthentication.login);
		loginrequest.put(login);
		
		JSONObject pwhash = new JSONObject();
		pwhash.put("password", TestAuthentication.passwordhash);
		loginrequest.put(pwhash);
		
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/ws.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		JSONArray response  = new JSONArray(simpleConnect.sendRequest(HTTPMethod.GET, MyUtilities.sha256adder(loginrequest, "")));
		assertNotNull(response);
		assertEquals("error", MyUtilities.getJSONObject("result", response).get("result"));
		assertEquals("Invalid action value!", MyUtilities.getJSONObject("message", response).get("message"));
	}
	
	*/
	@Test
	public void ws_invalidlogin() {
		JSONArray loginrequest = new JSONArray();
		
		JSONObject type = new JSONObject();
		type.put("action", "login");
		loginrequest.put(type);
		
		JSONObject login  = new JSONObject();
		login.put("login",  TestAuthentication.login);
		loginrequest.put(login);
		
		JSONObject pwhash = new JSONObject();
		pwhash.put("password", "notapasswordhash");
		loginrequest.put(pwhash);
		
		
		WSStuff simpleConnect = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/ws.php");
		simpleConnect.initSSLContext( new File("fulgentcorp.cer"));
		JSONArray response  = new JSONArray(simpleConnect.sendRequest(HTTPMethod.GET, MyUtilities.sha256adder(loginrequest, "")));
		assertNotNull(response);
		assertEquals("error", MyUtilities.getJSONObject("result", response).get("result"));
		assertEquals("Unable to login!", MyUtilities.getJSONObject("message", response).get("message"));
	}


}
