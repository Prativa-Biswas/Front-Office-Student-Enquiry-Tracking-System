package com.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portal.enitiy.UserDetails;
import java.util.List;


public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {

	public UserDetails getByEmail(String email);
}
