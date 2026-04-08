package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.account.model.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT account
        FROM Account account
        WHERE account.id IN (:sourceAccountIdentifier, :targetAccountIdentifier)
        ORDER BY account.id ASC
        """)
    List<Account> findAccountsByIdentifiersWithPessimisticLock(
        Long sourceAccountIdentifier,
        Long targetAccountIdentifier
    );
}
