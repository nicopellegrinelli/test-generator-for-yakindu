package sctwriting;

public class Action {
	String enter;
	String event;
	String state;
	String not;
	String proceed;
	String isActive;
	String isFinal;

	public Action(boolean enter, String event, String state, boolean isActive, boolean isFinal, boolean assertTrue, boolean proceed) {
		this.enter = enter ? "" : null;
		this.event = event;
		this.state = state;
		this.isActive = isActive ? "" : null;
		this.isFinal = isFinal ? "" : null;
		this.not = assertTrue ? null : "";
		this.proceed = proceed ? "" : null;
	}
	
	public String getEnter() {
		return enter;
	}

	public String getEvent() {
		return event;
	}

	public String getState() {
		return state;
	}
	
	public String getIsActive() {
		return isActive;
	}
	
	public String getIsFinal() {
		return isFinal;
	}

	public String getNot() {
		return not;
	}

	public String getProceed() {
		return proceed;
	}

}
