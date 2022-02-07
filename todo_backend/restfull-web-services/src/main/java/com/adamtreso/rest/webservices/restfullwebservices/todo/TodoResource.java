package com.adamtreso.rest.webservices.restfullwebservices.todo;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class TodoResource {
	/*
	@Autowired
	private TodoHardCodedService todoService;
	
	
	@GetMapping("users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
		return todoService.findAll();	//not dependent from username  
	}

	@DeleteMapping("users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id){
		//not dependent from username
		if (todoService.deleteById(id) != null)
			return ResponseEntity.noContent().build(); 
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Todo todo){
		//if(todoService.updateById(id, description, targetDate)
		Todo updatedTodo = todoService.save(todo);
		return new ResponseEntity<Todo>(updatedTodo, HttpStatus.OK);
	}
	
	@PostMapping("users/{username}/todos")
	public ResponseEntity<Todo> createTodo(@PathVariable String username, @RequestBody Todo todo){
		//if(todoService.updateById(id, description, targetDate)
		todo.setId(-1);
		Todo createdTodo = todoService.save(todo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("users/{username}/todos/{id}")
	public Todo retrieveTodo(@PathVariable String username, @PathVariable long id){
		return todoService.findById(id);
	}*/
	
	@Autowired
	private TodoJpaRepository todoRepos;
	
	@GetMapping("users/{username}/todos")
	public ResponseEntity<List<Todo>> getAllTodos(@PathVariable String username){
		Optional<List<Todo>> resoult = todoRepos.findByUsernameOrderById(username);
		if (resoult.isEmpty())
			return ResponseEntity.notFound().build();
		return new ResponseEntity<List<Todo>>(resoult.get(), HttpStatus.OK);
	}
	
	@DeleteMapping("users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id){
		Optional<Todo> response = todoRepos.findOneByUsernameAndId(username, id);
		if (response.isEmpty())
			return ResponseEntity.notFound().build();
		todoRepos.deleteById(response.get().getId());
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Todo todo){
		Optional<Todo> response = todoRepos.findOneByUsernameAndId(username, id);
		if (response.isEmpty())
			return ResponseEntity.notFound().build();
		//rewrite the username and id so we are sure that the entity is overwrited
		todo.setId(response.get().getId());
		todo.setUsername(response.get().getUsername());
		todoRepos.save(todo);
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
	}
	
	@PostMapping("users/{username}/todos")
	public ResponseEntity<Void> createTodo(@PathVariable String username, @RequestBody Todo todo){
		Todo savedTodo = todoRepos.save(todo);
		String uriString = "users/" + username + "/todos/" + savedTodo.getId(); 
		URI uri = URI.create(uriString);
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("users/{username}/todos/{id}")
	public ResponseEntity<Todo> findTodo(@PathVariable String username, @PathVariable long id){
		Optional<Todo> resoult = todoRepos.findOneByUsernameAndId(username, id);
		if (resoult.isEmpty())
			return ResponseEntity.notFound().build();
		return new ResponseEntity<Todo>(resoult.get(), HttpStatus.OK);
	}
}
