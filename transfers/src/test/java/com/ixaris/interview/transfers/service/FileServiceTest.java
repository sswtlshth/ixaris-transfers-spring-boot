package com.ixaris.interview.transfers.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ixaris.interview.transfers.modal.Transaction;

class FileServiceTest {
	
	FileService fileService = new FileService();
	@Test
	void testReadAndConvertFile_emptyFile() {
		String file="emptyFile.txt";
		List<Transaction> expectedList = null;
		List<Transaction> actualList=fileService.readAndConvertFile(file);
		assertEquals(expectedList, actualList);
	}
	
	@Test
	void testReadAndConvertFile_onlyHeadersFile() {
		String file="onlyHeadersFile.txt";
		List<Transaction> expectedList = null;
		List<Transaction> actualList=fileService.readAndConvertFile(file);
		assertEquals(expectedList, actualList);
	}
	
	@Test
	void testReadAndConvertFile_dataFile() {
		String file="dataFile.txt";
		List<Transaction> expectedList = List.of(new Transaction(Long.valueOf(111),Long.valueOf(222),34,new Date("10/08/2055"),1));
		List<Transaction> actualList=fileService.readAndConvertFile(file);
		assertThat(expectedList).hasSize(1).hasSameElementsAs(actualList);
	}
	
	@Test
	void testConvertRowToTransaction() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Method m = FileService.class.getDeclaredMethod("convertRowToTransaction", String.class);
		m.setAccessible(true);
		String input="111,222,34,10/08/2055,1";
		Transaction expected = new Transaction(Long.valueOf(111),Long.valueOf(222),34,new Date("10/08/2055"),1);
		Transaction actual = (Transaction) m.invoke(fileService, input);
		assertEquals(expected, actual);
	}

}
