package com.wallmart.persistence;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseRepository<K, T> {

	@SuppressWarnings("unchecked")
	public Class<T> getParameterizedEntity() {

		Type type = ((ParameterizedType) getClass().getGenericSuperclass())
		    .getActualTypeArguments()[1];
		return (Class<T>) type;
	}

	public abstract T save(T obj);

	public abstract T save(K id, T obj);

	public abstract T findById(K id);
}
