package com.parkee_book_management_system.book_management_system.borrowing;

import com.parkee_book_management_system.book_management_system.book.BookRepository;
import com.parkee_book_management_system.book_management_system.book.model.models.Book;
import com.parkee_book_management_system.book_management_system.borrower.BorrowerRepository;
import com.parkee_book_management_system.book_management_system.borrower.model.models.Borrower;
import com.parkee_book_management_system.book_management_system.borrowing.model.models.Borrowing;
import com.parkee_book_management_system.book_management_system.borrowing.model.models.BorrowingLogs;
import com.parkee_book_management_system.book_management_system.borrowing.model.request.BorrowingGetAllRequest;
import com.parkee_book_management_system.book_management_system.borrowing.model.response.BorrowingGetAllResponse;
import com.parkee_book_management_system.book_management_system.genericresponse.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BorrowingServiceImpl implements BorrowingService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private BorrowingLogsRepository borrowingLogsRepository;
    @Autowired
    private BorrowerRepository borrowerRepository;

    @Override
    public ResponseEntity<GenericResponse<Borrowing>> borrowBook(BorrowingGetAllRequest borrowerGetAllDataRequest) {
        //bookId
        //borrowerId
        //isbnBook
        //dateReturnExpectation

        Long bookId = borrowerGetAllDataRequest.getBookId();
        Long borrowerId = borrowerGetAllDataRequest.getBorrowerId();
        LocalDateTime dateLine = borrowerGetAllDataRequest.getDateReturnExpectation();

        Book book = bookRepository.findById(bookId).orElse(null);
        Borrowing borrowing = borrowingRepository.findByBorrowerId(borrowerId);

        if (borrowing != null && "1".equals(borrowing.getBorrowStatus())) {
            return ResponseEntity.badRequest().body(new GenericResponse<>("400", "Saat ini buku sedang dipinjam", null));
        }
        if (book == null || book.getStock() <= 0) {
            return ResponseEntity.badRequest().body(new GenericResponse<>("400", "Stok buku tidak tersedia", null));
        }
        long daysToDeadline = java.time.temporal.ChronoUnit.DAYS.between(LocalDateTime.now(), dateLine);

        Long secToDateLine = dateLine.toEpochSecond(java.time.ZoneOffset.UTC) - LocalDateTime.now().toEpochSecond(java.time.ZoneOffset.UTC);

        log.info(daysToDeadline + ": JUMLAH HARI");

        log.info(secToDateLine + ": JUMLAH DETIK");

        if (secToDateLine < 1 || secToDateLine > 2592000) {
            return ResponseEntity.badRequest().body(new GenericResponse<>("400", "Batas waktu peminjaman tidak valid", null));
        }

        String requestId = String.format("%ty%tm%06d", LocalDateTime.now(), LocalDateTime.now(), bookId);
        Borrowing borrowingData = Borrowing.builder()
                .bookId(bookId)
                .isbnBook(borrowerGetAllDataRequest.getIsbnBook())
                .borrowerId(borrowerId)
                .borrowStatus("1")
                .lateStatus("0")
                .dateReturnExpectation(dateLine)
                .requestId(requestId)
                .createDate(LocalDateTime.now())
                .build();

        borrowingRepository.save(borrowingData);
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        BorrowingLogs borrowingLogs = BorrowingLogs.builder()
                .requestId(requestId)
                .borrowerId(borrowerId)
                .action("Borrowing")
                .borrowStatus("1")
                .lateStatus("0")
                .bookId(bookId)
                .createdDate(LocalDateTime.now())
                .build();

        borrowingLogsRepository.save(borrowingLogs);
        return ResponseEntity.ok(new GenericResponse<>("200", "Buku berhasil dipinjam", borrowingData));
    }

    @Override
    public ResponseEntity<GenericResponse<Borrowing>> returnBook(BorrowingGetAllRequest borrowerGetAllDataRequest) {
        String lateStatus;
        Long bookId = borrowerGetAllDataRequest.getBookId();
        String requestId = borrowerGetAllDataRequest.getRequestId();
        Long borrowerId = borrowerGetAllDataRequest.getBorrowerId();

        //bookId
        //requestId
        //borrowerId

        Book book = bookRepository.findById(bookId).orElse(null);
        Borrowing borrowing = borrowingRepository.findByRequestId(requestId);

        if (!Objects.equals(borrowing.getBorrowerId(), borrowerId)) {
            return ResponseEntity.badRequest().body(new GenericResponse<>("400", "Data peminjam tidak valid", null));
        }

        if (!Objects.equals(borrowing.getBookId(), bookId)) {
            return ResponseEntity.badRequest().body(new GenericResponse<>("400", "Data buku tidak valid", null));
        }

        if (borrowing == null || "0".equals(borrowing.getBorrowStatus())) {
            return ResponseEntity.badRequest().body(new GenericResponse<>("400", "Buku sudah dikembalikan", null));
        }

        if (borrowing.getDateReturnExpectation().isBefore(LocalDateTime.now())) {
            lateStatus = "1";
        } else {
            lateStatus = "0";
        }

        borrowing.setBorrowStatus("0");
        borrowing.setLateStatus(lateStatus);
        borrowing.setDateReturnRealization(LocalDateTime.now());
        borrowingRepository.save(borrowing);

        if (book != null) {
            book.setStock(book.getStock() + 1);
            bookRepository.save(book);
        } else {
            return ResponseEntity.badRequest().body(new GenericResponse<>("400", "Buku tidak ditemukan", null));
        }

        BorrowingLogs borrowingLogs = BorrowingLogs.builder()
                .requestId(requestId)
                .borrowerId(borrowerId)
                .action("Returning")
                .borrowStatus("0")
                .lateStatus(lateStatus)
                .bookId(bookId)
                .createdDate(LocalDateTime.now())
                .isbnBook(borrowing.getIsbnBook())
                .build();

        borrowingLogsRepository.save(borrowingLogs);
        return ResponseEntity.ok(new GenericResponse<>("200", "Buku berhasil dikembalikan", borrowing));
    }

    @Override
    public ResponseEntity<GenericResponse<List<BorrowingGetAllResponse>>> borrowBookMonitoring(BorrowingGetAllRequest borrowerGetAllDataRequest) {
        List<Borrowing> borrowings = borrowingRepository.findAll();

        List<BorrowingGetAllResponse> filteredBorrowings;
        if ("0".equals(borrowerGetAllDataRequest.getGetFilter())) {
            filteredBorrowings = borrowings.stream()
                    .map(borrowing -> BorrowingGetAllResponse.builder()
                            .id(borrowing.getId())
                            .bookId(borrowing.getBookId())
                            .bookName(bookRepository.findById(borrowing.getBookId()).orElse(null).getTitle().toString())
                            .isbnBook(borrowing.getIsbnBook())
                            .borrowerId(borrowing.getBorrowerId())
                            .borrowStatus("1".equals(borrowing.getBorrowStatus()) ? "Borrowed" : "Returned")
                            .lateStatus("1".equals(borrowing.getLateStatus()) ? "Late" : "On Time")
                            .dateReturnExpectation(borrowing.getDateReturnExpectation())
                            .dateReturnRealization(borrowing.getDateReturnRealization())
                            .createDate(borrowing.getCreateDate())
                            .requestId(borrowing.getRequestId())
                            .borrowerName(borrowerRepository.findById(borrowing.getBorrowerId()).orElse(null).getName().toString())
                            .build())
                    .collect(Collectors.toList());
        } else if ("1".equals(borrowerGetAllDataRequest.getGetFilter())) {
            filteredBorrowings = borrowings.stream()
                    .filter(borrowing ->
                            (borrowerGetAllDataRequest.getRequestId() == null || borrowerGetAllDataRequest.getRequestId().equals(borrowing.getRequestId())) &&
                                    (borrowerGetAllDataRequest.getBorrowerId() == null || borrowerGetAllDataRequest.getBorrowerId().equals(borrowing.getBorrowerId())) &&
                            (borrowerGetAllDataRequest.getBorrowStatus() == null || borrowerGetAllDataRequest.getBorrowStatus().equals(borrowing.getBorrowStatus())) &&
                            (borrowerGetAllDataRequest.getLateStatus() == null || borrowerGetAllDataRequest.getLateStatus().equals(borrowing.getLateStatus()))
                    )
                    .map(borrowing -> BorrowingGetAllResponse.builder()
                            .id(borrowing.getId())
                            .bookId(borrowing.getBookId())
                            .bookName(bookRepository.findById(borrowing.getBookId()).orElse(null).getTitle().toString())
                            .isbnBook(borrowing.getIsbnBook())
                            .borrowerId(borrowing.getBorrowerId())
                            .borrowerName(borrowerRepository.findById(borrowing.getBorrowerId()).orElse(null).getName().toString())
                            .borrowStatus("1".equals(borrowing.getBorrowStatus()) ? "Borrowed" : "Returned")
                            .lateStatus("1".equals(borrowing.getLateStatus()) ? "Late" : "On Time")
                            .dateReturnExpectation(borrowing.getDateReturnExpectation())
                            .dateReturnRealization(borrowing.getDateReturnRealization())
                            .createDate(borrowing.getCreateDate())
                            .requestId(borrowing.getRequestId())
                            .build())
                    .collect(Collectors.toList());
        } else {
            return ResponseEntity.badRequest().body(new GenericResponse<>("400", "Data tidak ditemukan", null));
        }

        if (filteredBorrowings.isEmpty()) {
            return ResponseEntity.badRequest().body(new GenericResponse<>("400", "Data tidak ditemukan", null));
        }

        return ResponseEntity.ok(new GenericResponse<>("200", "List data ditemukan", filteredBorrowings));
    }

    @Override
    public ResponseEntity<GenericResponse<List<BorrowingGetAllResponse>>> borrowBookLogs(BorrowingGetAllRequest borrowerGetAllDataRequest) {
        List<BorrowingLogs> borrowingsLogs = borrowingLogsRepository.findAll();

        List<BorrowingGetAllResponse> filteredBorrowings;
        if ("0".equals(borrowerGetAllDataRequest.getGetFilter())) {
            filteredBorrowings = borrowingsLogs.stream()
                    .map(borrowing -> BorrowingGetAllResponse.builder()
                            .id(borrowing.getId())
                            .bookId(borrowing.getBookId())
                            .bookName(bookRepository.findById(borrowing.getBookId()).orElse(null).getTitle().toString())
                            .isbnBook(borrowing.getIsbnBook())
                            .borrowerId(borrowing.getBorrowerId())
                            .borrowStatus("1".equals(borrowing.getBorrowStatus()) ? "Borrowed" : "Returned")
                            .lateStatus("1".equals(borrowing.getLateStatus()) ? "Late" : "On Time")
                            .action(borrowing.getAction())
                            .requestId(borrowing.getRequestId())
                            .borrowerName(borrowerRepository.findById(borrowing.getBorrowerId()).orElse(null).getName().toString())
                            .createDate(borrowing.getCreatedDate())
                            .build())
                    .collect(Collectors.toList());
        } else if ("1".equals(borrowerGetAllDataRequest.getGetFilter())) {
            filteredBorrowings = borrowingsLogs.stream()
                    .filter(
                            borrowing ->
                            (borrowerGetAllDataRequest.getRequestId() == null || borrowerGetAllDataRequest.getRequestId().equals(borrowing.getRequestId())) &&
                            (borrowerGetAllDataRequest.getBorrowerId() == null || borrowerGetAllDataRequest.getBorrowerId().equals(borrowing.getBorrowerId())) &&
                            (borrowerGetAllDataRequest.getBorrowStatus() == null || borrowerGetAllDataRequest.getBorrowStatus().equals(borrowing.getBorrowStatus())) &&
                            (borrowerGetAllDataRequest.getLateStatus() == null || borrowerGetAllDataRequest.getLateStatus().equals(borrowing.getLateStatus()))
                    )
                    .map(borrowing -> BorrowingGetAllResponse.builder()
                            .id(borrowing.getId())
                            .bookId(borrowing.getBookId())
                            .bookName(bookRepository.findById(borrowing.getBookId()).orElse(null).getTitle().toString())
                            .isbnBook(borrowing.getIsbnBook())
                            .borrowerId(borrowing.getBorrowerId())
                            .borrowerName(borrowerRepository.findById(borrowing.getBorrowerId()).orElse(null).getName().toString())
                            .borrowStatus("1".equals(borrowing.getBorrowStatus()) ? "Borrowed" : "Returned")
                            .lateStatus("1".equals(borrowing.getLateStatus()) ? "Late" : "On Time")
                            .action(borrowing.getAction())
                            .requestId(borrowing.getRequestId())
                            .createDate(borrowing.getCreatedDate())
                            .build())
                    .collect(Collectors.toList());
        } else {
            return ResponseEntity.badRequest().body(new GenericResponse<>("400", "Data tidak ditemukan", null));
        }

        if (filteredBorrowings.isEmpty()) {
            return ResponseEntity.badRequest().body(new GenericResponse<>("400", "Data tidak ditemukan", null));
        }

        return ResponseEntity.ok(new GenericResponse<>("200", "List data ditemukan", filteredBorrowings));
    }
}


