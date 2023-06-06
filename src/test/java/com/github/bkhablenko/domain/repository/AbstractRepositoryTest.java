package com.github.bkhablenko.domain.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public abstract class AbstractRepositoryTest {

    @Inject
    protected EntityManager entityManager;

    protected <T> T persist(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
