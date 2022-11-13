package com.iviprojects.util.database;

public class DbNotFoundException extends DbException {

	private static final long serialVersionUID = 1L;
	
	public DbNotFoundException(Object id) {
		super("Not Found: " + id, 11);
	}
}