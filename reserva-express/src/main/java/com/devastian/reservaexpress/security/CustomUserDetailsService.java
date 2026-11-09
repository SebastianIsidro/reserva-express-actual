package com.devastian.reservaexpress.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devastian.reservaexpress.entity.User;
import com.devastian.reservaexpress.exceptions.NotFoundException;
import com.devastian.reservaexpress.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(()-> new NotFoundException("Usuario no encontrado"));
		
		return AuthUser.builder()
				.user(user)
				.build();
	}

}
