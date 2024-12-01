package com.parkee_book_management_system.book_management_system.borrower;

import com.parkee_book_management_system.book_management_system.borrower.model.models.Borrower;
import com.parkee_book_management_system.book_management_system.borrower.model.request.BorrowerGetAllDataRequest;
import com.parkee_book_management_system.book_management_system.borrower.model.response.BorrowerGetAllDataResponse;
import com.parkee_book_management_system.book_management_system.genericresponse.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowerServiceImpl implements BorrowerService{

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Override
    public ResponseEntity<GenericResponse<List<Borrower>>> getAllBorrowers() {
        List<Borrower> borrowers = borrowerRepository.findAll();
        GenericResponse<List<Borrower>> response = new GenericResponse<>("200", "Success", borrowers);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GenericResponse<BorrowerGetAllDataResponse>> createBorrower(BorrowerGetAllDataRequest borrowerGetAllDataRequest) {
        Borrower borrower = new Borrower();
        borrower.setCitizenId(borrowerGetAllDataRequest.getCitizenId());
        borrower.setName(borrowerGetAllDataRequest.getName());
        borrower.setEmail(borrowerGetAllDataRequest.getEmail());
        borrower.setCreateDate(LocalDateTime.now());
        borrower.setActiveStatus(borrowerGetAllDataRequest.getActiveStatus());

        borrower = borrowerRepository.save(borrower);

        BorrowerGetAllDataResponse responseData = new BorrowerGetAllDataResponse(borrower.getId(), borrower.getCitizenId(),
                borrower.getName(), borrower.getEmail(), borrower.getCreateDate(), borrower.getActiveStatus());
        GenericResponse<BorrowerGetAllDataResponse> response = new GenericResponse<>("200", "Created", responseData);
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<GenericResponse<BorrowerGetAllDataResponse>> deleteBorrower(BorrowerGetAllDataRequest borrowerGetAllDataRequest) {
        borrowerRepository.deleteById(borrowerGetAllDataRequest.getId());
        GenericResponse<BorrowerGetAllDataResponse> response = new GenericResponse<>("200", "Deleted", null);
        return ResponseEntity.status(204).body(response);
    }

    @Override
    public ResponseEntity<GenericResponse<BorrowerGetAllDataResponse>> updateBorrower(BorrowerGetAllDataRequest borrowerGetAllDataRequest) {
        Borrower borrower = borrowerRepository.findById(borrowerGetAllDataRequest.getId()).orElse(null);
        if (borrower != null) {
            if (borrowerGetAllDataRequest.getCitizenId() != null) {
                borrower.setCitizenId(borrowerGetAllDataRequest.getCitizenId());
            }
            if (borrowerGetAllDataRequest.getName() != null) {
                borrower.setName(borrowerGetAllDataRequest.getName());
            }
            if (borrowerGetAllDataRequest.getEmail() != null) {
                borrower.setEmail(borrowerGetAllDataRequest.getEmail());
            }
            if (borrowerGetAllDataRequest.getCreateDate() != null) {
                borrower.setCreateDate(borrowerGetAllDataRequest.getCreateDate());
            }
            if (borrowerGetAllDataRequest.getActiveStatus() != null) {
                borrower.setActiveStatus(borrowerGetAllDataRequest.getActiveStatus());
            }

            borrower.setUpdateDate(LocalDateTime.now());

            borrower = borrowerRepository.save(borrower);

            BorrowerGetAllDataResponse responseData = new BorrowerGetAllDataResponse(borrower.getId(), borrower.getCitizenId(),
                    borrower.getName(), borrower.getEmail(), borrower.getCreateDate(), borrower.getActiveStatus());
            GenericResponse<BorrowerGetAllDataResponse> response = new GenericResponse<>("200", "Update data berhasil", responseData);
            return ResponseEntity.ok(response);
        } else {
            GenericResponse<BorrowerGetAllDataResponse> response = new GenericResponse<>("404", "Peminjam tidak ditemukan", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @Override
    public ResponseEntity<GenericResponse<BorrowerGetAllDataResponse>> getBorrowerById(BorrowerGetAllDataRequest borrowerGetAllDataRequest) {
        Borrower borrower = borrowerRepository.findById(borrowerGetAllDataRequest.getId()).orElseThrow();
        BorrowerGetAllDataResponse responseData = new BorrowerGetAllDataResponse(borrower.getId(), borrower.getCitizenId(),
                borrower.getName(), borrower.getEmail(), borrower.getCreateDate(), borrower.getActiveStatus());
        GenericResponse<BorrowerGetAllDataResponse> response = new GenericResponse<>("200", "Data ditemukan", responseData);
        return ResponseEntity.ok(response);
    }
}
