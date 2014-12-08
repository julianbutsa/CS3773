package source;

import java.util.regex.*;
import java.lang.Throwable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import madmarcos.CryptoStuff;
import madmarcos.HTTPMethod;
import madmarcos.WSStuff;

public class User {
	private String login;
	private String password;
	public String userName;
	
	public User(String l, String p) throws Exception{
		if(User.validLogin(l) && User.validPassword(p)){
			this.login = l;
			this.password = CryptoStuff.hashSha256(p);
		}
		else{
			//throw new Exception("failed to make user");
		}
	}
	/*
	public User(String l, String p, userName){
		
	}*/


	public static boolean validLogin(String login){
		//no nulls
		if(login == null)
			return false;
		// too short
		if(login.length() < 8)
			return false;
		//too long
		if(login.length() > 100)
			return false;
		//special characters
		if(!Pattern.matches("^[A-Za-z0-9]*$",login))
			return false;
		
		
		
		return true;
	}
	
	public String getLogin() {
		return login;
	}


	public String getPassword() {
		return password;
	}


	public String getUserName() {
		return userName;
	}


	public static boolean validPassword(String password){
		if(password == null){
			//System.out.println("Null");
			return false;
		}
		if(password.length() < 8){
			//System.out.println("short");
			return false;
		}
		if(password.length() > 20){
			//System.out.println("long");
			return false;
		}
		if(!Pattern.matches("^[A-Za-z].*", password)){
			//System.out.println("does not start with a letter");
			return false;
		}
		
		if(Pattern.matches("^[a-z0-9]*$", password) 
			|| Pattern.matches("^[A-Z0-9]*$", password)  
			|| Pattern.matches("^[a-zA-Z]*$", password)  ){
			//System.out.println("Missing Something");
			return false;
		}
		
		if(!Pattern.matches("^[A-Za-z0-9]*$", password)){
			//System.out.println("Special characters");
			return false;
		}
		return true;
	}
	
	public Session login(WSStuff context) throws JSONException, Exception{
		Session newsession;
		JSONArray loginInfo = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("action", "login");
		loginInfo.put(obj);
		obj = new JSONObject();
		obj.put("login", this.login);
		loginInfo.put(obj);
		
		obj = new JSONObject();
		obj.put("password", this.password);
		loginInfo.put(obj);
		
		String response = context.sendRequest(HTTPMethod.GET, MyUtilities.sha256adder(loginInfo, ""));
		JSONArray rArray = new JSONArray(response);
		String responsetype = (String) MyUtilities.getJSONObject("result", rArray).get("result");
		
		if(responsetype.equals( "error")){
			throw new Exception (  (String) MyUtilities.getJSONObject("message", rArray).get("message"));
		}
		
		newsession = new Session(MyUtilities.getJSONObject("session_id", rArray).get("session_id"),
								MyUtilities.getJSONObject("session_salt", rArray).get("session_salt"));
		return newsession;
	}

	public static String hello(Session usersession, WSStuff simpleConnect) throws Exception {
		// TODO Auto-generated method stub
		String returnString = "";
		JSONArray helloRequest = new JSONArray();
		
		JSONObject action = new JSONObject();
		action.put("action", "hello");
		JSONObject sessionid = new JSONObject();
		action.put("session_id", usersession.getSessionID());
		
		
		helloRequest.put(action);
		helloRequest.put(sessionid);
		
		String response = simpleConnect.sendRequest(HTTPMethod.GET, MyUtilities.sha256adder(helloRequest, usersession.getSessionSalt()));
		JSONArray rArray = new JSONArray ( response);
		
		JSONObject returnType = MyUtilities.getJSONObject("result", rArray);
		if(returnType.get("result").equals("success"))
			returnString = (String) MyUtilities.getJSONObject("message", rArray).get("message");
		else{
			throw new Exception ( (String) MyUtilities.getJSONObject("message", rArray).get("message"));
		}
		return returnString;
	}


	public static boolean addUser (User usertoadd, Session adminsession, WSStuff context) {
		// TODO Auto-generated method stub
		if(usertoadd == null){
			throw new IllegalArgumentException("User cannot be null");
		}
		
		
		return true;
	}
	
	
	
}
