package com.iviprojects.util.database;

import com.iviprojects.entities.Account;

public class DbAccountBalanceException extends DbException {

	private static final long serialVersionUID = 1L;

	public DbAccountBalanceException(Account acc) {
		super("EXCEPTION IN BALANCE VALUE " + acc.getBalance(), 31);
	}
}