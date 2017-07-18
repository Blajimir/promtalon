package ru.promtalon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.promtalon.entity.Role;

public interface RoleDAO extends JpaRepository<Role, Long> {
    @Query("select case when count(r)>0 then 'true' else 'false' end from Role r where r.name = ?1")
    boolean existRoleByName(String name);

    Role findRoleByName(String name);
}
