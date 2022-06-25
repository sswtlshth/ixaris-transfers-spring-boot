package com.ixaris.interview.transfers;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import com.ixaris.interview.transfers.modal.Transaction;
import com.ixaris.interview.transfers.service.FileService;
import com.ixaris.interview.transfers.service.TransactionService;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * <p>
 * 	A Command-line application to parse and process a transfers file and provide the business requirements, namely:
 * 	<ul>
 * 	    <li>1. Print the final balances on all bank accounts</li>
 * 	    <li>2. Print the bank account with the highest balance</li>
 * 	    <li>3. Print the most frequently used source bank account</li>
 * 	</ul>
 * </p>
 */
@SpringBootApplication
@Log4j2
public class TransfersApplication implements CommandLineRunner {
	private static final Logger log = LogManager.getLogger(TransfersApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TransfersApplication.class, args);
	}

	@Override
	public void run(final String... args) {
		// Below is some sample code to get you started. Good luck :)
		String file="transfers.txt";
		FileService fileService = new FileService();
		List<Transaction> allTransactions= fileService.readAndConvertFile(file);
		TransactionService transactionService = new TransactionService();
		Map<Long, Double> allBalance= transactionService.getBalanceOfAllAccount(allTransactions);
		log.info("Balance of all account"+allBalance.toString());
		Long accountWithHighestBalance = transactionService.getAccountWithHighestBalance(allBalance);
		log.info("Account with highest balance "+accountWithHighestBalance);
		Long freqentSourceAccount = transactionService.getFrequentlyUsedSourceAccount(allTransactions);
		log.info("Account most frequently used as source "+freqentSourceAccount);
		
	
	}
}
