package com.wipro.course.access.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.course.access.entity.Course;
import com.wipro.course.access.entity.CourseVideos;

@Repository
public interface CourseVideosRepository extends JpaRepository<CourseVideos, Integer>{
	
	List<CourseVideos> findByCourseId(Course course);

}
