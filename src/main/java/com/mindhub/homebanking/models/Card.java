package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;
    private String number;
    private CardColor color;
    private CardType type;
    private Integer cvv;
    private String cardHolder;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    public Card(){ }
    public Card(LocalDateTime fromDate, LocalDateTime thruDate, String number, CardColor color, CardType type,
                Integer cvv, String cardHolder){
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.number = number;
        this.color = color;
        this.type = type;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
    }

    public long getId() {
        return id;
    }
    public LocalDateTime getFromDate() {
        return fromDate;
    }
    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }
    public LocalDateTime getThruDate() {
        return thruDate;
    }
    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public CardColor getColor() {
        return color;
    }
    public void setColor(CardColor color) {
        this.color = color;
    }
    public CardType getType() {
        return type;
    }
    public void setType(CardType type) {
        this.type = type;
    }
    public Integer getCvv() {
        return cvv;
    }
    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }
    public String getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
}
