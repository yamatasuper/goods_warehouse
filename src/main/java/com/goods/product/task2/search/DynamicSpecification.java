package com.goods.product.task2.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class DynamicSpecification<T> implements Specification<T> {

  private final List<SearchCriteriaDTO> criteriaList;

  public DynamicSpecification(List<SearchCriteriaDTO> criteriaList) {
    this.criteriaList = criteriaList;
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    for (SearchCriteriaDTO criteria : criteriaList) {
      String operation = criteria.getOperation().toLowerCase();
      Path<?> path = root.get(criteria.getField());
      Class<?> fieldType = path.getJavaType();

      switch (operation) {
        case "=":
        case "equal":
          predicates.add(builder.equal(path, convertValue(criteria.getValue(), fieldType)));
          break;

        case ">=":
        case "greater_than_or_eq":
          if (Comparable.class.isAssignableFrom(fieldType)) {
            predicates.add(
                builder.greaterThanOrEqualTo(
                    (Expression<? extends Comparable>) path,
                    (Comparable) convertValue(criteria.getValue(), fieldType)));
          } else {
            throw new IllegalArgumentException(
                "Field type is not comparable for '>=': " + fieldType);
          }
          break;

        case "<=":
        case "less_than_or_eq":
          if (Comparable.class.isAssignableFrom(fieldType)) {
            predicates.add(
                builder.lessThanOrEqualTo(
                    (Expression<? extends Comparable>) path,
                    (Comparable) convertValue(criteria.getValue(), fieldType)));
          } else {
            throw new IllegalArgumentException(
                "Field type is not comparable for '<=': " + fieldType);
          }
          break;

        case "~":
        case "like":
          if (fieldType.equals(String.class)) {
            predicates.add(
                builder.like(path.as(String.class), "%" + criteria.getValue().toString() + "%"));
          } else {
            throw new IllegalArgumentException(
                "LIKE operation is only supported for String fields.");
          }
          break;

        default:
          throw new IllegalArgumentException("Unsupported operation: " + operation);
      }
    }

    return builder.and(predicates.toArray(new Predicate[0]));
  }

  private Object convertValue(Object value, Class<?> targetType) {
    if (value == null) {
      return null;
    }

    if (targetType.equals(String.class)) {
      return value.toString();
    }

    if (targetType.equals(Integer.class) || targetType.equals(int.class)) {
      return Integer.parseInt(value.toString());
    }

    if (targetType.equals(Double.class) || targetType.equals(double.class)) {
      return Double.parseDouble(value.toString());
    }

    if (targetType.equals(BigDecimal.class)) {
      return new BigDecimal(value.toString());
    }

    if (targetType.equals(LocalDateTime.class)) {
      return LocalDateTime.parse(value.toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    if (targetType.equals(LocalDate.class)) {
      return LocalDate.parse(value.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
    }

    if (targetType.equals(Date.class)) {
      return Date.from(
          LocalDateTime.parse(value.toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
              .atZone(ZoneId.systemDefault())
              .toInstant());
    }

    throw new IllegalArgumentException("Unsupported target type: " + targetType);
  }
}
