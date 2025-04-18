/* Generated by itemis CREATE code generator. */
package statechart;

import com.yakindu.core.IEventDriven;
import com.yakindu.core.IStatemachine;
import java.util.LinkedList;
import java.util.Queue;

public class Test_remove_unnecessary_nesting_orthogonal_state implements IEventDriven {
	public enum State {
		MAIN_REGION_C,
		MAIN_REGION_C_D_B,
		MAIN_REGION_C_E_F,
		MAIN_REGION_A,
		$NULLSTATE$
	};
	
	private final State[] stateVector = new State[2];
	
	private Queue<Runnable> inEventQueue = new LinkedList<Runnable>();
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
	public Test_remove_unnecessary_nesting_orthogonal_state() {
		for (int i = 0; i < 2; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		
		
		isExecuting = false;
	}
	
	public void enter() {
		/* Activates the state machine. */
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		/* Default enter sequence for statechart test_remove_unnecessary_nesting_orthogonal_state */
		enterSequence_main_region_default();
		isExecuting = false;
	}
	
	public void exit() {
		/* Deactivates the state machine. */
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		/* Default exit sequence for statechart test_remove_unnecessary_nesting_orthogonal_state */
		exitSequence_main_region();
		stateVector[0] = State.$NULLSTATE$;
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
		operate = false;
		hello = false;
	}
	
	private void microStep() {
		long transitioned = -1l;
		stateConfVectorPosition = 0l;
		switch (stateVector[0]) {
		case MAIN_REGION_C_D_B:
			break;
		case MAIN_REGION_A:
			transitioned = main_region_A_react(transitioned);
			break;
		default:
			break;
		}
		if (getStateConfVectorPosition()<1l) {
			switch (stateVector[1]) {
			case MAIN_REGION_C_E_F:
				main_region_C_E_F_react(transitioned);
				break;
			default:
				break;
			}
		}
	}
	
	private void runCycle() {
		/* Performs a 'run to completion' step. */
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		nextEvent();
		do { 
			microStep();
			clearInEvents();
		} while (nextEvent());
		isExecuting = false;
	}
	
	protected boolean nextEvent() {
		if(!inEventQueue.isEmpty()) {
			inEventQueue.poll().run();
			return true;
		}
		return false;
	}
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case MAIN_REGION_C:
			return stateVector[0].ordinal() >= State.
					MAIN_REGION_C.ordinal()&& stateVector[0].ordinal() <= State.MAIN_REGION_C_E_F.ordinal();
		case MAIN_REGION_C_D_B:
			return stateVector[0] == State.MAIN_REGION_C_D_B;
		case MAIN_REGION_C_E_F:
			return stateVector[1] == State.MAIN_REGION_C_E_F;
		case MAIN_REGION_A:
			return stateVector[0] == State.MAIN_REGION_A;
		default:
			return false;
		}
	}
	
	
	private boolean operate;
	
	
	public void raiseOperate() {
		inEventQueue.add(() -> {
			operate = true;
		});
		runCycle();
	}
	
	private boolean hello;
	
	
	public void raiseHello() {
		inEventQueue.add(() -> {
			hello = true;
		});
		runCycle();
	}
	
	/* 'default' enter sequence for state C */
	private void enterSequence_main_region_C_default() {
		/* 'default' enter sequence for state C */
		enterSequence_main_region_C_D_default();
		enterSequence_main_region_C_E_default();
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_main_region_C_D_B_default() {
		/* 'default' enter sequence for state B */
		stateVector[0] = State.MAIN_REGION_C_D_B;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state F */
	private void enterSequence_main_region_C_E_F_default() {
		/* 'default' enter sequence for state F */
		stateVector[1] = State.MAIN_REGION_C_E_F;
		stateConfVectorPosition = 1;
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_main_region_A_default() {
		/* 'default' enter sequence for state A */
		stateVector[0] = State.MAIN_REGION_A;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for region main region */
	private void enterSequence_main_region_default() {
		/* 'default' enter sequence for region main region */
		react_main_region__entry_Default();
	}
	
	/* 'default' enter sequence for region D */
	private void enterSequence_main_region_C_D_default() {
		/* 'default' enter sequence for region D */
		react_main_region_C_D__entry_Default();
	}
	
	/* 'default' enter sequence for region E */
	private void enterSequence_main_region_C_E_default() {
		/* 'default' enter sequence for region E */
		react_main_region_C_E__entry_Default();
	}
	
	/* Default exit sequence for state C */
	private void exitSequence_main_region_C() {
		/* Default exit sequence for state C */
		exitSequence_main_region_C_D();
		exitSequence_main_region_C_E();
		stateVector[0] = State.$NULLSTATE$;
		stateVector[1] = State.$NULLSTATE$;
		stateConfVectorPosition = 1;
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_main_region_C_D_B() {
		/* Default exit sequence for state B */
		stateVector[0] = State.MAIN_REGION_C;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state F */
	private void exitSequence_main_region_C_E_F() {
		/* Default exit sequence for state F */
		stateVector[1] = State.MAIN_REGION_C;
		stateConfVectorPosition = 1;
	}
	
	/* Default exit sequence for state A */
	private void exitSequence_main_region_A() {
		/* Default exit sequence for state A */
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		/* Default exit sequence for region main region */
		switch (stateVector[0]) {
		case MAIN_REGION_C_D_B:
			exitSequence_main_region_C_D_B();
			break;
		case MAIN_REGION_A:
			exitSequence_main_region_A();
			break;
		default:
			break;
		}
		switch (stateVector[1]) {
		case MAIN_REGION_C_E_F:
			exitSequence_main_region_C_E_F();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region D */
	private void exitSequence_main_region_C_D() {
		/* Default exit sequence for region D */
		switch (stateVector[0]) {
		case MAIN_REGION_C_D_B:
			exitSequence_main_region_C_D_B();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region E */
	private void exitSequence_main_region_C_E() {
		/* Default exit sequence for region E */
		switch (stateVector[1]) {
		case MAIN_REGION_C_E_F:
			exitSequence_main_region_C_E_F();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence_main_region_A_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region_C_D__entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence_main_region_C_D_B_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region_C_E__entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence_main_region_C_E_F_default();
	}
	
	private long main_region_C_react(long transitioned_before) {
		/* The reactions of state C. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (operate) {
				exitSequence_main_region_C();
				enterSequence_main_region_A_default();
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
	
	private long main_region_C_E_F_react(long transitioned_before) {
		/* The reactions of state F. */
		long transitioned_after = transitioned_before;
		/* Always execute local reactions. */
		transitioned_after = main_region_C_react(transitioned_before);
		return transitioned_after;
	}
	
	private long main_region_A_react(long transitioned_before) {
		/* The reactions of state A. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (operate) {
				exitSequence_main_region_A();
				enterSequence_main_region_C_default();
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
	
	/* Can be used by the client code to trigger a run to completion step without raising an event. */
	public void triggerWithoutEvent() {
		runCycle();
	}
}
