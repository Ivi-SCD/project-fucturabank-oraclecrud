package com.iviprojects.dao.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.iviprojects.dao.UserDao;
import com.iviprojects.entities.User;
import com.iviprojects.util.connection.ConnectionFactory;
import com.iviprojects.util.database.DbNotFoundException;
import com.iviprojects.util.database.DbPersistenceException;
import com.iviprojects.util.database.DbUnexpectedException;

public class UserDaoIm implements UserDao {

	private EntityManager em = ConnectionFactory.getEntityManager();

	@Override
	public void insert(User ObjType) {
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
	public User findById(Integer id) {
		User user = em.find(User.class, id);
		if (user == null) {
			throw new DbNotFoundException(id);
		}
		return user;
	}

	@Override
	@Transactional
	public void update(Integer id, User newObjType) {
		try {
			em.getTransaction().begin();

			User user = updateUserData(findById(id), newObjType);
			em.merge(user);

			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Integer id) {
		try {
			em.getTransaction().begin();

			User user = findById(id);
			if (user != null) {
				Query query = em.createNativeQuery("DELETE FROM TB_ACCOUNT WHERE USER_ID = " + id);
				query.executeUpdate();
			}

			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public List <User> findAll() {
		try {
			TypedQuery <User> query = em.createQuery("SELECT u FROM User u", User.class);
			return queryListToUserList(query);
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public List <User> findUsersByBirthdate(String birthdate) {
		try {
			TypedQuery <User> query = em.createQuery("SELECT u FROM User u WHERE BIRTHDATE >= '" + birthdate + "'", User.class);
			return queryListToUserList(query);
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public List <User> findUsersWithInName(String content) {
		try {
			TypedQuery <User> query = em.createQuery("SELECT u FROM User u WHERE NAME LIKE '%" + content + "%'", User.class);
			return queryListToUserList(query);
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	@Override
	public User findUserByCpf(String cpf) {
		try {
			Query query = em.createQuery("SELECT u FROM User u WHERE CPF = " + cpf);
			User user = (User) query.getSingleResult();
			findById(user.getId());
			return user;
		} catch (Exception e) {
			throw new DbUnexpectedException(e.getMessage());
		}
	}

	private User updateUserData(User userToUpdate, User newUser) {
		userToUpdate.setName(newUser.getName());
		userToUpdate.setCpf(newUser.getCpf());
		userToUpdate.setBirthdate(newUser.getBirthdate());
		userToUpdate.setCpf(newUser.getCpf());

		return userToUpdate;
	}
	
	private List <User> queryListToUserList (TypedQuery <User> tq) {
		if (tq.getResultList().isEmpty()) {
			throw new DbNotFoundException("List of Type <" + User.class + ">");
		}
		return tq.getResultList();
	}
}