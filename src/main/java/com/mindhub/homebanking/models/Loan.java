package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private Double maxAmount;
    @ElementCollection
    private List<Integer> payments;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "loan")
    private Set<ClientLoan> clientLoans = new HashSet<>();;

    public Loan() { }
    public Loan(String name, Double maxAcount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAcount;
        this.payments = payments;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getMaxAmount() {
        return maxAmount;
    }
    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }
    public List<Integer> getPayments() {
        return payments;
    }
    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public List<Client> getClient(){
        return clientLoans.stream().map(element -> element.getClient()).collect(Collectors.toList());
    }

    public void addClient(ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        this.clientLoans.add(clientLoan);
    }
}
