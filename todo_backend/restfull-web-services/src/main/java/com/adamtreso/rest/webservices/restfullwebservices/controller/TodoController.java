package com.adamtreso.rest.webservices.restfullwebservices.controller;

import java.net.URI;
import java.util.List;

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

import com.adamtreso.rest.webservices.restfullwebservices.entity.Todo;
import com.adamtreso.rest.webservices.restfullwebservices.service.TodoService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@GetMapping("users/{username}/todos")
	public ResponseEntity<List<Todo>> getAllTodos(@PathVariable String username){
		List<Todo> todoList = todoService.getAllTodos(username);
		if (todoList == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(todoList);
	}
	
	@DeleteMapping("users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id){
		boolean isDeleted = todoService.deleteTodo(username, id);
		if (isDeleted)
			return ResponseEntity.ok().build();
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Todo todo){
		Todo updatedTodo = todoService.updateTodo(username, id, todo);
		if (updatedTodo == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(updatedTodo);
	}
	
	@PostMapping("users/{username}/todos")
	public ResponseEntity<Object> createTodo(@PathVariable String username, @RequestBody Todo todo){
		URI uri = todoService.createTodo(username, todo);
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("users/{username}/todos/{id}")
	public ResponseEntity<Todo> findTodo(@PathVariable String username, @PathVariable long id){
		Todo todo = todoService.findTodo(username, id);
		if (todo == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(todo);
	}
}
