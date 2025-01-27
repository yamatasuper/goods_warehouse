package com.goods.product.task2.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Поднимает только слой JPA для тестирования
class RemarkRepositoryTest {

    @Autowired
    private RemarkRepository remarkRepository;

    @Test
    @DisplayName("Должен сохранить и найти Remark")
    void shouldSaveAndFindRemark() {
        // Создаем объект Remark
        Remark remark = new Remark("Test remark", RemarkType.INFO);

        // Сохраняем в базу
        Remark savedRemark = remarkRepository.save(remark);

        // Проверяем, что он сохранен
        Optional<Remark> foundRemark = remarkRepository.findById(savedRemark.getId());
        assertThat(foundRemark).isPresent();
        assertThat(foundRemark.get().getDescription()).isEqualTo("Test remark");
        assertThat(foundRemark.get().getType()).isEqualTo(RemarkType.INFO);
    }

    @Test
    @DisplayName("Должен находить все Remark")
    void shouldFindAllRemarks() {
        // Добавляем несколько объектов Remark
        remarkRepository.save(new Remark("Remark 1", RemarkType.WARNING));
        remarkRepository.save(new Remark("Remark 2", RemarkType.ERROR));

        // Ищем все записи
        List<Remark> remarks = remarkRepository.findAll();

        // Проверяем размер и содержимое
        assertThat(remarks).hasSize(2);
        assertThat(remarks)
                .extracting("description")
                .containsExactlyInAnyOrder("Remark 1", "Remark 2");
    }

    @Test
    @DisplayName("Должен удалить Remark по ID")
    void shouldDeleteRemarkById() {
        // Добавляем объект Remark
        Remark remark = remarkRepository.save(new Remark("Remark to delete", RemarkType.INFO));

        // Удаляем объект
        remarkRepository.deleteById(remark.getId());

        // Проверяем, что объект удален
        Optional<Remark> foundRemark = remarkRepository.findById(remark.getId());
        assertThat(foundRemark).isNotPresent();
    }
}