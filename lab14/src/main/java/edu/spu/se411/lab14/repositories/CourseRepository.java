package edu.spu.se411.lab14.repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.spu.se411.lab14.model.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

	// save() treats a record that already has an id as an UPDATE, so inserting a
	// course with a client-chosen id needs an explicit INSERT statement.
	@Modifying
	@Query("INSERT INTO course (id, code, name, description, credits) "
			+ "VALUES (:id, :code, :name, :description, :credits)")
	void insertWithId(@Param("id") Long id,
			@Param("code") String code,
			@Param("name") String name,
			@Param("description") String description,
			@Param("credits") Integer credits);

}