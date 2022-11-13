package com.iviprojects.util.database;

public class DbUnexpectedException extends DbException {

	private static final long serialVersionUID = 1L;

	public DbUnexpectedException(String msg) {
		super("Unexpected Error " + msg, 21);
	}
}