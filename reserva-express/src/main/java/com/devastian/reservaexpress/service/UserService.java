package com.devastian.reservaexpress.service;

import java.util.List;

import com.devastian.reservaexpress.dto.Response;
import com.devastian.reservaexpress.dto.UserRequest;
import com.devastian.reservaexpress.dto.UserResponse;

public interface UserService {
	
	Response<UserResponse> getCurrentUser();
	Response<UserResponse> updateCurrentUser(UserRequest userRequest);
	Response<List<UserResponse>> getAllUsers();
	Response<UserResponse> getUserById(Long id);
	
}
