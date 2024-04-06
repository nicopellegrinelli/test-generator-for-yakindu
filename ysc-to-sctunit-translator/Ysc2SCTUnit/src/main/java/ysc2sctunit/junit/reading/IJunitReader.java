package ysc2sctunit.junit.reading;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import ysc2sctunit.java.reading.ProceedTime;

public interface IJunitReader {
	
	public List<TestCase> getTestCases(String junitPath, String statechartName, Map<String, String> statesNames,
			Map<String, String> eventsNames, Map<String, String> interfacesNames,
			Map<Integer, ProceedTime> proceedTimes) throws IOException;
	
}
