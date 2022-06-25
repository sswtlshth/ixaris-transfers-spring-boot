package com.ixaris.interview.transfers.modal;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Transaction {
	private Long sourceAccount;
	private Long destinationAccount;
	private double amount;
	private Date date;
	private long transferId;

}
