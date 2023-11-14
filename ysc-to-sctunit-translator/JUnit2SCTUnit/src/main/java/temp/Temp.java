package temp;

import org.stringtemplate.v4.STGroupFile;

public class Temp {
	public static void main(String[] args) {
		STGroupFile group = new STGroupFile(".\\template\\action.stg");
		System.out.println(group.getInstanceOf("test_class").add("statechart_name", "LightSwitch").render());
	}
	
}
