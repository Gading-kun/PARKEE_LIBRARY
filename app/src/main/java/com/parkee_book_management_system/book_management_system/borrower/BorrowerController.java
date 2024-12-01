package com.parkee_book_management_system.book_management_system.borrower;

import com.parkee_book_management_system.book_management_system.borrower.model.models.Borrower;
import com.parkee_book_management_system.book_management_system.borrower.model.request.BorrowerGetAllDataRequest;
import com.parkee_book_management_system.book_management_system.borrower.model.response.BorrowerGetAllDataResponse;
import com.parkee_book_management_system.book_management_system.borrowing.BorrowingService;
import com.parkee_book_management_system.book_management_system.borrowing.model.request.BorrowingGetAllRequest;
import com.parkee_book_management_system.book_management_system.borrowing.model.response.BorrowingGetAllResponse;
import com.parkee_book_management_system.book_management_system.genericresponse.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrower/v1")
@RequiredArgsConstructor
public class BorrowerController {

    private final BorrowerService borrowerService;

    @GetMapping(value = "/getAllBorrower")
    public ResponseEntity<GenericResponse<List<Borrower>>> getAllBorrowings() {
        return borrowerService.getAllBorrowers();
    }

    @PostMapping(value = "/createBorrower")
    public ResponseEntity<GenericResponse<BorrowerGetAllDataResponse>> createBorrowing(@RequestBody BorrowerGetAllDataRequest borrowerGetAllDataRequest) {
        return borrowerService.createBorrower(borrowerGetAllDataRequest);
    }

    @PostMapping(value = "/deleteBorrower")
    public ResponseEntity<GenericResponse<BorrowerGetAllDataResponse>> deleteBorrowing(@RequestBody BorrowerGetAllDataRequest borrowerGetAllDataRequest) {
        return borrowerService.deleteBorrower(borrowerGetAllDataRequest);
    }

    @PostMapping(value = "/updateBorrower")
    public ResponseEntity<GenericResponse<BorrowerGetAllDataResponse>> updateBorrowing(@RequestBody BorrowerGetAllDataRequest borrowerGetAllDataRequest) {
        return borrowerService.updateBorrower(borrowerGetAllDataRequest);
    }

    @PostMapping(value = "/getBorrowerById")
    public ResponseEntity<GenericResponse<BorrowerGetAllDataResponse>> getBorrowingById(@RequestBody BorrowerGetAllDataRequest borrowerGetAllDataRequest) {
        return borrowerService.getBorrowerById(borrowerGetAllDataRequest);
    }
}
