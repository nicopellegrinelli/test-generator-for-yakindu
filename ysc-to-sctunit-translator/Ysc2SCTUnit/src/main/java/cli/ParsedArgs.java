package cli;

public class ParsedArgs {
	
	private String projectName;
	private String workspacePath;
	private String sourceDir;
	private String sourceFile;
	private String targetPackage;
	private String targetDir;
	private boolean t;
	
	public String getProjectName() {
		return projectName;
	}
	
	public String getWorkspacePath() {
		return workspacePath;
	}
	
	public String getSourceDir() {
		return sourceDir;
	}
	
	public String getSourceFile() {
		return sourceFile;
	}
	
	public String getTargetPackage() {
		return targetPackage;
	}
	
	public String getTargetDir() {
		return targetDir;
	}
	
	public boolean hasT() {
		return t;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setWorkspacePath(String workspacePath) {
		this.workspacePath = workspacePath;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public void setTargetPackage(String targetPackage) {
		this.targetPackage = targetPackage;
	}

	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}

	public void setT(boolean t) {
		this.t = t;
	}

}
