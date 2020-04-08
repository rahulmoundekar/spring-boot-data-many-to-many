package com.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.app.entity.User;
import com.app.repo.UserRepository;

import io.swagger.annotations.Api;

@Api(value = "UserRestController", description = "REST Apis related to User Entity!!!!")
@RestController
public class UserController {

	@Autowired
	private UserRepository UserRepository;

	@GetMapping("users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Integer id) {
		Optional<User> User = UserRepository.findById(id);
		if (User.isPresent())
			return new ResponseEntity<Optional<User>>(User, HttpStatus.OK);
		else
			return new ResponseEntity<String>("User Not Found", HttpStatus.OK);
	}

	@GetMapping("users")
	public ResponseEntity<?> getAllusers() {
		List<User> list = UserRepository.findAll();
		System.out.println(list);
		if (list.isEmpty())
			return new ResponseEntity<String>("User not Found", HttpStatus.OK);
		else
			return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

	@PostMapping("users")
	public ResponseEntity<Void> addUser(@RequestBody User User, UriComponentsBuilder builder) {
		boolean flag = UserRepository.save(User) != null;
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/users/{id}").buildAndExpand(User.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("users")
	public ResponseEntity<?> updateUser(@RequestBody User User) {
		User = UserRepository.save(User);
		if (User != null)
			return new ResponseEntity<User>(User, HttpStatus.OK);
		else
			return new ResponseEntity<String>("User Not Updated", HttpStatus.OK);
	}

	@DeleteMapping("users/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
		UserRepository.deleteById(id);
		return new ResponseEntity<String>("User Delete by" + id + "perosn id", HttpStatus.NO_CONTENT);
	}

}
