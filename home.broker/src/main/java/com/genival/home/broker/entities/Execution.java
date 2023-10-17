package com.genival.home.broker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_execution")
public class Execution {
    private static long serial;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ordem ordem;

    @ManyToOne
    private Account accountPurchase;

    @ManyToOne
    private Account accountSale;

    private Integer amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateCreation;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateModification;

    public Execution() {
        this.id = ++Execution.serial;
        this.dateCreation = dateModification = LocalDateTime.now();
    }

    public static long getSerial() {
        return serial;
    }

    public static void setSerial(long serial) {
        Execution.serial = serial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ordem getOrdem() {
        return ordem;
    }

    public void setOrdem(Ordem ordem) {
        this.ordem = ordem;
    }

    public Account getAccountPurchase() {
        return accountPurchase;
    }

    public void setAccountPurchase(Account accountPurchase) {
        this.accountPurchase = accountPurchase;
    }

    public Account getAccountSale() {
        return accountSale;
    }

    public void setAccountSale(Account accountSale) {
        this.accountSale = accountSale;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.ordem);
        hash = 67 * hash + this.amount;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Execution other = (Execution) obj;
        if (this.amount != other.amount) {
            return false;
        }
        return Objects.equals(this.ordem, other.ordem);
    }

}
