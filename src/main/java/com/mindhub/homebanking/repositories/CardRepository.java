package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    Integer countCardByClientAndTypeAndColor(Client client, CardType cardType, CardColor cardColor);
    Boolean existsCardByClientAndTypeAndColor(Client client, CardType cardtype, CardColor cardColor);
    Boolean existsByNumber(String number);
}