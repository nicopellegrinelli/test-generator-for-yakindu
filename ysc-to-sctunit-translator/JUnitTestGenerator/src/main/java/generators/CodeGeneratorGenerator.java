package generators;

import java.io.File;
import java.io.IOException;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

public final class CodeGeneratorGenerator {
	public static void genarate(String projectPath, String statechartName, String packageName, String projectName, boolean time)
			throws IOException {
		File genFile = new File(projectPath + "\\model\\" + statechartName + ".sgen");
		STGroupFile group = new STGroupFile(".\\template\\generator.stg");
		ST st = group.getInstanceOf("generator");
		st.add("project_name", projectName);
		st.add("package_name", packageName);
		st.add("statechart_name", statechartName);
		if(time) st.add("time", "");
		st.write(genFile, null);
	}
}
