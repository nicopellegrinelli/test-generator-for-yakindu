package sctwriting;

public class Action {
	String event;
	String state;
	String not;
	String proceed;
	
	public Action (String event, String state, boolean assertTrue, boolean proceed) {
		this.event = event;
		this.state = state;
		this.not = assertTrue ? null : "";
		this.proceed = proceed ? "" : null;
	}

	public String getEvent() {
		return event;
	}

	public String getState() {
		return state;
	}

	public String getNot() {
		return not;
	}
	
	public String getProceed() {
		return proceed;
	}
	
}
