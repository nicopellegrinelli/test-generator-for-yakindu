package generators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import visitors.ClassDeclarationVisitor;
import visitors.ConstructorDeclarationVisitor;
import visitors.MethodDeclarationVisitor;

public final class Generators {
	public static void genarateSgen(String projectPath, String statechartName, String packageName, String projectName, boolean time)
			throws IOException {
		System.out.println("*******************************************");
		System.out.println("Generating .sgen file...");
		System.out.println("*******************************************");
		File genFile = new File(projectPath + "\\model\\" + statechartName + ".sgen");
		STGroupFile group = new STGroupFile(".\\template\\generator.stg");
		ST st = group.getInstanceOf("generator");
		st.add("project_name", projectName);
		st.add("package_name", packageName);
		st.add("statechart_name", statechartName);
		if(time) st.add("time", "");
		st.write(genFile, null);
	}
	
	public static void genarateJava(String projectPath, String itemisScc)
			throws IOException {
		System.out.println("*******************************************");
		System.out.println("Calling Itemis Create code generator...");
		System.out.println("*******************************************");
		ProcessBuilder pb = new ProcessBuilder();
		pb.redirectErrorStream(true);
		pb.directory(new File(projectPath));
		pb.command(itemisScc);
		Process p = pb.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		System.out.println("");
		reader.close();
	}
	
	public static void genarateSimplifiedJava(String classPath, String simplifiedClassPath)
			throws IOException {
		System.out.println("*******************************************");
		System.out.println("Generating a simplified java class...");
		System.out.println("*******************************************");
		CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(classPath));
		VoidVisitor<Void> classVisitor = new ClassDeclarationVisitor();
		VoidVisitor<Void> constructorVisitor = new ConstructorDeclarationVisitor();
		VoidVisitor<Void> methodVisitor = new MethodDeclarationVisitor();
		classVisitor.visit(cu, null);
		constructorVisitor.visit(cu, null);
		methodVisitor.visit(cu, null);
		BufferedWriter writer = new BufferedWriter(new FileWriter(simplifiedClassPath));
		writer.write(cu.toString());
		writer.close();
	}
	
	public static void genarateJunit(String evoTarget, String evoOptions, String evoBaseDir)
			throws IOException {
		System.out.println("*******************************************");
		System.out.println("Calling Evosuite test generator...");
		System.out.println("*******************************************");
	    List<String> evoArgs = new ArrayList<>();
		evoArgs.addAll(Arrays.asList(evoTarget.split(" ")));
		evoArgs.addAll(Arrays.asList(evoOptions.split(" ")));
		evoArgs.addAll(Arrays.asList(evoBaseDir.split(" ")));
		evoArgs.add("-Dsearch_budget=1");
		evoArgs.add("-generateSuite");
		org.evosuite.EvoSuite.main(evoArgs.toArray(new String[evoArgs.size()]));
	}
	

}
