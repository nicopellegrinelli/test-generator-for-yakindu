/* Generated by itemis CREATE code generator. */
package statechart;

import com.yakindu.core.IEventDriven;
import com.yakindu.core.IStatemachine;
import com.yakindu.core.ITimed;
import com.yakindu.core.ITimerService;
import java.util.LinkedList;
import java.util.Queue;

public class Network_Component implements ITimed, IEventDriven {
	public enum State {
		_NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING_,
		_NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION,
		_NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER,
		_NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__SUCCESS,
		_NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__FAILURE,
		_NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__NETWORKTIMEOUT,
		_NETWORK_COMPONENT___OFF_,
		$NULLSTATE$
	};
	
	private final State[] stateVector = new State[1];
	
	private ITimerService timerService;
	
	private final boolean[] timeEvents = new boolean[4];
	
	private Queue<Runnable> inEventQueue = new LinkedList<Runnable>();
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		return isExecuting;
	}
	
	protected void setIsExecuting(boolean value) {
		this.isExecuting = value;
	}
	public Network_Component() {
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		
		/* Default init sequence for statechart Network_Component */
		setTimeout_counter(0l);
		setTimeout_value(5l);
		setReconnect_value(5l);
		setConnection(false);
		
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
		/* Default enter sequence for statechart Network_Component */
		enterSequence__Network_Component__default();
		isExecuting = false;
	}
	
	public void exit() {
		/* Deactivates the state machine. */
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		/* Default exit sequence for statechart Network_Component */
		exitSequence__Network_Component_();
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
	private void clearInEvents() {
		on = false;
		off = false;
		timeEvents[0] = false;
		timeEvents[1] = false;
		timeEvents[2] = false;
		timeEvents[3] = false;
	}
	
	private void microStep() {
		switch (stateVector[0]) {
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__SUCCESS:
			_Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success_react(-1l);
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__FAILURE:
			_Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure_react(-1l);
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__NETWORKTIMEOUT:
			_Network_Component___Network_Component_Working___Network_Connection__networkTimeout_react(-1l);
			break;
		case _NETWORK_COMPONENT___OFF_:
			_Network_Component___off__react(-1l);
			break;
		default:
			break;
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
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING_:
			return stateVector[0].ordinal() >= State.
					_NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING_.ordinal()&& stateVector[0].ordinal() <= State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__NETWORKTIMEOUT.ordinal();
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION:
			return stateVector[0].ordinal() >= State.
					_NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION.ordinal()&& stateVector[0].ordinal() <= State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__FAILURE.ordinal();
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER:
			return stateVector[0].ordinal() >= State.
					_NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER.ordinal()&& stateVector[0].ordinal() <= State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__FAILURE.ordinal();
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__SUCCESS:
			return stateVector[0] == State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__SUCCESS;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__FAILURE:
			return stateVector[0] == State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__FAILURE;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__NETWORKTIMEOUT:
			return stateVector[0] == State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__NETWORKTIMEOUT;
		case _NETWORK_COMPONENT___OFF_:
			return stateVector[0] == State._NETWORK_COMPONENT___OFF_;
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
	
	
	private boolean on;
	
	
	public void raiseOn() {
		inEventQueue.add(() -> {
			on = true;
		});
		runCycle();
	}
	
	private boolean off;
	
	
	public void raiseOff() {
		inEventQueue.add(() -> {
			off = true;
		});
		runCycle();
	}
	
	private long timeout_counter;
	
	public long getTimeout_counter() {
		return timeout_counter;
	}
	
	public void setTimeout_counter(long value) {
		this.timeout_counter = value;
	}
	
	private long timeout_value;
	
	public long getTimeout_value() {
		return timeout_value;
	}
	
	public void setTimeout_value(long value) {
		this.timeout_value = value;
	}
	
	private long reconnect_value;
	
	public long getReconnect_value() {
		return reconnect_value;
	}
	
	public void setReconnect_value(long value) {
		this.reconnect_value = value;
	}
	
	private boolean connection;
	
	public boolean getConnection() {
		return connection;
	}
	
	public void setConnection(boolean value) {
		this.connection = value;
	}
	
	/* Entry action for state '<Network_Component_Working>'. */
	private void entryAction__Network_Component___Network_Component_Working_() {
		/* Entry action for state '<Network_Component_Working>'. */
		setConnection(true);
		setTimeout_counter(timeout_value);
	}
	
	/* Entry action for state 'connectingToServer'. */
	private void entryAction__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer() {
		/* Entry action for state 'connectingToServer'. */
		setConnection(true);
	}
	
	/* Entry action for state 'success'. */
	private void entryAction__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success() {
		/* Entry action for state 'success'. */
		timerService.setTimer(this, 0, (1l * 1000l), true);
	}
	
	/* Entry action for state 'failure'. */
	private void entryAction__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure() {
		/* Entry action for state 'failure'. */
		timerService.setTimer(this, 1, (getReconnect_value() * 1000l), false);
		timerService.setTimer(this, 2, (1l * 1000l), true);
	}
	
	/* Entry action for state 'networkTimeout'. */
	private void entryAction__Network_Component___Network_Component_Working___Network_Connection__networkTimeout() {
		/* Entry action for state 'networkTimeout'. */
		timerService.setTimer(this, 3, (1l * 1000l), false);
		setConnection(false);
		setTimeout_counter(timeout_value);
	}
	
	/* Entry action for state '<off>'. */
	private void entryAction__Network_Component___off_() {
		/* Entry action for state '<off>'. */
		setConnection(false);
	}
	
	/* Exit action for state 'success'. */
	private void exitAction__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success() {
		/* Exit action for state 'success'. */
		timerService.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'failure'. */
	private void exitAction__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure() {
		/* Exit action for state 'failure'. */
		timerService.unsetTimer(this, 1);
		timerService.unsetTimer(this, 2);
	}
	
	/* Exit action for state 'networkTimeout'. */
	private void exitAction__Network_Component___Network_Component_Working___Network_Connection__networkTimeout() {
		/* Exit action for state 'networkTimeout'. */
		timerService.unsetTimer(this, 3);
	}
	
	/* 'default' enter sequence for state <Network_Component_Working> */
	private void enterSequence__Network_Component___Network_Component_Working__default() {
		/* 'default' enter sequence for state <Network_Component_Working> */
		entryAction__Network_Component___Network_Component_Working_();
		enterSequence__Network_Component___Network_Component_Working___Network_Connection__default();
	}
	
	/* 'default' enter sequence for state checkingForNetworkConnection */
	private void enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection_default() {
		/* 'default' enter sequence for state checkingForNetworkConnection */
		enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__default();
	}
	
	/* 'default' enter sequence for state connectingToServer */
	private void enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer_default() {
		/* 'default' enter sequence for state connectingToServer */
		entryAction__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer();
		enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__default();
	}
	
	/* 'default' enter sequence for state success */
	private void enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success_default() {
		/* 'default' enter sequence for state success */
		entryAction__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success();
		stateVector[0] = State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__SUCCESS;
	}
	
	/* 'default' enter sequence for state failure */
	private void enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure_default() {
		/* 'default' enter sequence for state failure */
		entryAction__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure();
		stateVector[0] = State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__FAILURE;
	}
	
	/* 'default' enter sequence for state networkTimeout */
	private void enterSequence__Network_Component___Network_Component_Working___Network_Connection__networkTimeout_default() {
		/* 'default' enter sequence for state networkTimeout */
		entryAction__Network_Component___Network_Component_Working___Network_Connection__networkTimeout();
		stateVector[0] = State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__NETWORKTIMEOUT;
	}
	
	/* 'default' enter sequence for state <off> */
	private void enterSequence__Network_Component___off__default() {
		/* 'default' enter sequence for state <off> */
		entryAction__Network_Component___off_();
		stateVector[0] = State._NETWORK_COMPONENT___OFF_;
	}
	
	/* 'default' enter sequence for region <Network_Component> */
	private void enterSequence__Network_Component__default() {
		/* 'default' enter sequence for region <Network_Component> */
		react__Network_Component___entry_Default();
	}
	
	/* 'default' enter sequence for region <Network_Connection> */
	private void enterSequence__Network_Component___Network_Component_Working___Network_Connection__default() {
		/* 'default' enter sequence for region <Network_Connection> */
		react__Network_Component___Network_Component_Working___Network_Connection___entry_Default();
	}
	
	/* 'default' enter sequence for region <CheckingForConnectionUpdates> */
	private void enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__default() {
		/* 'default' enter sequence for region <CheckingForConnectionUpdates> */
		react__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates___entry_Default();
	}
	
	/* 'default' enter sequence for region <Server_Connection> */
	private void enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__default() {
		/* 'default' enter sequence for region <Server_Connection> */
		react__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection___entry_Default();
	}
	
	/* Default exit sequence for state <Network_Component_Working> */
	private void exitSequence__Network_Component___Network_Component_Working_() {
		/* Default exit sequence for state <Network_Component_Working> */
		exitSequence__Network_Component___Network_Component_Working___Network_Connection_();
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state checkingForNetworkConnection */
	private void exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection() {
		/* Default exit sequence for state checkingForNetworkConnection */
		exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates_();
		stateVector[0] = State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING_;
	}
	
	/* Default exit sequence for state connectingToServer */
	private void exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer() {
		/* Default exit sequence for state connectingToServer */
		exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection_();
		stateVector[0] = State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION;
	}
	
	/* Default exit sequence for state success */
	private void exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success() {
		/* Default exit sequence for state success */
		stateVector[0] = State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER;
		exitAction__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success();
	}
	
	/* Default exit sequence for state failure */
	private void exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure() {
		/* Default exit sequence for state failure */
		stateVector[0] = State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER;
		exitAction__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure();
	}
	
	/* Default exit sequence for state networkTimeout */
	private void exitSequence__Network_Component___Network_Component_Working___Network_Connection__networkTimeout() {
		/* Default exit sequence for state networkTimeout */
		stateVector[0] = State._NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING_;
		exitAction__Network_Component___Network_Component_Working___Network_Connection__networkTimeout();
	}
	
	/* Default exit sequence for state <off> */
	private void exitSequence__Network_Component___off_() {
		/* Default exit sequence for state <off> */
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for region <Network_Component> */
	private void exitSequence__Network_Component_() {
		/* Default exit sequence for region <Network_Component> */
		switch (stateVector[0]) {
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING_:
			exitSequence__Network_Component___Network_Component_Working_();
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection();
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer();
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__SUCCESS:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success();
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__FAILURE:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure();
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__NETWORKTIMEOUT:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__networkTimeout();
			break;
		case _NETWORK_COMPONENT___OFF_:
			exitSequence__Network_Component___off_();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region <Network_Connection> */
	private void exitSequence__Network_Component___Network_Component_Working___Network_Connection_() {
		/* Default exit sequence for region <Network_Connection> */
		switch (stateVector[0]) {
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection();
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer();
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__SUCCESS:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success();
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__FAILURE:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure();
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__NETWORKTIMEOUT:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__networkTimeout();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region <CheckingForConnectionUpdates> */
	private void exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates_() {
		/* Default exit sequence for region <CheckingForConnectionUpdates> */
		switch (stateVector[0]) {
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer();
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__SUCCESS:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success();
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__FAILURE:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region <Server_Connection> */
	private void exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection_() {
		/* Default exit sequence for region <Server_Connection> */
		switch (stateVector[0]) {
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__SUCCESS:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success();
			break;
		case _NETWORK_COMPONENT___NETWORK_COMPONENT_WORKING___NETWORK_CONNECTION__CHECKINGFORNETWORKCONNECTION__CHECKINGFORCONNECTIONUPDATES__CONNECTINGTOSERVER__SERVER_CONNECTION__FAILURE:
			exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure();
			break;
		default:
			break;
		}
	}
	
	/* The reactions of state null. */
	private void react__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection___choice_0() {
		/* The reactions of state null. */
		if (getConnection()) {
			enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success_default();
		} else {
			enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure_default();
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react__Network_Component___entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence__Network_Component___Network_Component_Working__default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates___entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection___entry_Default() {
		/* Default react sequence for initial entry  */
		react__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection___choice_0();
	}
	
	/* Default react sequence for initial entry  */
	private void react__Network_Component___Network_Component_Working___Network_Connection___entry_Default() {
		/* Default react sequence for initial entry  */
		enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection_default();
	}
	
	private long _Network_Component___Network_Component_Working__react(long transitioned_before) {
		/* The reactions of state <Network_Component_Working>. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (off) {
				exitSequence__Network_Component___Network_Component_Working_();
				enterSequence__Network_Component___off__default();
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
	
	private long _Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection_react(long transitioned_before) {
		/* The reactions of state checkingForNetworkConnection. */
		long transitioned_after = transitioned_before;
		/* Always execute local reactions. */
		transitioned_after = _Network_Component___Network_Component_Working__react(transitioned_before);
		return transitioned_after;
	}
	
	private long _Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer_react(long transitioned_before) {
		/* The reactions of state connectingToServer. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (getTimeout_counter()<=0l) {
				exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection();
				enterSequence__Network_Component___Network_Component_Working___Network_Connection__networkTimeout_default();
				_Network_Component___Network_Component_Working__react(0l);
				transitioned_after = 0l;
			}
		}
		/* If no transition was taken */
		if (transitioned_after==transitioned_before) {
			/* then execute local reactions. */
			transitioned_after = _Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection_react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__success_react(long transitioned_before) {
		/* The reactions of state success. */
		long transitioned_after = transitioned_before;
		/* Always execute local reactions. */
		if (timeEvents[0]) {
			timeout_counter--;
		}
		transitioned_after = _Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer_react(transitioned_before);
		return transitioned_after;
	}
	
	private long _Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer__Server_Connection__failure_react(long transitioned_before) {
		/* The reactions of state failure. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (timeEvents[1]) {
				exitSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer();
				setConnection(true);
				timeEvents[1] = false;
				enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer_default();
				_Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection_react(0l);
				transitioned_after = 0l;
			}
		}
		/* If no transition was taken */
		if (transitioned_after==transitioned_before) {
			/* then execute local reactions. */
			if (timeEvents[2]) {
				timeout_counter--;
			}
			transitioned_after = _Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer_react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _Network_Component___Network_Component_Working___Network_Connection__networkTimeout_react(long transitioned_before) {
		/* The reactions of state networkTimeout. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (((timeEvents[3]) && (!(getConnection())))) {
				exitSequence__Network_Component___Network_Component_Working___Network_Connection__networkTimeout();
				timeEvents[3] = false;
				enterSequence__Network_Component___Network_Component_Working___Network_Connection__checkingForNetworkConnection__CheckingForConnectionUpdates__connectingToServer_default();
				_Network_Component___Network_Component_Working__react(0l);
				transitioned_after = 0l;
			}
		}
		/* If no transition was taken */
		if (transitioned_after==transitioned_before) {
			/* then execute local reactions. */
			transitioned_after = _Network_Component___Network_Component_Working__react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _Network_Component___off__react(long transitioned_before) {
		/* The reactions of state <off>. */
		long transitioned_after = transitioned_before;
		if (transitioned_after<0l) {
			if (on) {
				exitSequence__Network_Component___off_();
				enterSequence__Network_Component___Network_Component_Working__default();
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
