package tests;

import static org.junit.Assert.*;

import java.io.File;

import madmarcos.WSStuff;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import source.Session;
import source.User;

public class TestAddUser {

	public  String username;
	public  String password;
	public  User u;
	
	public WSStuff context;
	public Session adminsession;
	public String adminusername;
	public String adminpassword;
	public User adminUser;
	
	@BeforeClass
	public void setupbeforeclass(){
		//set up user
		this.username = "Jimmybob4";
		this.password = "jX0az84b";
		try {
			this.u = new User( this.username, this.password );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//set up WSSTuff connection and context;
		this.adminusername ="jbc878Bruneault" ;
		this.adminpassword="qcGQp8Z5tzahZXk" ;
		try {
			this.adminUser = new User( this.adminusername, this.adminpassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.context = new WSStuff("https://devcloud.fulgentcorp.com/bifrost/ws.php");
		context.initSSLContext( new File("fulgentcorp.cer"));
		
		try {
			this.adminsession = adminUser.login(this.context);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test_AddUser_success() {

	}

}
