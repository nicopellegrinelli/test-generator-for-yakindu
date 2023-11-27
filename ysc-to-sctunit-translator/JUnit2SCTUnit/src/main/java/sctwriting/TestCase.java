package sctwriting;

import java.util.ArrayList;
import java.util.List;

public class TestCase {
	private String name;
	private List<Action> actions;
	
	public TestCase(String name) {
		this.name = name;
		actions = new ArrayList<Action>();
	}
	
	public void addEnter() {
		actions.add(new Action(true, null, null, false, false, false, false));
	}
	
	public void addEvent(String event) {
		actions.add(new Action(false, event, null, false, false, false, false));
	}
	
	public void addAssertState(String state, boolean assertTrue) {
		actions.add(new Action(false, null, state, false, false, assertTrue, false));
	}
	
	public void addIsActive(boolean assertTrue) {
		actions.add(new Action(false, null, null, true, false, assertTrue, false));
	}
	
	public void addIsFinal(boolean assertTrue) {
		actions.add(new Action(false, null, null, false, true, assertTrue, false));
	}
	
	public void addProceedCycle() {
		actions.add(new Action(false, null, null, false, false, false, true));
	}
	
	public void addExit() {
		actions.add(new Action(false, null, null, false, false, false, false));
	}
	
	public String toString() {
		return name;
	}
	
	public List<Action> getActions(){
		return actions;
	}
	
}
