package com.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portal.enitiy.StudentEnquiry;

public interface StudentEnquiryRepository extends JpaRepository<StudentEnquiry, Integer> {

	public long countByEnquiryStatus(String status);

}
