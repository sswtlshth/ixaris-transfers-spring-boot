package com.ixaris.interview.transfers.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ixaris.interview.transfers.modal.Transaction;

public class TransactionService {
	public Map<Long, Double> getBalanceOfAllAccount(List<Transaction> allTransactions){
		if( allTransactions ==null || allTransactions.isEmpty() || allTransactions.size() ==0) {
			return null;
		}else {
			Map<Long, Double> allBalance = new HashMap<Long, Double>();
			for(Transaction t: allTransactions) {
				updateDestinationAccount(allBalance, t);			
				updateSourceAccount(allBalance, t);	
			}
			return allBalance;
		}
	}

	private void updateSourceAccount(Map<Long, Double> allBalance, Transaction t) {
		double updatedBalance=t.getAmount();;
		if(t.getSourceAccount() != 0 && allBalance.containsKey(t.getSourceAccount())) {
			updatedBalance=allBalance.get(t.getSourceAccount())-updatedBalance;			
			allBalance.put(t.getSourceAccount(),updatedBalance);
		}
	}

	private void updateDestinationAccount(Map<Long, Double> allBalance, Transaction t) {
		double updatedBalance=t.getAmount();
		if(allBalance.containsKey(t.getDestinationAccount())) {
			updatedBalance=allBalance.get(t.getDestinationAccount())+updatedBalance;			
		}
		allBalance.put(t.getDestinationAccount(),updatedBalance);
	}
	
	public Long getAccountWithHighestBalance(Map<Long,Double> allBalances) {
		if(allBalances==null) {
			return null;
		}
		return allBalances.entrySet().stream().max((e1,e2)->e1.getValue().compareTo(e2.getValue())).get().getKey();
	}
	
	public Long getFrequentlyUsedSourceAccount(List<Transaction> allTransactions) {
		if(allTransactions == null) { return null;}
		Optional<Entry<Long, Integer>> result= allTransactions.stream()
			.filter(t -> !(t.getSourceAccount()==0)).map(t -> t.getSourceAccount())
			.collect(Collectors.toMap(i->i, count->1,Integer::sum))
			.entrySet().stream()
			.max((e1,e2)->e1.getValue().compareTo(e2.getValue()));
		if(result.isPresent()) {
			return result.get().getKey();
		}
		return null;
		
	}
}
