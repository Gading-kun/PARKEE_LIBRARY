package com.parkee_book_management_system.book_management_system.borrowing;

import com.parkee_book_management_system.book_management_system.borrowing.model.models.Borrowing;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    @Query(value = "SELECT id, book_id, isbn_book, borrower_id, borrow_status, late_status, date_return_expectation, date_return_realization, create_date, request_id FROM borrowing_table WHERE request_id = :requestId", nativeQuery = true)
    Borrowing findByRequestId(
            @Param("requestId") String requestId
    );

    @Query(value = "SELECT id, book_id, isbn_book, borrower_id, borrow_status, late_status, date_return_expectation, date_return_realization, create_date, request_id FROM borrowing_table WHERE borrower_id = :borrowerId", nativeQuery = true)
    Borrowing findByBorrowerId(
            @Param("borrowerId") Long borrowerId
    );

}
