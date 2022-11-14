package com.iviprojects.dao.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.iviprojects.dao.BankDao;
import com.iviprojects.entities.Bank;
import com.iviprojects.util.connection.ConnectionFactory;
import com.iviprojects.util.database.DbNotFoundException;
import com.iviprojects.util.database.DbPersistenceException;
import com.iviprojects.util.database.DbUnexpectedException;

public class BankDaoIm implements BankDao {

	private EntityManager em = ConnectionFactory.getEntityManager();

	@Override
	public void insert(Bank ObjType) {
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
	public Bank findById(Integer id) {
		Bank bank = em.find(Bank.class, id);
		if (bank == null) {
			throw new DbNotFoundException(id);
		}
		return bank;
	}

	@Override
	@Transactional
	public void update(Integer id, Bank newObjType) {
		try {
			em.getTransaction().begin();

			Bank bank = updateBankData(findById(id), newObjType);
			em.merge(bank);

			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Integer id) {
		try {
			em.getTransaction().begin();

			Bank bank = findById(id);
			if (bank != null) {
				Query query = em.createNativeQuery("DELETE FROM TB_BANK WHERE ID_BANK = " + id);
				query.executeUpdate();
			}

			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public List <Bank> findAll() {
		try {
			TypedQuery<Bank> query = em.createQuery("SELECT b FROM Bank b", Bank.class);
			if (query.getResultList().isEmpty()) {
				throw new DbNotFoundException("List of Type <" + Bank.class + ">");
			}
			return query.getResultList();
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public Bank findByBankName(String name) {
		try {
			Query query = em.createQuery("SELECT b FROM Bank b WHERE BANKNAME = '" + name + "'", Bank.class);
			return queryToBank(query);
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public Bank findByCnpj(String cnpj) {
		try {
			Query query = em.createQuery("SELECT b FROM Bank b WHERE CNPJ = " + cnpj, Bank.class);
			return queryToBank(query);
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	private Bank updateBankData(Bank bankToUpdate, Bank newBank) {
		bankToUpdate.setName(newBank.getName());
		bankToUpdate.setCnpj(newBank.getCnpj());

		return bankToUpdate;
	}
	
	private Bank queryToBank (Query query) {
		Bank bank = (Bank) query.getSingleResult();
		findById(bank.getId());
		return bank;
	}
}