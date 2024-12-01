package com.parkee_book_management_system.book_management_system.book;

import com.parkee_book_management_system.book_management_system.book.model.models.Book;
import com.parkee_book_management_system.book_management_system.book.model.request.BookGetAllRequest;
import com.parkee_book_management_system.book_management_system.book.model.response.BookGetAllResponse;
import com.parkee_book_management_system.book_management_system.genericresponse.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseEntity<GenericResponse<List<Book>>> getAllBook() {
        List<Book> books = bookRepository.findAll();

        GenericResponse<List<Book>> response = new GenericResponse<>();
        response.setResponseCode("200");
        response.setResponseMessage("Success");
        response.setData(books);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GenericResponse<List<BookGetAllResponse>>> createBook(BookGetAllRequest bookGetAllRequest) {
        Book newBook = Book.builder()
                .title(bookGetAllRequest.getTitle())
                .author(bookGetAllRequest.getAuthor())
                .publisher(bookGetAllRequest.getPublisher())
                .isbn(bookGetAllRequest.getIsbn())
                .createDate(LocalDateTime.now())
                .stock(bookGetAllRequest.getStock())
                .build();

        bookRepository.save(newBook);

        BookGetAllResponse newBookResponse = BookGetAllResponse.builder()
                .id(newBook.getId())
                .title(newBook.getTitle())
                .author(newBook.getAuthor())
                .publisher(newBook.getPublisher())
                .isbn(newBook.getIsbn())
                .create_at(newBook.getCreateDate())
                .stock(newBook.getStock())
                .build();

        GenericResponse<List<BookGetAllResponse>> response = new GenericResponse<>();
        response.setResponseCode("200");
        response.setResponseMessage("Buku berhasil ditambahkan");
        response.setData(List.of(newBookResponse));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GenericResponse<List<BookGetAllResponse>>> deleteBook(BookGetAllRequest bookGetAllRequest) {
        Book existingBook = bookRepository.findById(bookGetAllRequest.getId()).orElse(null);

        if (existingBook == null) {
            GenericResponse<List<BookGetAllResponse>> response = new GenericResponse<>();
            response.setResponseCode("404");
            response.setResponseMessage("Buku tidak ditemukan");
            response.setData(Collections.emptyList());
            return ResponseEntity.ok(response);
        }

        if (bookGetAllRequest.getId() != null) {
            bookRepository.deleteById(bookGetAllRequest.getId());
        }

        GenericResponse<List<BookGetAllResponse>> response = new GenericResponse<>();
        response.setResponseCode("200");
        response.setResponseMessage("Buku berhasil dihapus");
        response.setData(Collections.emptyList());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GenericResponse<List<BookGetAllResponse>>> updateBook(BookGetAllRequest bookGetAllRequest) {
        GenericResponse<List<BookGetAllResponse>> response = new GenericResponse<>();

        if (bookGetAllRequest.getId() != null) {
            Book existingBook = bookRepository.findById(bookGetAllRequest.getId()).orElse(null);
            if (existingBook != null) {
                if (bookGetAllRequest.getTitle() != null) {
                    existingBook.setTitle(bookGetAllRequest.getTitle());
                }
                if (bookGetAllRequest.getAuthor() != null) {
                    existingBook.setAuthor(bookGetAllRequest.getAuthor());
                }
                if (bookGetAllRequest.getPublisher() != null) {
                    existingBook.setPublisher(bookGetAllRequest.getPublisher());
                }
                if (bookGetAllRequest.getIsbn() != null) {
                    existingBook.setIsbn(bookGetAllRequest.getIsbn());
                }
                if (bookGetAllRequest.getStock() >= 0) {
                    existingBook.setStock(bookGetAllRequest.getStock());
                } else {
                    response.setResponseCode("400");
                    response.setResponseMessage("Stok harus positif");
                    response.setData(Collections.emptyList());
                    return ResponseEntity.ok(response);
                }

                existingBook.setUpdateDate(LocalDateTime.now());

                bookRepository.save(existingBook);

                BookGetAllResponse updatedResponse = BookGetAllResponse.builder()
                        .id(existingBook.getId())
                        .title(existingBook.getTitle())
                        .author(existingBook.getAuthor())
                        .publisher(existingBook.getPublisher())
                        .isbn(existingBook.getIsbn())
                        .create_at(existingBook.getCreateDate())
                        .stock(existingBook.getStock())
                        .build();

                response.setResponseCode("200");
                response.setResponseMessage("Buku berhasil diupdate");
                response.setData(List.of(updatedResponse));

                return ResponseEntity.ok(response);
            }
        }

        response.setResponseCode("404");
        response.setResponseMessage("Buku tidak ditemukan");
        response.setData(Collections.emptyList());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GenericResponse<List<BookGetAllResponse>>> getBookById(BookGetAllRequest bookGetAllRequest) {
        GenericResponse<List<BookGetAllResponse>> response = new GenericResponse<>();

        if (bookGetAllRequest.getId() != null) {
            Book book = bookRepository.findById(bookGetAllRequest.getId()).orElse(null);
            if (book != null) {
                BookGetAllResponse bookResponse = BookGetAllResponse.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .publisher(book.getPublisher())
                        .isbn(book.getIsbn())
                        .create_at(book.getCreateDate())
                        .stock(book.getStock())
                        .build();

                response.setResponseCode("200");
                response.setResponseMessage("Book found");
                response.setData(List.of(bookResponse));

                return ResponseEntity.ok(response);
            }
        }

        response.setResponseCode("404");
        response.setResponseMessage("Book not found");
        response.setData(Collections.emptyList());

        return ResponseEntity.ok(response);
    }
}
