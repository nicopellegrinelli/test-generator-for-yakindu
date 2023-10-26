package callhandling;

import com.yakindu.core.TimerService;

public class CallHandlingClient {

	public static void main(String[] args) throws InterruptedException {

		// Create the state machine:
		CallHandlingMachine sm = new CallHandlingMachine();
		sm.setTimerService(new TimerService());

		// Enter the state machine and implicitly activate its "Idle" state:
		sm.enter();

		// Raise an incoming call:
		sm.phone().raiseIncoming_call();

		// Accept the call:
		sm.user().raiseAccept_call();
		
		// Keep the phone conversation busy for a while:
		for (int i = 0; i < 50; i++) {
			Thread.sleep(200);
		}

		// Before hang-up, output the duration of the call:
		System.out.println(String.format("The phone call took %d seconds.", sm.phone().getDuration()));

		// Hang up the phone:
		sm.user().raiseDismiss_call();
	}
}