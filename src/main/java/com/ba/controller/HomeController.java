package com.ba.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ba.model.CustomerDetails;
import com.ba.service.CustomerService;
import com.ba.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	@Autowired
	private CustomerService userService;
	
	@Autowired
	private LoginService loginService;
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	/*
	 * @GetMapping("/") public String index() { return "index"; }
	 */
	
	/*
	 * @GetMapping("/login") public String login() { return "Login"; }
	 */
	
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	
	@GetMapping("/Register")
	public String register() {
		return "Register";
	}
	


}
