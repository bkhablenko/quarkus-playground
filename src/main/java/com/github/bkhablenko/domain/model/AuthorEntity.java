package com.github.bkhablenko.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AuthorEntity extends AbstractEntity {

    @Column(name = "full_name")
    private String fullName;

    @Builder.Default
    @OneToMany(mappedBy = "author")
    private List<BookEntity> books = new ArrayList<>();
}
