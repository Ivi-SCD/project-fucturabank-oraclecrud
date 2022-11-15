package com.iviprojects.entities;

import java.util.Objects;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ACCOUNT")
@DynamicUpdate
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ACCOUNT", nullable = false)
	private Integer id;

	@Column(name = "NM_ACCOUNT", nullable = false, unique = true)
	private Integer numAccount;

	@Column(name = "BALANCE", nullable = false)
	private Double balance;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_USER", nullable = false, referencedColumnName = "ID_USER")
	private User user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_BANK", nullable = false, referencedColumnName = "ID_BANK")
	private Bank bank;

	public Account() {
	}

	public Account(Integer numAccount, Double balance, String password, User user, Bank bank) {
		this.numAccount = numAccount;
		this.balance = balance;
		this.password = password;
		this.user = user;
		this.bank = bank;
	}

	public Integer getNumAccount() {
		return numAccount;
	}

	public void setNumAccount(Integer numAccount) {
		this.numAccount = numAccount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Number Account: " + numAccount + " | Balance: " + balance 
				+ "\nUser CPF: " + user.getCpf() 
				+ "\nCNPJ Bank: " + bank.getCnpj() + " | Name Bank:" + bank.getName();
	}
}