package tests;

import static org.junit.Assert.*;
import madmarcos.CryptoStuff;

import org.junit.BeforeClass;
import org.junit.Test;

import source.User;

public class TestLogin {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
	}

	/*
	 * login rules	 *
	 * 	Cannot be null
	 * 	between 10 and 100 characters
	 * 	upper and lower alph / numeric only. NO SPECIAL CHARACTESR
	 * 	Unique
	 */

	
	//excepting false
	@Test
	public void test_validlogin_nullinput(){
		assertEquals(false, User.validLogin(null));
	}
	
	//expecting false
	@Test
	public void test_validlogin_length_small(){
		String input = "abcdef";
		assertEquals(User.validLogin(input), false);
	}
	
	@Test
	public void test_validlogin_length_large(){
		String input = "";
		for(int i = 0;i < 102; i++)
			input += "a";
		assertEquals(User.validLogin(input), false);
	}
	//should accept any and all upper cases
	@Test
	public void test_validlogin_uppercase(){
		String input = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		assertEquals(User.validLogin(input), true);
	}
	//should accept any and all lower cases
	@Test public void test_validlogin_lowercase(){
		String input = "abcdefghijklmnopqrstuvwxyz";
		assertEquals(User.validLogin(input), true);
	}
	
	//should accept any and all numbers
	@Test
	public void test_validlogin_numbers(){
		String input = "1234567890";
		assertEquals(User.validLogin(input), true);
	}
	
	//validlogin should reject anything with these characters
	@Test
	public void test_validlogin_specialchars(){
		
		String input = "aaaaaaaaaX34";
		for(int i = 32 ;  i < 48; i++){
			assertEquals(User.validLogin(input + (char)i), false);
		}
		for(int i = 91; i < 97; i ++){
			assertEquals(User.validLogin(input + (char)i), false);
		}
		for(int i = 123; i <256;i++){
			assertEquals(User.validLogin(input + (char)i), false);
		}		
	}
	
	public void test_validlogin_validinput(){
		String testinput = "HelloMan34";
		assertEquals(true, User.validLogin(testinput));
	}
	/* Password requirements
	 *  8 - 20 characters
	 *  1 lower case at least
	 *  1 upper case at least
	 *  1 number at least
	 *  first char must be alpha
	 *  no special chars
	 */
	@Test
	public void test_validpassword_null(){
		String input = null;
		assertEquals(User.validPassword(input), false);
	}
	@Test
	public void test_validpassword_small(){
		String input = "Aa123";
		assertEquals(User.validPassword(input),false);
	}
	@Test
	public void test_validpassword_large(){
		String input = "A1a";
		for(int i = 0; i < 25; i++){
			input += "a";
		}
		assertEquals(User.validPassword(input),false);
	}
	@Test
	public void test_validpassword_nolowers(){
		String input = "AAA1AABA3";
		assertEquals(User.validPassword(input),false);
	}
	@Test
	public void test_validpassword_nouppers(){
		String input = "aaa13aaba34";
		assertEquals(User.validPassword(input),false);
	}
	@Test
	public void test_validpassword_nonumbers(){
		String input = "AaaAbBAkAl";
		assertEquals(User.validPassword(input),false);
	}
	@Test
	public void test_validpassword_firstchar(){
		String input = "1Abas0Adoz";
		assertEquals(User.validPassword(input),false);
	}
	
	@Test
	public void test_validpassword_specialchars(){
		String input = "a123Aaaaaa";
		for(int i = 32 ;  i < 48; i++){
			//System.out.printf("%c ", (char)i);
			String s = input+(char)i;
			assertEquals(User.validPassword(s), false);
			
		}
		System.out.println();
		for(int i = 58; i < 65; i++){
			//System.out.printf("%c ", (char)i);
			String s = input+(char)i;
			assertEquals(User.validPassword(s), false);
		}
		System.out.println();
		for(int i = 91; i < 97; i ++){
			//System.out.printf("%c ", (char)i);
			assertEquals(User.validPassword(input + (char)i), false);
		}
		System.out.println();
		for(int i = 123; i <256;i++){
			//System.out.printf("%c ", (char)i);
			assertEquals(User.validPassword(input + (char)i), false);
		}	
		System.out.println();
	}
	
	@Test
	public void test_validpassword_validinput(){
		String testpassword = "NotNull1234";
		//System.out.print("Validpassword :");
		assertEquals(true, User.validPassword(testpassword));
	}
	 /*  Username rules
	 *  If not provided, should store as empty string
	 */
	
	/*
	 * create user tests
	 */
	@Test
	public void test_createuser() throws Exception{
		String testlogin = "hellomango";
		String testpassword = "as210BX7g";
		String testpasswordhash = CryptoStuff.hashSha256(testpassword); 
		
		assertEquals(true, User.validPassword(testpassword));
		assertEquals(true, User.validLogin(testlogin));
		User testuser = new User(testlogin, testpassword);
		assertNotNull(testuser);
		assertEquals(testlogin, testuser.getLogin());
		assertEquals(testpasswordhash, testuser.getPassword());
	}
	

}
