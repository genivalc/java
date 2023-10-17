package com.genival.home.broker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_account")
public class Account {
    private static long serial;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    private boolean stockExchangeAccount;
    private BigDecimal balance = BigDecimal.ZERO;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateCreation;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateModification;


    public Account() {
        this.id = ++Account.serial;
        this.dateCreation = dateModification = LocalDateTime.now();
        //BigDecimal balance = new BigDecimal ("20000.00");
    }

    public static long getSerial() {
        return serial;
    }

    public static void setSerial(long serial) {
        Account.serial = serial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setClient(String client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setClient(int client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isStockExchangeAccount() {
        return stockExchangeAccount;
    }

    public void setStockExchangeAccount(boolean stockExchangeAccount) {
        this.stockExchangeAccount = stockExchangeAccount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public void valueEntry(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }

//    @Override
//    public String toString() {
//
//        return "-----------------------------------------------------------------------\n"
//                + "Identificacao (id): " + id + "\n"
//                + "Cliente           : " + client.getName() + "\n"
//                + "Saldo             : " + balance.toPlainString() + "\n"
//                + "Data Criacao      : " + dateCreation + "\n"
//                + "Data Modificacao  : " + dateModification + "\n";
//
//    }

    public void balanceWithdrawal(BigDecimal balance) {
        this.balance = this.balance.subtract(balance);
    }

    public String searchType(Integer type) {
        if (type == 1) {
            return "Administrador";
        } else if (type == 2) {
            return "Cliente";
        } else if (type == 3) {
            return "Cliente 2";
        } else {
            return null;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 71 * hash + Objects.hashCode(this.client);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.client, other.client);
    }

    public void setAddress(String address) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setCpf(int cpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
