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
@Table(name = "borrowing_table")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private String isbnBook;
    private Long borrowerId;
    private String borrowStatus;
    private String lateStatus;
    private LocalDateTime dateReturnExpectation;
    private LocalDateTime dateReturnRealization;
    private LocalDateTime createDate;
    private String requestId;
}
