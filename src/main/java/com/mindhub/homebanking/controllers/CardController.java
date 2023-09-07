package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountController accountController;

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Card> createCard(Authentication authentication, @RequestParam CardColor cardColor, @RequestParam CardType cardType) {
        String cardNumber;
        do {
            int cardNumber1 = accountController.getRandomNumber(1000, 9999);
            int cardNumber2 = accountController.getRandomNumber(1000, 9999);
            int cardNumber3 = accountController.getRandomNumber(1000, 9999);
            int cardNumber4 = accountController.getRandomNumber(1000, 9999);
            cardNumber = cardNumber1 + " " + cardNumber2 + " " + cardNumber3 + " " + cardNumber4;
        } while (cardRepository.existsByNumber(cardNumber));
        int cardCvvNumber = accountController.getRandomNumber(100, 999);
        Client client = clientRepository.findByEmail(authentication.getName());
        long numberOfCards = cardRepository.countCardByClientAndTypeAndColor(client, cardType, cardColor);
        boolean existsCards = cardRepository.existsCardByClientAndTypeAndColor(client, cardType, cardColor);
        if (numberOfCards >= 3 || existsCards) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else {
            Card card = cardRepository.save(new Card(LocalDateTime.now(), LocalDateTime.now().plusYears(5), cardNumber, cardColor, cardType, cardCvvNumber, client.getFirstName() + " " + client.getLastName()));
            client.addCard(card);
            clientRepository.save(client);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @RequestMapping("/clients/current/cards")
    public Set<CardDTO> getCards(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).getCards()
                .stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }
}