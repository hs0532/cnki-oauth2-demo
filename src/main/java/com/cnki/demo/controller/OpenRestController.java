package com.cnki.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenRestController {

	@RequestMapping("resource")
	public String Resource(){
		return "This is a Resource!";
	}
	
}
