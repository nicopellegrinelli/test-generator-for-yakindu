/* Generated by itemis CREATE code generator. */
package statechart;

import com.yakindu.core.IEventDriven;
import com.yakindu.core.IStatemachine;
import com.yakindu.core.ITimed;
import com.yakindu.core.ITimerService;
import java.util.LinkedList;
import java.util.Queue;

public class Microwave_Unit implements ITimed, IEventDriven {
	public static class Device {
		private Microwave_Unit parent;
		
		public Device(Microwave_Unit parent) {
			this.parent = parent;
		}
		private boolean on;
		
		
		public void raiseOn() {
			parent.inEventQueue.add(() -> {
				on = true;
			});
			parent.runCycle();
		}
		
		private boolean off;
		
		
		public void raiseOff() {
			parent.inEventQueue.add(() -> {
				off = true;
			});
			parent.runCycle();
		}
		
		private boolean start;
		
		
		public void raiseStart() {
			parent.inEventQueue.add(() -> {
				start = true;
			});
			parent.runCycle();
		}
		
		private boolean pause;
		
		
		public void raisePause() {
			parent.inEventQueue.add(() -> {
				pause = true;
			});
			parent.runCycle();
		}
		
		private boolean addTimer;
		
		
		public void raiseAddTimer() {
			parent.inEventQueue.add(() -> {
				addTimer = true;
			});
			parent.runCycle();
		}
		
		private boolean resetTimer;
		
		
		public void raiseResetTimer() {
			parent.inEventQueue.add(() -> {
				resetTimer = true;
			});
			parent.runCycle();
		}
		
		private boolean isOn;
		
		public boolean getIsOn() {
			return isOn;
		}
		
		public void setIsOn(boolean value) {
			this.isOn = value;
		}
		
		private long timer;
		
		public long getTimer() {
			return timer;
		}
		
		public void setTimer(long value) {
			this.timer = value;
		}
		
	}
	
	public static class Door {
		private Microwave_Unit parent;
		
		public Door(Microwave_Unit parent) {
			this.parent = parent;
		}
		private boolean open;
		
		
		public void raiseOpen() {
			parent.inEventQueue.add(() -> {
				open = true;
			});
			parent.runCycle();
		}
		
		private boolean close;
		
		
		public void raiseClose() {
			parent.inEventQueue.add(() -> {
				close = true;
			});
			parent.runCycle();
		}
		
		private boolean closed;
		
		public boolean getClosed() {
			return closed;
		}
		
		public void setClosed(boolean value) {
			this.closed = value;
		}
		
	}
	
	protected Device device;
	
	protected Door door;
	
	public enum State {
		_MICROWAVE_UNIT___OFF_,
		_MICROWAVE_UNIT___ON_,
		_MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__IDLE_STANDBY,
		_MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER,
		_MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__PAUSE,
		_MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__START,
		_MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__ADDTIMER,
		_MICROWAVE_UNIT___ON__DOORSTATUS_DOORCLOSED,
		_MICROWAVE_UNIT___ON__DOORSTATUS_DOOROPENED,
		$NULLSTATE$
	};
	
	private final State[] stateVector = new State[2];
	
	private ITimerService timerService;
	
	private final boolean[] timeEvents = new boolean[3];
	
	private Queue<Runnable> inEventQueue = new LinkedList<Runnable>();
	private boolean completed;
	
	protected boolean getCompleted() {
		return completed;
	}
	
	protected void setCompleted(boolean value) {
		this.completed = value;
	}
	private boolean doCompletion;
	
	protected boolean getDoCompletion() {
		return doCompletion;
	}
	
	protected void setDoCompletion(boolean value) {
		this.doCompletion = value;
	}
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
	public Microwave_Unit() {
		device = new Device(this);
		door = new Door(this);
		for (int i = 0; i < 2; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		
		/* Default init sequence for statechart Microwave_Unit */
		setMessage("");
		setFood_inside_sensed(false);
		setIn_use(false);
		device.setIsOn(false);
		device.setTimer(0l);
		door.setClosed(true);
		
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
		/* Default enter sequence for statechart Microwave_Unit */
		enterSequence__Microwave_Unit__default();
		doCompletion = false;
		do { 
			if (getCompleted()) {
				doCompletion = true;
			}
			completed = false;
			microStep();
			clearInEvents();
			doCompletion = false;
		} while (getCompleted());
		isExecuting = false;
	}
	
	public void exit() {
		/* Deactivates the state machine. */
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		/* Default exit sequence for statechart Microwave_Unit */
		exitSequence__Microwave_Unit_();
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
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
		device.on = false;
		device.off = false;
		device.start = false;
		device.pause = false;
		device.addTimer = false;
		device.resetTimer = false;
		door.open = false;
		door.close = false;
		timeEvents[0] = false;
		timeEvents[1] = false;
		timeEvents[2] = false;
	}
	
	private void microStep() {
		long transitioned = -1l;
		stateConfVectorPosition = 0l;
		switch (stateVector[0]) {
		case _MICROWAVE_UNIT___OFF_:
			transitioned = _Microwave_Unit___off__react(transitioned);
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__IDLE_STANDBY:
			transitioned = _Microwave_Unit___on___Microwave_isWorking__idle_standby_react(transitioned);
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__PAUSE:
			transitioned = _Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause_react(transitioned);
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__START:
			transitioned = _Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start_react(transitioned);
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__ADDTIMER:
			transitioned = _Microwave_Unit___on___Microwave_isWorking__addTimer_react(transitioned);
			break;
		default:
			break;
		}
		if (getStateConfVectorPosition()<1l) {
			switch (stateVector[1]) {
			case _MICROWAVE_UNIT___ON__DOORSTATUS_DOORCLOSED:
				_Microwave_Unit___on__doorStatus_DoorClosed_react(transitioned);
				break;
			case _MICROWAVE_UNIT___ON__DOORSTATUS_DOOROPENED:
				_Microwave_Unit___on__doorStatus_DoorOpened_react(transitioned);
				break;
			default:
				break;
			}
		}
	}
	
	private void runCycle() {
		/* Performs a 'run to completion' step. */
		if (timerService == null) {
			throw new IllegalStateException("Timer service must be set.");
		}
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		nextEvent();
		do { 
			doCompletion = false;
			do { 
				if (getCompleted()) {
					doCompletion = true;
				}
				completed = false;
				microStep();
				clearInEvents();
				doCompletion = false;
			} while (getCompleted());
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
		case _MICROWAVE_UNIT___OFF_:
			return stateVector[0] == State._MICROWAVE_UNIT___OFF_;
		case _MICROWAVE_UNIT___ON_:
			return stateVector[0].ordinal() >= State.
					_MICROWAVE_UNIT___ON_.ordinal()&& stateVector[0].ordinal() <= State._MICROWAVE_UNIT___ON__DOORSTATUS_DOOROPENED.ordinal();
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__IDLE_STANDBY:
			return stateVector[0] == State._MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__IDLE_STANDBY;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER:
			return stateVector[0].ordinal() >= State.
					_MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER.ordinal()&& stateVector[0].ordinal() <= State._MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__START.ordinal();
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__PAUSE:
			return stateVector[0] == State._MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__PAUSE;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__START:
			return stateVector[0] == State._MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__START;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__ADDTIMER:
			return stateVector[0] == State._MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__ADDTIMER;
		case _MICROWAVE_UNIT___ON__DOORSTATUS_DOORCLOSED:
			return stateVector[1] == State._MICROWAVE_UNIT___ON__DOORSTATUS_DOORCLOSED;
		case _MICROWAVE_UNIT___ON__DOORSTATUS_DOOROPENED:
			return stateVector[1] == State._MICROWAVE_UNIT___ON__DOORSTATUS_DOOROPENED;
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
		inEventQueue.add(() -> {
			timeEvents[eventID] = true;
		});
		runCycle();
	}
	
	public Device device() {
		return device;
	}
	
	public Door door() {
		return door;
	}
	
	
	private String message;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String value) {
		this.message = value;
	}
	
	private boolean food_inside_sensed;
	
	public boolean getFood_inside_sensed() {
		return food_inside_sensed;
	}
	
	public void setFood_inside_sensed(boolean value) {
		this.food_inside_sensed = value;
	}
	
	private boolean in_use;
	
	public boolean getIn_use() {
		return in_use;
	}
	
	public void setIn_use(boolean value) {
		this.in_use = value;
	}
	
	private void effect__Microwave_Unit___on___Microwave_isWorking__startTimer_tr1() {
		exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer();
		enterSequence__Microwave_Unit___on___Microwave_isWorking__idle_standby_default();
	}
	
	/* Entry action for state '<off>'. */
	private void entryAction__Microwave_Unit___off_() {
		/* Entry action for state '<off>'. */
		device.setIsOn(false);
	}
	
	/* Entry action for state '<on>'. */
	private void entryAction__Microwave_Unit___on_() {
		/* Entry action for state '<on>'. */
		device.setIsOn(true);
	}
	
	/* Entry action for state 'idle/standby'. */
	private void entryAction__Microwave_Unit___on___Microwave_isWorking__idle_standby() {
		/* Entry action for state 'idle/standby'. */
		setMessage("Microwave is on STANDBY...");
	}
	
	/* Entry action for state 'startTimer'. */
	private void entryAction__Microwave_Unit___on___Microwave_isWorking__startTimer() {
		/* Entry action for state 'startTimer'. */
		if (door.getClosed()) {
			setMessage("Smart devices main function is here");
		}
	}
	
	/* Entry action for state 'pause'. */
	private void entryAction__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause() {
		/* Entry action for state 'pause'. */
		timerService.setTimer(this, 0, (1l * 1000l), false);
		setIn_use(false);
	}
	
	/* Entry action for state 'start'. */
	private void entryAction__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start() {
		/* Entry action for state 'start'. */
		timerService.setTimer(this, 1, (1l * 1000l), false);
		timerService.setTimer(this, 2, (1l * 1000l), true);
		setMessage("Food is heating up...");
		setIn_use(true);
	}
	
	private void entryAction__Microwave_Unit___on___Microwave_isWorking__addTimer() {
		/* Entry action for state 'addTimer'. */
		device.setTimer(device.getTimer() + 10l);
		completed = true;
	}
	
	/* Entry action for state 'DoorClosed'. */
	private void entryAction__Microwave_Unit___on__doorStatus_DoorClosed() {
		/* Entry action for state 'DoorClosed'. */
		door.setClosed(true);
	}
	
	/* Entry action for state 'DoorOpened'. */
	private void entryAction__Microwave_Unit___on__doorStatus_DoorOpened() {
		/* Entry action for state 'DoorOpened'. */
		door.setClosed(false);
		device.raisePause();
	}
	
	/* Exit action for state 'pause'. */
	private void exitAction__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause() {
		/* Exit action for state 'pause'. */
		timerService.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'start'. */
	private void exitAction__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start() {
		/* Exit action for state 'start'. */
		timerService.unsetTimer(this, 1);
		timerService.unsetTimer(this, 2);
	}
	
	/* 'default' enter sequence for state <off> */
	private void enterSequence__Microwave_Unit___off__default() {
		/* 'default' enter sequence for state <off> */
		entryAction__Microwave_Unit___off_();
		stateVector[0] = State._MICROWAVE_UNIT___OFF_;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state <on> */
	private void enterSequence__Microwave_Unit___on__default() {
		/* 'default' enter sequence for state <on> */
		entryAction__Microwave_Unit___on_();
		enterSequence__Microwave_Unit___on___Microwave_isWorking__default();
		enterSequence__Microwave_Unit___on__doorStatus_default();
	}
	
	/* 'default' enter sequence for state idle/standby */
	private void enterSequence__Microwave_Unit___on___Microwave_isWorking__idle_standby_default() {
		/* 'default' enter sequence for state idle/standby */
		entryAction__Microwave_Unit___on___Microwave_isWorking__idle_standby();
		stateVector[0] = State._MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__IDLE_STANDBY;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state startTimer */
	private void enterSequence__Microwave_Unit___on___Microwave_isWorking__startTimer_default() {
		/* 'default' enter sequence for state startTimer */
		entryAction__Microwave_Unit___on___Microwave_isWorking__startTimer();
		enterSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__default();
	}
	
	/* 'default' enter sequence for state pause */
	private void enterSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause_default() {
		/* 'default' enter sequence for state pause */
		entryAction__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause();
		stateVector[0] = State._MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__PAUSE;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state start */
	private void enterSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start_default() {
		/* 'default' enter sequence for state start */
		entryAction__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start();
		stateVector[0] = State._MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__START;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state addTimer */
	private void enterSequence__Microwave_Unit___on___Microwave_isWorking__addTimer_default() {
		/* 'default' enter sequence for state addTimer */
		entryAction__Microwave_Unit___on___Microwave_isWorking__addTimer();
		stateVector[0] = State._MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__ADDTIMER;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state DoorClosed */
	private void enterSequence__Microwave_Unit___on__doorStatus_DoorClosed_default() {
		/* 'default' enter sequence for state DoorClosed */
		entryAction__Microwave_Unit___on__doorStatus_DoorClosed();
		stateVector[1] = State._MICROWAVE_UNIT___ON__DOORSTATUS_DOORCLOSED;
		stateConfVectorPosition = 1;
	}
	
	/* 'default' enter sequence for state DoorOpened */
	private void enterSequence__Microwave_Unit___on__doorStatus_DoorOpened_default() {
		/* 'default' enter sequence for state DoorOpened */
		entryAction__Microwave_Unit___on__doorStatus_DoorOpened();
		stateVector[1] = State._MICROWAVE_UNIT___ON__DOORSTATUS_DOOROPENED;
		stateConfVectorPosition = 1;
	}
	
	/* 'default' enter sequence for region <Microwave_Unit> */
	private void enterSequence__Microwave_Unit__default() {
		/* 'default' enter sequence for region <Microwave_Unit> */
		react__Microwave_Unit___entry_Default();
	}
	
	/* 'default' enter sequence for region <Microwave_isWorking> */
	private void enterSequence__Microwave_Unit___on___Microwave_isWorking__default() {
		/* 'default' enter sequence for region <Microwave_isWorking> */
		react__Microwave_Unit___on___Microwave_isWorking___entry_Default();
	}
	
	/* 'default' enter sequence for region <Starting_Timer> */
	private void enterSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__default() {
		/* 'default' enter sequence for region <Starting_Timer> */
		react__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer___entry_Default();
	}
	
	/* 'default' enter sequence for region doorStatus */
	private void enterSequence__Microwave_Unit___on__doorStatus_default() {
		/* 'default' enter sequence for region doorStatus */
		react__Microwave_Unit___on__doorStatus__entry_Default();
	}
	
	/* Default exit sequence for state <off> */
	private void exitSequence__Microwave_Unit___off_() {
		/* Default exit sequence for state <off> */
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state <on> */
	private void exitSequence__Microwave_Unit___on_() {
		/* Default exit sequence for state <on> */
		exitSequence__Microwave_Unit___on___Microwave_isWorking_();
		exitSequence__Microwave_Unit___on__doorStatus();
		stateVector[0] = State.$NULLSTATE$;
		stateVector[1] = State.$NULLSTATE$;
		stateConfVectorPosition = 1;
	}
	
	/* Default exit sequence for state idle/standby */
	private void exitSequence__Microwave_Unit___on___Microwave_isWorking__idle_standby() {
		/* Default exit sequence for state idle/standby */
		stateVector[0] = State._MICROWAVE_UNIT___ON_;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state startTimer */
	private void exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer() {
		/* Default exit sequence for state startTimer */
		exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer_();
		stateVector[0] = State._MICROWAVE_UNIT___ON_;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state pause */
	private void exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause() {
		/* Default exit sequence for state pause */
		stateVector[0] = State._MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER;
		stateConfVectorPosition = 0;
		exitAction__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause();
	}
	
	/* Default exit sequence for state start */
	private void exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start() {
		/* Default exit sequence for state start */
		stateVector[0] = State._MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER;
		stateConfVectorPosition = 0;
		exitAction__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start();
	}
	
	/* Default exit sequence for state addTimer */
	private void exitSequence__Microwave_Unit___on___Microwave_isWorking__addTimer() {
		/* Default exit sequence for state addTimer */
		stateVector[0] = State._MICROWAVE_UNIT___ON_;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state DoorClosed */
	private void exitSequence__Microwave_Unit___on__doorStatus_DoorClosed() {
		/* Default exit sequence for state DoorClosed */
		stateVector[1] = State._MICROWAVE_UNIT___ON_;
		stateConfVectorPosition = 1;
	}
	
	/* Default exit sequence for state DoorOpened */
	private void exitSequence__Microwave_Unit___on__doorStatus_DoorOpened() {
		/* Default exit sequence for state DoorOpened */
		stateVector[1] = State._MICROWAVE_UNIT___ON_;
		stateConfVectorPosition = 1;
	}
	
	/* Default exit sequence for region <Microwave_Unit> */
	private void exitSequence__Microwave_Unit_() {
		/* Default exit sequence for region <Microwave_Unit> */
		switch (stateVector[0]) {
		case _MICROWAVE_UNIT___OFF_:
			exitSequence__Microwave_Unit___off_();
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__IDLE_STANDBY:
			exitSequence__Microwave_Unit___on___Microwave_isWorking__idle_standby();
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER:
			exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer();
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__PAUSE:
			exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause();
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__START:
			exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start();
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__ADDTIMER:
			exitSequence__Microwave_Unit___on___Microwave_isWorking__addTimer();
			break;
		default:
			break;
		}
		switch (stateVector[1]) {
		case _MICROWAVE_UNIT___ON__DOORSTATUS_DOORCLOSED:
			exitSequence__Microwave_Unit___on__doorStatus_DoorClosed();
			break;
		case _MICROWAVE_UNIT___ON__DOORSTATUS_DOOROPENED:
			exitSequence__Microwave_Unit___on__doorStatus_DoorOpened();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region <Microwave_isWorking> */
	private void exitSequence__Microwave_Unit___on___Microwave_isWorking_() {
		/* Default exit sequence for region <Microwave_isWorking> */
		switch (stateVector[0]) {
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__IDLE_STANDBY:
			exitSequence__Microwave_Unit___on___Microwave_isWorking__idle_standby();
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER:
			exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer();
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__PAUSE:
			exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause();
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__START:
			exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start();
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__ADDTIMER:
			exitSequence__Microwave_Unit___on___Microwave_isWorking__addTimer();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region <Starting_Timer> */
	private void exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer_() {
		/* Default exit sequence for region <Starting_Timer> */
		switch (stateVector[0]) {
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__PAUSE:
			exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause();
			break;
		case _MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__STARTTIMER__STARTING_TIMER__START:
			exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region doorStatus */
	private void exitSequence__Microwave_Unit___on__doorStatus() {
		/* Default exit sequence for region doorStatus */
		switch (stateVector[1]) {
		case _MICROWAVE_UNIT___ON__DOORSTATUS_DOORCLOSED:
			exitSequence__Microwave_Unit___on__doorStatus_DoorClosed();
			break;
		case _MICROWAVE_UNIT___ON__DOORSTATUS_DOOROPENED:
			exitSequence__Microwave_Unit___on__doorStatus_DoorOpened();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react__Microwave_Unit___entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence__Microwave_Unit___off__default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer___entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__Microwave_Unit___on___Microwave_isWorking___entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence__Microwave_Unit___on___Microwave_isWorking__idle_standby_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__Microwave_Unit___on__doorStatus__entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence__Microwave_Unit___on__doorStatus_DoorClosed_default();
	}
	
	/* The reactions of exit default. */
	private void react__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer___exit_Default() {
		/* The reactions of exit default. */
		effect__Microwave_Unit___on___Microwave_isWorking__startTimer_tr1();
	}
	
	private long _Microwave_Unit___off__react(long transitioned_before) {
		/* The reactions of state <off>. */
		long transitioned_after = transitioned_before;
		if (!(getDoCompletion())) {
			if (transitioned_after<0l) {
				if (device.on) {
					exitSequence__Microwave_Unit___off_();
					enterSequence__Microwave_Unit___on__default();
					transitioned_after = 0l;
				}
			}
			/* If no transition was taken */
			if (transitioned_after==transitioned_before) {
				/* then execute local reactions. */
				transitioned_after = transitioned_before;
			}
		}
		return transitioned_after;
	}
	
	private long _Microwave_Unit___on__react(long transitioned_before) {
		/* The reactions of state <on>. */
		long transitioned_after = transitioned_before;
		if (!(getDoCompletion())) {
			if (transitioned_after<0l) {
				if (device.off) {
					exitSequence__Microwave_Unit___on_();
					enterSequence__Microwave_Unit___off__default();
					transitioned_after = 1l;
				}
			}
			/* If no transition was taken */
			if (transitioned_after==transitioned_before) {
				/* then execute local reactions. */
				transitioned_after = transitioned_before;
			}
		}
		return transitioned_after;
	}
	
	private long _Microwave_Unit___on___Microwave_isWorking__idle_standby_react(long transitioned_before) {
		/* The reactions of state idle/standby. */
		long transitioned_after = transitioned_before;
		if (!(getDoCompletion())) {
			if (transitioned_after<0l) {
				if (((device.start) && (door.getClosed()))) {
					exitSequence__Microwave_Unit___on___Microwave_isWorking__idle_standby();
					enterSequence__Microwave_Unit___on___Microwave_isWorking__startTimer_default();
					transitioned_after = 0l;
				} else {
					if (device.addTimer) {
						exitSequence__Microwave_Unit___on___Microwave_isWorking__idle_standby();
						enterSequence__Microwave_Unit___on___Microwave_isWorking__addTimer_default();
						transitioned_after = 0l;
					} else {
						if (device.resetTimer) {
							exitSequence__Microwave_Unit___on___Microwave_isWorking__idle_standby();
							device.setTimer(0l);
							enterSequence__Microwave_Unit___on___Microwave_isWorking__idle_standby_default();
							transitioned_after = 0l;
						}
					}
				}
			}
		}
		return transitioned_after;
	}
	
	private long _Microwave_Unit___on___Microwave_isWorking__startTimer_react(long transitioned_before) {
		/* The reactions of state startTimer. */
		long transitioned_after = transitioned_before;
		if (!(getDoCompletion())) {
			if (transitioned_after<0l) {
				if (device.pause) {
					exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer();
					enterSequence__Microwave_Unit___on___Microwave_isWorking__idle_standby_default();
					transitioned_after = 0l;
				}
			}
		}
		return transitioned_after;
	}
	
	private long _Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause_react(long transitioned_before) {
		/* The reactions of state pause. */
		long transitioned_after = transitioned_before;
		if (!(getDoCompletion())) {
			if (transitioned_after<0l) {
				if (device.getTimer()>0l) {
					exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause();
					enterSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start_default();
					_Microwave_Unit___on___Microwave_isWorking__startTimer_react(0l);
					transitioned_after = 0l;
				} else {
					if (((timeEvents[0]) && (device.getTimer()<=0l))) {
						exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause();
						device.raisePause();
						timeEvents[0] = false;
						enterSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause_default();
						_Microwave_Unit___on___Microwave_isWorking__startTimer_react(0l);
						transitioned_after = 0l;
					}
				}
			}
			/* If no transition was taken */
			if (transitioned_after==transitioned_before) {
				/* then execute local reactions. */
				transitioned_after = _Microwave_Unit___on___Microwave_isWorking__startTimer_react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	private long _Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start_react(long transitioned_before) {
		/* The reactions of state start. */
		long transitioned_after = transitioned_before;
		if (!(getDoCompletion())) {
			if (transitioned_after<0l) {
				if (device.getTimer()<=0l) {
					exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start();
					device.raisePause();
					enterSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__pause_default();
					_Microwave_Unit___on___Microwave_isWorking__startTimer_react(0l);
					transitioned_after = 0l;
				} else {
					if (((timeEvents[1]) && (getFood_inside_sensed()))) {
						exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start();
						device.timer--;
						timeEvents[1] = false;
						enterSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start_default();
						_Microwave_Unit___on___Microwave_isWorking__startTimer_react(0l);
						transitioned_after = 0l;
					} else {
						if (((timeEvents[2]) && (!(getFood_inside_sensed())))) {
							exitSequence__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer__start();
							device.raisePause();
							setMessage("No food inside !");
							timeEvents[2] = false;
							react__Microwave_Unit___on___Microwave_isWorking__startTimer__Starting_Timer___exit_Default();
							transitioned_after = 0l;
						}
					}
				}
			}
			/* If no transition was taken */
			if (transitioned_after==transitioned_before) {
				/* then execute local reactions. */
				transitioned_after = _Microwave_Unit___on___Microwave_isWorking__startTimer_react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	private long _Microwave_Unit___on___Microwave_isWorking__addTimer_react(long transitioned_before) {
		/* The reactions of state addTimer. */
		long transitioned_after = transitioned_before;
		if (getDoCompletion()) {
			/* Default exit sequence for state addTimer */
			stateVector[0] = State._MICROWAVE_UNIT___ON_;
			stateConfVectorPosition = 0;
			/* 'default' enter sequence for state idle/standby */
			entryAction__Microwave_Unit___on___Microwave_isWorking__idle_standby();
			stateVector[0] = State._MICROWAVE_UNIT___ON___MICROWAVE_ISWORKING__IDLE_STANDBY;
			stateConfVectorPosition = 0;
		}
		return transitioned_after;
	}
	
	private long _Microwave_Unit___on__doorStatus_DoorClosed_react(long transitioned_before) {
		/* The reactions of state DoorClosed. */
		long transitioned_after = transitioned_before;
		if (!(getDoCompletion())) {
			if (transitioned_after<1l) {
				if (door.open) {
					exitSequence__Microwave_Unit___on__doorStatus_DoorClosed();
					enterSequence__Microwave_Unit___on__doorStatus_DoorOpened_default();
					_Microwave_Unit___on__react(0l);
					transitioned_after = 1l;
				}
			}
			/* If no transition was taken */
			if (transitioned_after==transitioned_before) {
				/* then execute local reactions. */
				transitioned_after = _Microwave_Unit___on__react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	private long _Microwave_Unit___on__doorStatus_DoorOpened_react(long transitioned_before) {
		/* The reactions of state DoorOpened. */
		long transitioned_after = transitioned_before;
		if (!(getDoCompletion())) {
			if (transitioned_after<1l) {
				if (door.close) {
					exitSequence__Microwave_Unit___on__doorStatus_DoorOpened();
					enterSequence__Microwave_Unit___on__doorStatus_DoorClosed_default();
					_Microwave_Unit___on__react(0l);
					transitioned_after = 1l;
				}
			}
			/* If no transition was taken */
			if (transitioned_after==transitioned_before) {
				/* then execute local reactions. */
				transitioned_after = _Microwave_Unit___on__react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	/* Can be used by the client code to trigger a run to completion step without raising an event. */
	public void triggerWithoutEvent() {
		runCycle();
	}
}
