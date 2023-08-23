package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private Long id;

    private Long idLoan;

    private String name;

    private Double amount;

    private Integer payments;

    public ClientLoanDTO(ClientLoan clientLoan) {
        id = clientLoan.getId();
        amount = clientLoan.getAmount();
        payments = clientLoan.getPayments();
        idLoan = clientLoan.getLoan().getId();
        name = clientLoan.getLoan().getName();
    }

    public long getId() {
        return id;
    }

    public long getIdLoan() {
        return idLoan;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }
}
