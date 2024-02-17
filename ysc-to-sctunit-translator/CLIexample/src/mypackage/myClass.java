package mypackage;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class myClass {

	public static void main(String[] args) throws ParseException {
	      Options options = new Options();
	      Option projectPath = Option.builder("projectPath")
	    		  .required()
                  .argName("arg")
                  .hasArg()
                  .desc("path of the Java project, the base path of the execution")
                  .build();
	      Option sourceDir = Option.builder("sourceDir")
	    		  .required()
                  .argName("arg")
                  .hasArg()
                  .desc("relative path of the directory where the statechart file is located")
                  .build(); 
	      Option sourceFile = Option.builder("sourceFile")
	    		  .required()
                  .argName("arg")
                  .hasArg()
                  .desc("name of the statechart file without extension, the extension can either be .ysc or .sct")
                  .build();
	      Option targetDir = Option.builder("targetDir")
                  .argName("arg")
                  .hasArg()
                  .desc("relative path of the directory where the generated packages will be placed, default is \"src\"")
                  .build();
	      Option targetPackage = Option.builder("targetPackage")
	    		  .required()
                  .argName("arg")
                  .hasArg()
                  .desc("package where the .java file implementing the statechart will be placed")
                  .build();
	      Option timeService = new Option("t", "timeService", false, "enable the generation of a timer service for statecharts that use timed event triggers");
	      Option help = new Option("h", "help", false, "print this message");

	      options.addOption(projectPath);
	      options.addOption(sourceDir);
	      options.addOption(sourceFile);
	      options.addOption(targetDir);
	      options.addOption(targetPackage);
	      options.addOption(timeService);
	      options.addOption(help);
	      
	      CommandLineParser parser = new DefaultParser();
	      CommandLine cmd = parser.parse(options, args);
	      
	      HelpFormatter formatter = new HelpFormatter();
	      formatter.printHelp("ysc2sctunit", options);
	   }

}
