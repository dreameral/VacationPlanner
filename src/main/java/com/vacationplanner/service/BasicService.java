package com.vacationplanner.service;

import com.vacationplanner.entity.BasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BasicService<E extends BasicEntity, R extends JpaRepository<E, Long>> {

    protected R repository;

    public List<E> findAll() {
        return repository.findAll();
    }

    public E getById(Long id) {
        Optional<E> entity = repository.findById(id);
        return entity.orElse(null);
    }

    public E save(E entity) {
        return repository.save(entity);
    }

    public void softDeleteById(Long id) {
        E entity = getById(id);

        if (entity != null)
            softDelete(entity);
    }

    public void softDelete(E entity) {
        entity.setDeleted(true);
        save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void delete(E entity) {
        repository.delete(entity);
    }

    public long count() {
        return repository.count();
    }
}
