package com.vacationplanner.service;

import com.vacationplanner.entity.BasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BasicService<E extends BasicEntity, R extends JpaRepository<E, Long>> {

    protected R repository;

    protected List<E> findAll() {
        return repository.findAll();
    }

    public E getById(long id) {
        Optional<E> entity = repository.findById(id);
        return entity.orElse(null);
    }

    protected E save(E entity) {
        return repository.save(entity);
    }

    protected void softDeleteById(long id) {
        E entity = getById(id);

        if (entity != null)
            softDelete(entity);
    }

    protected void softDelete(E entity) {
        entity.setDeleted(true);
        save(entity);
    }

    protected void deleteById(long id) {
        repository.deleteById(id);
    }

    protected void delete(E entity) {
        repository.delete(entity);
    }

    protected long count() {
        return repository.count();
    }
}
