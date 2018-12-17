package com.cnki.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cnki.demo.bean.Msg;

@Controller
public class DemoController {

	@RequestMapping("login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("index")
	public String index(Model model){
		
		 Msg msg = new Msg("测试标题", "测试内容", "额外信息，只对管理员显示");
	        model.addAttribute("msg", msg);
		return "index";
	}
	
}
