package com.goods.product.task1.logs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class FileLogger {
  public void logToFile(String fileName, String message) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
      writer.write(message);
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
