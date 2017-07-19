package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.Promo;

public interface PromoDao extends JpaRepository<Promo, Long> {
}
