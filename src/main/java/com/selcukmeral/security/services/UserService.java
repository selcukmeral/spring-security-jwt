package com.selcukmeral.security.services;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selcukmeral.security.model.LoginRequest;
import com.selcukmeral.security.model.LoginResponseModel;
import com.selcukmeral.security.model.UserModel;
import com.selcukmeral.security.security.TokenHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {


	private final TokenHelper tokenHelper;

	public LoginResponseModel loginUser(LoginRequest request) {
		if(!StringUtils.hasLength(request.getUsername())) {
			log.error("user name is null");
			return null;
		}	
		final UserModel user = getUserByName(request.getUsername());
		if(user == null) {
			log.error("user not found");
			return null;
		}
		if(!user.getPassword().equals(request.getPassword())) {
			log.error("user password not equals");
			return null;
		}
		return generateLoginResponseModel(user);

	}
	
	public UserModel getUserByName(String username) {
		final File userListFile = new File("src/main/resources/users.json");
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			List<UserModel> userList = mapper.readValue(userListFile, new TypeReference<List<UserModel>>() {
			});
			if (!CollectionUtils.isEmpty(userList)) {
				for (UserModel user : userList) {
					if (user.getUsername().equals(username)) {
						return user;

					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public List<UserModel> getAllUser(UserModel userModel) {
		log.info("user name:{}",userModel.getUsername());
		
		final File userListFile = new File("src/main/resources/users.json");
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			List<UserModel> userList = mapper.readValue(userListFile, new TypeReference<List<UserModel>>() {
			});
			
			return userList;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
		
	}
	
	private LoginResponseModel generateLoginResponseModel(final UserModel userModel) {
		final Date expirationDate = tokenHelper.generateTokenExpirationDate();
		final String jwtToken = tokenHelper.generateToken(userModel,expirationDate);
		
		final LoginResponseModel loginResponseModel = new LoginResponseModel();
		loginResponseModel.setExpirationDate(expirationDate);
		loginResponseModel.setToken(jwtToken);
		loginResponseModel.setUser(userModel);
		return loginResponseModel;
	}
	
}