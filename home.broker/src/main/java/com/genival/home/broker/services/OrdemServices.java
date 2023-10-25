package com.genival.home.broker.services;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.Active;
import com.genival.home.broker.entities.Client;
import com.genival.home.broker.entities.Ordem;
import com.genival.home.broker.repositories.OrdemRepositories;
import com.genival.home.broker.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrdemServices {

    Util util = new Util();

    @Autowired
    private OrdemRepositories ordemRepositories;

    @Autowired
    private AccountServices accountServices;

    @Autowired
    private ActiveServices activeServices;

    @Autowired
    private ExecutionServices executionServices;

    @Autowired
    private ActiveAccountServices activeAccountServices;


    public boolean save(Ordem ordem) {
        ordemRepositories.save(ordem);
        return true;
    }

    @Transactional(readOnly = true)
    public Ordem findById(Long id) {
        return ordemRepositories.findById(id).orElse(null);
    }

    public boolean delete(Long id) {
        findById(id);
        ordemRepositories.deleteById(id);
        return true;
    }

    public boolean upgrade(Ordem ordem) {
        findById(ordem.getId());
        ordemRepositories.save(ordem);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Ordem> searchAll(Client client) {
        List<Ordem> allOrdem = ordemRepositories.findAll();
        List<Ordem> ordens = new ArrayList<>();

        for (Ordem ordems : allOrdem) {
            Ordem ordem = new Ordem();
            ordem.setId(ordems.getId());
            ordem.setAccount(accountServices.customersearchbyAccount(ordems.getAccount().getId(), client));
            ordem.setTypeOrdem(ordems.getTypeOrdem());
            ordem.setValue(ordems.getValue());
            ordem.setTicker(ordems.getTicker());
            ordem.setAmount(ordems.getAmount());
            ordem.setStatusOrdem(ordems.getStatusOrdem());
            ordem.setDateCreation(ordems.getDateCreation());
            ordem.setDateModification(ordems.getDateModification());
            ordens.add(ordem);
        }
        return ordens;
    }

    @Transactional(readOnly = true)
    public Ordem searchOrderByID(Long idOrdem, Client client) {
        List<Ordem> ordens = searchAll(client);

        for (int i = 0; i < ordens.size(); i++) {
            Ordem ordem = ordens.get(i);
            if (Objects.equals(ordem.getId(), idOrdem)) {
                return ordens.get(i);
            }

        }
        return null;
    }

    @Transactional(readOnly = true)
    public String showPurchaseOrders(Client client) {
        List<Ordem> ordens = searchAll(client);

        StringBuilder result = new StringBuilder();

        for (Ordem ordem : ordens) {
            if (ordem.getTypeOrdem() != 2 && ordem.getStatusOrdem() != 2) {
                result.append(ordem).append("\n");
            }
        }
        return result.toString();

    }

    @Transactional(readOnly = true)
    public StringBuilder showSalesOrders(Client client) {
        List<Ordem> ordens = searchAll(client);

        StringBuilder result = new StringBuilder();

        for (Ordem ordem : ordens) {
            if (ordem.getTypeOrdem() == 2 && ordem.getStatusOrdem() != 2) {
                result.append(ordem).append("\n");
            }
        }
        return result;

    }

    @Transactional(readOnly = true)
    public String searchTickerById(Long id, Client client) {
        List<Ordem> ordens = searchAll(client);
        for (Ordem ordem : ordens) {
            if (ordem != null && ordem.getId().equals(id)) {
                return ordem.getTicker();
            }
        }
        return null;
    }

    public StringBuilder executeOrders(Client client) {
        List<Ordem> ordens = searchAll(client);

        StringBuilder sb = new StringBuilder("\n\n");
        int qtdeOrdensTotais = 0;
        int qtdeOrdensParciais = 0;
        int qtdeOrdensNaoExecutadas = 0;
        int qtdeOrdensZero = 0;
        for (Ordem ordemVenda : ordens) {
            if (ordemVenda.getTypeOrdem() == 2 && ordemVenda.getStatusOrdem() != 2) {
                for (Ordem ordemCompra : ordens) {
                    if (ordemCompra.getTypeOrdem() != 2 && !ordemCompra.getAccount().equals(ordemVenda.getAccount()) && ordemCompra.getStatusOrdem() != 2) {
                        if (ordemCompra.getTicker().equals(ordemVenda.getTicker())) {
                            if (ordemVenda.getValue().compareTo(ordemCompra.getValue()) <= 0) {
                                Account cVendedor = ordemVenda.getAccount();
                                Account cComprador = ordemCompra.getAccount();
                                BigDecimal valorVenda = ordemVenda.getValue();
                                int qtdeVenda = ordemVenda.getAmount();
                                int qtdeCompra = ordemCompra.getAmount();
                                BigDecimal totalOperacao;
                                boolean ordem0 = false;
                                if (ordemCompra.getTypeOrdem() == 0 && Util.getLoggedClient().getQuantityOrder() <= 3) {
                                    totalOperacao = ordemVenda.getValueTotal().multiply(BigDecimal.valueOf(0.9));
                                    ordem0 = true;
                                } else {
                                    totalOperacao = ordemVenda.getValueTotal();
                                }

                                activeServices.changesPrice(ordemVenda.getTicker(), valorVenda);


                                if (qtdeVenda == qtdeCompra) {

                                    if (accountServices.transfere(cComprador, cVendedor, totalOperacao)) {

                                        ordemVenda.setStatusOrdem(2);
                                        ordemCompra.setStatusOrdem(2);
                                        upgrade(ordemVenda);
                                        upgrade(ordemCompra);

                                        executionServices.newExecution(cComprador, cVendedor, ordemVenda, qtdeVenda);

                                        executionServices.newExecution(cComprador, cVendedor, ordemCompra, qtdeCompra);


                                        Active ativo = activeServices.busca(ordemCompra.getTicker());
                                        activeAccountServices.updatePortfolio(ativo, cComprador, cVendedor, qtdeCompra);


                                        qtdeOrdensTotais += 2;
                                        if (ordem0) {
                                            qtdeOrdensZero++;
                                        }

                                    } else {
                                        qtdeOrdensNaoExecutadas++;
                                    }


                                } else if (qtdeVenda > qtdeCompra) {

                                    if (ordemCompra.getTypeOrdem() == 0 && Util.getLoggedClient().getQuantityOrder() <= 3) {
                                        totalOperacao = ordemVenda.getValue().multiply(BigDecimal.valueOf(qtdeCompra)).multiply(BigDecimal.valueOf(0.9));
                                    } else {
                                        totalOperacao = ordemVenda.getValue().multiply(BigDecimal.valueOf(qtdeCompra));
                                    }

                                    if (accountServices.transfere(cComprador, cVendedor, totalOperacao)) {

                                        ordemVenda.setStatusOrdem(1);
                                        ordemCompra.setStatusOrdem(2);
                                        upgrade(ordemVenda);
                                        upgrade(ordemCompra);

                                        executionServices.newExecution(cComprador, cVendedor, ordemVenda, qtdeCompra);

                                        executionServices.newExecution(cComprador, cVendedor, ordemCompra, qtdeCompra);

                                        Active ativo = activeServices.busca(ordemCompra.getTicker());
                                        activeAccountServices.updatePortfolio(ativo, cComprador, cVendedor, qtdeCompra);

                                        ordemVenda.setAmount(ordemVenda.getAmount() - qtdeCompra);
                                        upgrade(ordemVenda);

                                        qtdeOrdensTotais++;
                                        qtdeOrdensParciais++;
                                        if (ordem0) {
                                            qtdeOrdensZero++;
                                        }

                                    } else {
                                        qtdeOrdensNaoExecutadas++;
                                    }
                                } else {

                                    if (accountServices.transfere(cComprador, cVendedor, totalOperacao)) {

                                        ordemVenda.setStatusOrdem(2);
                                        ordemCompra.setStatusOrdem(1);
                                        upgrade(ordemVenda);
                                        upgrade(ordemCompra);

                                        executionServices.newExecution(cComprador, cVendedor, ordemVenda, qtdeVenda);

                                        executionServices.newExecution(cComprador, cVendedor, ordemCompra, qtdeVenda);

                                        Active ativo = activeServices.busca(ordemCompra.getTicker());
                                        activeAccountServices.updatePortfolio(ativo, cComprador, cVendedor, qtdeVenda);

                                        ordemCompra.setAmount(ordemCompra.getAmount() - qtdeVenda);
                                        upgrade(ordemCompra);

                                        qtdeOrdensTotais++;
                                        qtdeOrdensParciais++;
                                        if (ordem0) {
                                            qtdeOrdensZero++;
                                        }

                                    } else {
                                        qtdeOrdensNaoExecutadas++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        sb.append("\n __________R E L A T Ó R I O   D E   E X E C U Ç Õ E S ___________");
        sb.append("\n Quantidade de Ordens totalmente executadas              : ").append(qtdeOrdensTotais);
        sb.append("\n Quantidade de Ordens parcialmente executadas            : ").append(qtdeOrdensParciais);
        sb.append("\n Quantidade de Ordens não executadas por falta de saldo  : ").append(qtdeOrdensNaoExecutadas);
        sb.append("\n Quantidade de Ordens Zero                               : ").append(qtdeOrdensZero);

        return sb;
    }
}