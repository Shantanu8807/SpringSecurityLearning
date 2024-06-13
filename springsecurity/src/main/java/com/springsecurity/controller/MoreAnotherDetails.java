package com.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moreAnotherDetails")
public class MoreAnotherDetails {
     
	
	@GetMapping("/getMoreAnotherDetails")
	public String getMoreAnotherDetails()
	{
		return "here are more another details";
	}
}
