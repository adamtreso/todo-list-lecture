package com.adamtreso.rest.webservices.restfullwebservices.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adamtreso.rest.webservices.restfullwebservices.common.ExtendedConversionService;
import com.adamtreso.rest.webservices.restfullwebservices.dto.TodoDto;
import com.adamtreso.rest.webservices.restfullwebservices.entity.Todo;
import com.adamtreso.rest.webservices.restfullwebservices.entity.User;
import com.adamtreso.rest.webservices.restfullwebservices.repository.TodoRepository;
import com.adamtreso.rest.webservices.restfullwebservices.test.UserRepository;

@Service
public class TodoService {

	@Autowired
	private ExtendedConversionService extendedConvService;

	@Autowired
	private TodoRepository todoRepos;

	@Autowired
	private UserRepository userRepos;

	public Optional<List<TodoDto>> getAllTodos(final String username) {
		Optional<User> userResoult = userRepos.findByUsername(username);
		if (userResoult.isPresent()) {
			List<Todo> resoult = todoRepos.findByUsernameOrderById(username);
			return Optional.of(extendedConvService.convert(resoult, TodoDto.class));
		}
		return Optional.empty();
	}

	public boolean deleteTodo(final String username, final long id) {
		Optional<Todo> response = todoRepos.findOneByUsernameAndId(username, id);

		if (response.isPresent()) {
			todoRepos.deleteById(response.get().getId());
			return true;
		}
		return false;
	}

	public Optional<TodoDto> updateTodo(final String username, final long id, final Todo todo) {
		Optional<Todo> response = todoRepos.findOneByUsernameAndId(username, id);

		if (response.isPresent()) {
			todo.setId(response.get().getId());
			todo.setUsername(response.get().getUsername());
			Todo updatedTodo = todoRepos.save(todo);
			return Optional.of(extendedConvService.convert(updatedTodo, TodoDto.class));
		}
		return Optional.empty();
	}

	public URI createTodo(final String username, final Todo todo) {
		Todo savedTodo = todoRepos.save(todo);
		String uriString = "users/" + username + "/todos/" + savedTodo.getId();
		return URI.create(uriString);
	}

	public Optional<TodoDto> getTodo(final String username, final long id) {
		Optional<Todo> resoult = todoRepos.findOneByUsernameAndId(username, id);
		if (resoult.isPresent()) {
			return Optional.of(extendedConvService.convert(resoult.get(), TodoDto.class));
		}
		return Optional.empty();
	}
}
