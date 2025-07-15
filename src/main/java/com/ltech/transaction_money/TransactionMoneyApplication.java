package com.ltech.transaction_money;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@SpringBootApplication
@EnableJdbcAuditing
public class TransactionMoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionMoneyApplication.class, args);
	}

}
