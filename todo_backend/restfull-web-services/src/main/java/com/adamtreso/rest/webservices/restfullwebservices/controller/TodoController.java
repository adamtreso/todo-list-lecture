package com.adamtreso.rest.webservices.restfullwebservices.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adamtreso.rest.webservices.restfullwebservices.dto.TodoDto;
import com.adamtreso.rest.webservices.restfullwebservices.entity.Todo;
import com.adamtreso.rest.webservices.restfullwebservices.service.TodoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@GetMapping("users/{username}/todos")
	public ResponseEntity<List<TodoDto>> getAllTodos(@PathVariable final String username) {
		Optional<List<TodoDto>> response = todoService.getAllTodos(username);
		return response.isPresent() ? ResponseEntity.ok(response.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable final String username, @PathVariable final long id) {
		boolean isDeleted = todoService.deleteTodo(username, id);
		return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}

	@PutMapping("users/{username}/todos/{id}")
	public ResponseEntity<TodoDto> updateTodo(
			@PathVariable final String username,
			@PathVariable final long id,
			@RequestBody final Todo todo) {
		Optional<TodoDto> response = todoService.updateTodo(username, id, todo);
		return response.isPresent() ? ResponseEntity.ok(response.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping("users/{username}/todos")
	public ResponseEntity<URI> createTodo(@PathVariable final String username, @RequestBody final Todo todo) {
		Optional<URI> response = todoService.createTodo(username, todo);
		return response.isPresent() ? ResponseEntity.created(response.get()).build() : ResponseEntity.notFound().build();
	}

	@GetMapping("users/{username}/todos/{id}")
	public ResponseEntity<TodoDto> getTodo(@PathVariable final String username, @PathVariable final long id) {
		Optional<TodoDto> response = todoService.getTodo(username, id);
		return response.isPresent() ? ResponseEntity.ok(response.get()) : ResponseEntity.notFound().build();
	}
}
