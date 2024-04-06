package ysc2sctunit.ysc.reading;

import java.util.Map;

public interface IYscReader {

	public String getStatechartName();

	public Map<String, String> getStatesNames();

	public Map<String, String> getEventsNames();

	public Map<String, String> getInterfacesNames();

}
