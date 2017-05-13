package com.atworksys.hobbytracker.common;

/**
 * Utility methods (static) and constants.
 * 
 * @author Leslie L Foster
 */
public class CommonUtil {
	private CommonUtil() {} // No instance use.  Ever.
	
	public static final String USER_ALL_QRY = "User.findAll";
	public static final String USER_BY_UNAME_QRY = "User.findByUname";
	
	public static final String USERHOBBY_ALL_QRY = "Userhobby.findAll";
	public static final String USERHOBBY_BY_ID_QRY = "Userhobby.findById";
	public static final String USERHOBBY_BY_UID_NAME_QRY = "UserHobby.findByUserIdHobbyName";
	
	public static final String USERPHONE_ALL_QRY = "Userphone.findAll";
	public static final String USERPHONE_BY_ID_QRY = "Userphone.findById";
	
	public static final String USERROLE_ALL_QRY = "Userrole.findAll";
	
}
