/* Generated by itemis CREATE code generator. */
package statechart;

import com.yakindu.core.ICycleBased;
import com.yakindu.core.IStatemachine;

public class Rgb implements ICycleBased {
	private static class EvBuf {
		private boolean red;
		private boolean green;
		private boolean blue;
		private boolean end;
	}
	private static class RgbEvBuf {
		private EvBuf iface = new EvBuf();
	}
	public enum State {
		MAIN_REGION_INIT,
		MAIN_REGION_IDLE,
		MAIN_REGION_GREEN,
		MAIN_REGION_BLUE,
		MAIN_REGION_RED,
		MAIN_REGION__FINAL_,
		$NULLSTATE$
	};
	
	private final State[] stateVector = new State[1];
	
	private long e;
	
	protected long getE() {
		return e;
	}
	
	protected void setE(long value) {
		this.e = value;
	}
	
	
	private RgbEvBuf current = new RgbEvBuf();
	
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		return isExecuting;
	}
	
	protected void setIsExecuting(boolean value) {
		this.isExecuting = value;
	}
	public Rgb() {
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		
		/* Default init sequence for statechart rgb */
		setE(0l);
		
		isExecuting = false;
	}
	
	public void runCycle() {
		/* Performs a 'run to completion' step. */
		if (this.operationCallback == null) {
			throw new IllegalStateException("Operation callback must be set.");
		}
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		swapInEvents();
		microStep();
		isExecuting = false;
	}
	
	public void enter() {
		/* Activates the state machine. */
		if (this.operationCallback == null) {
			throw new IllegalStateException("Operation callback must be set.");
		}
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		/* Default enter sequence for statechart rgb */
		enterSequence_main_region_default();
		isExecuting = false;
	}
	
	public void exit() {
		/* Deactivates the state machine. */
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		/* Default exit sequence for statechart rgb */
		exitSequence_main_region();
		stateVector[0] = State.$NULLSTATE$;
		isExecuting = false;
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {
		return stateVector[0] != State.$NULLSTATE$;
	}
	
	/** 
	* @see IStatemachine#isFinal()
	*/
	public boolean isFinal() {
		return (stateVector[0] == State.MAIN_REGION__FINAL_);
	}
	private void swapInEvents() {
		current.iface.red = red;
		red = false;
		current.iface.green = green;
		green = false;
		current.iface.blue = blue;
		blue = false;
		current.iface.end = end;
		end = false;
	}
	
	private void clearInEvents() {
		red = false;
		green = false;
		blue = false;
		end = false;
	}
	
	private void microStep() {
		switch (stateVector[0]) {
		case MAIN_REGION_INIT:
			main_region_init_react(-1l);
			break;
		case MAIN_REGION_IDLE:
			main_region_idle_react(-1l);
			break;
		case MAIN_REGION_GREEN:
			main_region_green_react(-1l);
			break;
		case MAIN_REGION_BLUE:
			main_region_blue_react(-1l);
			break;
		case MAIN_REGION_RED:
			main_region_red_react(-1l);
			break;
		case MAIN_REGION__FINAL_:
			break;
		default:
			break;
		}
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case MAIN_REGION_INIT:
			return stateVector[0] == State.MAIN_REGION_INIT;
		case MAIN_REGION_IDLE:
			return stateVector[0] == State.MAIN_REGION_IDLE;
		case MAIN_REGION_GREEN:
			return stateVector[0] == State.MAIN_REGION_GREEN;
		case MAIN_REGION_BLUE:
			return stateVector[0] == State.MAIN_REGION_BLUE;
		case MAIN_REGION_RED:
			return stateVector[0] == State.MAIN_REGION_RED;
		case MAIN_REGION__FINAL_:
			return stateVector[0] == State.MAIN_REGION__FINAL_;
		default:
			return false;
		}
	}
	
	
	public interface OperationCallback {
	
		public long init();
		
		public void usage();
		
		public void setColor(boolean r, boolean g, boolean b);
		
		public void teardown();
		
	}
	
	private OperationCallback operationCallback;
	
	public void setOperationCallback(OperationCallback operationCallback) {
		this.operationCallback = operationCallback;
	}
	
	private boolean red;
	
	
	public void raiseRed() {
		red = true;
	}
	
	private boolean green;
	
	
	public void raiseGreen() {
		green = true;
	}
	
	private boolean blue;
	
	
	public void raiseBlue() {
		blue = true;
	}
	
	private boolean end;
	
	
	public void raiseEnd() {
		end = true;
	}
	
	/* Entry action for state 'init'. */
	private void entryAction_main_region_init() {
		/* Entry action for state 'init'. */
		setE(operationCallback.init());
	}
	
	/* Entry action for state 'idle'. */
	private void entryAction_main_region_idle() {
		/* Entry action for state 'idle'. */
		operationCallback.setColor(false, false, false);
	}
	
	/* Entry action for state 'green'. */
	private void entryAction_main_region_green() {
		/* Entry action for state 'green'. */
		operationCallback.setColor(false, true, false);
	}
	
	/* Entry action for state 'blue'. */
	private void entryAction_main_region_blue() {
		/* Entry action for state 'blue'. */
		operationCallback.setColor(false, false, true);
	}
	
	/* Entry action for state 'red'. */
	private void entryAction_main_region_red() {
		/* Entry action for state 'red'. */
		operationCallback.setColor(true, false, false);
	}
	
	/* 'default' enter sequence for state init */
	private void enterSequence_main_region_init_default() {
		/* 'default' enter sequence for state init */
		entryAction_main_region_init();
		stateVector[0] = State.MAIN_REGION_INIT;
	}
	
	/* 'default' enter sequence for state idle */
	private void enterSequence_main_region_idle_default() {
		/* 'default' enter sequence for state idle */
		entryAction_main_region_idle();
		stateVector[0] = State.MAIN_REGION_IDLE;
	}
	
	/* 'default' enter sequence for state green */
	private void enterSequence_main_region_green_default() {
		/* 'default' enter sequence for state green */
		entryAction_main_region_green();
		stateVector[0] = State.MAIN_REGION_GREEN;
	}
	
	/* 'default' enter sequence for state blue */
	private void enterSequence_main_region_blue_default() {
		/* 'default' enter sequence for state blue */
		entryAction_main_region_blue();
		stateVector[0] = State.MAIN_REGION_BLUE;
	}
	
	/* 'default' enter sequence for state red */
	private void enterSequence_main_region_red_default() {
		/* 'default' enter sequence for state red */
		entryAction_main_region_red();
		stateVector[0] = State.MAIN_REGION_RED;
	}
	
	/* Default enter sequence for final state */
	private void enterSequence_main_region__final__default() {
		/* Default enter sequence for final state */
		stateVector[0] = State.MAIN_REGION__FINAL_;
	}
	
	/* 'default' enter sequence for region main region */
	private void enterSequence_main_region_default() {
		/* 'default' enter sequence for region main region */
		react_main_region__entry_Default();
	}
	
	/* Default exit sequence for state init */
	private void exitSequence_main_region_init() {
		/* Default exit sequence for state init */
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state idle */
	private void exitSequence_main_region_idle() {
		/* Default exit sequence for state idle */
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state green */
	private void exitSequence_main_region_green() {
		/* Default exit sequence for state green */
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state blue */
	private void exitSequence_main_region_blue() {
		/* Default exit sequence for state blue */
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state red */
	private void exitSequence_main_region_red() {
		/* Default exit sequence for state red */
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for final state. */
	private void exitSequence_main_region__final_() {
		/* Default exit sequence for final state. */
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		/* Default exit sequence for region main region */
		switch (stateVector[0]) {
		case MAIN_REGION_INIT:
			exitSequence_main_region_init();
			break;
		case MAIN_REGION_IDLE:
			exitSequence_main_region_idle();
			break;
		case MAIN_REGION_GREEN:
			exitSequence_main_region_green();
			break;
		case MAIN_REGION_BLUE:
			exitSequence_main_region_blue();
			break;
		case MAIN_REGION_RED:
			exitSequence_main_region_red();
			break;
		case MAIN_REGION__FINAL_:
			exitSequence_main_region__final_();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence_main_region_init_default();
	}
	
	private long main_region_init_react(long transitioned_before) {
		/* The reactions of state init. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (getE()==0l) {
				exitSequence_main_region_init();
				operationCallback.usage();
				enterSequence_main_region_idle_default();
				transitioned_after = 0l;
			} else {
				if (getE()==1l) {
					exitSequence_main_region_init();
					enterSequence_main_region__final__default();
					transitioned_after = 0l;
				}
			}
		}
		/* If no transition was taken */
		if (transitioned_after==transitioned_before) {
			/* then execute local reactions. */
			transitioned_after = transitioned_before;
		}
		return transitioned_after;
	}
	
	private long main_region_idle_react(long transitioned_before) {
		/* The reactions of state idle. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (current.iface.green) {
				exitSequence_main_region_idle();
				enterSequence_main_region_green_default();
				transitioned_after = 0l;
			} else {
				if (current.iface.blue) {
					exitSequence_main_region_idle();
					enterSequence_main_region_blue_default();
					transitioned_after = 0l;
				} else {
					if (current.iface.red) {
						exitSequence_main_region_idle();
						enterSequence_main_region_red_default();
						transitioned_after = 0l;
					} else {
						if (current.iface.end) {
							exitSequence_main_region_idle();
							operationCallback.teardown();
							enterSequence_main_region__final__default();
							transitioned_after = 0l;
						}
					}
				}
			}
		}
		/* If no transition was taken */
		if (transitioned_after==transitioned_before) {
			/* then execute local reactions. */
			transitioned_after = transitioned_before;
		}
		return transitioned_after;
	}
	
	private long main_region_green_react(long transitioned_before) {
		/* The reactions of state green. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (current.iface.green) {
				exitSequence_main_region_green();
				enterSequence_main_region_idle_default();
				transitioned_after = 0l;
			}
		}
		/* If no transition was taken */
		if (transitioned_after==transitioned_before) {
			/* then execute local reactions. */
			transitioned_after = transitioned_before;
		}
		return transitioned_after;
	}
	
	private long main_region_blue_react(long transitioned_before) {
		/* The reactions of state blue. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (current.iface.blue) {
				exitSequence_main_region_blue();
				enterSequence_main_region_idle_default();
				transitioned_after = 0l;
			}
		}
		/* If no transition was taken */
		if (transitioned_after==transitioned_before) {
			/* then execute local reactions. */
			transitioned_after = transitioned_before;
		}
		return transitioned_after;
	}
	
	private long main_region_red_react(long transitioned_before) {
		/* The reactions of state red. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (current.iface.red) {
				exitSequence_main_region_red();
				enterSequence_main_region_idle_default();
				transitioned_after = 0l;
			}
		}
		/* If no transition was taken */
		if (transitioned_after==transitioned_before) {
			/* then execute local reactions. */
			transitioned_after = transitioned_before;
		}
		return transitioned_after;
	}
	
}
