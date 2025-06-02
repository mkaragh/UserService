package com.tripiq.userservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		
		System.out.println("+++++++++++++ inside api ++++++++++++");

		try {
			List<User> users = userService.getUsers();
			return new ResponseEntity<>(users, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
		try {
			User user = userService.getUser(id);

			return new ResponseEntity<>(user, HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		try {

			User userFromDB = userService.saveUser(user);

			return new ResponseEntity<>(userFromDB, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user) {
		try {
			return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

}
