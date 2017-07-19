package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {
}
