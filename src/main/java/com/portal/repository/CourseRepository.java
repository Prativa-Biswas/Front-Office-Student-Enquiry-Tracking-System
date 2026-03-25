package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.portal.enitiy.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

	@Query("select distinct courseName from Course")
	public List<String> getCourseName();
}
