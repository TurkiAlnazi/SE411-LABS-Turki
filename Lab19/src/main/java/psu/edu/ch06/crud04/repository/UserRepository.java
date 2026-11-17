package psu.edu.ch06.crud04.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import psu.edu.ch06.crud04.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT id, username, password FROM app_user WHERE username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
