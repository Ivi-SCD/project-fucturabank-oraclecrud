package com.iviprojects.dao.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.iviprojects.dao.AccountDao;
import com.iviprojects.entities.Account;
import com.iviprojects.entities.Bank;
import com.iviprojects.entities.User;
import com.iviprojects.util.connection.ConnectionFactory;
import com.iviprojects.util.database.DbAccountBalanceException;
import com.iviprojects.util.database.DbNotFoundException;
import com.iviprojects.util.database.DbPersistenceException;
import com.iviprojects.util.database.DbUnexpectedException;

public class AccountDaoIm implements AccountDao {
	
	private EntityManager em = ConnectionFactory.getEntityManager();
	
	@Override
	@Transactional
	public void insert(Account ObjType) {
		try {
			em.getTransaction().begin();
			em.persist(ObjType);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			throw new DbPersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public Account findById(Integer id) {
		Account account = em.find(Account.class, id);
		if (account == null) {
			throw new DbNotFoundException(id);
		}
		return account;
	}

	@Override
	@Transactional
	public void update(Integer id, Account newObjType) {
		try {
			em.getTransaction().begin();

			Account account = updateUserData(findById(id), newObjType);
			em.merge(account);

			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Integer id) {
		try {
			em.getTransaction().begin();

			Account account = findById(id);
			if (account != null) {
				Query query = em.createNativeQuery("DELETE FROM TB_ACCOUNT WHERE ID_ACCOUNT = " + id);
				query.executeUpdate();
			}

			em.remove(account);

			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public List <Account> findAll() {
		try {
			TypedQuery <Account> query = em.createQuery("SELECT a FROM Account a", Account.class);
			return queryListToUserList(query);
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void transferValue(Integer numAcc, Integer numAccToTransfer, Double value) {
		try {
			Account accTransact = findByNumber(numAcc);
			Account accTransfer = findByNumber(numAccToTransfer);
			if(value > accTransact.getBalance()) {
				throw new DbAccountBalanceException(accTransact);
			}
			accTransact.setBalance(accTransact.getBalance() - value);
			accTransfer.setBalance(accTransfer.getBalance() + value);
			
			em.merge(accTransact);
			em.merge(accTransfer);
			
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public Account findByNumber(Integer numberAcc) {
		try {
			Query query = em.createQuery("SELECT a FROM Account a WHERE NM_ACCOUNT = " + numberAcc);
			Account account = (Account) query.getSingleResult();
			findById(account.getId());
			return account;
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void depositValue(Integer numAcc, Double value) {
		try {
			em.getTransaction().begin();
			Account acc = findByNumber(numAcc);
			if(value > findByNumber(numAcc).getBalance()) {
				throw new DbAccountBalanceException(acc);
			}
			acc.setBalance(acc.getBalance() - value);
			em.merge(acc);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public List <User> findAccountsByUser(User user) {
		try {
			TypedQuery <User> query = em.createQuery("SELECT u FROM User u WHERE ID_USER" + user.getId(), User.class);
			return queryListToUserList(query);
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public List <Bank> findAccountsByBank(Bank bank) {
		try {
			TypedQuery <Bank> query = em.createQuery("SELECT b FROM Bank b WHERE ID_BANK" + bank.getId(), Bank.class);
			return queryListToUserList(query);
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}
	
	private Account updateUserData(Account accountToUpdate, Account newAccount) {
		accountToUpdate.setUser(newAccount.getUser());
		accountToUpdate.setBank(newAccount.getBank());
		accountToUpdate.setPassword(newAccount.getPassword());
		
		return accountToUpdate;
	}

	private <A> List <A> queryListToUserList (TypedQuery <A> tq) {
		if (tq.getResultList().isEmpty()) {
			throw new DbNotFoundException("List of Type <" + tq.getClass() + ">");
		}
		return tq.getResultList();
	}
}