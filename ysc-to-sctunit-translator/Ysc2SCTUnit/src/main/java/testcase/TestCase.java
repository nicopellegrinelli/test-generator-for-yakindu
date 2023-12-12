package testcase;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class TestCase.
 */
public class TestCase {
	
	private String name;
	private List<Action> actions;
	
	/**
	 * Instantiate a new test case.
	 *
	 * @param name the name of the test case
	 */
	public TestCase(String name) {
		this.name = name;
		actions = new ArrayList<Action>();
	}
	
	/**
	 * Add an "enter" action.
	 */
	public void addEnter() {
		actions.add(new Action(true, null, null, false, false, false, false, false));
	}
	
	/**
	 * Add a "raise event" action.
	 *
	 * @param event the event to be raised
	 */
	public void addEvent(String event) {
		actions.add(new Action(false, event, null, false, false, false, false, false));
	}
	
	/**
	 * Add an "assert active (state)" action.
	 *
	 * @param state the state to assert if it is active
	 * @param assertTrue true if the truth is wanted to be asserted, false if the falseness is wanted to be asserted
	 */
	public void addAssertState(String state, boolean assertTrue) {
		actions.add(new Action(false, null, state, false, false, assertTrue, false, false));
	}
	
	/**
	 * Adds an "assert is_active" action.
	 *
	 * @param assertTrue true if the truth is wanted to be asserted, false if the falseness is wanted to be asserted
	 */
	public void addIsActive(boolean assertTrue) {
		actions.add(new Action(false, null, null, true, false, assertTrue, false, false));
	}
	
	/**
	 * Adds an "assert is_final" action.
	 *
	 * @param assertTrue true if the truth is wanted to be asserted, false if the falseness is wanted to be asserted
	 */
	public void addIsFinal(boolean assertTrue) {
		actions.add(new Action(false, null, null, false, true, assertTrue, false, false));
	}
	
	/**
	 * Adds a "proceed 1 cycle" action.
	 */
	public void addProceedCycle() {
		actions.add(new Action(false, null, null, false, false, false, true, false));
	}
	
	/**
	 * Add a "triggerWithoutEvent" action.
	 */
	public void addTriggerWithoutEvent() {
		actions.add(new Action(false, null, null, false, false, false, false, true));
	}
	
	/**
	 * Add an "exit" action.
	 */
	public void addExit() {
		actions.add(new Action(false, null, null, false, false, false, false, false));
	}
	
	/**
	 * To string.
	 *
	 * @return the string representing the name of the test case
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * Get the actions.
	 *
	 * @return the list of actions
	 */
	public List<Action> getActions(){
		return actions;
	}
	
}
