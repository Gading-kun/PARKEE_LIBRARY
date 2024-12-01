package com.parkee_book_management_system.book_management_system.genericresponse;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
    private String responseCode;
    private String responseMessage;
    private T data;
}
