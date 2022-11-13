package com.iviprojects.util.database;

public class DbPersistenceException extends DbException {

	private static final long serialVersionUID = 1L;

	public DbPersistenceException(String msg) {
		super(msg, 1);
	}
}