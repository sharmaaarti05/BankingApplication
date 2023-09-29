package com.ba.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ba.dto.LoginDTO;
import com.ba.dto.ResponseDTO;
import com.ba.model.Admin;
import com.ba.model.CustomerDetails;
import com.ba.repository.AdminRepository;
import com.ba.repository.CustomerRepository;
import com.ba.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public ResponseDTO userLogin(LoginDTO loginDTO) {

		ResponseDTO loginResponseDTO = new ResponseDTO();
		Map<String, String> map = new HashMap<>();
		
		
		if(loginDTO==null || loginDTO.getUsername()==null || loginDTO.getPassword()==null) {
			loginResponseDTO.setMessage("Please send username and password");
			return loginResponseDTO;
		}
		// TODO Auto-generated method stub
		if ("admin".equalsIgnoreCase(loginDTO.getRole())) {
			Admin admin = adminRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
			if (admin != null) {
				loginResponseDTO.setMessage("Success");
				map.put("role", "admin");

			} else {
				loginResponseDTO.setMessage("Login Fail: Please check admin username and password");

			}

		} else {
			int accontNumber = 0;
			try {
				 accontNumber = Integer.parseInt(loginDTO.getUsername());
			}
			catch (NumberFormatException e) {
				// TODO: handle exception
				loginResponseDTO.setMessage("For Customer:UserName should be your account number in numeric Form ");
				return loginResponseDTO;
			}
			
			CustomerDetails customerDetails = customerRepository
					.findByAccountNumberAndPassword(accontNumber, loginDTO.getPassword());
			if (customerDetails != null) {
				loginResponseDTO.setMessage("Success");
				map.put("role", "Customer");
			} else {
				loginResponseDTO.setMessage("Login Fail: Please check customer username and password");
			}
		}

		return loginResponseDTO;
	}

	@Override
	public ResponseDTO changePassword(LoginDTO loginDTO) {
		ResponseDTO loginResponseDTO = new ResponseDTO();
		Map<String , String> responseMap= new HashMap<>();
		// TODO Auto-generated method stub
		if ("admin".equalsIgnoreCase(loginDTO.getRole())) {
			loginResponseDTO.setMessage("Failure : admin password change is not supported");
		} else {

			int rowUpdated = customerRepository.updatePassword(loginDTO.getPassword(),
					Integer.parseInt(loginDTO.getUsername()));

			if (rowUpdated == 1) {
				responseMap.put("New Password", loginDTO.getPassword());
				loginResponseDTO.setMessage("Password is updated");
				loginResponseDTO.setData(responseMap);
			}
		}

		return loginResponseDTO;
	}

}
