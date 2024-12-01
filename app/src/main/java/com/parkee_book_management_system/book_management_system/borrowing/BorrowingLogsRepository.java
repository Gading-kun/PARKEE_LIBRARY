package com.parkee_book_management_system.book_management_system.borrowing;

import com.parkee_book_management_system.book_management_system.borrowing.model.models.BorrowingLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingLogsRepository extends JpaRepository<BorrowingLogs, Long> {
}
