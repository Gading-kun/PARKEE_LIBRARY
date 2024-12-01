package com.parkee_book_management_system.book_management_system.borrowing.model.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "borrowing_logs_table")
public class BorrowingLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private String isbnBook;
    private Long borrowerId;
    private String borrowStatus;
    private String lateStatus;
    private String action;
    private LocalDateTime createdDate;
    private String requestId;
}
