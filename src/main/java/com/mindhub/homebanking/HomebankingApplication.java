package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,
									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository) {
		return (args) -> {

			// Crear clientes
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
			Client client2 = new Client("Sebastian", "Schettino", "sschettino@gmail.com");
			Client client3 = new Client("Nestor", "Garcia", "ngarcia@gmail.com");

			// Guardar clientes en la base de datos
			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);

			// Crear cuentas
			Account account1 = new Account();
			account1.setbalance(5000.0);
			account1.setnumber("VIN001");
			account1.setcreationDate(LocalDate.now());

			Account account2 = new Account();
			account2.setbalance(7500.0);
			account2.setnumber("VIN002");
			account2.setcreationDate(LocalDate.now().plusDays(1));

			Account account3 = new Account();
			account3.setbalance(344000.0);
			account3.setnumber("VIN003");
			account3.setcreationDate(LocalDate.now().minusDays(2));

			Account account4 = new Account();
			account4.setbalance(7500.0);
			account4.setnumber("VIN004");
			account4.setcreationDate(LocalDate.now().plusDays(5));

			// Agregar cuentas a clientes
			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client3.addAccount(account4);

			// Guardar cuentas en la base de datos
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			// Crear transacciones
			Transaction transaction1 = new Transaction();
			transaction1.setType(TransactionType.CREDIT);
			transaction1.setAmount(400.0);
			transaction1.setDescription("Pago - Clase de guitarra");
			transaction1.setDate(LocalDateTime.now());

			Transaction transaction2 = new Transaction();
			transaction2.setType(TransactionType.DEBIT);
			transaction2.setAmount(-200.0);
			transaction2.setDescription("Kiosco 24hs");
			transaction2.setDate(LocalDateTime.now());

			Transaction transaction3 = new Transaction();
			transaction3.setType(TransactionType.CREDIT);
			transaction3.setAmount(500.000);
			transaction3.setDescription("Pago de haberes");
			transaction3.setDate(LocalDateTime.now());

			Transaction transaction4 = new Transaction();
			transaction4.setType(TransactionType.CREDIT);
			transaction4.setAmount(6700.0);
			transaction4.setDescription("Transferencia de terceros");
			transaction4.setDate(LocalDateTime.now());

			Transaction transaction5 = new Transaction();
			transaction5.setType(TransactionType.DEBIT);
			transaction5.setAmount(-22200.0);
			transaction5.setDescription("Débito - VISA");
			transaction5.setDate(LocalDateTime.now());

			Transaction transaction6 = new Transaction();
			transaction6.setType(TransactionType.CREDIT);
			transaction6.setAmount(1200.0);
			transaction6.setDescription("Transferencia de terceros");
			transaction6.setDate(LocalDateTime.now());

			// Agregar transacciones a las cuentas
			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account1.addTransaction(transaction3);
			account2.addTransaction(transaction4);
			account3.addTransaction(transaction5);
			account4.addTransaction(transaction6);

			// Guardar transferencias en la base de datos
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);

			// Crear prestamos
			Loan loan1 = new Loan("Mortgage", 500.000, List.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal", 500.000, List.of(6,12,24));
			Loan loan3 = new Loan("Automotive", 500.000, List.of(6,12,24,36));

			//Guardar prestamos
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			//Crear prestamos del cliente
			ClientLoan clientLoan = new ClientLoan(400000d, 60, loan1, client1);
			ClientLoan clientLoan2 = new ClientLoan(50000d, 12, loan2, client1);

			ClientLoan clientLoan3 = new ClientLoan(100000d, 24, loan2, client2);
			ClientLoan clientLoan4 = new ClientLoan(200000d, 36, loan3, client2);

			//Guardar prestamos de clientes
			clientLoanRepository.save(clientLoan);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);
		};
}
}