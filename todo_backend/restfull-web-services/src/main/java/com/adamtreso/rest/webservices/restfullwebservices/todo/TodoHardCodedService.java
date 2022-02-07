package com.adamtreso.rest.webservices.restfullwebservices.todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TodoHardCodedService {
	
	private static List<Todo> todos = new ArrayList<Todo>();
	
	private static long idCounter = 0;
	static {
		todos.add(new Todo(++idCounter, "adamtreso", "Learn to code 2", new Date(), false)); 
		todos.add(new Todo(++idCounter, "adamtreso", "Learn about microservices", new Date(), false));
		todos.add(new Todo(++idCounter, "adamtreso", "Learn about angular", new Date(), false));
	}
	
	public List<Todo> findAll(){
		return todos;
	}
	
	public Todo deleteById(long id) {
		Todo todo = findById(id);
		if (todo != null) {
			todos.remove(todo);
			return todo;
		}
		return null;
	}
	
	public Todo findById(long id) {
		for (Todo todo : todos) {
			if (todo.getId() == id)
				return todo;
		}
		return null;
	}
	
	public Todo save(Todo todo) {
		if (todo.getId() == -1) {
			todo.setId(++idCounter);
		}else {
			deleteById(todo.getId());
		}
		todos.add(todo);
		return todo;
	}
}
