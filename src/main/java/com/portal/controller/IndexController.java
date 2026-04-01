package com.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	/**
	 * Controller class responsible for handling requests related to the home/index page.
     * 
	 * @return: This controller maps the root URL ("/") and returns the index view.
	 */
	@GetMapping("/")
	public String getIndexPage() {
		
		return "index";
	}
	
}
