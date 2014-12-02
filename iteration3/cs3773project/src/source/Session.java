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

	@Override
	public String toString() {
		return "Session [sessionID=" + sessionID + ", sessionSalt="
				+ sessionSalt + "]";
	}
	
	
}
