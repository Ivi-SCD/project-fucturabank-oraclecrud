package com.iviprojects.dao;

import com.iviprojects.entities.Bank;

public interface BankDao extends GenericDao <Bank> {
	
	Bank findByBankName(String name);
	Bank findByCnpj(String cnpj);
}