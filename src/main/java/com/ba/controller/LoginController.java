package com.ba.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ba.dto.LoginDTO;
import com.ba.dto.ResponseDTO;
import com.ba.service.LoginService;

@RestController()
public class LoginController {


	
	@Autowired
	private LoginService loginService;
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@PostMapping("/user-login")
	public ResponseDTO userLogin(@RequestBody LoginDTO loginDTO) {
		log.info("inside method login : "+loginDTO);
		return loginService.userLogin(loginDTO);
	}
	
	
	@PostMapping("/change-password")
	public ResponseDTO changePassword(@RequestBody LoginDTO loginDTO ) {
		return loginService.changePassword(loginDTO);
		
	}
}
