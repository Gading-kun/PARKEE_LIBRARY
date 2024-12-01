package com.parkee_book_management_system.book_management_system.book;

import com.parkee_book_management_system.book_management_system.book.model.models.Book;
import com.parkee_book_management_system.book_management_system.book.model.request.BookGetAllRequest;
import com.parkee_book_management_system.book_management_system.book.model.response.BookGetAllResponse;
import com.parkee_book_management_system.book_management_system.genericresponse.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {

    ResponseEntity<GenericResponse<List<Book>>> getAllBook();

    ResponseEntity<GenericResponse<List<BookGetAllResponse>>> createBook(BookGetAllRequest bookGetAllRequest);

    ResponseEntity<GenericResponse<List<BookGetAllResponse>>> deleteBook(BookGetAllRequest bookGetAllRequest);

    ResponseEntity<GenericResponse<List<BookGetAllResponse>>> updateBook(BookGetAllRequest bookGetAllRequest);

    ResponseEntity<GenericResponse<List<BookGetAllResponse>>> getBookById(BookGetAllRequest bookGetAllRequest);
}
