package edu.spu.se411.lab14.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.spu.se411.lab14.exceptions.CourseAlreadyExistsException;
import edu.spu.se411.lab14.model.Course;
import edu.spu.se411.lab14.repositories.CourseRepository;

@Service
public class CourseService {

	private final CourseRepository courseRepository;

	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public Iterable<Course> findAll() {
		return courseRepository.findAll();
	}

	public Optional<Course> findById(Long id) {
		return courseRepository.findById(id);
	}

	/** Creates a course. If the client supplies an id it must not exist yet. */
	public Course create(Course course) {

		if (course.id() == null) {
			
			// database generate id
			
			return courseRepository.save(course);
		}

		if (courseRepository.existsById(course.id())) {
			throw new CourseAlreadyExistsException(course.id());
		}

		courseRepository.insertWithId(course.id(), course.code(), course.name(),
				course.description(), course.credits());
		return course;
	}

	/** @return false when no course has this id. */
	public boolean update(Long id, Course course) {

		if (!courseRepository.existsById(id)) {
			return false;
		}

		
		courseRepository.save(new Course(id, course.code(), course.name(),
				course.description(), course.credits()));
		return true;
	}

	/** @return false when no course has this id. */
	public boolean delete(Long id) {

		if (!courseRepository.existsById(id)) {
			return false;
		}

		courseRepository.deleteById(id);
		return true;
	}

}