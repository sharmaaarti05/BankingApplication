package com.ba.service;

import java.io.IOException;
import java.util.List;

import com.ba.dto.CustomerDetailsDTO;
import com.ba.dto.ResponseDTO;
import com.ba.dto.TransactionDTO;
import com.ba.model.CustomerDetails;
import com.ba.model.TransactionHistory;

public interface CustomerService {
	public ResponseDTO createUser(CustomerDetails user);

	public List<CustomerDetailsDTO> fetchCustomerDetails();

	public CustomerDetails getUser(int id);
	
	public ResponseDTO deleteUser(int id);
	
	public ResponseDTO updateUser(CustomerDetails user);
	
	
	public ResponseDTO deposit(TransactionDTO txnDTO);
	
	
	public ResponseDTO withdraw(TransactionDTO txnDTO);
	
	
	public ResponseDTO viewLast10Transaction(int customerID);
	
	
	public void downloadPDf(int customerID, String filePath) throws IOException;
}
