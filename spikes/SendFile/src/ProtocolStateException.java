public class ProtocolStateException extends Exception {
	private int state;
	public ProtocolStateException(int state) {
		super();
		this.state = state;
	}
	
	public String getMessage() {
		return "Current state was " + state + ".";
	}
}
