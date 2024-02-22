package cli;

/**
 * The Class ParsedArgs.
 */
public class ParsedArgs {
	
	/** The project name. */
	private String projectName;
	
	/** The workspace path. */
	private String workspacePath;
	
	/** The source directory. */
	private String sourceDir;
	
	/** The name of the source file */
	private String sourceFile;
	
	/** The target package */
	private String targetPackage;
	
	/** The target directory. */
	private String targetDir;
	
	/** The (evosuite) search budget. */
	private int searchBudget = 0;
	
	/** True if the parsed arguments have the option -searchBudget. */
	private boolean hasSearchBudget = false;
	
	/** True if the parsed arguments have the option -t. */
	private boolean t;
	
	/**
	 * Gets the project name.
	 *
	 * @return the project name
	 */
	public String getProjectName() {
		return projectName;
	}
	
	/**
	 * Gets the workspace path.
	 *
	 * @return the workspace path
	 */
	public String getWorkspacePath() {
		return workspacePath;
	}
	
	/**
	 * Gets the source directory.
	 *
	 * @return the source directory
	 */
	public String getSourceDir() {
		return sourceDir;
	}
	
	/**
	 * Gets the source file name.
	 *
	 * @return the source file name
	 */
	public String getSourceFile() {
		return sourceFile;
	}
	
	/**
	 * Gets the target package.
	 *
	 * @return the target package
	 */
	public String getTargetPackage() {
		return targetPackage;
	}
	
	/**
	 * Gets the target directory.
	 *
	 * @return the target directory
	 */
	public String getTargetDir() {
		return targetDir;
	}
	
	/**
	 * Gets the (evosuite) target directory.
	 *
	 * @return the (evosuite) search budget
	 */
	public int getSearchBudget() {
		return searchBudget;
	}
	
	/**
	 * Checks for the option -searchBudget.
	 *
	 * @return true if the parsed arguments have the option -searchBudget, false otherwise
	 */
	public boolean hasSearchBudget() {
		return hasSearchBudget;
	}
	
	/**
	 * Checks for the option -t (or --timeService).
	 *
	 * @return true if the parsed arguments have the option -t, false otherwise
	 */
	public boolean hasT() {
		return t;
	}
	
	/**
	 * Sets the project name.
	 *
	 * @param projectName the project name
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Sets the workspace path.
	 *
	 * @param workspacePath the workspace path
	 */
	public void setWorkspacePath(String workspacePath) {
		this.workspacePath = workspacePath;
	}

	/**
	 * Sets the source dir.
	 *
	 * @param sourceDir the source dir
	 */
	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	/**
	 * Sets the source file name.
	 *
	 * @param sourceFile the source file name
	 */
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	/**
	 * Sets the target package.
	 *
	 * @param targetPackage the target package
	 */
	public void setTargetPackage(String targetPackage) {
		this.targetPackage = targetPackage;
	}

	/**
	 * Sets the target dir.
	 *
	 * @param targetDir the target dir
	 */
	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}
	
	/**
	 * Sets the (evosuite) search budget.
	 *
	 * @param searchBudget the (evosuite) search budget
	 */
	public void setSearchBudget(int searchBudget) {
		this.hasSearchBudget = true;
		this.searchBudget = searchBudget;
	}


	/**
	 * Sets if the parsed arguments have the option t.
	 *
	 * @param t specifies whether there is a -t option or not
	 */
	public void setT(boolean t) {
		this.t = t;
	}

}
