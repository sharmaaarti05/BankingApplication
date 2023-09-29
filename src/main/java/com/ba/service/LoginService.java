package com.ba.service;

import com.ba.dto.LoginDTO;
import com.ba.dto.ResponseDTO;

public interface LoginService {
	

	public ResponseDTO userLogin(LoginDTO loginDTO);
	
	public ResponseDTO changePassword(LoginDTO loginDTO);
	
	
}
