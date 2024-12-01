package com.parkee_book_management_system.book_management_system.book.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookGetAllRequest {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private LocalDateTime create_at;
    private int stock;
}


// Example JSON representation of the BookGetAllRequest object
/*
{
    "id": 12345,
    "title": "Example Book Title",
    "author": "Author Name",
    "publisher": "Publisher Name",
    "isbn": "123-4567890123",
    "create_at": "2023-10-01T12:30:00",
    "stock": 10
}
*/