package com.selcukmeral.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selcukmeral.security.model.LoginRequest;
import com.selcukmeral.security.model.LoginResponseModel;
import com.selcukmeral.security.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/login-service")
public class LoginController {

	private final UserService userService;
	

	@PostMapping("/login")
	public ResponseEntity<LoginResponseModel> loginUser(@RequestBody LoginRequest loginRequest) {
		final LoginResponseModel response = userService.loginUser(loginRequest);
		return ResponseEntity.ok().body(response);
	}
	
}
