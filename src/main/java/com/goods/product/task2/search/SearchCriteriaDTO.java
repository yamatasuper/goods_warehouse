package com.goods.product.task2.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteriaDTO {

  @NotBlank(message = "Field name cannot be blank")
  private String field;

  @JsonSerialize(using = CustomValueSerializer.class)
  @JsonDeserialize(using = CustomValueDeserializer.class)
  private Object value;

  @NotBlank(message = "Operation must be provided")
  private String operation;
}
