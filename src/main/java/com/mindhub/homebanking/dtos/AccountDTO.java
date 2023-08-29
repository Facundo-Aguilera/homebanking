package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;
    private String number;
    private LocalDate date;
    private Set<TransactionDTO> transactions;
    private Double balance;

    public AccountDTO(Account account){
        id = account.getId();
        number = account.getnumber();
        date = account.getDate();
        balance = account.getbalance();
        transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public LocalDate getDate() {
        return date;
    }
    public Double getBalance() {
        return balance;
    }
    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
