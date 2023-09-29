package com.ba.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ba.dto.CustomerDetailsDTO;
import com.ba.dto.ResponseDTO;
import com.ba.dto.TransactionDTO;
import com.ba.model.CustomerDetails;
import com.ba.model.TransactionHistory;
import com.ba.repository.CustomerRepository;
import com.ba.repository.TransactionHistoryRepository;
import com.ba.service.CustomerService;
import com.ba.utility.PDFGenerator;
import com.ba.utility.PasswordGeneratedUtil;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TransactionHistoryRepository transactionHistoryRepository;

	/**
	 * 
	 */
	@Override
	public ResponseDTO createUser(CustomerDetails user) {
		// TODO Auto-generated method stub

		ResponseDTO responseDTO = new ResponseDTO();
		if (user.getInitialBalance() >= 1000) {
			String password = PasswordGeneratedUtil.generateRandomPassword(12);
			user.setPassword(password);

			int accountNumber = customerRepository.fetchMaxAccountNumber() + 1;
			user.setAccountNumber(accountNumber);
			user.setActive(true);
			customerRepository.save(user);
			responseDTO.setData(user);
			responseDTO.setMessage("SUCCESS");
		} else {
			responseDTO.setMessage("Can not create user as initial balance is less than 1000");
		}

		return responseDTO;

	}

	@Override
	public List<CustomerDetailsDTO> fetchCustomerDetails() {
		List<CustomerDetailsDTO> custDTOList = new ArrayList<>();
		List<CustomerDetails> custList = new ArrayList<>();
		// TODO Auto-generated method stub

		custList = customerRepository.findAll();

		for (CustomerDetails customerDetails : custList) {
			CustomerDetailsDTO cDTO = new CustomerDetailsDTO();
			cDTO.setId(customerDetails.getId());
			cDTO.setAccountType(customerDetails.getAccountType());
			cDTO.setAddress(customerDetails.getAddress());
			cDTO.setDob(customerDetails.getDob());
			cDTO.setEmail(customerDetails.getEmail());
			cDTO.setFullName(customerDetails.getFullName());
			cDTO.setMobileNo(customerDetails.getMobileNo());
			cDTO.setIdProof(customerDetails.getIdProof());

			custDTOList.add(cDTO);

		}

		return custDTOList;
	}

	@Override
	public CustomerDetails getUser(int id) {
		// TODO Auto-generated method stub
		Optional<CustomerDetails> op = customerRepository.findById(id);

		CustomerDetails customerDetails = !op.isEmpty() ? op.get() : null;

		return customerDetails;

	}

	@Override
	public ResponseDTO deleteUser(int id) {
		// TODO Auto-generated method stub

		ResponseDTO res = new ResponseDTO();
		Optional<CustomerDetails> op = customerRepository.findById(id);

		if (op.isPresent()) {
			CustomerDetails customerDetails = op.get();
			if (customerDetails.getInitialBalance() > 0) {
				res.setMessage("can not delete the custmer as it has balance greater than 0");
				Map<String, Object> map = new HashMap<>();
				map.put("balance", customerDetails.getInitialBalance());
				res.setData(map);
			} else {
				customerRepository.deleteById(id);
				res.setMessage("successfully deleted");
			}

		}
		else {
			res.setMessage("Failure: User does not exist in system");
		}

		return res;

	}

	@Override
	public ResponseDTO updateUser(CustomerDetails user) {
		// TODO Auto-generated method stub
		ResponseDTO res = new ResponseDTO();

		Optional<CustomerDetails> op = customerRepository.findById(user.getId());



		if (op.isPresent()) {
			CustomerDetails existingCustomerDetails = op.get();
			try {
				user.setAccountNumber(existingCustomerDetails.getAccountNumber());
				user.setPassword(existingCustomerDetails.getPassword());
				user.setActive(true);
				customerRepository.save(user);

				res.setMessage("successfully updated");
			} catch (Exception e) {
				// TODO: handle exception
				res.setMessage("exception in  updated");
			}
		} else {

			res.setMessage("No such user exists in system");
		}

		return res;
	}

	@Override
	public ResponseDTO deposit(TransactionDTO txnDTO) {
		ResponseDTO res = new ResponseDTO();
		try {
			CustomerDetails custDetails = customerRepository.getOne(txnDTO.getCustomerID());
			double newBal = custDetails.getInitialBalance() + txnDTO.getAmount();
			TransactionHistory transactionHistory = new TransactionHistory();
			transactionHistory.setCreditDebit("CR");
			transactionHistory.setAmount(txnDTO.getAmount());
			transactionHistory.setFinalBalance(newBal);
			transactionHistory.setDatetime(new Date());
			transactionHistory.setCustomerId(custDetails.getId());
			custDetails.setInitialBalance(newBal);
			transactionHistoryRepository.save(transactionHistory);
			customerRepository.save(custDetails);
			res.setMessage("Success");
			res.setData("new Balance is :" + newBal);
		} catch (Exception e) {
			// TODO: handle exception
			res.setMessage("Failure");
			res.setData(e.getMessage());
		}

		return res;
	}

	@Override
	public ResponseDTO withdraw(TransactionDTO txnDTO) {

		ResponseDTO res = new ResponseDTO();
		try {
			CustomerDetails custDetails = customerRepository.getOne(txnDTO.getCustomerID());

			double newBal = custDetails.getInitialBalance() - txnDTO.getAmount();
			if (newBal >= 0) {
				TransactionHistory transactionHistory = new TransactionHistory();
				transactionHistory.setCreditDebit("DB");
				transactionHistory.setAmount(txnDTO.getAmount());
				transactionHistory.setFinalBalance(newBal);
				transactionHistory.setDatetime(new Date());
				transactionHistory.setCustomerId(custDetails.getId());
				custDetails.setInitialBalance(newBal);
				transactionHistoryRepository.save(transactionHistory);
				customerRepository.save(custDetails);
				res.setMessage("Success");
				res.setData("new Balance is :" + newBal);
			} else {
				res.setMessage("Failure");
				res.setData("amount is greater than existing balance ");
			}

		} catch (Exception e) {
			// TODO: handle exception
			res.setMessage("Failure");
			res.setData(e.getMessage());
		}

		return res;

	}

	@Override
	public ResponseDTO viewLast10Transaction(int customerID) {
		// TODO Auto-generated method stub
		ResponseDTO res = new ResponseDTO();
		try {

			List<TransactionHistory> list = transactionHistoryRepository.fetchLast10TxnByCustomerID(customerID);
			res.setData(list);
			res.setMessage("success");
		} catch (Exception e) {
			// TODO: handle exception
			res.setData(e.getMessage());
			res.setMessage("failure");
		}

		return res;
	}

	@Override
	public void downloadPDf(int customerID, String filePath) throws IOException {
		// TODO Auto-generated method stub
		List<TransactionHistory> list = transactionHistoryRepository.fetchTxnByCustomerID(customerID);

		PDFGenerator.generatePDF(list, filePath);

	}

}
