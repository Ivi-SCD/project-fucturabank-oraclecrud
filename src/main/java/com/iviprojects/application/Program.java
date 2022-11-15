package com.iviprojects.application;

import java.text.ParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.iviprojects.dao.AccountDao;
import com.iviprojects.dao.BankDao;
import com.iviprojects.dao.DaoFactory;
import com.iviprojects.dao.UserDao;
import com.iviprojects.entities.Account;
import com.iviprojects.entities.Bank;
import com.iviprojects.entities.User;
import com.iviprojects.util.connection.ConnectionFactory;
import com.iviprojects.util.database.DbAccountBalanceException;
import com.iviprojects.util.database.DbNotFoundException;
import com.iviprojects.util.database.DbUnexpectedException;

public class Program {

	static Scanner sc = new Scanner(System.in);
	static UserDao userDao = DaoFactory.createUserDao();
	static BankDao bankDao = DaoFactory.createBankDao();
	static AccountDao accDao = DaoFactory.createAccountDao();
	
	public static void main(String[] args) throws ParseException {
		
		instantiateBanks();
		
		/*
		 * @author: Ivisson Pereira
		 */
		
		loadMenu();
		
		
		sc.close();
		ConnectionFactory.closeConnections();
	}
	
	private static void instantiateBanks () {
		Bank b1 = new Bank("Santander", "90400888000142");
		Bank b2 = new Bank("Bradesco", "60746948000112");
		Bank b3 = new Bank("Itau", "60701190000104");
		
		List <Bank> banks = Arrays.asList(b1,b2,b3);
		banks.forEach(bankDao::insert);
	}
	
	private static void loadMenu() throws ParseException {
		try {
			boolean bool = true;
			while(bool) {
				System.out.println("=-=-=-=-=-=-=--=-= WELCOME TO FUCTURABANK =-=-=-=-=-=-=--=-="
						+ "\n1. Register User"
						+ "\n2. User Already Registered"
						+ "\n3. Exit");
				
				Integer option = sc.nextInt();
				
				switch(option) {
					case 1:
						System.out.print("\n=-=-=-=-= Register Menu -=-=--=-="
								+ "\nName: ");
						sc.nextLine();
						String name = sc.nextLine();
						
						System.out.print("BirthDate (dd/MM/yyyy): ");
						String birthdate = sc.nextLine();
						
						System.out.print("CPF (No semicolon): ");
						String cpf = sc.nextLine();
						
						try {
							userDao.findUserByCpf(cpf);
							System.out.println("User already Registered!");
							break;
						} catch (DbUnexpectedException e) {
							userDao.insert(new User(name, birthdate, cpf));
							System.out.println("\nRegistration Completed!");
							loadMenuFucturaBank(userDao.findUserByCpf(cpf));
							break;
						}
					case 2:
						System.out.println("\nCPF: ");
						cpf = sc.next();
						
						try {
							userDao.findUserByCpf(cpf);
							loadMenuFucturaBank(userDao.findUserByCpf(cpf));
							break;
						} catch (DbUnexpectedException e) {
							System.out.println("User not found! Please Register your user!");
							break;
						} catch (DbNotFoundException e) {
							System.out.println("User not found! Please Register your user!");
							break;
						}
					case 3:
						bool = false;
						break;
			}
			}
			
		} catch(InputMismatchException e) {
			System.out.println("Invalid number! Please Restart your application.");
			throw new DbUnexpectedException(e.getMessage());
		}
	}
	
	private static void loadMenuFucturaBank(User user) {
		
		boolean bool = true;
		while (bool) {
			System.out.println("\n=-=-=-=-= Welcome " + user.getName() + " =-=-=-=-="
					+ "\n1. List all banks available"
					+ "\n2. Findbank by name"
					+ "\n3. Findbank by CNPJ"
					+ "\n4. Register a new BankAccount"
					+ "\n5. Login account"
					+ "\n6. Exit");
			int option = sc.nextInt();
			
			switch (option) {
				case 1:
					bankDao.findAll().forEach(System.out::println);
					break;
				case 2:
					System.out.println("Name of bank:");
					String name = sc.next();
					try {
						System.out.println(name);
						bankDao.findByBankName(name);
						System.out.print("\nNAME: " + bankDao.findByBankName("Santander").getName());
						System.out.print("\nCNPJ: " + bankDao.findByBankName(name).getCnpj());
						break;
					} catch (DbNotFoundException e) {
						System.out.println("No one bank with this name!");
						break;
					} catch (DbUnexpectedException e) {
						System.out.println("No one bank with this name!");
						break;
					}
				case 3:
					System.out.println("CNPJ of bank:");
					String cnpj = sc.next();
					try {
						bankDao.findByCnpj(cnpj);
						System.out.print("\nNAME: " + bankDao.findByCnpj(cnpj).getName());
						System.out.print("\nCNPJ: " + bankDao.findByCnpj(cnpj).getCnpj());
						break;
					} catch (DbNotFoundException e) {
						System.out.println("No one bank with this CNPJ!");
						break;
					} catch (DbUnexpectedException e) {
						System.out.println("No one bank with this CNPJ!");
						break;
					}
				case 4:
					System.out.print("\nCPF: ");
					sc.nextLine();
					String cpf = sc.nextLine();
					
					System.out.print("CNPJ of Bank: ");
					cnpj = sc.nextLine();
					
					System.out.print("Account Number: ");
					Integer numberAcc = sc.nextInt();
					
					System.out.print("Password: ");
					sc.nextLine();
					String password = sc.nextLine();
					
					System.out.print("Initial depoist: ");
					Double value = sc.nextDouble();
					
					try {
						accDao.findById(numberAcc);
						System.out.println("Account already Registered!");
						break;
					} catch (DbNotFoundException e) {
						accDao.insert(new Account(numberAcc, value, password, userDao.findUserByCpf(cpf), bankDao.findByCnpj(cnpj)));
						System.out.println("\nAccount registered with sucessful!\n");
						loadMenuLogged(accDao.findByNumber(numberAcc));
						break;
					} catch (DbUnexpectedException e) {
						accDao.insert(new Account(numberAcc, value, password, userDao.findUserByCpf(cpf), bankDao.findByCnpj(cnpj)));
						System.out.println("\nAccount registered with sucessful!\n");
						loadMenuLogged(accDao.findByNumber(numberAcc));
						break;
					}
					
				case 5:
					System.out.print("\nNumber Account: ");
					numberAcc = sc.nextInt();
					
					System.out.print("Owner: ");
					sc.nextLine();
					String owner = sc.nextLine();
					
					System.out.print("Password: ");
					password = sc.nextLine();
					
					try {
						Account acc = accDao.findByNumber(numberAcc);
						if(acc.getPassword().equals(password) 
								&& acc.getUser().getName().equals(owner)) {
							loadMenuLogged(acc);
						}
						
					} catch (DbNotFoundException e) {
						System.out.println("Not Found Account, please register a new account");
						break;
					} catch (DbUnexpectedException e) {
						System.out.println("Not Found Account, please register a new account");
						break;
					}
					
					break;
				case 6:
					bool = false;
					break;
			}
		}
	}
	
	private static void loadMenuLogged(Account acc) {
		boolean bool = true;
		while(bool) {
			System.out.println("=-=-=-=-= Welcome to your account =-=-=-=-="
					+ "\n1. Account informations."
					+ "\n2. Transfer values."
					+ "\n3. Exit");
			Integer option = sc.nextInt();
			switch(option) {
				case 1:
					System.out.println(accDao.findById(acc.getId()));
					break;
				case 2:
					System.out.println("\nValue to transfer: ");
					Double value = sc.nextDouble();
					
					System.out.println("\nTarget number account: ");
					Integer numberAccount = sc.nextInt();
					
					try {
						accDao.transferValue(acc.getNumAccount(), numberAccount, value);
					} catch (DbNotFoundException e) {
						System.out.println("Not Found Account. Please try again.");
						break;
					} catch (DbUnexpectedException e) {
						System.out.println("Unexpected error. Please try again.");
						break;
					} catch (DbAccountBalanceException e) {
						System.out.println("Balance not enough.");
						break;
					}
				case 3:
					bool = false;
					break;
			}
		}
	}

}