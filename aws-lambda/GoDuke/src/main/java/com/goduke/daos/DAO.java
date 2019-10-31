package com.goduke.daos;

import java.util.List;

public interface DAO<T> {
	public T getItem(String id);
	public void saveItem(T item);
	public void deleteItem(String id);
	public List<T> getAllItems();
}
