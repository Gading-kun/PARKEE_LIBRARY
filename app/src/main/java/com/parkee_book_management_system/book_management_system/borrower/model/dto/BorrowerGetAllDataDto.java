package com.parkee_book_management_system.book_management_system.borrower.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowerGetAllDataDto implements Serializable {
    private Long id;
    private String citizenId;
    private String name;
    private String email;
    private LocalDateTime createDate;
    private String activeStatus;
}
