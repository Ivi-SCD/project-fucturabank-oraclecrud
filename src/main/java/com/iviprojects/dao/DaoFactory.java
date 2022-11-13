package com.iviprojects.dao;

import com.iviprojects.dao.implementations.AccountDaoIm;
import com.iviprojects.dao.implementations.BankDaoIm;
import com.iviprojects.dao.implementations.UserDaoIm;

public class DaoFactory {
	
	public static UserDao createUserDao() {
		return new UserDaoIm();
	}
	
	public static BankDao createBankDao() {
		return new BankDaoIm();
	}
	
	public static AccountDao createAccountDao () {
		return new AccountDaoIm();
	}
}