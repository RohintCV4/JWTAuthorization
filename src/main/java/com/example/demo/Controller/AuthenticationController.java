package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.JwtAuthenticationResponse;
import com.example.demo.Dto.RefreshTokenRequest;
import com.example.demo.Dto.SignInRequest;
import com.example.demo.Dto.SignUpRequest;
import com.example.demo.Entity.User;
import com.example.demo.Service.AuthenticationServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequest signUpRequest){
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/admin-signup")
    public User adminSignUp(@RequestBody SignUpRequest signUpRequest){
        return authenticationService.adminSignUp(signUpRequest);
    }
    
    @PostMapping("/hr-signup")
    public User hrSignUp(@RequestBody SignUpRequest signUpRequest){
        return authenticationService.hrSignUp(signUpRequest);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody SignInRequest signInRequest){
        return authenticationService.signIn(signInRequest);
    }


    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

}