package com.goods.product.task7.S3Images;

import com.goods.product.model.Product;
import com.goods.product.service.ProductService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;

@Service
public class ProductImageSeeder {

  public ProductImageSeeder(
      ProductService productService,
      S3Service s3Service,
      ProductImageRepository productImageRepository,
      ResourcePatternResolver resourcePatternResolver) {
    this.productService = productService;
    this.s3Service = s3Service;
    this.productImageRepository = productImageRepository;
    this.resourcePatternResolver = resourcePatternResolver;
  }

  private final ProductService productService;
  private final S3Service s3Service;
  private final ProductImageRepository productImageRepository;
  private final ResourcePatternResolver resourcePatternResolver;

  public void seedRandomImagesToProducts(int imagesPerProduct) throws IOException {
    Resource[] images = resourcePatternResolver.getResources("classpath:seed-images/*");
    if (images.length == 0) {
      throw new IllegalStateException("No seed images found in resources/seed-images");
    }

    List<Product> products = productService.getAllProductEntities();
    Random random = new Random();

    for (Product product : products) {
      for (int i = 0; i < imagesPerProduct; i++) {
        Resource image = images[random.nextInt(images.length)];
        String filename = image.getFilename();
        String extension = filename.substring(filename.lastIndexOf("."));
        String s3Key = UUID.randomUUID() + extension;

        // Upload to MinIO
        try (InputStream is = image.getInputStream()) {
          byte[] bytes = is.readAllBytes();
          s3Service.uploadFile(
              new MockMultipartFile(
                  filename,
                  filename,
                  "image/jpeg", // или "image/png" в зависимости от типа
                  bytes),
              s3Key);
        }

        // Save to DB
        ProductImage imageEntity = new ProductImage();
        imageEntity.setProduct(product);
        imageEntity.setS3Key(s3Key);
        productImageRepository.save(imageEntity);
      }
    }
  }
}
