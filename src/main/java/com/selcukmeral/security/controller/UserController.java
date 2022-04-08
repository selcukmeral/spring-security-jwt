package com.selcukmeral.security.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selcukmeral.security.model.UserModel;
import com.selcukmeral.security.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user-service")
public class UserController {

	private final UserService userService;
	
	@GetMapping("/getAllUser")
	public ResponseEntity<List<UserModel>> getAllUser(@AuthenticationPrincipal UserModel userModel) {
		final List<UserModel> response = userService.getAllUser(userModel);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/getAllUser")
	public ResponseEntity<List<UserModel>> getAllUserPost(@AuthenticationPrincipal UserModel userModel) {
		final List<UserModel> response = userService.getAllUser(userModel);
		return ResponseEntity.ok().body(response);
	}
	
}
