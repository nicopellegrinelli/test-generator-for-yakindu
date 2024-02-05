package support;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * The Class CompilerManager.
 */
public final class CompilerManager {
	
	/**
	 * Compile.
	 *
	 * @param compilerD the string "-d ProjectPath", where ProjectPath is the path of the directory containing the .class files
	 * @param compilerClasspath the string "-classpath ClassPath", where ClassPath is the path of the directory containing the .java files
	 * @param classPath the path of the class to compile
	 */
	public static void compile(String compilerD, String compilerClasspath, String classPath){
		System.out.println("*******************************************");
		System.out.println("Compiling...");
		System.out.println("*******************************************");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		List<String> compilationArgs = new ArrayList<String>();
		compilationArgs.addAll(Arrays.asList(compilerD.split(" ")));
		compilationArgs.addAll(Arrays.asList(compilerClasspath.split(" ")));
		compilationArgs.add("-implicit:class");
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, null, null);
		File f = new File(classPath);
        Iterable<? extends JavaFileObject> compilationUnits = stdFileManager.getJavaFileObjectsFromFiles(Arrays.asList(f));
		compiler.getTask(null, null, null, compilationArgs, null, compilationUnits).call();
	}
}
