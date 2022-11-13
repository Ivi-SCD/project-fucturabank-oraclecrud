package com.iviprojects.dao;

import java.util.List;

import com.iviprojects.entities.Account;
import com.iviprojects.entities.Bank;
import com.iviprojects.entities.User;

public interface AccountDao extends GenericDao <Account> {
	
	void transferValue (Integer numAcc, Integer numAccToTransfer, Double value);
	Account findByNumber (Integer numberAcc);
	void depositValue (Integer numAcc, Double value);
	List <User> findAccountsByUser(User user);
	List <Bank> findAccountsByBank(Bank bank);
}