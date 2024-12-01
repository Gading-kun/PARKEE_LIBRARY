package com.parkee_book_management_system.book_management_system.borrower.model.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "borrower_table")
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String citizenId;
    private String name;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String activeStatus;
}
