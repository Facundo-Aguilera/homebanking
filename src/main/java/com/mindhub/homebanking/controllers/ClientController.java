package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountController accountController;

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Client> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password
            ) {
        String accountNumber;
        do {
            int accountNumber1 = accountController.getRandomNumber(10000000, 99999999);
            accountNumber = "VIN-" + accountNumber1;
        } while (accountRepository.existsByNumber(accountNumber));
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity("Name already in use", HttpStatus.FORBIDDEN);
        }
        Client client = clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        Account newAccount = accountRepository.save(new Account(accountNumber, LocalDate.now(), 0d));
        client.addAccount(newAccount);
        clientRepository.save(client);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        List<Client> listClient = clientRepository.findAll();

        List<ClientDTO> listClientDTO = listClient.stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());

        return listClientDTO;
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }

    @RequestMapping("/clients/current")
    public ClientDTO getCurrent(Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }

    }
