package com.ixaris.interview.transfers.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ixaris.interview.transfers.modal.Transaction;

public class FileService {
	
	private static final Logger log = LogManager.getLogger(FileService.class);
	
	private static final Integer SOURCE_COLUMN=0;
	private static final Integer DESTINATION_COLUMN=1;
	private static final Integer AMOUNT_COLUMN=2;
	private static final Integer DATE_COLUMN=3;
	private static final Integer TRANSACTION_ID_COLUMN=4;
	
	//passing null in case of empty file,file having only headers.Will handle with exception eventually
	public List<Transaction> readAndConvertFile(String inputFile){
		final URL file = getClass().getClassLoader().getResource(inputFile);
		List<String> allInput=null;
		try {
			allInput = Files.readAllLines(Path.of(file.toURI()))
					.stream()
					.filter(str -> !(str.startsWith("#") || str.isBlank() || str.isEmpty()))
					.map(st -> st.replaceAll("\\s+",""))
					.collect(Collectors.toList());
			
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(allInput.isEmpty() || allInput == null || allInput.size()==1) {
			log.debug("File "+inputFile+" does not have any data");
			return null;
		}
		return allInput.stream().skip(1).map(this::convertRowToTransaction).collect(Collectors.toList());
	}
	
	private Transaction convertRowToTransaction(String column) {
		if(column==null || column.length()==0) {
			log.error("Empty string");
			return null;
		}else {
		String[] allValues=column.split(",");
			return new Transaction(Long.valueOf(allValues[SOURCE_COLUMN]),Long.valueOf(allValues[DESTINATION_COLUMN]),Double.parseDouble(allValues[AMOUNT_COLUMN]),
				new Date(allValues[DATE_COLUMN]), Long.parseLong(allValues[TRANSACTION_ID_COLUMN]));
		}
	}
}
