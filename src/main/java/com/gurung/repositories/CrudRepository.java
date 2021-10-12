package com.gurung.repositories;

import java.util.List;

public interface CrudRepository<T>{
    //Create
    T add(T t);

    //Read
    T getById(Integer id);

    List<T> getAll();

    //Update
    void update(T t);

    //Delete
    void delete(Integer integer);
}
