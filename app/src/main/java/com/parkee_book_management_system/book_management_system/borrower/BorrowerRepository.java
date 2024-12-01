package com.parkee_book_management_system.book_management_system.borrower;

import com.parkee_book_management_system.book_management_system.borrower.model.models.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
}
