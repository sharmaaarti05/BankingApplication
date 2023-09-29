package com.ba.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ba.dto.TransactionDTO;
import com.ba.dto.ResponseDTO;
import com.ba.model.CustomerDetails;
import com.ba.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/customer-details/{id}")
	public ResponseDTO fetchCustomer(@PathVariable int id) {
		ResponseDTO res = new ResponseDTO();

		CustomerDetails cd = customerService.getUser(id);
		if (cd != null) {
			res.setMessage("success");
		} else {
			res.setMessage("no record found");
		}
		res.setData(cd);
		return res;
	}

	@PostMapping("/deposit")
	public ResponseDTO deposit(@RequestBody TransactionDTO depositDTO) {
		return customerService.deposit(depositDTO);

	}

	@PostMapping("/withdraw")
	public ResponseDTO withdraw(@RequestBody TransactionDTO depositDTO) {
		return customerService.withdraw(depositDTO);
	}

	@GetMapping("/txn-view/{id}")
	public ResponseDTO viewTxn(@PathVariable int id) {
		return customerService.viewLast10Transaction(id);

	}

	@GetMapping("/downloadPDF/{customerId}")
	public ResponseEntity<FileSystemResource> downloadPDF(@PathVariable int customerId) {
		String filePath = "file.pdf";
		File file = new File(filePath);

		try {
			customerService.downloadPDf(customerId, filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Transaction.pdf");

		return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.APPLICATION_PDF)
				.body(new FileSystemResource(file));
	}
}
