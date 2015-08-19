/**
 * Do whatever you want with this.
 *
 * @created: 23/10/2012
**/
package uk.co.adambh.util.unixpermissions;



import java.nio.file.attribute.*;
import java.util.*;



/**
 * Utility class work working with Unix file permissions.
 *
 * @author Adam Bromage-Hughes <thehollyhopdive@gmail.com>
**/
public final class UnixFilePermissions {


	// ************************************************************************\
	// Enums                                                                   *
	// ************************************************************************/


	// ************************************************************************\
	// Static Variables                                                        *
	// ************************************************************************/


	// ************************************************************************\
	// Instance Variables                                                      *
	// ************************************************************************/


	// ************************************************************************\
	// Constructors                                                            *
	// ************************************************************************/
	
	private UnixFilePermissions() {}


	// ************************************************************************\
	// Public Methods                                                          *
	// ************************************************************************/
	
	/**
	 * Returns the set of supplied permissions as a mode representation in the
	 * radix supplied. Valid radix are 2, 8, 10 and 16.
	 * 
	 * @param thePerms the permissions.
	 * @param theRadix the radix. Must be one of 2, 8, 10 or 16.
	 * 
	 * @return the permissions as an octal.
	 * 
	 * @throws NullPointerException if the permissions supplied are null.
	 * @throws UnsupportedOperationException if the radix is not supported.
	**/
	public static String toMode(Set<UnixFilePermission> thePerms, 
								int theRadix) {
		if (thePerms==null) {
            throw new NullPointerException("Can not accept null permissions");
        }
        
		Integer octal = 0;
		for (UnixFilePermission aPermission: thePerms) {
			octal += aPermission.getOctal();
		}
		Integer decimal = Integer.valueOf(octal.toString(), 8);
		switch (theRadix) {
			case 2:
				return Integer.toBinaryString(decimal);
			case 8:
				return Integer.toOctalString(decimal);
			case 10:
				return decimal.toString();
			case 16:
				return Integer.toHexString(decimal);
			default:
				throw new UnsupportedOperationException(
									"Radix " + theRadix + " is not supported");
		}
	}

	
	/**
	 * Returns the set of permissions from the mode representation supplied.
	 * 
	 * @param theMode the mode representation of the permissions.
	 * @param theRadix the radix of the mode representation.
	 * 
	 * @return the set of permissions.
	 * 
	 * @throws NullPointerException if the mode supplied is null.
	 * @throws IllegalArgumentException if the mode can not be parsed. 
	**/ 
	public static Set<UnixFilePermission> parseMode(String theMode, 
													int theRadix) {
		String octalStr = 
					Integer.toOctalString(Integer.valueOf(theMode, theRadix));
		int octal = Integer.parseInt(octalStr);
		int current = 0;
		Set<UnixFilePermission> perms = new HashSet<>();
		for (int octalMod = 10; octalMod<=1000000; octalMod *= 10) {
			if (octalMod==100000) {
				octalMod *= 10;
			}
			int mod = (octal % octalMod) - current;
			current += mod;
			perms.addAll(UnixFilePermission.parse(mod));
		}		
		return perms;
	}
	
	
	/**
	 * Converts the UNIX file permissions supplied into POSIX file permissions.
	 * Any UNIX file permissions which do not have an equivalent POSIX type
	 * are not included in the returned POSIX file permissions.
	 * 
	 * @param theUnixFilePermissions the UNIX file permissions.
	 * 
	 * @return the POSIX file permissions.
	 * 
	 * @throws NullPointerException if the UNIX file permissions supplied are
	 *							    null.
	**/
	public static Set<PosixFilePermission> toPosix(
							Set<UnixFilePermission> theUnixFilePermissions) {
		if (theUnixFilePermissions==null) {
            throw new NullPointerException(
                            "Can not accept a null set of file permissions");
        }
        
		Set<PosixFilePermission> posix = new HashSet<>();
		for (UnixFilePermission aUnixPerm: theUnixFilePermissions) {
			try {
				posix.add(PosixFilePermission.valueOf((aUnixPerm.name())));
			}
			catch (IllegalArgumentException e) {
				// Do nothing, just don't add it.
			}
		}
		return posix;	
	}
	
	
	/**
	 * Converts the POSIX file permissions supplied into UNIX file permissions.
	 * 
	 * @param thePosixFilePermissions the POSIX file permissions.
	 * 
	 * @return the UNIX file permissions.
	 * 
	 * @throws NullPointerException if the POSIX file permissions supplied are
	 *							    null.
	**/
	public static Set<UnixFilePermission> fromPosix(
							Set<PosixFilePermission> thePosixFilePermissions) {
		if (thePosixFilePermissions==null) {
            throw new NullPointerException(
                            "Can not accept a null set of file permissions");
        }
        
		Set<UnixFilePermission> unix = new HashSet<>();
		for (PosixFilePermission aPosixPerm: thePosixFilePermissions) {
			try {
				unix.add(UnixFilePermission.valueOf((aPosixPerm.name())));
			}
			catch (IllegalArgumentException e) {
				// Do nothing, just don't add it.
			}
		}
		return unix;	
	}


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


}
