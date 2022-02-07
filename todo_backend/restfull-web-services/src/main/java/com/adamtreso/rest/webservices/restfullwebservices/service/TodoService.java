package com.adamtreso.rest.webservices.restfullwebservices.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adamtreso.rest.webservices.restfullwebservices.entity.Todo;
import com.adamtreso.rest.webservices.restfullwebservices.repository.TodoRepository;

@Service
public class TodoService{
	
	@Autowired
	private TodoRepository todoRepos;
	
	public List<Todo> getAllTodos(String username){
		Optional<List<Todo>> resoult = todoRepos.findByUsernameOrderById(username);
		if (resoult.isEmpty())
			return null;	
		return resoult.get();
	}
	public boolean deleteTodo(String username,  long id){
		Optional<Todo> response = todoRepos.findOneByUsernameAndId(username, id);
		
		if (response.isEmpty())
			return false;
		
		todoRepos.deleteById(response.get().getId());
		return true;
	}
	
	public Todo updateTodo(String username, long id, Todo todo){
		Optional<Todo> response = todoRepos.findOneByUsernameAndId(username, id);
		
		if (response.isEmpty())
			return null;
		
		todo.setId(response.get().getId());
		todo.setUsername(response.get().getUsername());
		Todo savedTodo = todoRepos.save(todo);
		return savedTodo;
	}
	
	public URI createTodo(String username, Todo todo){
		Todo savedTodo = todoRepos.save(todo);
		String uriString = "users/" + username + "/todos/" + savedTodo.getId();
		return URI.create(uriString);
	}
	
	public Todo findTodo(String username, long id){
		Optional<Todo> resoult = todoRepos.findOneByUsernameAndId(username, id);
		if (resoult.isEmpty())
			return null;
		return resoult.get();
	}
}
