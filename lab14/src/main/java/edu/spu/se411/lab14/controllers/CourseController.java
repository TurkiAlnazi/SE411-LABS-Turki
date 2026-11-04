package edu.spu.se411.lab14.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.spu.se411.lab14.exceptions.CourseAlreadyExistsException;
import edu.spu.se411.lab14.model.Course;
import edu.spu.se411.lab14.services.CourseService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	// POST /api/v1/courses -> 201 Created + Location header
	//                         400 invalid body (validation) / 422 id already exists
	@PostMapping
	public ResponseEntity<Course> create(@Valid @RequestBody Course course) {
		Course created = courseService.create(course);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(created.id())
				.toUri();

		return ResponseEntity.created(location).body(created);
	}

	// GET /api/v1/courses -> 200
	@GetMapping
	public ResponseEntity<Iterable<Course>> findAll() {
		return ResponseEntity.ok(courseService.findAll());
	}

	// GET /api/v1/courses/{id} -> 200 / 404
	@GetMapping("/{id}")
	public ResponseEntity<Course> findById(@PathVariable Long id) {
		return courseService.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	// PUT /api/v1/courses/{id} -> 204 No Content / 404
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody Course course) {
		return courseService.update(id, course)
				? ResponseEntity.noContent().build()
				: ResponseEntity.notFound().build();
	}

	// DELETE /api/v1/courses/{id} -> 204 No Content / 404
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		return courseService.delete(id)
				? ResponseEntity.noContent().build()
				: ResponseEntity.notFound().build();
	}

	// id already exists on POST -> 422 Unprocessable (status given by number)
	@ExceptionHandler(CourseAlreadyExistsException.class)
	public ResponseEntity<String> handleAlreadyExists(CourseAlreadyExistsException e) {
		return ResponseEntity.status(422).body(e.getMessage());
	}

}