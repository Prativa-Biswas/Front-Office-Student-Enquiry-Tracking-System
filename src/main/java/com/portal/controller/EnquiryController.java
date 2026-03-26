package com.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.dto.DashBoardResponse;


@Controller
public class EnquiryController {
	
	@GetMapping("/dashboard")
	public String getDashboardPage(DashBoardResponse res) {
		
		return "dashboard";
	}
	

}
