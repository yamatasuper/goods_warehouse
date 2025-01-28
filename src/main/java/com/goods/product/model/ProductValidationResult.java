package com.goods.product.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductValidationResult {
    private boolean valid;
    private List<String> errors;
}

