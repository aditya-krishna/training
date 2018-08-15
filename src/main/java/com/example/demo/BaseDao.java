package com.example.demo;

public interface BaseDao<T, K> {
    /**
     * Retrieve object from table based on Primary Key
     * 
     * @param key
     *            Primary Key of the Table
     * 
     * @return Single Row from the Table
     */
    public T findById(K key);

    /**
     * Save data to database. If an entry is present data is updated, othewise
     * new entry is created
     * 
     * @param data
     *            Data to be saved
     */
    public void save(T data);
}
