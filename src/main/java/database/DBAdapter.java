package database;

import java.util.List;

public interface DBAdapter<T> {
	T get(String id);
	List<T> get();
	void add(T obj);
	void update(String id, T obj);
	void remove(String id);
}
