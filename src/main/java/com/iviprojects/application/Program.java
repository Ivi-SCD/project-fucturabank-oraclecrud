package com.iviprojects.application;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import com.iviprojects.dao.AccountDao;
import com.iviprojects.dao.BankDao;
import com.iviprojects.dao.DaoFactory;
import com.iviprojects.dao.UserDao;
import com.iviprojects.entities.Account;
import com.iviprojects.entities.Bank;
import com.iviprojects.entities.User;

public class Program {	

	public static void main(String[] args) throws ParseException {
		UserDao userDao = DaoFactory.createUserDao();
		BankDao bankDao = DaoFactory.createBankDao();
		AccountDao accDao = DaoFactory.createAccountDao();
		
		User u1 = new User("Leticia Paixão", "31/9/2004", "12345678910");
		User u2 = new User("Ivisson Pereira", "11/12/2003", "23456767890");
		User u3 = new User("Lucas Martins", "26/02/2005", "34556789012");
		
		Bank b1 = new Bank("Santander", "90400888000142");
		Bank b2 = new Bank("Bradesco", "60746948000112");
		Bank b3 = new Bank("Itau", "60701190000104");
		
		Account ac1 = new Account(1111,5.0, "12345", u2, b1);
		Account ac2 = new Account(2222,2000d, "12345", u1, b3);
		Account ac3 = new Account(3333, 2500d, "12345", u3, b2);
		
		List <Account> accounts = Arrays.asList(ac1,ac2,ac3);
		
		accounts.forEach(accDao::insert);
		
		userDao.findUsersByBirthdate("01/01/2004").forEach(System.out::println);
		userDao.findUsersWithInName("s").forEach(System.out::println);
		System.out.println(userDao.findUserByCpf("12345678910"));
		
		System.out.println(bankDao.findByBankName("Santander"));
		System.out.println(bankDao.findByCnpj("60701190000104"));
		
		accDao.findAccountsByBank(b3).forEach(System.out::println);
		accDao.findAccountsByUser(u3).forEach(System.out::println);
		System.out.println(accDao.findByNumber(11111));
		
	}
}