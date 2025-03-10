package com.goods.product.task7.images.service;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;

@Service
public class S3Service {

  private final S3Client s3Client;
  private final String bucket = "my-bucket";

  public S3Service(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  public void uploadFile(String key, InputStream file) throws IOException {
    s3Client.putObject(
        r -> r.bucket(bucket).key(key), RequestBody.fromInputStream(file, file.available()));
  }

  public InputStream downloadFile(String key) {
    return s3Client.getObject(r -> r.bucket(bucket).key(key));
  }
}
