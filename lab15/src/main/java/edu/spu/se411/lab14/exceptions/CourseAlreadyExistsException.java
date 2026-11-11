package edu.spu.se411.lab14.exceptions;

public class CourseAlreadyExistsException extends RuntimeException {

	public CourseAlreadyExistsException(Long id) {
		super("A course with id " + id + " already exists");
	}

}