generator(project_name, package_name, statechart_name, time) ::= 
<<GeneratorModel for create::java {

	const PROJECT : string = "<project_name>"

	const FOLDER : string = "src"

	statechart <statechart_name> {
		<if(time)>
		
		feature GeneralFeatures {
    		TimerService = true
		}
		<endif>
		
		feature Naming {
		    basePackage = "<package_name>"
		}
	
		feature Outlet {
			targetProject = PROJECT
			targetFolder = FOLDER
			libraryTargetFolder = "src"
		}
	}
}>>