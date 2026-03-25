package com.portal.service;

import java.util.List;

import com.portal.dto.EnquiryForm;
import com.portal.dto.DashBoardResponse;
import com.portal.dto.EnquirySearchCriteria;

public interface StudentEnquiryService {
	
	public List<String> getCourseName();
	public List<String> getStatusName();
	public  DashBoardResponse getDashboardResponse(Integer userId);
	public String addEnqury( EnquiryForm enquiry);
	public List<EnquiryForm> getEnquiries(EnquirySearchCriteria search, Integer userId);
	public EnquiryForm getEnquiry(Integer enqId);
	


}
