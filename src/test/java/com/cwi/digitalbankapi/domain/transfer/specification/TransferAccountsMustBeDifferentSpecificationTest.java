package com.cwi.digitalbankapi.domain.transfer.specification;

import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.transfer.exception.SourceAndTargetAccountsMustBeDifferentException;
import com.cwi.digitalbankapi.domain.transfer.model.Transfer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

class TransferAccountsMustBeDifferentSpecificationTest {

    private final TransferAccountsMustBeDifferentSpecification transferAccountsMustBeDifferentSpecification =
            new TransferAccountsMustBeDifferentSpecification();

    @Test
    @DisplayName("Deve permitir transferencia quando conta de origem e conta de destino forem diferentes")
    void shouldAllowTransferWhenSourceAndTargetAccountsAreDifferent() {
        // GIVEN
        Account sourceAccount = new Account(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        Account targetAccount = new Account(2L, "Bruno Lima", new BigDecimal("980.50"), OffsetDateTime.now(), OffsetDateTime.now());
        Transfer transfer = new Transfer(sourceAccount, targetAccount, new BigDecimal("100.00"));

        // WHEN
        Assertions.assertDoesNotThrow(() -> transferAccountsMustBeDifferentSpecification.ensureSatisfiedBy(transfer));

        // THEN
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Deve rejeitar transferencia quando conta de origem e conta de destino forem iguais")
    void shouldRejectTransferWhenSourceAndTargetAccountsAreTheSame() {
        // GIVEN
        Account sourceAccount = new Account(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        Transfer transfer = new Transfer(sourceAccount, sourceAccount, new BigDecimal("100.00"));

        // WHEN
        SourceAndTargetAccountsMustBeDifferentException exception = Assertions.assertThrows(
                SourceAndTargetAccountsMustBeDifferentException.class,
                () -> transferAccountsMustBeDifferentSpecification.ensureSatisfiedBy(transfer)
        );

        // THEN
        Assertions.assertEquals("SOURCE_AND_TARGET_ACCOUNTS_MUST_BE_DIFFERENT", exception.getKey());
        Assertions.assertEquals("Conta de origem e conta de destino devem ser diferentes.", exception.getValue());
    }
}
