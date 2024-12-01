package com.parkee_book_management_system.book_management_system.book.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookGetAllResponse {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private LocalDateTime create_at;
    private int stock;
}
