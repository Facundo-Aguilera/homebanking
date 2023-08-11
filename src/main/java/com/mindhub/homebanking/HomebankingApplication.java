package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
		return (args) -> {

			// Crear al clientes
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
			Client client2 = new Client("Sebastian", "Schettino", "sschettino@gmail.com");
			Client client3 = new Client("Nestor", "Garcia", "ngarcia@gmail.com");

			// Guardar en base de datos al cliente
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

			// Agregar la cuenta al cliente
			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client3.addAccount(account4);

			//Guardar la cuenta en la base de datos
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			// Crear transacciones
			Transaction transaction1 = new Transaction();
			transaction1.setType(TransactionType.CREDITO);
			transaction1.setAmount(400.0);
			transaction1.setDescription("Pago - Clase de guitarra");
			transaction1.setDate(LocalDateTime.now());

			Transaction transaction2 = new Transaction();
			transaction2.setType(TransactionType.DEBITO);
			transaction2.setAmount(-200.0);
			transaction2.setDescription("Kiosco 24hs");
			transaction2.setDate(LocalDateTime.now());

			Transaction transaction3 = new Transaction();
			transaction3.setType(TransactionType.CREDITO);
			transaction3.setAmount(500.000);
			transaction3.setDescription("Pago de haberes");
			transaction3.setDate(LocalDateTime.now());

			Transaction transaction4 = new Transaction();
			transaction4.setType(TransactionType.CREDITO);
			transaction4.setAmount(6700.0);
			transaction4.setDescription("Transferencia de terceros");
			transaction4.setDate(LocalDateTime.now());

			Transaction transaction5 = new Transaction();
			transaction5.setType(TransactionType.DEBITO);
			transaction5.setAmount(-22200.0);
			transaction5.setDescription("Débito - VISA");
			transaction5.setDate(LocalDateTime.now());

			Transaction transaction6 = new Transaction();
			transaction6.setType(TransactionType.CREDITO);
			transaction6.setAmount(1200.0);
			transaction6.setDescription("Transferencia de terceros");
			transaction6.setDate(LocalDateTime.now());

			// Agregar las transacciones a las cuentas
			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account1.addTransaction(transaction3);
			account2.addTransaction(transaction4);
			account3.addTransaction(transaction5);
			account4.addTransaction(transaction6);

			//Guardar la transferencia en la base de datos
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
		};
}
}