package com.parkee_book_management_system.book_management_system.book;

import com.parkee_book_management_system.book_management_system.book.model.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
