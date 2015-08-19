/**
 * Do whatever you want with this.
 *
 * @created: 23/10/2012
**/
package uk.co.adambh.util.unixpermissions;



import java.io.*;
import java.nio.file.*;
import java.util.*;



/**
 * Utility class for file operations regarding Unix file permissions for the new 
 * {@link java.nio.file} package.
 *
 * @author Adam Bromage-Hughes <thehollyhopdrive@gmail.com>
**/
public final class UnixFiles {


	// ************************************************************************\
	// Enums                                                                   *
	// ************************************************************************/


	// ************************************************************************\
	// Static Variables                                                        *
	// ************************************************************************/
	
	private static final String UNIX_VIEW = "unix";
	private static final String UNIX_MODE_ATTRIB = "unix:mode";


	// ************************************************************************\
	// Instance Variables                                                      *
	// ************************************************************************/


	// ************************************************************************\
	// Constructors                                                            *
	// ************************************************************************/
	
	private UnixFiles() {}


	// ************************************************************************\
	// Public Methods                                                          *
	// ************************************************************************/


	// ************************************************************************\
	// Protected Methods                                                       *
	// ************************************************************************/


	// ************************************************************************\
	// Private Methods                                                         *
	// ************************************************************************/


	// ************************************************************************\
	// Inner Classes                                                           *
	// ************************************************************************/



	// ************************************************************************\
	// Static Methods                                                          *
	// ************************************************************************/
	
	/**
	 * Convenience method to determine whether the {@code FileSystem} 
	 * supplied has UNIX file permissions available. This method indicates only
	 * that the file system supports UNIX file permissions, not that they will
	 * be available for all paths associated with the supplied file system.
	 * 
	 * @param theFileSystem the file system.
	 * 
	 * @return true if the file system supports UNIX file permissions, false 
	 *		   otherwise.
	 * 
	 * @throws NullPointerException if the file system supplied is null.
	**/
	public static boolean hasUnixFilePermissions(FileSystem theFileSystem) {
		if (theFileSystem==null) {
            throw new NullPointerException("Can not accept a null file system");
        }
		return theFileSystem.supportedFileAttributeViews().contains(UNIX_VIEW);
	}
	
	
	/**
	 * <p>Returns a file's UNIX file permissions.</p>
	 * 
	 * <p>The path supplied must be associated with a {@code FileSystem} that
	 * supports the {@code UnixFileAttributeView} which can be checked with
	 * a call to {@link #hasUnixFilePermissions(FileSystem)}.</p>
	 * 
	 * <p>The options array may be used to indicate how symbolic links are 
	 * handled for the case that the file is a symbolic link. By default, 
	 * symbolic links are followed and the file attribute of the final target of 
	 * the link is read. If the option NOFOLLOW_LINKS is present then symbolic 
	 * links are not followed.</p>
	 * 
	 * @param thePath the path to get the UNIX file permissions for.
	 * @param theOptions options indicating how symbolic links are handled.
	 * 
	 * @return the file permissions.
	 * 
	 * @throws IOException if an I/O error occurs.
	 * @throws NullPointerException if the path supplied is null.
	 * @throws SecurityException if a default security manager is installed
	 *			which either denies 
	 *			{@link RuntimePermission}("accessUserInformation")
	 *			or which denies read access to the file via a call to 
	 *			{@link SecurityManager#checkRead(String) checkRead}.					 
	 * @throws UnsupportedOperationException if the associated file system does
	 *									     not support the 
	 *										 {@code UnixFileAttributeView}.
	**/
	public static Set<UnixFilePermission> getUnixFilePermissions(
													Path thePath,
													LinkOption... theOptions) 
			throws IOException {
		// Check for null first.
		if (thePath==null) { 
            throw new NullPointerException("Can not accept a null path");
        }
		// Check the file system allows us to get unix file attributes.
		FileSystem fs = thePath.getFileSystem();
        if (!fs.supportedFileAttributeViews().contains(UNIX_VIEW)) {
            throw new UnsupportedOperationException(
                        "Can not accept path from file system '" + fs + 
                        "' as it does not support 'unix' file attribute " +
                        "view");
        }
		// Check the security manager.
		SecurityManager sm = System.getSecurityManager();
		if (sm!=null) {
			sm.checkPermission(new RuntimePermission("accessUserInformation"));
		}
		// Attempt to get the file attribute.
		Object obj = java.nio.file.Files.getAttribute(thePath, 
													  UNIX_MODE_ATTRIB, 
													  theOptions);
		// If null, throw UOE.
		if (obj==null) {
			throw new UnsupportedOperationException( 
						"Can not accept path '" + thePath + "' as it does " +
						"not have UNIX file permissions available");
		}
		// Convert to actual unix permissions and return.
		return UnixFilePermissions.parseMode(obj.toString(), 10);
	}
	
	
	/**
	 * <p>Sets a file's UNIX file permissions.</p>
	 * 
	 * <p>The path supplied must be associated with a {@code FileSystem} that
	 * supports the {@code UnixFileAttributeView} which can be checked with
	 * a call to {@link #hasUnixFilePermissions(FileSystem)}.</p>
	 * 
	 * @param thePath the path to get the UNIX file permissions for.
	 * @param thePerms the new set of permissions.
	 * 
	 * @return the path (additional operations).
	 * 
	 * @throws IOException if an I/O error occurs.
	 * @throws NullPointerException if the path or permissions supplied are 
	 *								null.
	 * @throws SecurityException if a default security manager is installed
	 *			which either denies 
	 *			{@link RuntimePermission}("accessUserInformation")
	 *			or which denies write access to the file via a call to 
	 *			{@link SecurityManager#checkWrite(String) checkWrite}.					 
	 * @throws UnsupportedOperationException if the associated file system does
	 *									     not support the 
	 *										 {@code UnixFileAttributeView}.
	**/
	public static Path setUnixFilePermissions(Path thePath, 
											  Set<UnixFilePermission> thePerms)
			throws IOException {
		// Check for null first.
		if (thePath==null) { 
            throw new NullPointerException("Can not accept a null path");
        }
		if (thePerms==null) {
            throw new NullPointerException("Can not accept null permissions");
        }
		// Check the file system allows us to get unix file attributes.
		FileSystem fs = thePath.getFileSystem();
        if (!fs.supportedFileAttributeViews().contains(UNIX_VIEW)) {
            throw new UnsupportedOperationException(
                        "Can not accept path from file system '" + fs + 
                        "' as it does not support 'unix' file attribute " +
                        "view");
        }
		// Check the security manager.
		SecurityManager sm = System.getSecurityManager();
		if (sm!=null) {
			sm.checkPermission(new RuntimePermission("accessUserInformation"));
		}
		// Attempt to get the file attribute.
		Object obj = Files.getAttribute(thePath, UNIX_MODE_ATTRIB);
		// If null, throw UOE.
		if (obj==null) {
			throw new UnsupportedOperationException( 
						"Can not accept path '" + thePath + "' as it does " +
						"not have UNIX file permissions available");
		}
		// Otherwise, we know the mode is available, so set it.
		java.nio.file.Files.setAttribute(
					thePath, 
					UNIX_MODE_ATTRIB,
					Integer.valueOf(UnixFilePermissions.toMode(thePerms, 10)));
		// Return the path to match the functionality of 
		// Files.setPosixFilePermissions()
		return thePath;
	}
}
