/* Generated by itemis CREATE code generator. */
package statechart;

import com.yakindu.core.ICycleBased;
import com.yakindu.core.IStatemachine;

public class EventBuffersDisabled implements ICycleBased {
	public enum State {
		R1_A,
		R1_B,
		R2_C,
		R2_D,
		$NULLSTATE$
	};
	
	private final State[] stateVector = new State[2];
	
	private boolean l1;
	private boolean l2;
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		return isExecuting;
	}
	
	protected void setIsExecuting(boolean value) {
		this.isExecuting = value;
	}
	private long stateConfVectorPosition;
	
	protected long getStateConfVectorPosition() {
		return stateConfVectorPosition;
	}
	
	protected void setStateConfVectorPosition(long value) {
		this.stateConfVectorPosition = value;
	}
	public EventBuffersDisabled() {
		for (int i = 0; i < 2; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		clearInternalEvents();
		
		
		isExecuting = false;
	}
	
	public void runCycle() {
		/* Performs a 'run to completion' step. */
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		microStep();
		clearInEvents();
		clearInternalEvents();
		isExecuting = false;
	}
	
	public void enter() {
		/* Activates the state machine. */
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		/* Default enter sequence for statechart EventBuffersDisabled */
		enterSequence_r1_default();
		enterSequence_r2_default();
		isExecuting = false;
	}
	
	public void exit() {
		/* Deactivates the state machine. */
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		/* Default exit sequence for statechart EventBuffersDisabled */
		exitSequence_r1();
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
		exitSequence_r2();
		stateVector[1] = State.$NULLSTATE$;
		stateConfVectorPosition = 1;
		isExecuting = false;
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {
		return stateVector[0] != State.$NULLSTATE$||stateVector[1] != State.$NULLSTATE$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public boolean isFinal() {
		return false;
	}
	private void clearInEvents() {
		e = false;
		f = false;
	}
	
	private void clearInternalEvents() {
		l1 = false;
		l2 = false;
	}
	
	private void microStep() {
		long transitioned = -1l;
		stateConfVectorPosition = 0l;
		switch (stateVector[0]) {
		case R1_A:
			transitioned = r1_A_react(transitioned);
			break;
		case R1_B:
			transitioned = r1_B_react(transitioned);
			break;
		default:
			break;
		}
		if (getStateConfVectorPosition()<1l) {
			switch (stateVector[1]) {
			case R2_C:
				r2_C_react(transitioned);
				break;
			case R2_D:
				r2_D_react(transitioned);
				break;
			default:
				break;
			}
		}
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case R1_A:
			return stateVector[0] == State.R1_A;
		case R1_B:
			return stateVector[0] == State.R1_B;
		case R2_C:
			return stateVector[1] == State.R2_C;
		case R2_D:
			return stateVector[1] == State.R2_D;
		default:
			return false;
		}
	}
	
	
	protected void raiseL1() {
		l1 = true;
	}
	
	protected void raiseL2() {
		l2 = true;
	}
	
	private boolean e;
	
	
	public void raiseE() {
		e = true;
	}
	
	private boolean f;
	
	
	public void raiseF() {
		f = true;
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_r1_A_default() {
		/* 'default' enter sequence for state A */
		stateVector[0] = State.R1_A;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_r1_B_default() {
		/* 'default' enter sequence for state B */
		stateVector[0] = State.R1_B;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state C */
	private void enterSequence_r2_C_default() {
		/* 'default' enter sequence for state C */
		stateVector[1] = State.R2_C;
		stateConfVectorPosition = 1;
	}
	
	/* 'default' enter sequence for state D */
	private void enterSequence_r2_D_default() {
		/* 'default' enter sequence for state D */
		stateVector[1] = State.R2_D;
		stateConfVectorPosition = 1;
	}
	
	/* 'default' enter sequence for region r1 */
	private void enterSequence_r1_default() {
		/* 'default' enter sequence for region r1 */
		react_r1__entry_Default();
	}
	
	/* 'default' enter sequence for region r2 */
	private void enterSequence_r2_default() {
		/* 'default' enter sequence for region r2 */
		react_r2__entry_Default();
	}
	
	/* Default exit sequence for state A */
	private void exitSequence_r1_A() {
		/* Default exit sequence for state A */
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_r1_B() {
		/* Default exit sequence for state B */
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state C */
	private void exitSequence_r2_C() {
		/* Default exit sequence for state C */
		stateVector[1] = State.$NULLSTATE$;
		stateConfVectorPosition = 1;
	}
	
	/* Default exit sequence for state D */
	private void exitSequence_r2_D() {
		/* Default exit sequence for state D */
		stateVector[1] = State.$NULLSTATE$;
		stateConfVectorPosition = 1;
	}
	
	/* Default exit sequence for region r1 */
	private void exitSequence_r1() {
		/* Default exit sequence for region r1 */
		switch (stateVector[0]) {
		case R1_A:
			exitSequence_r1_A();
			break;
		case R1_B:
			exitSequence_r1_B();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r2 */
	private void exitSequence_r2() {
		/* Default exit sequence for region r2 */
		switch (stateVector[1]) {
		case R2_C:
			exitSequence_r2_C();
			break;
		case R2_D:
			exitSequence_r2_D();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_r1__entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence_r1_A_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_r2__entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence_r2_C_default();
	}
	
	private long r1_A_react(long transitioned_before) {
		/* The reactions of state A. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (l2) {
				exitSequence_r1_A();
				enterSequence_r1_B_default();
				transitioned_after = 0l;
			}
		}
		/* If no transition was taken */
		if (transitioned_after==transitioned_before) {
			/* then execute local reactions. */
			if (e) {
				raiseL1();
			}
		}
		return transitioned_after;
	}
	
	private long r1_B_react(long transitioned_before) {
		/* The reactions of state B. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (e) {
				exitSequence_r1_B();
				enterSequence_r1_A_default();
				transitioned_after = 0l;
			}
		}
		return transitioned_after;
	}
	
	private long r2_C_react(long transitioned_before) {
		/* The reactions of state C. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<1l) {
			if (l1) {
				exitSequence_r2_C();
				enterSequence_r2_D_default();
				transitioned_after = 1l;
			}
		}
		/* If no transition was taken */
		if (transitioned_after==transitioned_before) {
			/* then execute local reactions. */
			transitioned_after = transitioned_before;
		}
		return transitioned_after;
	}
	
	private long r2_D_react(long transitioned_before) {
		/* The reactions of state D. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<1l) {
			if (f) {
				exitSequence_r2_D();
				raiseL2();
				enterSequence_r2_C_default();
				transitioned_after = 1l;
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
