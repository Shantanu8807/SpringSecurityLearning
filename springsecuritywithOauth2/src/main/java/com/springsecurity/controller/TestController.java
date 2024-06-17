package com.springsecurity.controller;

import javax.swing.text.Position;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testController")
public class TestController {

	@GetMapping("/getDetails")
	//@PreAuthorize("hasRole('USER')")
	//@PostAuthorize("hasRole('USER')")
	///for useing this filterObject the accepting parameneter or return parameter must be of type collection 
	//@PreAuthorize("filterObject.contactName!='Test'")
	//@PostAuthorize("filterObject.contactName!='Test'")
	public String getDetails()
	{
		return "here are the details";
	}
}
