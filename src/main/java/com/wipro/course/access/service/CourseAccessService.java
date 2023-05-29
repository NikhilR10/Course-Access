package com.wipro.course.access.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.wipro.course.access.entity.Course;
import com.wipro.course.access.entity.UserCourses;

public interface CourseAccessService {
	
	ResponseEntity<List<Course>> viewAllCourses();
	
	ResponseEntity<String> viewCourseDetails(Integer id);
	
	ResponseEntity<String> viewCourseVideos(Integer courseId);
	
	ResponseEntity<byte[]> viewVideo(Integer videoId, String userId);
	
	ResponseEntity<List<UserCourses>> getAllUserCourses(String userId);
}