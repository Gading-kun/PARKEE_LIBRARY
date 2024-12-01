package com.parkee_book_management_system.book_management_system.borrowing;

import com.parkee_book_management_system.book_management_system.borrower.model.request.BorrowerGetAllDataRequest;
import com.parkee_book_management_system.book_management_system.borrower.model.response.BorrowerGetAllDataResponse;
import com.parkee_book_management_system.book_management_system.borrowing.model.models.Borrowing;
import com.parkee_book_management_system.book_management_system.borrowing.model.request.BorrowingGetAllRequest;
import com.parkee_book_management_system.book_management_system.borrowing.model.response.BorrowingGetAllResponse;
import com.parkee_book_management_system.book_management_system.genericresponse.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/borrowing/v1")
@RequiredArgsConstructor
public class BorrowingController {
    private final BorrowingService borrowingService;

    @PostMapping(value = "/borrow")
    public ResponseEntity<GenericResponse<Borrowing>> borrowBook(@RequestBody BorrowingGetAllRequest borrowerGetAllDataRequest) {
        return borrowingService.borrowBook(borrowerGetAllDataRequest);
    }

    @PostMapping(value = "/return")
    public ResponseEntity<GenericResponse<Borrowing>> returnBook(@RequestBody BorrowingGetAllRequest borrowerGetAllDataRequest) {
        return borrowingService.returnBook(borrowerGetAllDataRequest);
    }

    @PostMapping(value = "/borrow/monitor")
    public ResponseEntity<GenericResponse<List<BorrowingGetAllResponse>>> borrowBookMonitoring(@RequestBody BorrowingGetAllRequest borrowerGetAllDataRequest) {
        return borrowingService.borrowBookMonitoring(borrowerGetAllDataRequest);
    }

    @PostMapping(value = "/borrow/logs")
    public ResponseEntity<GenericResponse<List<BorrowingGetAllResponse>>> borrowBookLogs(@RequestBody BorrowingGetAllRequest borrowerGetAllDataRequest) {
        return borrowingService.borrowBookLogs(borrowerGetAllDataRequest);
    }
}
