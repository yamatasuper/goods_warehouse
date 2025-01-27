package com.goods.product.task2.exceptions;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Remark {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Description cannot be blank")
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RemarkType type;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Удобный конструктор для создания объектов Remark
    public Remark(String description, RemarkType type) {
        this.description = description;
        this.type = type;
    }

    // Пустой конструктор для JPA
    public Remark() {
    }

    // Getters and setters (если требуются)

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RemarkType getType() {
        return type;
    }

    public void setType(RemarkType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


