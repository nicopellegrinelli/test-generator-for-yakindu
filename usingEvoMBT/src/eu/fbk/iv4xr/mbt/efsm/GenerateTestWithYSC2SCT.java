package eu.fbk.iv4xr.mbt.efsm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ysc2sctunit.Ysc2Sctunit;

public class GenerateTestWithYSC2SCT {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

//		 -binaryDir <arg>         relative path of the (existing) directory where
//         the binary (.class) files relative to the
//         generated java code will be put. For example if
//         working in a Maven project,it should be
//         "target\classes"; default is "bin".
		
		//String currentPath = new File(".").getAbsolutePath();
		String currentPath = "C:\\Users\\angel\\codicefromrepos\\ricerca\\test-generator-for-yakindu\\usingEvoMBT\\";

		String binaryDir = "-binaryDir";
		
		String sccPos = "C:\\Users\\angel\\programmi\\itemis_CREATE\\scc.bat";
		String scc = "-scc";
		
		String[] genargs = {binaryDir, currentPath + "\\temp_bin", scc, sccPos};
		Ysc2Sctunit.main(genargs);
	}

}
