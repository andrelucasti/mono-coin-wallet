package com.crypto.wallettransaction.business;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID>{
    void save(T t);
    List<T> findAll();
    Optional<T> findById(ID id);
}
