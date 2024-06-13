package com.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testController")
public class TestController {

	@GetMapping("/getDetails")
	public String getDetails()
	{
		return "here are the details";
	}
}
