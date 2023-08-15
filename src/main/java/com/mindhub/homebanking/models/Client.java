package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<ClientLoan> clientLoans = new HashSet<>();
    public Client() { }

    public Client(String first, String last, String email) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public long getId() {
        return id;
    }

    public void addAccount(Account account){
        account.setClient(this);
        this.accounts.add(account);
    }

    public List<ClientLoan> getLoans() {
        return new ArrayList<>(clientLoans);
    }

    public void addLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        this.clientLoans.add(clientLoan);

    }
}

