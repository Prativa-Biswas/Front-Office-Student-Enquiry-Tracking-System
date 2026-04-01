package com.portal.dto;

import lombok.Data;

@Data
public class EnquiryForm {

	private Integer enquiryId;
	private String studentName;
	private Long contactNo;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
}
