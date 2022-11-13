package com.iviprojects.util.database;

public class DbException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DbException(String msg, Integer code) {
		super("ERRORMESSAGE: " + msg + "\nCODEERROR: " 
	+ code + "\nWHERE: ");
	}
}