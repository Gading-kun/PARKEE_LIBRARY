package com.parkee_book_management_system.book_management_system.borrower;

import com.parkee_book_management_system.book_management_system.borrower.model.models.Borrower;
import com.parkee_book_management_system.book_management_system.borrower.model.request.BorrowerGetAllDataRequest;
import com.parkee_book_management_system.book_management_system.borrower.model.response.BorrowerGetAllDataResponse;
import com.parkee_book_management_system.book_management_system.genericresponse.GenericResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BorrowerService {
    ResponseEntity<GenericResponse<List<Borrower>>> getAllBorrowers();

    ResponseEntity<GenericResponse<BorrowerGetAllDataResponse>> createBorrower(BorrowerGetAllDataRequest borrowerGetAllDataRequest);

    ResponseEntity<GenericResponse<BorrowerGetAllDataResponse>> deleteBorrower(BorrowerGetAllDataRequest borrowerGetAllDataRequest);

    ResponseEntity<GenericResponse<BorrowerGetAllDataResponse>> updateBorrower(BorrowerGetAllDataRequest borrowerGetAllDataRequest);

    ResponseEntity<GenericResponse<BorrowerGetAllDataResponse>> getBorrowerById(BorrowerGetAllDataRequest borrowerGetAllDataRequest);
}
