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
@Table(name = "tb_active")
public class Active {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String ticker;
    private Integer totalActive;
    private BigDecimal initialPrice;
    private BigDecimal currentPrice;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateCreation;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateModification;

    private Integer typeUser; // 1 for Administrator, 2 for Client, 3 for Client 2

    public Active() {
        // Initialize date fields and other necessary setup
        this.dateCreation = this.dateModification = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Integer getTotalActive() {
        return totalActive;
    }

    public void setTotalActive(Integer totalActive) {
        this.totalActive = totalActive;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
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

    public Integer getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(Integer typeUser) {
        this.typeUser = typeUser;
    }

    public boolean validateType(int id) {
        return id == 1 || id == 2;
    }

    public void subTotal(int qtd) {
        if (this.totalActive - qtd >= 0) {
            this.totalActive -= qtd;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Active other = (Active) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.companyName, other.companyName)) {
            return false;
        }
        return Objects.equals(this.ticker, other.ticker);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.companyName);
        hash = 97 * hash + Objects.hashCode(this.ticker);
        return hash;
    }

//    @Override
//    public String toString() {
//        return "-----------------------------------------------------------------------\n"
//                + "Identificacao (id): " + id + "\n"
//                + "Nome da Empresa   : " + companyName + "\n"
//                + "Ticker            : " + ticker + "\n"
//                + "Total de Ativos   : " + totalActive + "\n"
//                + "Preço Inicial     : " + initialPrice + "\n"
//                + "Preço Atual       : " + currentPrice + "\n"
//                + "Data Criacao      : " + dateCreation + "\n"
//                + "Data Modificacao  : " + dateModification + "\n";
//    }
}


