package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;

public class AccountDTO {

    private long id;
    private String number;
    private LocalDate date;
    private Double balance;

    public AccountDTO(Account account){
        id = account.getId();
        number = account.getnumber();
        date = account.getcreationDate();
        balance = account.getbalance();
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
}
