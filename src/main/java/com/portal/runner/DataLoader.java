package com.portal.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.portal.enitiy.Course;
import com.portal.enitiy.EnquiryStatus;
import com.portal.repository.CourseRepository;
import com.portal.repository.EnquiryStatusRepository;

@Component
public class DataLoader implements CommandLineRunner {
	
	@Autowired
	private CourseRepository courseRepo;
	@Autowired
	private EnquiryStatusRepository enquiryRepo;
	

	@Override
	public void run(String... args) throws Exception {
        
		courseRepo.deleteAll();
		enquiryRepo.deleteAll();
		
		EnquiryStatus s1= new EnquiryStatus();
		s1.setStarusName("New");
		EnquiryStatus s2= new EnquiryStatus();
		s2.setStarusName("Enrolled");
		EnquiryStatus s3= new EnquiryStatus();
		s3.setStarusName("Lost");
		
		List<EnquiryStatus> statusList = Arrays.asList(s1,s2,s3);
		enquiryRepo.saveAll(statusList);
		
		Course c1= new Course();
		c1.setCourseName("Java Full Stack");
		Course c2= new Course();
		c2.setCourseName("Devops");
		Course c3= new Course();
		c3.setCourseName("Python");
		Course c4= new Course();
		c4.setCourseName("Mern Stack full course");
		Course c5= new Course();
		c5.setCourseName("Data Science");
		Course c6= new Course();
		c6.setCourseName("Spoken English");
		Course c7= new Course();
		c7.setCourseName("Realtime Project Java");
		Course c8= new Course();
		c8.setCourseName("HTML/CSS/JavaScript");
		Course c9= new Course();
		c9.setCourseName("Linux");
		Course c10= new Course();
		c10.setCourseName("Java Placment Assitance ");
		
		List<Course> courseList = Arrays.asList(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10);	
		courseRepo.saveAll(courseList);
		
		
		
		

	}

}
