package com.goods.product.task2.exceptions;

import java.time.LocalDateTime;

public class ErrorDetails {
    private String exceptionName; // Название исключения
    private String exceptionClass; // Класс, в котором возникло исключение
    private String message; // Сообщение об ошибке
    private LocalDateTime timestamp; // Дата и время

    public ErrorDetails(String exceptionName, String exceptionClass, String message, LocalDateTime timestamp) {
        this.exceptionName = exceptionName;
        this.exceptionClass = exceptionClass;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

