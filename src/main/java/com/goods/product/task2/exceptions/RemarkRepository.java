package com.goods.product.task2.exceptions;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemarkRepository extends JpaRepository<Remark, UUID> {}
