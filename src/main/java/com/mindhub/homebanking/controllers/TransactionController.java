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
    public ResponseEntity<String> createTransaction(Authentication authentication, @RequestParam Double amount, @RequestParam String description, String fromAccountNumber, String toAccountNumber){
        Client client = clientRepository.findByEmail(authentication.getName());

        if (amount <= 0){
            return new ResponseEntity<>("Por favor, ingrese el monto a transferir.", HttpStatus.FORBIDDEN);
        }
        if (description.isBlank()){
            return new ResponseEntity<>("Por favor, ingrese una descripción para la transferencia.", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.isBlank()){
            return new ResponseEntity<>("Por favor, ingrese la cuenta de origen.", HttpStatus.FORBIDDEN);
        }
        if (toAccountNumber.isBlank()){
            return new ResponseEntity<>("Por favor, ingrese la cuenta de destino.", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("La cuenta de origen no puede ser igual a la de destino.", HttpStatus.FORBIDDEN);
        }
        if (!accountRepository.existsByNumber(fromAccountNumber)){
            return new ResponseEntity<>("La cuenta de origen no existe.", HttpStatus.FORBIDDEN);
        }

        Account fromAccountAuth = accountRepository.findByNumber(fromAccountNumber);

        if (!accountRepository.existsByNumber(toAccountNumber)){
            return new ResponseEntity<>("La cuenta de destino no existe.", HttpStatus.FORBIDDEN);
        }

        Account toAccount = accountRepository.findByNumber(toAccountNumber);

        if (fromAccountAuth.getbalance() < amount){
            return new ResponseEntity<>("El monto a transferir supera los fondos de la cuenta.", HttpStatus.FORBIDDEN);
        } else {
            Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -amount,"VIN " + fromAccountNumber + description, LocalDateTime.now());
            Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount,"VIN " + toAccountNumber + description, LocalDateTime.now());
            Double newBalanceFromAccount = fromAccountAuth.getbalance() - amount;
            Double newBalanceToAccount = toAccount.getbalance() + amount;
            fromAccountAuth.setbalance(newBalanceFromAccount);
            toAccount.setbalance(newBalanceToAccount);
            fromAccountAuth.addTransaction(debitTransaction);
            toAccount.addTransaction(creditTransaction);
            transactionRepository.save(debitTransaction);
            transactionRepository.save(creditTransaction);
            accountRepository.save(fromAccountAuth);
            accountRepository.save(toAccount);
            return new ResponseEntity<>("Transferencia exitosa!",HttpStatus.CREATED);
        }
    }
}