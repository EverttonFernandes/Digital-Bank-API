package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.infrastructure.persistence.entity.AccountEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataAccountJpaRepository extends JpaRepository<AccountEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT account
        FROM AccountEntity account
        WHERE account.id IN (:sourceAccountIdentifier, :targetAccountIdentifier)
        ORDER BY account.id ASC
        """)
    List<AccountEntity> findAccountsByIdentifiersWithPessimisticLock(
        Long sourceAccountIdentifier,
        Long targetAccountIdentifier
    );
}
