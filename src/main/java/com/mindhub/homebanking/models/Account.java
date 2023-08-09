package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    public Account() { }

    public Account(String number, LocalDate creationDate, Double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getnumber() {
        return number;
    }

    public void setnumber(String number) {
        this.number = number;
    }

    public LocalDate getcreationDate() {
        return creationDate;
    }

    public void setcreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Double getbalance() {
        return balance;
    }

    public void setbalance(Double balance) { this.balance = balance; }

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
