package com.example.demo.Dto;

import lombok.Data;

@Data
public class SignUpRequest {

	private String userName;
	private String email;
	private String password;
}
