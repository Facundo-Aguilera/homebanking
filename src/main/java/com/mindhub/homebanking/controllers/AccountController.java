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
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        List<Account> ListAccount = accountRepository.findAll();
        List<AccountDTO> ListAccountDTO = ListAccount.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        return ListAccountDTO;
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Account> createAccount(Authentication authentication) {
            int accountNumber = getRandomNumber(10000000, 99999999);
            Client client = clientRepository.findByEmail(authentication.getName());
            if (client.getAccounts().size() >= 3) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                Account account = accountRepository.save(new Account("VIN-" + accountNumber, LocalDate.now(), 0d));
                client.addAccount(account);
                clientRepository.save(client);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
    }

    @RequestMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccounts(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).getAccounts()
                .stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }
}