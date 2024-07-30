package eu.fbk.iv4xr.mbt.efsm;

import eu.fbk.iv4xr.mbt.Main;

public class GenerateTestWithEvoMBT {

	public static void main(String[] args) {
		String[] genargs = { "-sbt", "-Dsut_efsm=examples.traffic_light" };
		Main.main(genargs);
	}

}
