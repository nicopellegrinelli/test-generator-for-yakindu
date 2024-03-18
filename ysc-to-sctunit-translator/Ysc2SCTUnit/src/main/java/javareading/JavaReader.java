package javareading;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;

public final class JavaReader {

	public static List<ProceedTime> getProceedTimes(String javaPath) throws FileNotFoundException {
		// Get the compilation unit of the java class
		CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(javaPath));
		// Create a dictionary for the time events, the key is the ID, the value is the
		// associated time
		List<ProceedTime> proceedTimes = new ArrayList<ProceedTime>();
		for (MethodCallExpr m : cu.findAll(MethodCallExpr.class)) {
			// At each time event correspond a setTimer() method call
			if (m.getNameAsString().equals("setTimer")) {
				// The second argument is the integer representing the ID
				int id = Integer.parseInt(m.getArgument(1).toString());
				// The third argument represents the proceed time in millisconds associated with
				// the event, e.g: (5l * 1000l), (500l / 1000000l), 500l
				String expr = m.getArgument(2).toString().replaceAll("[()]", "");
				int value = Integer.parseInt(expr.substring(0, expr.indexOf('l')));
				TimeUnit unit;
				if (expr.contains("* 1000l")) {
					unit = TimeUnit.SECONDS;
				} else if(expr.contains("/ 1000l")) {
					unit = TimeUnit.MICROSECONDS;
				} else if (expr.contains("/ 1000000l")) {
					unit = TimeUnit.NANOSECONDS;
				} else {
					unit = TimeUnit.MILLISECONDS;
				}
				proceedTimes.add(new ProceedTime(id, value, unit));
			}
		}
		return proceedTimes;
	}

}
