package com.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.dto.DashBoardResponse;
import com.portal.dto.EnquiryForm;
import com.portal.dto.EnquirySearchCriteria;
import com.portal.enitiy.StudentEnquiry;
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
	public String getVewEnqueyPage( Model model) {
				init(model);	
				Integer userId = (Integer) session.getAttribute("userId");
				List<StudentEnquiry> enquiries = service.getEnquiries(userId);
				model.addAttribute("enquiries",enquiries);
		return "view-enquiries";
	}
	
	@GetMapping("/filterEnquiries")
	public String getFilterEnquery(@RequestParam("course") String course, 
			@RequestParam("status")  String status, 
			@RequestParam("mode")  String mode,  
			Model model) {
				init(model);
				Integer userId = (Integer) session.getAttribute("userId");
				EnquirySearchCriteria criteria = new EnquirySearchCriteria(); 
				criteria.setCourseName(course);
				criteria.setEnquiryStatus(status);
				criteria.setClassMode(mode);
				List<StudentEnquiry> enqueries = service.findFilterEnqueries(criteria,userId);
				model.addAttribute("enquiries",enqueries);			
	
				return "fitlerdView-enquiries";
	}
	
	@GetMapping("/edit")
	public String getMethodName(@RequestParam("enquiryId")Integer enquiryId,  Model model ) {
		Integer userId = (Integer) session.getAttribute("userId");
		EnquiryForm enquiry = service.getEnquiry(enquiryId, userId);
		 model.addAttribute("enquiryForm", enquiry);		 
		 init(model);
		return "editData";
	}
	
	@PostMapping("/edit")
	public String editFuctionality(@ModelAttribute("enquiryForm") EnquiryForm enquiryForm,  Model model ) {
		Integer userId = (Integer) session.getAttribute("userId");
		String msg = service.updateEnquiry(enquiryForm, userId);
		
		if(msg.contains("SUCCESS"))
		{
			model.addAttribute("successMsg","Enquiry Updated");
		}else
		{
			model.addAttribute("errMsg",msg);
		}
		 init(model);		 
		return "editData";
	}
	
	
	private void init(Model model) {
		model.addAttribute("courses",service.getCourseName()); 
	     model.addAttribute("status",service.getStatusName());
	}

}
