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
	
	/**
	 * Handles user logout functionality.
	 * 
	 * This method invalidates the current HTTP session,
	 * effectively logging out the user and clearing all session data.
	 * 
	 * @return String - redirects user to the index (home) page after logout
	 */
	@GetMapping("/logout")
	public String logoutUser() {
		
		session.invalidate();
		
		return "index";
	}
	
	/**
	 * Handles request to display the dashboard page.
	 * 
	 * This method retrieves dashboard data based on the logged-in user's ID
	 * from the session and adds it to the model to be displayed on the UI.
	 * 
	 * @param model Model object used to pass data from controller to view
	 * @return String - returns the dashboard view page
	 */
	@GetMapping("/dashboard")
	public String getDashboardPage( Model model) {
		
		DashBoardResponse response = service.getDashboardResponse((Integer) session.getAttribute("userId"));
		
		model.addAttribute("responseForm",response);
		
		return "dashboard";
	}
	
	
	/**
	 * Handles request to display the Add Enquiry page.
	 * 
	 * This method initializes an empty enquiry form object and loads
	 * required data (like dropdown values) into the model for the UI.
	 * 
	 * @param model Model object used to pass data to the view
	 * @return String - returns the add-enquiry page
	 */
	@GetMapping("/enquiry")
	public String getAddEnquiryPage(Model model ) {
		 model.addAttribute("enquiryForm", new EnquiryForm());
	     init(model); 	     
	     
		return "add-enquiry";
	}
	

	/**
	 * Handles submission of the Add Enquiry form.
	 * 
	 * This method retrieves the logged-in user's ID from the session,
	 * processes the enquiry form, and saves it to the database.
	 * It also sends success or error messages back to the UI.
	 * 
	 * @param enquiryForm EnquiryForm object containing user input data
	 * @param model Model object used to pass response messages to the view
	 * @return String - returns the add-enquiry page with status message
	 */
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
	
	/**
	 * Handles request to display all enquiries for the logged-in user.
	 * 
	 * This method retrieves the user ID from the session, fetches all
	 * associated enquiries from the service layer, and adds them to the model
	 * for display on the view page.
	 * 
	 * @param model Model object used to pass enquiry data to the view
	 * @return String - returns the view-enquiries page
	 */
	
	@GetMapping("/enquiries")
	public String getVewEnqueyPage( Model model) {
				init(model);	
				Integer userId = (Integer) session.getAttribute("userId");
				List<StudentEnquiry> enquiries = service.getEnquiries(userId);
				model.addAttribute("enquiries",enquiries);
		return "view-enquiries";
	}
	
	/**
	 * Handles filtering of enquiries based on search criteria.
	 * 
	 * This method receives filter parameters (course, status, mode) from the request,
	 * constructs a search criteria object, and retrieves filtered enquiries for the
	 * logged-in user. The result is sent back to the UI (typically via AJAX).
	 * 
	 * @param course Course name used as filter criteria
	 * @param status Enquiry status used as filter criteria
	 * @param mode Class mode used as filter criteria
	 * @param model Model object used to pass filtered data to the view
	 * @return String - returns the filtered enquiries view page
	 */
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
	
	/**
	 * Handles request to load the Edit Enquiry page.
	 * 
	 * This method retrieves the enquiry details based on enquiryId and
	 * the logged-in user's ID from the session. The data is then added
	 * to the model to pre-populate the edit form.
	 * 
	 * @param enquiryId ID of the enquiry to be edited
	 * @param model Model object used to pass enquiry data to the view
	 * @return String - returns the editData page
	 */
	@GetMapping("/edit")
	public String getMethodName(@RequestParam("enquiryId")Integer enquiryId,  Model model ) {
		Integer userId = (Integer) session.getAttribute("userId");
		EnquiryForm enquiry = service.getEnquiry(enquiryId, userId);
		 model.addAttribute("enquiryForm", enquiry);		 
		 init(model);
		return "editData";
	}
	
	/**
	 * Handles submission of the Edit Enquiry form.
	 * 
	 * This method updates the enquiry details in the database based on
	 * the submitted form data and the logged-in user's ID. It returns
	 * success or error messages to the UI accordingly.
	 * 
	 * @param enquiryForm EnquiryForm object containing updated enquiry details
	 * @param model Model object used to pass response messages to the view
	 * @return String - returns the editData page with update status
	 */
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
	
	/**
	 * Initializes common model attributes required for enquiry forms.
	 * 
	 * This method loads course names and enquiry status values from the service layer
	 * and adds them to the model. These attributes are typically used to populate
	 * dropdown fields in the UI.
	 * 
	 * @param model Model object used to pass data to the view
	 */
	
	private void init(Model model) {
		model.addAttribute("courses",service.getCourseName()); 
	     model.addAttribute("status",service.getStatusName());
	}

}
