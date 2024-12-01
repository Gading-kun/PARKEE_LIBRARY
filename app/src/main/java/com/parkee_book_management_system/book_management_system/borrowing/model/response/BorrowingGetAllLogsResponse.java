package com.parkee_book_management_system.book_management_system.borrowing.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingGetAllLogsResponse implements Serializable {
    private Long id;
    private Long bookId;
    private String bookName;
    private String isbnBook;
    private Long borrowerId;
    private String borrowerName;
    private String borrowStatus;
    private String lateStatus;
    private LocalDateTime dateReturnExpectation;
    private LocalDateTime dateReturnRealization;
    private LocalDateTime createDate;
    private String requestId;
}