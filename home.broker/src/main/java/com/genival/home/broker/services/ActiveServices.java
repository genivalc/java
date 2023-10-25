package com.genival.home.broker.services;


import com.genival.home.broker.entities.Active;
import com.genival.home.broker.repositories.ActiveRepositories;
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
public class ActiveServices {

    @Autowired
    private ActiveRepositories activeRepositories;

    public Active save(Active active) {
        return activeRepositories.save(active);
    }

    @Transactional(readOnly = true)
    public boolean isEmpty() {
        return activeRepositories.count() == 0;
    }

    @Transactional(readOnly = true)
    public List<Active> findAll() {
        return activeRepositories.findAll();
    }

    @Transactional(readOnly = true)
    public void printList() {
        List<Active> actives = findAll();
        for (Active active : actives) {
            System.out.println(active);
        }
    }

    public boolean remover(Long id) {
        Active actives = findById(id);
        activeRepositories.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public Active findById(Long id) {
        return activeRepositories.findById(id).orElse(null);
    }

    public void changesPrice(String ticker, BigDecimal valor) {
        List<Active> actives = findAll();
        for (Active active : actives) {
            if (active.getTicker().equals(ticker)) {
                active.setCurrentPrice(valor);
            }
        }
    }

    @Transactional(readOnly = true)
    public Active busca(String ticker) {
        List<Active> actives = findAll();
        for (Active active : actives) {
            if (active.getTicker().equals(ticker)) {
                return active;
            }
        }
        return null;
    }

    public Active upgrade(Active elemento) {
        findById(elemento.getId());
        return activeRepositories.save(elemento);
    }

    @Transactional(readOnly = true)
    public boolean hasActive(Active a) {
        List<Active> actives = findAll();
        for (Active ative : actives) {
            if (ative != null && ative.equals(a)) {
                return true;
            }
        }
        return false;
    }
}
