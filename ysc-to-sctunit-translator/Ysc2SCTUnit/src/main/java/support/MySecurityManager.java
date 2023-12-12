package support;

import java.security.Permission;

/**
 * The Class MySecurityManager.
 */
public class MySecurityManager extends SecurityManager {
	
	/**
	 * Check permission, allow anytihng.
	 *
	 * @param perm the permission
	 */
	@Override
	public void checkPermission(Permission perm) {
		// Allow anything.
	}

	/**
	 * Check permission, allow anytihng.
	 *
	 * @param perm the permission
	 * @param context the context
	 */
	@Override
	public void checkPermission(Permission perm, Object context) {
		// Allow anything.
	}

	/**
	 * If System.exit() is called, throw a SecurityException.
	 *
	 * @param status the actual parameter of the System.exit() call
	 */
	@Override
	public void checkExit(int status) {
		super.checkExit(status);
		throw new SecurityException();
	}
}
