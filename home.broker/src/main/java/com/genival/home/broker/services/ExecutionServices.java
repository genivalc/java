package com.genival.home.broker.services;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.Execution;
import com.genival.home.broker.entities.Ordem;
import com.genival.home.broker.repositories.ExecutionRepositories;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ExecutionServices {

    @Autowired
    private ExecutionRepositories executionRepositories;

    public boolean save(Execution element) {
        executionRepositories.save(element);
        return true;
    }

    @Transactional(readOnly = true)
    public Execution findById(Long id) {
        return executionRepositories.findById(id).orElse(null);
    }


    public boolean remove(Long id) {
        findById(id);
        executionRepositories.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Execution> findAll() {
        return executionRepositories.findAll();
    }

    @Transactional(readOnly = true)
    public boolean isEmpty() {
        return executionRepositories.count() == 0;
    }

    public boolean newExecution(Account buyer, Account seller, Ordem ordem, int salesQuantity) {
        Execution execution = new Execution();
        execution.setAccountPurchase(buyer);
        execution.setAccountSale(seller);
        execution.setOrdem(ordem);
        execution.setAmount(salesQuantity);
        return save(execution);
    }
}
