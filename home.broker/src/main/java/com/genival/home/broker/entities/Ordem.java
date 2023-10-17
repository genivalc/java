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
@Table(name = "tb_ordem")
public class Ordem {
    private static long serial;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    private Integer typeOrdem;    //0 ordem 0,       1 compra,  2 venda
    private Integer statusOrdem;  //0 nao executada, 1 parcial, 2 completa
    private String ticker;
    private Integer amount;
    @Column(name = "ordem_value", nullable = false, unique = false)
    private BigDecimal value;
    private BigDecimal valueTotal;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateCreation;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateModification;

    public Ordem() {
        this.id = ++Ordem.serial;
        this.dateCreation = dateModification = LocalDateTime.now();
    }

    public static long getSerial() {
        return serial;
    }

    public static void setSerial(long serial) {
        Ordem.serial = serial;
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

    public Integer getTypeOrdem() {
        return typeOrdem;
    }

    public void setTypeOrdem(Integer typeOrdem) {
        this.typeOrdem = typeOrdem;
    }

    public Integer getStatusOrdem() {
        return statusOrdem;
    }

    public void setStatusOrdem(Integer statusOrdem) {
        this.statusOrdem = statusOrdem;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValueTotal() {
        return valueTotal;
    }

    public void setValueTotal(BigDecimal valueTotal) {
        this.valueTotal = valueTotal.multiply(new BigDecimal(amount));
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

    public String buscaTipo(int tipo) {
        if (tipo == 0) {
            return "Ordem 0";
        } else if (tipo == 1) {
            return "Compra";
        } else if (tipo == 2) {
            return "Venda";
        } else {
            return null;
        }
    }

    public String searchState(Integer statusOrdem) {
        if (statusOrdem == 0) {
            return "Não Executada";
        } else if (statusOrdem == 1) {
            return "Execução parcial";
        } else if (statusOrdem == 2) {
            return "Execução completa";
        } else {
            return null;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 73 * hash + Objects.hashCode(this.account);
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
        final Ordem other = (Ordem) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.account, other.account);
    }
}
