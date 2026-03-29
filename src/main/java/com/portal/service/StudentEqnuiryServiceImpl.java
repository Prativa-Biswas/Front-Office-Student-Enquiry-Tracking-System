package com.portal.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.dto.DashBoardResponse;
import com.portal.dto.EnquiryForm;
import com.portal.dto.EnquirySearchCriteria;
import com.portal.enitiy.StudentEnquiry;
import com.portal.enitiy.UserDetails;
import com.portal.repository.CourseRepository;
import com.portal.repository.EnquiryStatusRepository;
import com.portal.repository.StudentEnquiryRepository;
import com.portal.repository.UserDetailsRepository;

@Service
public  class StudentEqnuiryServiceImpl implements StudentEnquiryService {

	@Autowired
	private CourseRepository courseRepo;
	@Autowired
	private EnquiryStatusRepository statusRepo;
	@Autowired
	private StudentEnquiryRepository enquiryRepo;
	
	@Autowired
	private  UserDetailsRepository userRepo;
	
	@Override
	public List<String> getCourseName() {
		return courseRepo.getCourseName();
	}

	@Override
	public List<String> getStatusName() {
		return statusRepo.getEnuiryStatus();
	}

	@Override
	public DashBoardResponse getDashboardResponse(Integer userId) {
		
		
		List<String> statusList = enquiryRepo.findStusentEnqueryStatus(userId);
		DashBoardResponse response= new DashBoardResponse();

		if (statusList == null) {
			response.setTotalEnquiery(0);
			response.setEnrolledEnquiery(0);
			response.setLostEnquiery(0);
			return response;
		}
		
		int totalCount = statusList.size();
		long lostCount = statusList.stream().filter(n->n.equalsIgnoreCase("LOST")).count();
		long enrolledCount = statusList.stream().filter(n->n.equalsIgnoreCase("ENROLLED")).count();
		
		response.setTotalEnquiery(totalCount);
		response.setEnrolledEnquiery(Math.toIntExact(enrolledCount));
		response.setLostEnquiery(Math.toIntExact(lostCount));
		
		return response;
	}

	@Override
	public Boolean addEnqury(EnquiryForm enquiry, Integer userId) {
		
		StudentEnquiry enquiryEntity= new StudentEnquiry();
		if(enquiry.getStudentName()!=null && !enquiry.getStudentName().isBlank())
		{
			enquiryEntity.setStudentName(enquiry.getStudentName());
		}
		else { return false;}
		
		if(enquiry.getContactNo()!=null)
		{
			enquiryEntity.setContactNo(enquiry.getContactNo());
		}
		else { return false;}
		
		if(enquiry.getCourseName()!=null && !enquiry.getCourseName().isBlank())
		{
			enquiryEntity.setCourseName(enquiry.getCourseName());
		}
		else { return false;}
		
		if(enquiry.getClassMode()!=null && !enquiry.getClassMode().isBlank())
		{
			enquiryEntity.setClassMode(enquiry.getClassMode());
		}
		else { return false;}

		if(enquiry.getEnquiryStatus()!=null && !enquiry.getEnquiryStatus().isBlank())
		{
			enquiryEntity.setEnquiryStatus(enquiry.getEnquiryStatus());
		}
		else { return false;}

		enquiryEntity.setEnquiryDate(LocalDate.now());
		UserDetails user = userRepo.findById(userId).orElse(null);
		if(user!=null){
			enquiryEntity.setUser(user);
		}
		enquiryRepo.save(enquiryEntity);
		
		return true;
	}

	@Override
	public List<EnquiryForm> getEnquiries(EnquirySearchCriteria search, Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnquiryForm getEnquiry(Integer enqId) {
		// TODO Auto-generated method stub
		return null;
	}



}
