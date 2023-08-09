package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.Jsr310Converters;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository) {
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
		};
}
}