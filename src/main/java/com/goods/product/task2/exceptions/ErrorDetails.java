package com.goods.product.task2.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    private String exceptionName;
    private String exceptionClass;
    private String message;
    private LocalDateTime timestamp;
}


