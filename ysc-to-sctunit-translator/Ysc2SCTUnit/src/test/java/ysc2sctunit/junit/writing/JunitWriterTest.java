package ysc2sctunit.junit.writing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class JunitWriterTest {
	private static final String BASE_PATH = "basepath";
	private static final String PACKAGE_NAME = "impl";
	private static final String FILE_NAME = "Statechart4";
	private static final String BINARY_DIR = "bin";
	private static final String TEST_DIR = "test";
	private static final String REPORT_DIR = "report";
	
	private static IJunitWriter writer;

	private static String rootPath;
	private static String junitPath;
	private static String scaffoldingPath;
	private static String csvPath;

	@ClassRule
	public static TemporaryFolder tmpFolder = new TemporaryFolder();

	@BeforeClass
	public static void initTempFolder() throws IOException {
		writer = new JunitWriter();
		rootPath = tmpFolder.getRoot().getCanonicalFile().toString();
		junitPath = rootPath + "/" + BASE_PATH + "/" + TEST_DIR + "/" + FILE_NAME + "_ESTest.java";
		scaffoldingPath = rootPath + "/" + BASE_PATH + "/" + TEST_DIR + "/" + FILE_NAME + "Scaffolding_ESTest.java";
		csvPath = rootPath + "/" + BASE_PATH + "/" + REPORT_DIR + "/report.csv";
		tmpFolder.newFolder(BASE_PATH, TEST_DIR);
		tmpFolder.newFolder(BASE_PATH, REPORT_DIR);
		String compilerD = "-d " + "src/test/resources/" + BINARY_DIR;
		String compilerClasspath = "-classpath " + "src/test/resources";
		String javaPath = "src/test/resources/" + PACKAGE_NAME + "/" + FILE_NAME + ".java";
		compile(compilerD, compilerClasspath, javaPath);
	}

	@Test
	public void testCorrectPath() throws IOException {
		assertFalse(Files.exists(Paths.get(junitPath)));
		assertFalse(Files.exists(Paths.get(scaffoldingPath)));
		assertFalse(Files.exists(Paths.get(csvPath)));
		String classOption = "-class " + PACKAGE_NAME + "." + FILE_NAME;
		String projectCpOption = "-projectCP " + "src/test/resources/" + BINARY_DIR;
		String testDirOption = "-Dtest_dir=" + rootPath + "/" + BASE_PATH + "/" + TEST_DIR;
		String reportDirOption = "-Dreport_dir=" + rootPath + "/" + BASE_PATH + "/" + REPORT_DIR;
		writer.callEvosuite(classOption, projectCpOption, testDirOption, reportDirOption, true, 10);
		assertTrue(Files.exists(Paths.get(junitPath)));
		assertTrue(Files.exists(Paths.get(scaffoldingPath)));
		assertTrue(Files.exists(Paths.get(csvPath)));
	}
	
	private static void compile(String compilerD, String compilerClasspath, String javaPath) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		List<String> compilationArgs = new ArrayList<String>();
		compilationArgs.addAll(Arrays.asList(compilerD.split(" ")));
		compilationArgs.addAll(Arrays.asList(compilerClasspath.split(" ")));
		compilationArgs.add("-implicit:class");
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, null, null);
		File f = new File(javaPath);
		Iterable<? extends JavaFileObject> compilationUnits = stdFileManager
				.getJavaFileObjectsFromFiles(Arrays.asList(f));
		compiler.getTask(null, null, null, compilationArgs, null, compilationUnits).call();
	}

}
