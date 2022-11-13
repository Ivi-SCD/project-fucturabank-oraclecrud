package com.iviprojects.dao;

import java.util.List;

public interface GenericDao <T> {
	
	void insert(T ObjType);
	T findById(Integer id);
	void update(Integer id, T newObjType);
	void deleteById(Integer id);
	List <T> findAll();
}