package com.trade;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.trade.entity.TradeEntity;
import com.trade.repo.TradeRepo;

@SpringBootApplication
public class TradeManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeManagerApplication.class, args);
	}

	@Bean
	  public CommandLineRunner demo(TradeRepo repository) {
	    return (args) -> {
	    	  repository.save(new TradeEntity());
	    };
	}
}
