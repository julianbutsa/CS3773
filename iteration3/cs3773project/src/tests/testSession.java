package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import source.Session;

public class testSession {

	@Test
	public void test_session_success() {
		int sessionid = 1;
		String sessionsalt = "supersalty";
		
		Session s = new Session( sessionid, sessionsalt);
		
		assertEquals(sessionid, s.getSessionID());
		assertEquals( sessionsalt, s.getSessionSalt());
	}
	
	@Test
	public void test_session_setid(){
		int sessionid = 1;
		int sessionid2 = 2;
		String sessionsalt = "supersalty";
		
		Session s = new Session( sessionid, sessionsalt);
		s.setSessionID(sessionid2);
		assertEquals(sessionid2, s.getSessionID());
	}
	
	@Test
	public void test_session_setsalt(){
		int sessionid = 1;
		String sessionsalt = "supersalty";
		String sessionsalt2 = "megasalty";
		Session s = new Session( sessionid, sessionsalt);
		s.setSessionSalt(sessionsalt2);
		assertEquals(sessionsalt2, s.getSessionSalt());
	}

}
