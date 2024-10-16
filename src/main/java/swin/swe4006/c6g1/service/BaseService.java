package swin.swe4006.c6g1.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<T,ID extends Serializable> {
    List<T> findAll();
    Optional<T> findById(ID id);
    T save(T entity);
    void delete(T entity);
    Page<T> findAll(Pageable pageable);
}
