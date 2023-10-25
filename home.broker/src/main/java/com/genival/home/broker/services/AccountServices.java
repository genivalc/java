package com.genival.home.broker.services;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.AccountMovement;
import com.genival.home.broker.entities.Client;
import com.genival.home.broker.repositories.AccountRepositories;
import com.genival.home.broker.repositories.ClientRepositories;
import com.genival.home.broker.utils.Util;
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
public class AccountServices {

    public Account[] contascorrente = new Account[50];
    Util util = new Util();
    @Autowired
    private AccountRepositories accountRepositories;
    @Autowired
    private ClientRepositories clientRepositories;
    @Autowired
    private AccountMovementServices accountMovementServices;

    public Account save(Account account) {
        return accountRepositories.save(account);
    }


    public void initialAccountCredit(Client client, Account newAccount) {
        BigDecimal valor = new BigDecimal("20000.0");
        validateTransfer(searchAccountStock(findAll()), newAccount, valor);
        String descricaoMov = "Boas Vindas de Abertura de Conta";
        accountMovementServices.saveMovement(searchAccountStock(findAll()), newAccount, valor, descricaoMov);
    }

    public boolean upgrade(Account account) {
        findById(account.getId());
        accountRepositories.save(account);
        return true;
    }

    public void monthlyPayment(Client client) {
        Account accountScholarship = searchAccountStock(findAll());
        BigDecimal value = new BigDecimal("20.0");
        for (Account conta : contascorrente) {
            if (conta != null) {
                validateTransfer(conta, accountScholarship, value);
                String descriptionOfAccountMovement = "Mensalidade da conta";
                accountMovementServices.saveMovement(conta, accountScholarship, value, descriptionOfAccountMovement);
            }
        }
    }

    @Transactional(readOnly = true)
    public Account searchAccounts(Client client) {
        List<Account> accounts = findAll();


        for (Account account : accounts) {
            if (account.getClient().getId().equals(client.getId())) {
                return account;
            }
        }
        return null;

    }

    @Transactional(readOnly = true)
    public Account findById(long id) {
        return accountRepositories.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return accountRepositories.findAll();
    }

    @Transactional(readOnly = true)
    public StringBuilder listAccounts() {
        List<Account> accounts = findAll();

        StringBuilder sb = new StringBuilder();

        sb.append("| ").append(util.columnText("ID Conta", 8)).append(" | ").append(util.columnText("Nome do Cliente", 20)).append(" |\n");

        for (Account conta : accounts) {
            if (conta != null) {
                sb.append("| ").append(util.columnText(String.valueOf(conta.getId()), 8)).append(" | ").append(util.columnText(String.valueOf(conta.getClient().getName()), 20)).append(" |\n");
            }
        }

        return sb;
    }

    @Transactional(readOnly = true)
    public Client customersearchbyAccountId(long id, Client client) {
        Account account = searchAccounts(client);
        return account.getClient();
    }

    @Transactional(readOnly = true)
    public Account customersearchbyAccount(long id, Client client) {
        Account account = searchAccounts(client);
        return account;
    }

    @Transactional(readOnly = true)
    public boolean validateId(long id) {
        return accountRepositories.existsById(id);
    }

    @Transactional(readOnly = true)
    public Account searchAccountStock(List<Account> currentAccounts) {
        for (Account account : currentAccounts) {
            if (account.isStockExchangeAccount()) {
                return account;
            }
        }
        return null;
    }

    public boolean remover(long Id) {
        findById(Id);
        accountRepositories.deleteById(Id);
        return true;
    }

    public boolean deposit(Account destiny, BigDecimal value) {
        destiny.setBalance(value);
        upgrade(destiny);
        String descriptionOfAccountMovement = "Depósito";
        accountMovementServices.saveMovement(null, destiny, value, descriptionOfAccountMovement);
        return true;
    }

    public boolean withdraw(Account origin, BigDecimal value) {
        if (origin.getBalance().compareTo(value) >= 0) {
            origin.setBalance(origin.getBalance().subtract(value));
            upgrade(origin);
            String descricaoMov = "Saque";
            accountMovementServices.saveMovement(origin, null, value, descricaoMov);
            return true;
        }
        return false;
    }

    public boolean payment(Account origin, BigDecimal valor) {
        if (validateTransfer(origin, this.searchAccountStock(findAll()), valor)) {
            String descriptionOfAccountMovement = "Pagamento para Bolsa";
            accountMovementServices.saveMovement(origin, this.searchAccountStock(findAll()), valor, descriptionOfAccountMovement);
            return true;
        }
        return false;

    }

    public boolean transfere(Account origem, Account destino, BigDecimal valor) {
        if (validateTransfer(origem, destino, valor)) {
            AccountMovement m = new AccountMovement();
            m.setValue(valor);
            m.setAccount(origem);
            m.setTypeMovement(2);
            m.setDescription("Transferencia entre contas");
            accountMovementServices.save(m);
            m.setAccount(destino);
            m.setTypeMovement(1);
            accountMovementServices.save(m);

            return true;
        }
        return false;
    }

    public boolean validateTransfer(Account origin, Account destiny, BigDecimal transferAmount) {
        if (origin.getBalance().compareTo(transferAmount) >= 0) {
            origin.setBalance(origin.getBalance().subtract(transferAmount));
            destiny.setBalance(destiny.getBalance().add(transferAmount));
            upgrade(origin);
            upgrade(destiny);
            return true;
        } else {
            return false;
        }
    }

    public boolean pagaDividendos(Account origem, Account destino, BigDecimal valor) {
        if (validateTransfer(origem, destino, valor)) {
            String descricaoMov = "Pagamento de Dividendos";
            accountMovementServices.saveMovement(origem, destino, valor, descricaoMov);

            return true;
        }
        return false;
    }

}
