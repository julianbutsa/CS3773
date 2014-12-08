package source;

public class Session {

	private int sessionID;
	private String sessionSalt;
	
	public Session(Object object, Object object2){
		this.sessionID = (int) object;
		this.sessionSalt = (String) object2;
	}

	public int getSessionID() {
		return sessionID;
	}

	public String getSessionSalt() {
		return sessionSalt;
	}

	
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public void setSessionSalt(String sessionSalt) {
		this.sessionSalt = sessionSalt;
	}

	@Override
	public String toString() {
		return "Session [sessionID=" + sessionID + ", sessionSalt="
				+ sessionSalt + "]";
	}
	
	
}
