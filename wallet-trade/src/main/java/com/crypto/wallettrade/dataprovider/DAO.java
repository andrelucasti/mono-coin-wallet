package com.crypto.wallettrade.dataprovider;

import java.util.List;

public interface DAO<T>{
    void save(T t);

    List<T> findAll();
}
