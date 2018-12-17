package com.cnki.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloseRestController {

	@RequestMapping("clock")
	public String Resource(){
		return "This is a Resource!";
	}
	
}
