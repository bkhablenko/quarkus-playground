package com.github.bkhablenko.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnitUtil;
import jakarta.transaction.Transactional;

public abstract class AbstractRepositoryTest {

    @PersistenceContext
    protected EntityManager entityManager;

    @Transactional
    protected <T> T persist(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    protected <T> boolean isLoaded(T entity) {
        return getPersistenceUnitUtil().isLoaded(entity);
    }

    private PersistenceUnitUtil getPersistenceUnitUtil() {
        return entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
    }
}
