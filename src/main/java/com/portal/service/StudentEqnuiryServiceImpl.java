package com.portal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		
		DashBoardResponse response= new DashBoardResponse();
		Optional<UserDetails> userEntity = userRepo.findById(userId);
		if(userEntity.isPresent())
		{
			UserDetails userDetails = userEntity.get();
			List<StudentEnquiry> enquiryList = userDetails.getEnquiryList();
			
			int totalCount = enquiryList.size();
			int lostCount = enquiryList.stream().filter(n->n.getEnquiryStatus().equalsIgnoreCase("LOST")).collect(Collectors.toList()).size();
			int enrolledCount = enquiryList.stream().filter(n->n.getEnquiryStatus().equalsIgnoreCase("ENROLLED")).collect(Collectors.toList()).size();
			
			response.setTotalEnquiery(totalCount);
			response.setEnrolledEnquiery(enrolledCount);
			response.setLostEnquiery(lostCount);
		}
		
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
