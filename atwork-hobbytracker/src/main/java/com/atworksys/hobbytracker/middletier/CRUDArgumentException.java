package com.atworksys.hobbytracker.middletier;

public class CRUDArgumentException extends Exception {
	public CRUDArgumentException(String message) { super(message); }
	
	public static void throwIfNull(Object o) throws CRUDArgumentException {
		if (o == null) {
			throw new CRUDArgumentException("Null Argument");
		}
	}

}
