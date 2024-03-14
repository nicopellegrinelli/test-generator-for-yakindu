package testcase;

/**
 * The Class Action.
 */
public class Action {

	private String enter;
	private String event;
	private String eventValue;
	private String state;
	private String isActive;
	private String isFinal;
	private String not;
	private String proceed;
	private String triggerWithoutEvent;

	/**
	 * Instantiates a new action.
	 *
	 * @param enter               true if the action is "enter", false otherwise
	 * @param event               the event to be raised (if needed)
	 * @param eventValue          the value associated with the raised typed event,
	 *                            null if the event has no type
	 * @param state               the state to assert if it is active (if needed)
	 * @param isActive            true if the action is "assert is_active", false
	 *                            otherwise
	 * @param isFinal             true if the action is "assert is_final", false
	 *                            otherwise
	 * @param assertTrue          true if the truth is wanted to be asserted, false
	 *                            if the falseness is wanted to be asserted
	 * @param proceed             true if the action is "proceed 1 cycle", false
	 *                            otherwise
	 * @param triggerWithoutEvent true if the action is "triggerWithoutEvent", false
	 *                            otherwise
	 */
	public Action(boolean enter, String event, String eventValue, String state, boolean isActive, boolean isFinal,
			boolean assertTrue, boolean proceed, boolean triggerWithoutEvent) {
		this.enter = enter ? "" : null;
		this.event = event;
		this.eventValue = eventValue;
		this.state = state;
		this.isActive = isActive ? "" : null;
		this.isFinal = isFinal ? "" : null;
		this.not = assertTrue ? null : "";
		this.proceed = proceed ? "" : null;
		this.triggerWithoutEvent = triggerWithoutEvent ? "" : null;
	}

	/**
	 * Gets the enter string.
	 *
	 * @return the string
	 */
	public String getEnter() {
		return enter;
	}

	/**
	 * Gets the event string.
	 *
	 * @return the string
	 */
	public String getEvent() {
		return event;
	}
	
	/**
	 * Gets the string representing the value of the event.
	 *
	 * @return the string
	 */
	public String getEventValue() {
		return eventValue;
	}

	/**
	 * Gets the state string.
	 *
	 * @return the string
	 */
	public String getState() {
		return state;
	}

	/**
	 * Gets the is active string.
	 *
	 * @return the string
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * Gets the is final string.
	 *
	 * @return the string
	 */
	public String getIsFinal() {
		return isFinal;
	}

	/**
	 * Gets the not string.
	 *
	 * @return the string
	 */
	public String getNot() {
		return not;
	}

	/**
	 * Gets the proceed string.
	 *
	 * @return the string
	 */
	public String getProceed() {
		return proceed;
	}

	/**
	 * Gets the trigger without event string.
	 *
	 * @return the string
	 */
	public String getTriggerWithoutEvent() {
		return triggerWithoutEvent;
	}

}
