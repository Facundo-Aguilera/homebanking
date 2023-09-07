package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class CardDTO {
    private long id;
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;
    private String number;
    private CardColor color;
    private CardType type;
    private Integer cvv;
    private String cardHolder;

    public CardDTO(Card card){
        id = card.getId();
        fromDate = card.getFromDate();
        thruDate = card.getThruDate();
        number = card.getNumber();
        color = card.getColor();
        type = card.getType();
        cvv = card.getCvv();
        cardHolder = card.getCardHolder();
    }

    public long getId() {
        return id;
    }
    public LocalDateTime getFromDate() {
        return fromDate;
    }
    public LocalDateTime getThruDate() {
        return thruDate;
    }
    public String getNumber() {
        return number;
    }
    public CardColor getColor() {
        return color;
    }
    public CardType getType() {
        return type;
    }
    public Integer getCvv() {
        return cvv;
    }
    public String getCardHolder() {
        return cardHolder;
    }
}
