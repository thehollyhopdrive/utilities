/**
 * Do whatever you want with this.
 *
 * @created: 23/10/2012
**/
package uk.co.adambh.util.unixpermissions;



import java.util.*;



/**
 * Enumeration to hold the unix file permissions.
 *
 * @author Adam Bromage-Hughes <thehollyhopdrive@gmail.com>
**/
public enum UnixFilePermission {


	// ************************************************************************\
	// Enums                                                                   *
	// ************************************************************************/
	
	SOCKET(140000),
	SYMBOLIC_LINK(120000),
	REGULAR_FILE(100000),
	BLOCK_DEVICE(60000),
	DIRECTORY(40000),
	CHARACTER_DEVICE(20000),
	FIFO(10000),
	SET_UID_BIT(4000, 5000, 6000, 7000),
	SET_GID_BIT(2000, 6000, 7000),
	STICKY_BIT(1000, 3000, 5000, 7000),
	OWNER_READ(400, 500, 600, 700),
	OWNER_WRITE(200, 600, 700),
	OWNER_EXECUTE(100, 300, 500, 700),
	GROUP_READ(40, 50, 60, 70),
	GROUP_WRITE(20, 60, 70),
	GROUP_EXECUTE(10, 30, 50, 70),
	OTHERS_READ(4, 5, 6, 7),
	OTHERS_WRITE(2, 6, 7),
	OTHERS_EXECUTE(1, 3, 5, 7);


	// ************************************************************************\
	// Static Variables                                                        *
	// ************************************************************************/


	// ************************************************************************\
	// Instance Variables                                                      *
	// ************************************************************************/
	
	private final int myOctal;
	private final Set<Integer> myOctals = new HashSet<>();


	// ************************************************************************\
	// Constructors                                                            *
	// ************************************************************************/
	
	/**
	 * Creates a unix file permission with the main file permission's octal,
	 * plus all other valid octals. For example, OTHERS_EXECUTE has a main
	 * octal of 1, as this is its actual octal value, but it also has other
	 * octals 3, 5 and 7, as these octals contain the OTHER_EXECUTE 
	 * permission.
	 * 
	 * @param theOctal the permission's main octal.
	 * @param theOctals the additional octals.
	**/ 
	private UnixFilePermission(int theOctal, Integer... theOctals) {
		this.myOctal = theOctal;
		this.myOctals.add(theOctal);
		this.myOctals.addAll(Arrays.asList(theOctals));
	}


	// ************************************************************************\
	// Public Methods                                                          *
	// ************************************************************************/
	
	/**
	 * Returns the main octal for this permission. This is the actual octal
	 * value.
	**/ 
	public int getOctal() {
		return this.myOctal;
	}

	/**
	 * Parses the octal value into a set of file permissions. This is because
	 * an octal supplied here can represent more than one permission. For 
	 * example, the octal 7 returns a set of OTHERS_EXECUTE, OTHERS_WRITE, 
	 * and OTHERS_READ (octal values 1, 2 and 4 respectively) which add up to
	 * octal value 7.
	 * 
	 * @param theOctal the octal to get the permissions for.
	 * 
	 * @return the set of permissions for this octal.
	**/ 
	public static Set<UnixFilePermission> parse(int theOctal) {
		Set<UnixFilePermission> perms = new HashSet<>();
		for (UnixFilePermission aPermission: UnixFilePermission.values()) {
			if (aPermission.myOctals.contains(theOctal)) {
				perms.add(aPermission);
			}
		}
		return perms;
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
