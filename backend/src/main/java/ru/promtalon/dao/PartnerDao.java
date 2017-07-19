package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.promtalon.entity.Partner;

public interface PartnerDao extends JpaRepository<Partner, Long> {
}
