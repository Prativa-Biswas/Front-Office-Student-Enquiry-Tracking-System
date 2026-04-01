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
	public List<StudentEnquiry> getEnquiries( Integer userId) {
		
		Optional<UserDetails> userEntity = userRepo.findById(userId);
		if(userEntity.isPresent())
		{
			UserDetails userDetails = userEntity.get();
			List<StudentEnquiry> enquiryList = userDetails.getEnquiryList();
			return enquiryList;
		}
		
		return null;
	}
	
	public  List<StudentEnquiry>  findFilterEnqueries(EnquirySearchCriteria criteria, Integer userId) {
		
		Optional<UserDetails> userOpt = userRepo.findById(userId);
		if(userOpt.isPresent()) {
		
          List<StudentEnquiry> enquiryList = userOpt.get().getEnquiryList();
		
				if(criteria.getCourseName()!=null && !criteria.getCourseName().isBlank()) {
				
					enquiryList = enquiryList.stream().filter(enq->enq.getCourseName().equals(criteria.getCourseName())).collect(Collectors.toList());
				}
		
				if(criteria.getEnquiryStatus()!=null && !criteria.getEnquiryStatus().isBlank()) {
					
					enquiryList = enquiryList.stream().filter(enq->enq.getEnquiryStatus().equals(criteria.getEnquiryStatus())).collect(Collectors.toList());
				}
		
				if(criteria.getClassMode()!=null && !criteria.getClassMode().isBlank()) {
							
							enquiryList = enquiryList.stream().filter(enq->enq.getClassMode().equals(criteria.getClassMode())).collect(Collectors.toList());
				}
		
		return enquiryList;
		}
		
		return null;
		
	}

	@Override
	public EnquiryForm getEnquiry(Integer enqId, Integer userId) {
           Optional<UserDetails> userOpt = userRepo.findById(userId);
           if(userOpt.isPresent()) {
        	  EnquiryForm enquiryForm = new EnquiryForm ();
        	   UserDetails userDetails = userOpt.get();
        	   List<StudentEnquiry> enquiryList = userDetails.getEnquiryList();
        	   Optional<StudentEnquiry> first = enquiryList.stream().filter(e->e.getEnquiryId().equals(enqId)).findFirst();
        	   if(first.isPresent())
        	   {
        		   StudentEnquiry studentEnquiry = first.get();
        		   enquiryForm.setStudentName(studentEnquiry.getStudentName());
        		   enquiryForm.setContactNo(studentEnquiry.getContactNo());
        		   enquiryForm.setClassMode(studentEnquiry.getClassMode());
        		   enquiryForm.setCourseName(studentEnquiry.getCourseName());
        		   enquiryForm.setEnquiryStatus(studentEnquiry.getEnquiryStatus());
        		   enquiryForm.setEnquiryId(enqId);
        		   
        		   return enquiryForm;
        	   }
        		   return null;
           }				
		return null;
	}
	
	
	@Override
	public String updateEnquiry(EnquiryForm enquiry, Integer userId) {
	    System.out.println(enquiry.getEnquiryId());

	    Optional<UserDetails> userOpt = userRepo.findById(userId);
	    if (userOpt.isEmpty()) return "User Not found";

	    List<StudentEnquiry> enquiryList = userOpt.get().getEnquiryList();
	    if (enquiryList == null || enquiryList.isEmpty()) return "Enquiries not found for the user";

	    Optional<StudentEnquiry> studentOpt = enquiryList.stream()
	            .filter(e -> e.getEnquiryId().equals(enquiry.getEnquiryId()))
	            .findFirst();

	    if (studentOpt.isEmpty()) return "Enquiry Not found";

	    StudentEnquiry studentEnquiry = studentOpt.get();
	    studentEnquiry.setStudentName(enquiry.getStudentName());
	    studentEnquiry.setCourseName(enquiry.getCourseName());
	    studentEnquiry.setContactNo(enquiry.getContactNo());
	    studentEnquiry.setClassMode(enquiry.getClassMode());
	    studentEnquiry.setEnquiryStatus(enquiry.getEnquiryStatus());
	    studentEnquiry.setUpdateDate(LocalDate.now());

	    enquiryRepo.save(studentEnquiry);
	    return "SUCCESS";
	}	

}
