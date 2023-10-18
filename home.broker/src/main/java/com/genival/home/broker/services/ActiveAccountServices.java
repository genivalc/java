package com.genival.home.broker.services;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.Active;
import com.genival.home.broker.entities.ActiveAccount;
import com.genival.home.broker.entities.Client;
import com.genival.home.broker.repositories.ActiveAccountRepositories;
import com.genival.home.broker.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ActiveAccountServices {
    @Autowired
    private ActiveAccountRepositories activeAccountRepositories;

    Util util = new Util();

//    final int nAtivoConta = 100;
//    private final AtivoConta[] relacao = new AtivoConta[nAtivoConta];
//    private BigDecimal valorCompra;

    @Transactional
    public boolean addActiveAccount(ActiveAccount activeAccount) {
        activeAccount.setDateCreation(LocalDateTime.now());
        activeAccount.setDateModification(LocalDateTime.now());
        activeAccountRepositories.save(activeAccount);
        return true;
    }

    @Transactional
    public boolean updateActiveAccount(ActiveAccount activeAccount) {
        activeAccount.setDateModification(LocalDateTime.now());
        activeAccountRepositories.save(activeAccount);
        return true;
    }

//    @Transactional
//    public void payDividends(String ticker, BigDecimal dividendPerShare) {
//        List<AtivoConta> carteira = ativoContaRepository.findAll();
//
//        for (AtivoConta ativoConta : carteira) {
//            if (!ativoConta.getConta().equals(contaRepository.findBolsaConta(contaRepository.findAll()))) {
//                if (ativoConta.getAtivo().getTicker().equals(ticker)) {
//                    BigDecimal totalDividends = dividendPerShare.multiply(new BigDecimal(ativoConta.getTotalAtivos()));
//                    contaRepository.payDividends(contaRepository.findBolsaConta(contaRepository.findAll()), ativoConta.getConta(), totalDividends);
//                }
//            }
//        }
//    }

    public boolean isFull() {
        return activeAccountRepositories.count() == 0;
    }

    public boolean isEmpty() {
        return activeAccountRepositories.count() == 0;
    }

    public List<ActiveAccount> searchAlActiveAccount() {
        return activeAccountRepositories.findAll();
    }

    public List<ActiveAccount> getAllAtivoContas(Account account, Active active) {
        return activeAccountRepositories.findAllByContaAndAtivo(account, active);
    }

    public List<ActiveAccount> getAllActiveAccount(ContaDAO contaDAO, AtivoDAO ativoDAO, ClienteDAO clienteDAO) {
        List<ActiveAccount> alActiveAccount  =  searchAlActiveAccount();

       alActiveAccount.stream().map(rs -> {
                   Long id = rs.getId();
                   Long idConta = rs.get;
                   Long idAtivo = rs.getLong("ativo");
                   int totalAtivos = rs.getInt("totalAtivos");
                   BigDecimal valorCompra = new BigDecimal(rs.getFloat("valorCompra"));
                   LocalDateTime dataCriacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataCriacao"));
                   LocalDateTime dataModificacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataModificacao"));

                   AtivoConta ac = new AtivoConta();
                   ac.setId(id);
                   ac.setConta(contaDAO.buscaContaPorId(idConta, clienteDAO));
                   ac.setAtivo(ativoDAO.buscaAtivoPorId(idAtivo));
                   ac.setTotalAtivos(totalAtivos);
                   ac.setValorCompra(valorCompra);
                   ac.setDataCriacao(dataCriacao);
                   ac.setDataModificacao(dataModificacao);

                   return ac;
               })
               .collect(Collectors.toList());
    }

    public ActiveAccount getAtivoContaById(Long id) {
        return activeAccountRepositories.findById(id).orElse(null);
    }

    @Transactional
    public boolean deleteAtivoConta(Long id) {
        activeAccountRepositories.deleteById(id);
        return true;
    }

    public StringBuilder showAllAtivoContas(Client cliente) {
        List<ActiveAccount> carteira = searchAlActiveAccount();

        StringBuilder sb = new StringBuilder("");
        boolean headerPrinted = false;
        BigDecimal totalAssets = BigDecimal.ZERO;
        BigDecimal totalAssetsInitialPrice = BigDecimal.ZERO;
        BigDecimal totalAssetsPurchasePrice = BigDecimal.ZERO;

        for (ActiveAccount activeAccount : carteira) {
            if (cliente.equals(activeAccount.getAccount().getClient())) {
                if (!headerPrinted) {
                    sb.append(activeAccount.toStringHeader());
                    headerPrinted = true;
                }
                sb.append(activeAccount);
                BigDecimal totalAssetValue = activeAccount.getActive().getCurrentPrice().multiply(new BigDecimal(activeAccount.getTotalActive()));
                BigDecimal totalAssetInitialPrice = activeAccount.getActive().getInitialPrice().multiply(new BigDecimal(activeAccount.getTotalActive()));
                BigDecimal totalAssetPurchasePrice = activeAccount.getValorCompra().multiply(new BigDecimal(activeAccount.getTotalActive()));
                totalAssets = totalAssets.add(totalAssetValue);
                totalAssetsInitialPrice = totalAssetsInitialPrice.add(totalAssetInitialPrice);
                totalAssetsPurchasePrice = totalAssetsPurchasePrice.add(totalAssetPurchasePrice);
            }
        }

        BigDecimal profitOrLoss = totalAssets.subtract(totalAssetsPurchasePrice);
        BigDecimal percentage = calculatePercentage(totalAssets, totalAssetsPurchasePrice);
        sb.append("\n Total value in assets: $").append(totalAssets);
        sb.append("\n Total profit/loss (purchase price) is: $").append(profitOrLoss).append(" which corresponds to ").append(percentage).append("%\n");

        profitOrLoss = totalAssets.subtract(totalAssetsInitialPrice);
        percentage = calculatePercentage(totalAssets, totalAssetsInitialPrice);
        sb.append("\n Total profit/loss (initial IPO price) is: $").append(profitOrLoss).append(" which corresponds to ").append(percentage).append("%\n");

        return sb;
    }

    private BigDecimal calculatePercentage(BigDecimal totalAssets, BigDecimal totalAssetsInitialPrice) {
        BigDecimal divisionCurrentByInitial = totalAssets.divide(totalAssetsInitialPrice, 2, RoundingMode.CEILING);
        BigDecimal divisionMinusOne = divisionCurrentByInitial.subtract(BigDecimal.ONE);
        return divisionMinusOne.multiply(new BigDecimal(100));
    }

    public String getTickerById(Long id) {
        ActiveAccount activeAccount = getAtivoContaById(id);
        if (activeAccount != null) {
            return activeAccount.getActive().getTicker();
        }
        return null;
    }

    public int countAssets(Account currentAccount, Active targetAsset) {
        List<ActiveAccount> assetAccounts = getAllAtivoContas(currentAccount, targetAsset);
        return assetAccounts.size();
    }

    @Transactional
    public void updatePortfolio(Active asset, Account buyerAccount, Account sellerAccount, int quantity) {
        ActiveAccount newBuyerAccount = createActiveAccount(asset, buyerAccount, quantity);
        ActiveAccount newSellerAccount = createActiveAccount(asset, sellerAccount, quantity);

        List<ActiveAccount> portfolio = getAllAtivoContas();

        boolean found = false;

        for (ActiveAccount activeAccount : portfolio) {
            if (activeAccount.equals(newBuyerAccount)) {
                activeAccount.setTotalActive(activeAccount.getTotalActive() + quantity);
                updateActiveAccount(activeAccount);
                found = true;
            }
        }

        if (!found) {
            addActiveAccount(newBuyerAccount);
        }

        found = false;

        for (ActiveAccount activeAccount : portfolio) {
            if (activeAccount.equals(newSellerAccount) && activeAccount.getTotalActive() > quantity) {
                activeAccount.setTotalActive(activeAccount.getTotalActive() - quantity);
                updateActiveAccount(activeAccount);
                found = true;
            }
        }

        if (!found) {
            deleteAtivoConta(newSellerAccount.getId());
        }
    }

    private ActiveAccount createActiveAccount(Active asset, Account account, int quantity) {
        ActiveAccount activeAccount = new ActiveAccount();
        activeAccount.setActive(asset);
        activeAccount.setAccount(account);
        activeAccount.setTotalActive(quantity);
        activeAccount.setValorCompra(asset.getCurrentPrice());
        activeAccount.setDateCreation(LocalDateTime.now());
        activeAccount.setDateModification(LocalDateTime.now());
        return activeAccount;
    }
}