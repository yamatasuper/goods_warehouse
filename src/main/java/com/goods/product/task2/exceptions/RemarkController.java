package com.goods.product.task2.exceptions;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/remarks")
public class RemarkController {

    @Autowired
    private RemarkRepository remarkRepository;

    @GetMapping
    public List<Remark> getAllRemarks() {
        return remarkRepository.findAll();
    }

    @PostMapping
    public Remark createRemark(@RequestBody Remark remark) {
        return remarkRepository.save(remark);
    }

    @GetMapping("/{id}")
    public Remark getRemarkById(@PathVariable UUID id) {
        return remarkRepository.findById(id).orElseThrow(() -> new RuntimeException("Remark not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteRemark(@PathVariable UUID id) {
        remarkRepository.deleteById(id);
    }
}

