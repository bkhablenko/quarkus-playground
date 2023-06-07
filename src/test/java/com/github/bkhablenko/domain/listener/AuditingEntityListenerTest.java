package com.github.bkhablenko.domain.listener;

import com.github.bkhablenko.domain.model.AbstractEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@DisplayName("AuditingEntityEventListener")
public class AuditingEntityListenerTest {

    private static final LocalDateTime EXPECTED_TIMESTAMP = LocalDate.of(2023, Month.JUNE, 6).atTime(15, 55);

    private final AuditingEntityListener entityListener = new AuditingEntityListener();

    @BeforeAll
    public static void beforeAll() {
        final ZoneOffset zoneOffset = ZoneOffset.UTC;
        final Clock fixedClock = Clock.fixed(EXPECTED_TIMESTAMP.toInstant(zoneOffset), zoneOffset);

        AuditingEntityListener.setClock(fixedClock);
    }

    public static class TestEntity extends AbstractEntity {
    }

    @DisplayName("touchForCreate")
    @Nested
    public class TouchForCreateTest {

        @DisplayName("should set createdDate if null")
        @Test
        public void shouldSetCreatedDateIfNull() {
            final TestEntity entity = new TestEntity();
            entityListener.touchForCreate(entity);
            assertThat(entity.getCreatedDate(), is(EXPECTED_TIMESTAMP));
        }

        @DisplayName("should not set createdDate if not null")
        @Test
        public void shouldNotSetCreatedDateIfNotNull() {
            final TestEntity entity = new TestEntity();
            entity.setCreatedDate(LocalDateTime.now());
            entityListener.touchForCreate(entity);
            assertThat(entity.getCreatedDate(), is(not(EXPECTED_TIMESTAMP)));
        }

        @DisplayName("should set lastModifiedDate")
        @Test
        public void shouldSetLastModifiedDate() {
            final TestEntity entity = new TestEntity();
            entityListener.touchForCreate(entity);
            assertThat(entity.getLastModifiedDate(), is(EXPECTED_TIMESTAMP));
        }
    }

    @DisplayName("touchForUpdate")
    @Nested
    public class TouchForUpdateTest {

        @DisplayName("should set lastModifiedDate")
        @Test
        public void shouldSetLastModifiedDate() {
            final TestEntity entity = new TestEntity();

            final LocalDateTime timestamp = LocalDateTime.now();
            entity.setCreatedDate(timestamp);
            entity.setLastModifiedDate(timestamp);

            entityListener.touchForCreate(entity);
            assertThat(entity.getLastModifiedDate(), is(EXPECTED_TIMESTAMP));
        }
    }
}
