package com.ixaris.interview.transfers.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ixaris.interview.transfers.modal.Transaction;

public class TransactionServiceTest {

	TransactionService transactionService=new TransactionService();
	List<Transaction> inputAllTransactions_allNull = null;
	List<Transaction> inputAllTransactions_sameAccount = List.of(new Transaction(Long.valueOf(0),Long.valueOf(222),34,new Date("10/08/2055"),1),
			new Transaction(Long.valueOf(0),Long.valueOf(222),34,new Date("10/08/2055"),2));
	List<Transaction> inputAllTransactions_twoAccount = List.of(new Transaction(Long.valueOf(0),Long.valueOf(222),34,new Date("10/08/2055"),1),
			new Transaction(Long.valueOf(0),Long.valueOf(111),34,new Date("10/08/2055"),2),
			new Transaction(Long.valueOf(111),Long.valueOf(222),34,new Date("10/08/2055"),2));
	List<Transaction> inputAllTransactions_multipleTranactions = List.of(new Transaction(Long.valueOf(0),Long.valueOf(222),34,new Date("10/08/2055"),1),
			new Transaction(Long.valueOf(0),Long.valueOf(111),34,new Date("10/08/2055"),2),
			new Transaction(Long.valueOf(111),Long.valueOf(222),32,new Date("10/08/2055"),3),
			new Transaction(Long.valueOf(111),Long.valueOf(222),2,new Date("10/08/2055"),4),
			new Transaction(Long.valueOf(222),Long.valueOf(111),2,new Date("10/08/2055"),5));
	Map<Long,Double> allBalance_null = null;
	Map<Long,Double> allBalance_oneAccount =  new HashMap<Long, Double>();
	Map<Long,Double> allBalance_twoAccount = new HashMap<Long, Double>();
	@BeforeEach                                           // annotated method placed in the beginning.  
	public void before_test()  
	{  
		allBalance_oneAccount.put(Long.valueOf(222), Double.valueOf(68));
		allBalance_twoAccount.put(Long.valueOf(111), Double.valueOf(0));
		allBalance_twoAccount.put(Long.valueOf(222), Double.valueOf(68));
	}
	@Test
	public void testGetBalanceOfAllAccount(){
		Map<Long,Double> actual=transactionService.getBalanceOfAllAccount(inputAllTransactions_allNull);
		assertThat(actual).isNull();

		actual=transactionService.getBalanceOfAllAccount(inputAllTransactions_sameAccount);
		System.out.println(actual);
		assertThat(actual).isEqualTo(allBalance_oneAccount);

		actual=transactionService.getBalanceOfAllAccount(inputAllTransactions_twoAccount);
		assertThat(actual).isEqualTo(allBalance_twoAccount);

	}
	@Test
	public void testGetAccountWithHighestBalance() {
		Long expected = null;
		Long actual=transactionService.getAccountWithHighestBalance(allBalance_null);
		assertThat(expected).isNull();
		
		actual=transactionService.getAccountWithHighestBalance(allBalance_oneAccount);
		expected=Long.valueOf(222);
		assertThat(expected).isEqualTo(actual);
		
		actual=transactionService.getAccountWithHighestBalance(allBalance_twoAccount);
		expected=Long.valueOf(222);
		assertThat(expected).isEqualTo(actual);
	}
	@Test
	public void testGetFrequentlyUsedSourceAccount() {
		Long expected = null;
		Long actual=transactionService.getFrequentlyUsedSourceAccount(inputAllTransactions_allNull);
		assertThat(actual).isNull();
		
		actual=transactionService.getFrequentlyUsedSourceAccount(inputAllTransactions_sameAccount);
		expected=Long.valueOf(0);
		assertThat(actual).isNull();
		
		actual=transactionService.getFrequentlyUsedSourceAccount(inputAllTransactions_twoAccount);
		expected=Long.valueOf(111);
		assertThat(expected).isEqualTo(actual);
		
		actual=transactionService.getFrequentlyUsedSourceAccount(inputAllTransactions_multipleTranactions);
		expected=Long.valueOf(111);
		assertThat(expected).isEqualTo(actual);
	}
}
