package com.adamtreso.rest.webservices.restfullwebservices.todo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoJpaRepository extends JpaRepository<Todo, Long>{
	Optional<List<Todo>> findByUsernameOrderById(String username);
	
	Optional<Todo> findOneByUsernameAndId(String username, Long id);
}
