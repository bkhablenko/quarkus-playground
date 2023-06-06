package com.github.bkhablenko.domain.repository;

import com.github.bkhablenko.domain.model.BookEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class BookRepository implements PanacheRepositoryBase<BookEntity, UUID> {

    public Optional<BookEntity> findByTitle(String title) {
        return find("title", title).firstResultOptional();
    }
}
