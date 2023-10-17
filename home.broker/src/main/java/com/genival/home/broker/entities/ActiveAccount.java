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

    //ajeitar
//    @Override
//    public String toString() {
//
//        return
//                "| " + util.textoColuna("" + id,  3)
//                        + " | " + util.textoColuna(conta.getCliente().getNome(), 12)
//                        + " | " + util.textoColuna(ativo.getTicker(), 6)
//                        + " | " + util.textoColuna("" + totalAtivos, 5)
//                        + " | " + util.textoColuna("" + this.valorCompra.multiply(new BigDecimal(totalAtivos)), 11)
//                        + " | " + util.textoColuna("" + ativo.getPrecoAtual(), 13)
//                        + " | " + util.textoColuna(util.FormataData(dataCriacao), 12) + " |\n";
//    }
//
//    public String toStringHeader() {
//        return
//
//                "| " + util.textoColuna("ID",  3)
//                        + " | " + util.textoColuna("Cliente", 12)
//                        + " | " + util.textoColuna("Ativo", 6)
//                        + " | " + util.textoColuna("Total", 5)
//                        + " | " + util.textoColuna("Valor Total", 11)
//                        + " | " + util.textoColuna("Cotacao Atual", 13)
//                        + " | " + util.textoColuna("Data Criação", 12) + " |\n"
//                        + "-------------------------------------------------------------------------\n";
//    }
}
