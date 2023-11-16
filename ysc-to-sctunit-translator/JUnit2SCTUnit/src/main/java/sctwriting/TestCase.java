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
	
	public void addEvent(String event) {
		actions.add(new Action(event, null, false, false));
	}
	
	public void addAssertState(String state, boolean assertTrue) {
		actions.add(new Action(null, state, assertTrue, false));
	}
	
	public void addProceedCycle() {
		actions.add(new Action(null, null, false, true));
	}
	
	public void addExit() {
		actions.add(new Action(null, null, false, false));
	}
	
	public String toString() {
		return name;
	}
	
	public List<Action> getActions(){
		return actions;
	}
	
}
