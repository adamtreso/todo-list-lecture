package com.adamtreso.rest.webservices.restfullwebservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adamtreso.rest.webservices.restfullwebservices.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{
	Optional<List<Todo>> findByUsernameOrderById(String username);
	
	Optional<Todo> findOneByUsernameAndId(String username, Long id);
}
