package com.goods.product.task7.images.controller;

import com.goods.product.task7.images.entity.ProductImage;
import com.goods.product.task7.images.repository.ProductImageRepository;
import com.goods.product.task7.images.service.S3Service;
import jakarta.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

  private final S3Service s3Service;
  private final ProductImageRepository repo;

  public FileController(S3Service s3Service, ProductImageRepository repo) {
    this.s3Service = s3Service;
    this.repo = repo;
  }

  // Конструктор...

  @PostMapping("/upload")
  public ResponseEntity<String> upload(
      @RequestParam Long productId, @RequestParam MultipartFile file) throws IOException {

    String key = UUID.randomUUID().toString();
    s3Service.uploadFile(key, file.getInputStream());
    repo.save(new ProductImage(productId, key));
    return ResponseEntity.ok("File uploaded");
  }

  @GetMapping("/download")
  public ResponseEntity<Resource> download(@RequestParam Long productId) throws IOException {
    List<ProductImage> images = repo.findByProductId(productId);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ZipOutputStream zos = new ZipOutputStream(baos);

    for (ProductImage image : images) {
      zos.putNextEntry(new ZipEntry(image.getS3Key()));
      IOUtils.copy(s3Service.downloadFile(image.getS3Key()), zos);
      zos.closeEntry();
    }
    zos.close();

    ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=images.zip")
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body((Resource) resource);
  }
}
