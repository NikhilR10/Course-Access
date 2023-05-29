package com.wipro.course.access.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.course.access.entity.User;
import com.wipro.course.access.entity.UserCourses;

@Repository
public interface UserCoursesRepository extends JpaRepository<UserCourses, Integer>{
	
	List<UserCourses> findByUserNameAndPaymentStatus(User user, String paymentStatus);

}
