package com.iviprojects.dao;

import java.util.List;

import com.iviprojects.entities.User;

public interface UserDao extends GenericDao <User> {
	
	List <User> findUsersByBirthdate(String birthdate);
	List <User> findUsersWithInName(String content);
	User findUserByCpf(String cpf);
}