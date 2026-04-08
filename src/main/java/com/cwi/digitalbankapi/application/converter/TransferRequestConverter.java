package com.cwi.digitalbankapi.application.converter;

import com.cwi.digitalbankapi.application.dto.TransferRequest;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.transfer.exception.TransferAmountMustBePositiveException;
import com.cwi.digitalbankapi.domain.transfer.model.Transfer;
import com.cwi.digitalbankapi.shared.exception.InvalidRequestDataException;
import org.springframework.stereotype.Component;

@Component
public class TransferRequestConverter {

    public Transfer convert(TransferRequest transferRequest, Account sourceAccount, Account targetAccount) {
        if (transferRequest.sourceAccountId() == null) {
            throw new InvalidRequestDataException("O campo sourceAccountId e obrigatorio.");
        }

        if (transferRequest.targetAccountId() == null) {
            throw new InvalidRequestDataException("O campo targetAccountId e obrigatorio.");
        }

        if (transferRequest.amount() == null) {
            throw new InvalidRequestDataException("O campo amount e obrigatorio.");
        }

        if (transferRequest.amount() == null || transferRequest.amount().signum() <= 0) {
            throw new TransferAmountMustBePositiveException();
        }

        return new Transfer(
            sourceAccount,
            targetAccount,
            transferRequest.amount()
        );
    }
}
