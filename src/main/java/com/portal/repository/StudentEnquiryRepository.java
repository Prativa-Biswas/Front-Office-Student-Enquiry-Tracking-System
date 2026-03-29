package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.portal.enitiy.StudentEnquiry;

public interface StudentEnquiryRepository extends JpaRepository<StudentEnquiry, Integer> {

	@Query("select s.enquiryStatus from StudentEnquiry s where s.user.userId=:userId")
	public List<String> findStusentEnqueryStatus(@Param("userId") Integer userId);

}
