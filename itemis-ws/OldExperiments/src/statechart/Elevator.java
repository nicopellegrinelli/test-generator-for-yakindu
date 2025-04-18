/* Generated by itemis CREATE code generator. */
package statechart;

import com.yakindu.core.ICycleBased;
import com.yakindu.core.IStatemachine;
import com.yakindu.core.ITimed;
import com.yakindu.core.ITimerService;

public class Elevator implements ITimed, ICycleBased {
	private static class EvBuf {
		private boolean floor;
		
		private long floorValue;
	}
	private static class TimeEventsEvBuf {
		private boolean elevator_main_Idle_time_event_0;
	}
	private static class ElevatorEvBuf {
		private EvBuf iface = new EvBuf();
		private TimeEventsEvBuf timeEvents = new TimeEventsEvBuf();
	}
	public enum State {
		MAIN_IDLE,
		MAIN_MOVING,
		MAIN_MOVING_R1_MOVINGUP,
		MAIN_MOVING_R1_MOVINGDOWN,
		$NULLSTATE$
	};
	
	private final State[] stateVector = new State[1];
	
	private ITimerService timerService;
	
	private final boolean[] timeEvents = new boolean[1];
	
	private ElevatorEvBuf current = new ElevatorEvBuf();
	
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		return isExecuting;
	}
	
	protected void setIsExecuting(boolean value) {
		this.isExecuting = value;
	}
	public Elevator() {
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		
		/* Default init sequence for statechart Elevator */
		setCurrentFloor(0l);
		setDestination(0l);
		
		isExecuting = false;
	}
	
	public void runCycle() {
		/* Performs a 'run to completion' step. */
		if (timerService == null) {
			throw new IllegalStateException("Timer service must be set.");
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
		if (timerService == null) {
			throw new IllegalStateException("Timer service must be set.");
		}
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		/* Default enter sequence for statechart Elevator */
		enterSequence_main_default();
		isExecuting = false;
	}
	
	public void exit() {
		/* Deactivates the state machine. */
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		/* Default exit sequence for statechart Elevator */
		exitSequence_main();
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
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public boolean isFinal() {
		return false;
	}
	private void swapInEvents() {
		current.iface.floor = floor;
		current.iface.floorValue = floorValue;
		floor = false;
		current.timeEvents.elevator_main_Idle_time_event_0 = timeEvents[0];
		timeEvents[0] = false;
	}
	
	private void clearInEvents() {
		floor = false;
		timeEvents[0] = false;
	}
	
	private void microStep() {
		switch (stateVector[0]) {
		case MAIN_IDLE:
			main_Idle_react(-1l);
			break;
		case MAIN_MOVING_R1_MOVINGUP:
			main_Moving_r1_MovingUp_react(-1l);
			break;
		case MAIN_MOVING_R1_MOVINGDOWN:
			main_Moving_r1_MovingDown_react(-1l);
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
		case MAIN_IDLE:
			return stateVector[0] == State.MAIN_IDLE;
		case MAIN_MOVING:
			return stateVector[0].ordinal() >= State.
					MAIN_MOVING.ordinal()&& stateVector[0].ordinal() <= State.MAIN_MOVING_R1_MOVINGDOWN.ordinal();
		case MAIN_MOVING_R1_MOVINGUP:
			return stateVector[0] == State.MAIN_MOVING_R1_MOVINGUP;
		case MAIN_MOVING_R1_MOVINGDOWN:
			return stateVector[0] == State.MAIN_MOVING_R1_MOVINGDOWN;
		default:
			return false;
		}
	}
	
	public void setTimerService(ITimerService timerService) {
		this.timerService = timerService;
	}
	
	public ITimerService getTimerService() {
		return timerService;
	}
	
	public void raiseTimeEvent(int eventID) {
		timeEvents[eventID] = true;
	}
	
	
	private boolean floor;
	
	private long floorValue;
	
	
	public void raiseFloor(long value) {
		floorValue = value;
		floor = true;
	}
	protected long getFloorValue() {
		if (! floor ) 
			throw new IllegalStateException("Illegal event value access. Event Floor is not raised!");
		return floorValue;
	}
	
	private long currentFloor;
	
	public long getCurrentFloor() {
		return currentFloor;
	}
	
	public void setCurrentFloor(long value) {
		this.currentFloor = value;
	}
	
	private long destination;
	
	public long getDestination() {
		return destination;
	}
	
	public void setDestination(long value) {
		this.destination = value;
	}
	
	/* Entry action for state 'Idle'. */
	private void entryAction_main_Idle() {
		/* Entry action for state 'Idle'. */
		timerService.setTimer(this, 0, (5l * 1000l), false);
	}
	
	/* Entry action for state 'MovingUp'. */
	private void entryAction_main_Moving_r1_MovingUp() {
		/* Entry action for state 'MovingUp'. */
		setCurrentFloor((currentFloor + 1l));
	}
	
	/* Entry action for state 'MovingDown'. */
	private void entryAction_main_Moving_r1_MovingDown() {
		/* Entry action for state 'MovingDown'. */
		setCurrentFloor((currentFloor - 1l));
	}
	
	/* Exit action for state 'Idle'. */
	private void exitAction_main_Idle() {
		/* Exit action for state 'Idle'. */
		timerService.unsetTimer(this, 0);
	}
	
	/* 'default' enter sequence for state Idle */
	private void enterSequence_main_Idle_default() {
		/* 'default' enter sequence for state Idle */
		entryAction_main_Idle();
		stateVector[0] = State.MAIN_IDLE;
	}
	
	/* 'default' enter sequence for state MovingUp */
	private void enterSequence_main_Moving_r1_MovingUp_default() {
		/* 'default' enter sequence for state MovingUp */
		entryAction_main_Moving_r1_MovingUp();
		stateVector[0] = State.MAIN_MOVING_R1_MOVINGUP;
	}
	
	/* 'default' enter sequence for state MovingDown */
	private void enterSequence_main_Moving_r1_MovingDown_default() {
		/* 'default' enter sequence for state MovingDown */
		entryAction_main_Moving_r1_MovingDown();
		stateVector[0] = State.MAIN_MOVING_R1_MOVINGDOWN;
	}
	
	/* 'default' enter sequence for region main */
	private void enterSequence_main_default() {
		/* 'default' enter sequence for region main */
		react_main__entry_Default();
	}
	
	/* Default exit sequence for state Idle */
	private void exitSequence_main_Idle() {
		/* Default exit sequence for state Idle */
		stateVector[0] = State.$NULLSTATE$;
		exitAction_main_Idle();
	}
	
	/* Default exit sequence for state Moving */
	private void exitSequence_main_Moving() {
		/* Default exit sequence for state Moving */
		exitSequence_main_Moving_r1();
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state MovingUp */
	private void exitSequence_main_Moving_r1_MovingUp() {
		/* Default exit sequence for state MovingUp */
		stateVector[0] = State.MAIN_MOVING;
	}
	
	/* Default exit sequence for state MovingDown */
	private void exitSequence_main_Moving_r1_MovingDown() {
		/* Default exit sequence for state MovingDown */
		stateVector[0] = State.MAIN_MOVING;
	}
	
	/* Default exit sequence for region main */
	private void exitSequence_main() {
		/* Default exit sequence for region main */
		switch (stateVector[0]) {
		case MAIN_IDLE:
			exitSequence_main_Idle();
			break;
		case MAIN_MOVING:
			exitSequence_main_Moving();
			break;
		case MAIN_MOVING_R1_MOVINGUP:
			exitSequence_main_Moving_r1_MovingUp();
			break;
		case MAIN_MOVING_R1_MOVINGDOWN:
			exitSequence_main_Moving_r1_MovingDown();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r1 */
	private void exitSequence_main_Moving_r1() {
		/* Default exit sequence for region r1 */
		switch (stateVector[0]) {
		case MAIN_MOVING_R1_MOVINGUP:
			exitSequence_main_Moving_r1_MovingUp();
			break;
		case MAIN_MOVING_R1_MOVINGDOWN:
			exitSequence_main_Moving_r1_MovingDown();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main__entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence_main_Idle_default();
	}
	
	private long main_Idle_react(long transitioned_before) {
		/* The reactions of state Idle. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (((current.iface.floor) && ((current.iface.floorValue<getCurrentFloor() && current.iface.floorValue>=0l)))) {
				exitSequence_main_Idle();
				setDestination(current.iface.floorValue);
				enterSequence_main_Moving_r1_MovingDown_default();
				transitioned_after = 0l;
			} else {
				if (((current.timeEvents.elevator_main_Idle_time_event_0) && (getCurrentFloor()>0l))) {
					exitSequence_main_Idle();
					setDestination(0l);
					current.timeEvents.elevator_main_Idle_time_event_0 = false;
					enterSequence_main_Moving_r1_MovingDown_default();
					transitioned_after = 0l;
				} else {
					if (((current.iface.floor) && (current.iface.floorValue>getCurrentFloor()))) {
						exitSequence_main_Idle();
						setDestination(current.iface.floorValue);
						enterSequence_main_Moving_r1_MovingUp_default();
						transitioned_after = 0l;
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
	
	private long main_Moving_react(long transitioned_before) {
		/* The reactions of state Moving. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (getDestination()==getCurrentFloor()) {
				exitSequence_main_Moving();
				enterSequence_main_Idle_default();
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
	
	private long main_Moving_r1_MovingUp_react(long transitioned_before) {
		/* The reactions of state MovingUp. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (getCurrentFloor()<getDestination()) {
				exitSequence_main_Moving_r1_MovingUp();
				enterSequence_main_Moving_r1_MovingUp_default();
				main_Moving_react(0l);
				transitioned_after = 0l;
			}
		}
		/* If no transition was taken */
		if (transitioned_after==transitioned_before) {
			/* then execute local reactions. */
			transitioned_after = main_Moving_react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long main_Moving_r1_MovingDown_react(long transitioned_before) {
		/* The reactions of state MovingDown. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (getCurrentFloor()>getDestination()) {
				exitSequence_main_Moving_r1_MovingDown();
				enterSequence_main_Moving_r1_MovingDown_default();
				main_Moving_react(0l);
				transitioned_after = 0l;
			}
		}
		/* If no transition was taken */
		if (transitioned_after==transitioned_before) {
			/* then execute local reactions. */
			transitioned_after = main_Moving_react(transitioned_before);
		}
		return transitioned_after;
	}
	
}
