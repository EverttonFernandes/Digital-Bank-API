package com.digitalbankapi.infrastructure.persistence.repository;

import com.digitalbankapi.domain.account.model.Account;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000"))
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
