package com.portal.service;

import java.util.List;

import com.portal.dto.DashBoardResponse;
import com.portal.dto.EnquiryForm;
import com.portal.dto.EnquirySearchCriteria;
import com.portal.enitiy.StudentEnquiry;

public interface StudentEnquiryService {
	
	public List<String> getCourseName();
	public List<String> getStatusName();
	public  DashBoardResponse getDashboardResponse(Integer userId);
	public Boolean addEnqury( EnquiryForm enquiry, Integer userId);
	public List<StudentEnquiry> getEnquiries( Integer userId);
	public  List<StudentEnquiry>  findFilterEnqueries(EnquirySearchCriteria criteria, Integer userId);
	
	public EnquiryForm getEnquiry(Integer enqId, Integer userId);
	public String updateEnquiry( EnquiryForm enquiry, Integer userId);
	


}
