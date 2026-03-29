package com.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.portal.dto.DashBoardResponse;
import com.portal.dto.EnquiryForm;
import com.portal.dto.EnquirySearchCriteria;
import com.portal.service.StudentEqnuiryServiceImpl;

import jakarta.servlet.http.HttpSession;


@Controller
public class EnquiryController {
	
	@Autowired
	private StudentEqnuiryServiceImpl service;
	
	@Autowired
	private HttpSession session;
	
	
	@GetMapping("/logout")
	public String logoutUser() {
		
		session.invalidate();
		
		return "index";
	}
	
	
	@GetMapping("/dashboard")
	public String getDashboardPage( Model model) {
		
		DashBoardResponse response = service.getDashboardResponse((Integer) session.getAttribute("userId"));
		
		model.addAttribute("responseForm",response);
		
		return "dashboard";
	}
		
	@GetMapping("/enquiry")
	public String getAddEnquiryPage(Model model ) {
		 model.addAttribute("enquiryForm", new EnquiryForm());
	     init(model); 	     
	     
		return "add-enquiry";
	}

	
	@PostMapping("/enquiry")
	public String get(@ModelAttribute("enquiryForm") EnquiryForm enquiryForm,  Model model ) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		Boolean status = service.addEnqury(enquiryForm,userId);
		if(status)
		{
			model.addAttribute("successMsg","Enquiry addded");
		}else
		{
			model.addAttribute("errMsg","Field Requied");
		}
		init(model); 			
		return "add-enquiry";
	}
	
	
	
	@GetMapping("/enquiries")
	public String getMethodName( Model model) {
		
		model.addAttribute("searchForm",new EnquirySearchCriteria());
		init(model);		
		return "view-enquiries";
	}
	
	
	private void init(Model model) {
		model.addAttribute("courses",service.getCourseName()); 
	     model.addAttribute("status",service.getStatusName());
	}

}
