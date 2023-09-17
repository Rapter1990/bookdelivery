package com.example.demo.repository;

import com.example.demo.model.Book;
import jakarta.annotation.Nonnull;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

/**
 * Repository interface for accessing and managing Book entities in the database.
 * This interface extends JpaRepository to provide basic CRUD operations.
 */
public interface BookRepository extends JpaRepository<Book, String> {

    /**
     * <p>Retrieves a Book entity by its unique identifier while applying
     * a pessimistic write lock on the database record to prevent concurrent updates.</p>
     * <p>Note: A pessimistic write lock ensures that only one transaction can write to
     * this specific database record at a time, preventing conflicts from concurrent updates.
     * It helps maintain data integrity but may introduce slight performance overhead.</p>
     * @param id The unique identifier of the Book.
     * @return An Optional containing the Book entity if found, or an empty Optional if not found.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Nonnull // Added to suppress warning
    Optional<Book> findById(@Nonnull String id);
}
