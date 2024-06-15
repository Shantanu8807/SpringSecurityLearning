package com.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.model.Customer;
import com.springsecurity.repo.CustomerRepo;

@RestController
public class LoginController {
	
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer)
	{
		Customer savedCustomer=null;
		ResponseEntity response=null;
		
		try
		{   
			String hashPwd=passwordEncoder.encode(customer.getPassword());
			customer.setPassword(hashPwd);
			savedCustomer = customerRepo.save(customer);
			if(savedCustomer.getId()>0)
			{
				response=ResponseEntity.status(HttpStatus.CREATED).body("User Created Successfully");
			}
		}
		catch(Exception e)
		{
			response=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Exception occured "+e.getMessage());
		}
		return response;
	}

}
