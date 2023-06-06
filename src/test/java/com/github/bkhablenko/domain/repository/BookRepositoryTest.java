package com.github.bkhablenko.domain.repository;

import com.github.bkhablenko.domain.listener.AuditingEntityListener;
import com.github.bkhablenko.domain.model.AuthorEntity;
import com.github.bkhablenko.domain.model.BookEntity;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Optional;

import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class BookRepositoryTest extends AbstractRepositoryTest {

    private static final LocalDateTime EXPECTED_TIMESTAMP = LocalDate.of(2023, Month.JUNE, 6).atTime(15, 55);

    @Inject
    private BookRepository bookRepository;

    @BeforeAll
    public static void beforeAll() {
        final ZoneOffset zoneOffset = ZoneOffset.UTC;
        final Clock fixedClock = Clock.fixed(EXPECTED_TIMESTAMP.toInstant(zoneOffset), zoneOffset);

        AuditingEntityListener.setClock(fixedClock);
    }

    @Test
    @TestTransaction
    public void persistAndFindByTitle() {
        final String bookTitle = "Of Mice and Men";

        final AuthorEntity author = persist(AuthorEntity.builder().fullName("John Steinbeck").build());
        bookRepository.persist(BookEntity.builder().title(bookTitle).author(author).build());
        flushAndClear();

        final BookEntity foundBook = findByTitle(bookTitle);

        assertThat(foundBook.getCreatedDate(), is(equalTo(EXPECTED_TIMESTAMP)));
        assertThat(foundBook.getLastModifiedDate(), is(equalTo(EXPECTED_TIMESTAMP)));
    }

    private BookEntity findByTitle(String title) {
        final Optional<BookEntity> book = bookRepository.findByTitle(title);
        assertThat(book, is(optionalWithValue()));
        return book.get();
    }
}
