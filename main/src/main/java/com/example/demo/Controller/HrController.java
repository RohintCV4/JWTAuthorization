package com.example.demo.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/hr")
@RequiredArgsConstructor
public class HrController {

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hr logged In");
    }

}