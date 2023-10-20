package com.genival.home.broker.services;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.Client;
import com.genival.home.broker.repositories.AccountRepositories;
import com.genival.home.broker.repositories.ClientRepositories;
import com.genival.home.broker.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccountServices {

    Util util = new Util();
    @Autowired
    private AccountRepositories accountRepositories;
    @Autowired
    private ClientRepositories clientRepositories;

    public Account save(Account account) {
        return accountRepositories.save(account);
    }


        public void  creditoInicialConta(ClienteDAO clienteDAO, MovimentacaoContaDAO mCD, Conta novaConta) {
        BigDecimal valor = new BigDecimal(20000.0);
        transferencia(searchAccountStock(searchAccounts(clienteDAO)), novaConta, valor);
        String descricaoMov = "Boas Vindas de Abertura de Conta";
//        mCD.insereMovimentacao(searchAccountStock(searchAccounts(clienteDAO)), novaConta, valor, descricaoMov);
    }

    public boolean upgrade(Account account) {
        findById(account.getId());
        accountRepositories.save(account);
        return true;
    }

    public void mensalidade(MovimentacaoContaDAO mCD, ClienteDAO clienteDAO){
        Conta contaBolsa = searchAccountStock(searchAccounts(clienteDAO));
        BigDecimal valor = new BigDecimal(20.0);
        for (Conta conta : contascorrente) {
            if(conta != null){
                transferencia(conta, contaBolsa, valor);
                String descricaoMov = "Mensalidade da conta";
                mCD.insereMovimentacao(conta, contaBolsa, valor, descricaoMov);
            }
        }
    }

    public Account searchAccounts(Client client) {
        List<Account> accounts = findAll();


        for (Account account : accounts) {
            if (account.getClient().getId().equals(client.getId())) {
                return account;
            }
        }
        return null;

    }



    public Account findById(long id) {
        return accountRepositories.findById(id).orElse(null);
    }

    public List<Account> findAll() {
        return accountRepositories.findAll();
    }

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

    public Client customersearchbyAccountId(long id, Client client) {
        Account contas = searchAccounts(client);
        return contas.getClient();
    }

    public boolean validateId(long id) {
        return accountRepositories.existsById(id);
    }

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

    public boolean deposito(Conta destino, BigDecimal valor, MovimentacaoContaDAO mCD) {
        destino.entrada(valor);
        altera(destino);
        String descricaoMov = "Depósito";
        mCD.insereMovimentacao(null, destino, valor, descricaoMov);
        return true;
    }

    public boolean saque(Conta origem, BigDecimal valor, MovimentacaoContaDAO mCD) {
        if (origem.getSaldo().compareTo(valor) >= 0) {
            origem.retirada(valor);
            altera(origem);
            String descricaoMov = "Saque";
            mCD.insereMovimentacao(origem, null, valor, descricaoMov);
            return true;
        }
        return false;
    }

    public boolean pagamento(Conta origem, BigDecimal valor, MovimentacaoContaDAO mCD, ClienteDAO clienteDAO) {
        if (transferencia(origem, this.searchAccountStock(searchAccounts(clienteDAO)), valor)) {
            String descricaoMov = "Pagamento para Bolsa";
            mCD.insereMovimentacao(origem, this.searchAccountStock(searchAccounts(clienteDAO)), valor, descricaoMov);
            return true;
        }
        return false;

    }

    public boolean transfere(Conta origem, Conta destino, BigDecimal valor, MovimentacaoContaDAO mCD) {
        if (transferencia(origem, destino, valor)) {
            MovimentacaoConta m = new MovimentacaoConta();
            m.setValor(valor);
            m.setConta(origem);
            m.setTipMov(2);
            m.setDescricao("Transferencia entre contas");
            mCD.insere(m);
            m.setConta(destino);
            m.setTipMov(1);
            mCD.insere(m);

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
        if (transferencia(origem, destino, valor)) {
            String descricaoMov = "Pagamento de Dividendos";
            mCD.insereMovimentacao(origem, destino, valor, descricaoMov);

            return true;
        }
        return false;
    }

}
