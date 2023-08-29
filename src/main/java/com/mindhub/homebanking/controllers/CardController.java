package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

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
        int cardNumber = accountController.getRandomNumber(1000, 9999);
        int cardCvvNumber = accountController.getRandomNumber(100, 999);
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getCards().size() >= 3) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            Card card = cardRepository.save(new Card(LocalDateTime.now(), LocalDateTime.now().plusYears(5), cardNumber + " " + cardNumber + " " + cardNumber + " " + cardNumber, cardColor, cardType, cardCvvNumber, client.getFirstName() + " " + client.getLastName()));
            client.addCard(card);
            clientRepository.save(client);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}