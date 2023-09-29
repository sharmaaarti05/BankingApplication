package com.ba.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ba.dto.CustomerDetailsDTO;
import com.ba.dto.ResponseDTO;
import com.ba.model.CustomerDetails;
import com.ba.service.CustomerService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/customer-details")
	public ResponseDTO fetchCustomerDetails() {
		ResponseDTO res = new ResponseDTO();
		List<CustomerDetailsDTO> list= customerService.fetchCustomerDetails();
		 res.setData(list);
		 res.setMessage("size: "+list.size());
		 return res;
	}

	@PostMapping("/customer-details")
	public ResponseDTO createCustomer(@RequestBody CustomerDetails customerDetails) {
		return customerService.createUser(customerDetails);
	}
	
	
	@PutMapping("/customer-details")
	public ResponseDTO updateUser(@RequestBody CustomerDetails customerDetails) {

		return customerService.updateUser(customerDetails);
	}
	
	
	
	
	@DeleteMapping("/customer-details/{id}")
	public ResponseDTO deleteCustomer( @PathVariable int id) {
		ResponseDTO res	= customerService.deleteUser(id);
		 
		 
		 return res;
	}

}
