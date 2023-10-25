package com.genival.home.broker.services;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.AccountMovement;
import com.genival.home.broker.repositories.AccountMovementRepositories;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccountMovementServices {


    @Autowired
    private AccountMovementRepositories accountMovementRepositories;

    @Transactional(readOnly = true)
    public boolean isEmpty() {
        return accountMovementRepositories.count() == 0;
    }

    @Transactional()
    public boolean save(AccountMovement accountMovement) {
        accountMovementRepositories.save(accountMovement);
        return true;
    }

    public void saveMovement(Account debit, Account credit, BigDecimal value, String descriptionOfAccountMovement) {
        if (debit != null) {
            AccountMovement movementDebit = new AccountMovement();
            movementDebit.setValue(value);
            movementDebit.setAccount(debit);
            movementDebit.setTypeMovement(2);
            movementDebit.setDescription(descriptionOfAccountMovement);
            this.save(movementDebit);
        }

        if (credit != null) {
            AccountMovement movementCredit = new AccountMovement();
            movementCredit.setValue(value);
            movementCredit.setAccount(credit);
            movementCredit.setTypeMovement(1);
            movementCredit.setDescription(descriptionOfAccountMovement);
            this.save(movementCredit);
        }
    }


    @Transactional(readOnly = true)
    private StringBuilder imprimeLista(List<AccountMovement> accountMovements) {
        StringBuilder sb = new StringBuilder();
        for (AccountMovement movement : accountMovements) {
            sb.append(movement).append("\n");
        }
        return sb;
    }

    @Transactional(readOnly = true)
    public AccountMovement findById(Long id) {
        return accountMovementRepositories.findById(id).orElse(null);
    }

    public boolean remover(Long Id) {
        findById(Id);
        accountMovementRepositories.deleteById(Id);
        return true;

    }

    @Transactional(readOnly = true)
    public List<AccountMovement> findByAll() {
        return accountMovementRepositories.findAll();
    }

}

