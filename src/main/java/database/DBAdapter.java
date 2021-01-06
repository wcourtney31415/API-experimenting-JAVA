package database;

import java.util.List;

public interface DBAdapter<T> {
	T get(String idString);
	List<T> get();
	void add(T obj);
	void update(String idString, T obj);
	void remove(String idString);
}
