package com.teste.southsystem.account.repository;

import com.teste.southsystem.account.model.Entity.LimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitRepository extends JpaRepository<LimitEntity, Long> {
}
