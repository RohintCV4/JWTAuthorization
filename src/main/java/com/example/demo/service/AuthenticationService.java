package com.example.demo.service;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.SignInRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.entity.User;
import com.example.demo.exceptions.EmailNotFoundException;
import com.example.demo.exceptions.InvalidCredentialsException;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    public User signUp(SignUpRequest signUpRequest) {
        User user =  new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

//    public JwtAuthenticationResponse signIn(SignInRequest signInRequest){
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
//                signInRequest.getPassword()));
//
//        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() ->
//                new IllegalArgumentException("Invalid Email id"));
//        var jwt = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
//
//        JwtAuthenticationResponse jwtAuthenticationResponse =
//                new JwtAuthenticationResponse();
//        jwtAuthenticationResponse.setToken(jwt);
//        jwtAuthenticationResponse.setRefreshToken(refreshToken);
//
//        return jwtAuthenticationResponse;
//    }
    
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("Invalid email"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse =new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        
        return jwtAuthenticationResponse;
    }


    public User adminSignUp(SignUpRequest signUpRequest) {
        User user =  new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }
    
//    public User hrSignUp(SignUpRequest signUpRequest) {
//        User user =  new User();
//        user.setName(signUpRequest.getName());
//        user.setEmail(signUpRequest.getEmail());
//        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
//        user.setRole(Role.HR);
//        return userRepository.save(user);
//    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
		User user = userRepository.findByEmail(userEmail).orElseThrow();
		if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
			var jwt = jwtService.generateToken(user);
			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

			return jwtAuthenticationResponse;
		}
        return null;
    }
}