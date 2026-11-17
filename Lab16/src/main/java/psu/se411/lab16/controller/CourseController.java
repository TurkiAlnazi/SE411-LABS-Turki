package psu.se411.lab16.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import psu.se411.lab16.model.Course;
import psu.se411.lab16.repository.CourseRepository;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Course>> findAll() {
        return ResponseEntity.ok(courseRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createCourse(@RequestBody Course newCourse, UriComponentsBuilder ucb) {
        Course saved = courseRepository.save(newCourse);
        URI location = ucb.path("/api/v1/courses/{id}").buildAndExpand(saved.id()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> findById(@PathVariable Integer courseId) {
        return courseRepository.findById(courseId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Void> updateCourse(@PathVariable Integer courseId, @RequestBody Course update) {
        Optional<Course> existing = courseRepository.findById(courseId);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Course updated = new Course(courseId, update.code(), update.name(), update.description(), update.credits());
        courseRepository.save(updated);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer courseId) {
        if (!courseRepository.existsById(courseId)) {
            return ResponseEntity.notFound().build();
        }
        courseRepository.deleteById(courseId);
        return ResponseEntity.noContent().build();
    }
}
