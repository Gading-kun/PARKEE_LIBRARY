package com.parkee_book_management_system.book_management_system.book;

import com.parkee_book_management_system.book_management_system.book.model.models.Book;
import com.parkee_book_management_system.book_management_system.book.model.request.BookGetAllRequest;
import com.parkee_book_management_system.book_management_system.book.model.response.BookGetAllResponse;
import com.parkee_book_management_system.book_management_system.genericresponse.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book/v1")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping(value = "/getAllBook")
    public ResponseEntity<GenericResponse<List<Book>>> getAllBook(){
        return bookService.getAllBook();
    }

    @PostMapping(value = "/createBook")
    public ResponseEntity<GenericResponse<List<BookGetAllResponse>>> createBook(@RequestBody BookGetAllRequest bookGetAllRequest){
        return bookService.createBook(bookGetAllRequest);
    }

    @PostMapping(value = "/deleteBook")
    public ResponseEntity<GenericResponse<List<BookGetAllResponse>>> deleteBook(@RequestBody BookGetAllRequest bookGetAllRequest){
        return bookService.deleteBook(bookGetAllRequest);
    }

    @PostMapping(value = "/updateBook")
    public ResponseEntity<GenericResponse<List<BookGetAllResponse>>> updateBook(@RequestBody BookGetAllRequest bookGetAllRequest){
        return bookService.updateBook(bookGetAllRequest);
    }

    @PostMapping(value = "/getBookById")
    public ResponseEntity<GenericResponse<List<BookGetAllResponse>>> getBookById(@RequestBody BookGetAllRequest bookGetAllRequest){
        return bookService.getBookById(bookGetAllRequest);
    }
}
