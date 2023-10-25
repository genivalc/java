package com.genival.home.broker.services;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.Active;
import com.genival.home.broker.entities.ActiveAccount;
import com.genival.home.broker.entities.Client;
import com.genival.home.broker.repositories.ActiveAccountRepositories;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ActiveAccountServices {

    @Autowired
    private ActiveAccountRepositories activeAccountRepositories;
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private ActiveServices activeServices;


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

    @Transactional
    public void payDividends(String ticker, BigDecimal dividendPerShare, Client client) {
        List<ActiveAccount> carteira = activeAccountRepositories.findAll();

        for (ActiveAccount ativoConta : carteira) {
            if (!ativoConta.getAccount().equals(accountServices.searchAccountStock(accountServices.findAll()))) {
                if (ativoConta.getActive().getTicker().equals(ticker)) {
                    BigDecimal totalDividends = dividendPerShare.multiply(new BigDecimal(ativoConta.getTotalActive()));
                    accountServices.pagaDividendos(accountServices.searchAccountStock(accountServices.findAll()), ativoConta.getAccount(), totalDividends);
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public boolean isFull() {
        return activeAccountRepositories.count() == 0;
    }

    @Transactional(readOnly = true)
    public boolean isEmpty() {
        return activeAccountRepositories.count() == 0;
    }

    @Transactional(readOnly = true)
    public List<ActiveAccount> searchAlActiveAccount() {
        return activeAccountRepositories.findAll();
    }

    @Transactional(readOnly = true)
    public List<ActiveAccount> getAllActiveAccount(Client client) {
        List<ActiveAccount> allActiveAccounts = searchAlActiveAccount();

        return allActiveAccounts.stream().map(activeAccount -> {
            ActiveAccount ac = new ActiveAccount();
            ac.setId(activeAccount.getId());
            ac.setAccount(accountServices.searchAccounts(client));
            ac.setActive(activeServices.findById(ac.getActive().getId()));
            ac.setTotalActive(activeAccount.getTotalActive());
            ac.setValorCompra(activeAccount.getValorCompra());
            ac.setDateCreation(activeAccount.getDateCreation());
            ac.setDateModification(activeAccount.getDateModification());

            return ac;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ActiveAccount getAtivoContaById(Long id) {
        return activeAccountRepositories.findById(id).orElse(null);
    }

    @Transactional
    public boolean deleteAtivoConta(Long id) {
        activeAccountRepositories.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public StringBuilder showAllAtivoContas(Client cliente) {
        List<ActiveAccount> carteira = searchAlActiveAccount();

        StringBuilder sb = new StringBuilder();
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

    @Transactional(readOnly = true)
    public String getTickerById(Long id) {
        ActiveAccount activeAccount = getAtivoContaById(id);
        if (activeAccount != null) {
            return activeAccount.getActive().getTicker();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public int countAssets() {
        List<ActiveAccount> assetAccounts = searchAlActiveAccount();
        return assetAccounts.size();
    }

    @Transactional
    public void updatePortfolio(Active asset, Account buyerAccount, Account sellerAccount, int quantity) {
        ActiveAccount newBuyerAccount = createActiveAccount(asset, buyerAccount, quantity);
        ActiveAccount newSellerAccount = createActiveAccount(asset, sellerAccount, quantity);

        List<ActiveAccount> portfolio = searchAlActiveAccount();

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
