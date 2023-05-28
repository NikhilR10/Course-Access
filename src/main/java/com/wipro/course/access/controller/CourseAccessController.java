package com.wipro.course.access.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.course.access.entity.Course;
import com.wipro.course.access.service.CourseAccessService;

@RestController
public class CourseAccessController {
	
	@Autowired
	private CourseAccessService courseAccessService;
	
	@GetMapping("/courses")
	public ResponseEntity<List<Course>> viewAllCourses() {
		return courseAccessService.viewAllCourses();
	}
	
	@GetMapping("/courses/details")
	public ResponseEntity<String> viewCourseDetails(@RequestParam("courseId")Integer id){
		return courseAccessService.viewCourseDetails(id);
	}
	
	@GetMapping("/courses/videos")
	public ResponseEntity<String> viewCourseVideos(@RequestParam("courseId")Integer id){
		return courseAccessService.viewCourseVideos(id);
	}
	
	@GetMapping("/courses/videos")
	public ResponseEntity<?> viewVideo(@RequestParam("videoId")Integer id, HttpServletRequest request){
		String userId = request.getHeader("user_id");
		return courseAccessService.viewVideo(id, userId);
	}
}
