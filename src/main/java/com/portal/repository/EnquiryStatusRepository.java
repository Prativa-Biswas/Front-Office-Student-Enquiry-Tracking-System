package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.portal.enitiy.EnquiryStatus;

public interface EnquiryStatusRepository extends JpaRepository<EnquiryStatus, Integer> {
	
	@Query("select distinct starusName  from EnquiryStatus")
    public List<String> getEnuiryStatus();
}
