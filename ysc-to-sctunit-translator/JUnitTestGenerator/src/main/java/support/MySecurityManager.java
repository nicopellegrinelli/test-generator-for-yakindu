package support;

import java.security.Permission;

public class MySecurityManager extends SecurityManager {
	@Override
	public void checkPermission(Permission perm) {
		// Allow anything.
	}

	@Override
	public void checkPermission(Permission perm, Object context) {
		// Allow anything.
	}

	@Override
	public void checkExit(int status) {
		super.checkExit(status);
		throw new SecurityException();
	}
}
