package source;

import java.util.regex.*;
import java.lang.Throwable;

import madmarcos.CryptoStuff;

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
			//throw new Exception();
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
}
