package com.genival.home.broker.entities;

import com.genival.home.broker.utils.Util;
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
@Table(name = "tb_activeAccount")
public class ActiveAccount {

    private static long serial;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Account account;

    @ManyToOne
    private Active active;

    private Integer totalActive;
    private BigDecimal valorCompra;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateCreation;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateModification;

    public ActiveAccount() {
        this.id = ++ActiveAccount.serial;
        this.dateCreation = this.dateModification = LocalDateTime.now();
    }

    public static long getSerial() {
        return serial;
    }

    public static void setSerial(long serial) {
        ActiveAccount.serial = serial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Active getActive() {
        return active;
    }

    public void setActive(Active active) {
        this.active = active;
    }

    public Integer getTotalActive() {
        return totalActive;
    }

    public void setTotalActive(Integer totalActive) {
        this.totalActive = totalActive;
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
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
        final ActiveAccount other = (ActiveAccount) obj;
        if (!Objects.equals(this.account, other.account)) {
            return false;
        }
        return Objects.equals(this.active, other.active);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.account);
        hash = 53 * hash + Objects.hashCode(this.active);
        return hash;
    }

    public String columnText(String text, int size) {
        if (text.length() > size) {
            return text.substring(0, size);
        } else if (text.length() < size) {
            return String.format("%-" + size + "s", text);
        } else {
            return text;
        }
    }

    @Override
    public String toString() {

        return
                "| " + columnText("" + id,  3)
                        + " | " + columnText( account.getClient().getName(), 12)
                        + " | " + columnText( active.getTicker() , 6)
                        + " | " + columnText("" + getTotalActive(), 5)
                        + " | " + columnText("" + this.valorCompra.multiply(new BigDecimal(getTotalActive())), 11)
                        + " | " + columnText("" + active.getCurrentPrice(), 13);
//                        + " | " + columnText(getDateCreation(), 12) + " |\n";
    }

    public String toStringHeader() {
        return

                "| " + columnText("ID",  3)
                        + " | " + columnText("Cliente", 12)
                        + " | " + columnText("Ativo", 6)
                        + " | " + columnText("Total", 5)
                        + " | " + columnText("Valor Total", 11)
                        + " | " + columnText("Cotacao Atual", 13)
                        + " | " + columnText("Data Criação", 12) + " |\n"
                        + "-------------------------------------------------------------------------\n";
    }
}
