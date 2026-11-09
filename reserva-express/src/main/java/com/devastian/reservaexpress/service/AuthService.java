package com.devastian.reservaexpress.service;

import com.devastian.reservaexpress.dto.Response;
import com.devastian.reservaexpress.dto.LoginRequest;
import com.devastian.reservaexpress.dto.RegisterRequest;
import com.devastian.reservaexpress.entity.User;

public interface AuthService {
	Response<?> register(RegisterRequest registerRequest);
	Response<?> login(LoginRequest loginRequest);
	User getCurrentLoggedInUser();
}
