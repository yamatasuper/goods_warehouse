package com.goods.product.task2.exceptions;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RemarkRepository extends JpaRepository<Remark, UUID> {
}

