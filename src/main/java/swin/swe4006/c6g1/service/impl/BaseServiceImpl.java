package swin.swe4006.c6g1.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import swin.swe4006.c6g1.service.BaseService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T, ID extends Serializable, R extends JpaRepository<T, ID>> implements BaseService<T, ID> {
    private final R repository;
    protected BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    public List<T> findAll() {
        return repository.findAll();
    }
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }
    public T save(T entity) {
        return repository.save(entity);
    }
    public void delete(T entity) {
        repository.delete(entity);
    }
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
