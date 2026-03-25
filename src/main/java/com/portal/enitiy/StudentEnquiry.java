package com.portal.enitiy;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class StudentEnquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enquiryId;
	private String studentName;
	private Long contactNo;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
	private LocalDate enquiryDate;
	private LocalDate updateDate;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private UserDetails user;
}
