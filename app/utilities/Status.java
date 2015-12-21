package utilities;

public enum Status {
	NEW(1),
	OPEN(2),
	CLOSED(3);
	
	private int status;
	
	Status(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public static boolean isClosed(int status) {
		return status == CLOSED.getStatus();
	}
	
	public static boolean check(int status) {
		for(Status s:Status.values()) {
			if(status == s.getStatus()) {
				return true;
			}
		}
		return false;
 	}
}
