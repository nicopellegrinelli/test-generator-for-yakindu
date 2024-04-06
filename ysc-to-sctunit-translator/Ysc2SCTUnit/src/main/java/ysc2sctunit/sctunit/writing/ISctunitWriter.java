package ysc2sctunit.sctunit.writing;

import java.io.IOException;
import java.util.List;

import ysc2sctunit.junit.reading.TestCase;

public interface ISctunitWriter {

	public void writeSctunit(String sctunitPath, String statechartName, List<TestCase> testCaseList) throws IOException;

}
