package com.cathay.coindesk.repository;

import com.cathay.coindesk.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, String> {

    Optional<CurrencyEntity> findByCode(String code);
    void deleteByCode(String code);
}
