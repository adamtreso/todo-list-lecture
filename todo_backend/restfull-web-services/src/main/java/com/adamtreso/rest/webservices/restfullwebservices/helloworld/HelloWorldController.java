package com.adamtreso.rest.webservices.restfullwebservices.helloworld;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class HelloWorldController {
	//hello-world string
	//returning a hardcoded string
	@GetMapping(path="/hello-world")	//mapped the method to the '/hello-world' GET request
	public String helloWorld() {
		return "Hello World!";
	}
	
	//hello-world-bean
	//returning an object
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		//if u want to send back an object
		return new HelloWorldBean("Hello World! - new");	//kell a getter (?setter, ?toString)
		
		//if u want to send back an error response
		//throw new RuntimeException("Some error happened!");
	}
	
	//hello-world-bean
	//returning 
	//hello-world/path-variable/adamtreso
	@GetMapping(path="/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean("Have a great day " + name + "!");
	}
}
