package com.parkee_book_management_system.book_management_system.borrowing;

import com.parkee_book_management_system.book_management_system.borrowing.model.models.Borrowing;
import com.parkee_book_management_system.book_management_system.borrowing.model.request.BorrowingGetAllRequest;
import com.parkee_book_management_system.book_management_system.borrowing.model.response.BorrowingGetAllResponse;
import com.parkee_book_management_system.book_management_system.genericresponse.GenericResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BorrowingService {


    ResponseEntity<GenericResponse<Borrowing>> borrowBook(BorrowingGetAllRequest borrowerGetAllDataRequest);

    ResponseEntity<GenericResponse<Borrowing>> returnBook(BorrowingGetAllRequest borrowerGetAllDataRequest);

    ResponseEntity<GenericResponse<List<BorrowingGetAllResponse>>> borrowBookMonitoring(BorrowingGetAllRequest borrowerGetAllDataRequest);

    ResponseEntity<GenericResponse<List<BorrowingGetAllResponse>>> borrowBookLogs(BorrowingGetAllRequest borrowerGetAllDataRequest);
}
