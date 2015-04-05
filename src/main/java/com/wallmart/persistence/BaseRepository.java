package com.wallmart.persistence;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseRepository<T> {

	@SuppressWarnings("unchecked")
	public Class<T> getParameterizedEntity() {

		Type type = ((ParameterizedType) getClass().getGenericSuperclass())
		    .getActualTypeArguments()[0];
		return (Class<T>) type;
	}

	public abstract T save(T o);

	/*
	 * public abstract T findById(Object id);
	 * 
	 * public abstract void delete(T o);
	 * 
	 * public abstract List<T> listAll();
	 */
}
