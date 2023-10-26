package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired ClientLoanRepository clientLoanRepository;

    @RequestMapping("/loans")
    public List<LoanDTO> getLoans(){
        List<Loan> listLoans = loanRepository.findAll();
        return listLoans.stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(path = "/loans", method = RequestMethod.POST)
    public ResponseEntity<Loan> createLoan (Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Long idLoan = loanApplicationDTO.getLoanId();
        Loan loan = loanRepository.findLoanById(idLoan);
        Account account = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());
        Double loanApplication = loanApplicationDTO.getAmount() * 1.20;
        if (loanApplicationDTO.getLoanId() == null || loanApplicationDTO.getPayments() <= 0 || loanApplicationDTO.getAmount() <= 0) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (!loanRepository.existsByName(loan.getName())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (!loan.getPayments().stream().anyMatch(payment -> payment.equals(loanApplicationDTO.getPayments()))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (!accountRepository.existsByNumber(loanApplicationDTO.getToAccountNumber())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else
        {
            Transaction creditTransaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(),loan.getName() +" loan approved", LocalDateTime.now());
            Double newBalanceToAccount = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber()).getbalance() + loanApplicationDTO.getAmount();
            Account toAccountNumber = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());
            toAccountNumber.setbalance(newBalanceToAccount);
            ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount(), loanApplicationDTO.getPayments());
            account.addTransaction(creditTransaction);
            client.addLoan(clientLoan);
            loan.addLoan(clientLoan);
            transactionRepository.save(creditTransaction);
            clientLoanRepository.save(clientLoan);
            accountRepository.save(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
