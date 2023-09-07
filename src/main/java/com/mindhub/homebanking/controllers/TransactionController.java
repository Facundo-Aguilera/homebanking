package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountController accountController;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Transaction> createTransaction(Authentication authentication, @RequestParam Double amount, @RequestParam String description, String fromAccountNumber, String toAccountNumber){
        Client client = clientRepository.findByEmail(authentication.getName());
        if (amount <= 0 || description.isBlank() || fromAccountNumber.isBlank() || toAccountNumber.isBlank()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (!accountRepository.existsByNumber(fromAccountNumber)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (!accountRepository.existsByNumberAndClient(fromAccountNumber, client)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (!accountRepository.existsByNumber(toAccountNumber)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (accountRepository.findByNumber(fromAccountNumber).getbalance() < amount){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -amount,"VIN " + fromAccountNumber + description, LocalDateTime.now());
            Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount,"VIN " + toAccountNumber + description, LocalDateTime.now());
            Double newBalanceFromAccount = accountRepository.findByNumber(fromAccountNumber).getbalance() - amount;
            Double newBalanceToAccount = accountRepository.findByNumber(toAccountNumber).getbalance() + amount;
            Account fromAccountAuth = accountRepository.findByNumber(fromAccountNumber);
            Account toAccount = accountRepository.findByNumber(toAccountNumber);
            fromAccountAuth.setbalance(newBalanceFromAccount);
            toAccount.setbalance(newBalanceToAccount);
            transactionRepository.save(debitTransaction);
            transactionRepository.save(creditTransaction);
            accountRepository.save(fromAccountAuth);
            accountRepository.save(toAccount);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}