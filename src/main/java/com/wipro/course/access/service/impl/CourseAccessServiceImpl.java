package com.wipro.course.access.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.course.access.entity.Course;
import com.wipro.course.access.entity.CourseVideos;
import com.wipro.course.access.repository.CourseRepository;
import com.wipro.course.access.repository.CourseVideosRepository;
import com.wipro.course.access.service.CourseAccessService;

@Service
public class CourseAccessServiceImpl implements CourseAccessService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private CourseVideosRepository courseVideoRepository;

	@Autowired
	private ObjectMapper mapper;

	private static final String USER_DIRECTORY = "user.dir";
	private static final String FILE_SEPARATOR = "\\";

	@Override
	public ResponseEntity<List<Course>> viewAllCourses() {
		List<Course> courses = courseRepository.findAll();
		List<Course> response = new ArrayList<>();
		courses.forEach(course -> {
			Course c = new Course();
			c.setId(course.getId());
			c.setName(course.getName());
			c.setCost(course.getCost());
			c.setCreatedBy(course.getCreatedBy());
			response.add(c);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> viewCourseDetails(Integer id) {
		try {
			Optional<Course> course = courseRepository.findById(id);
			if (course.isPresent()) {
				return new ResponseEntity<>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(course.get()),
						HttpStatus.OK);
			}
			return new ResponseEntity<>("Course not available", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred, Please try again!", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<String> viewCourseVideos(Integer courseId) {
		try {
			Optional<Course> course = courseRepository.findById(courseId);
			List<CourseVideos> response = new ArrayList<>();
			if (course.isPresent()) {
				List<CourseVideos> videos = courseVideoRepository.findByCourseId(course.get());
				videos.forEach(video -> {
					CourseVideos v = new CourseVideos();
					v.setId(video.getId());
					v.setFileName(video.getFileName());
					response.add(v);
				});
				return new ResponseEntity<>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response),
						HttpStatus.OK);
			}
			return new ResponseEntity<>("No videos are available", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("An error has occurred! Please try again", HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<byte[]> viewVideo(Integer videoId, String userId) {
		try {
			Optional<CourseVideos> video = courseVideoRepository.findById(videoId);
			if (video.isPresent()) {
				String videoPath = new File(System.getProperty(USER_DIRECTORY)).getParent() + FILE_SEPARATOR
						+ "Course Videos" + FILE_SEPARATOR + userId + FILE_SEPARATOR
						+ video.get().getCourseId().getName() + FILE_SEPARATOR + video.get().getFileName();
				return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "video/mp4")
						.header("Accept-Ranges", "bytes").body(Files.readAllBytes(new File(videoPath).toPath()));
			}
			return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new byte[0], HttpStatus.BAD_REQUEST);
		}
	}
}
