package com.example.demo.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.entity.UserRequest;
import com.example.demo.entity.UserResponse;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtility;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "API for User Management")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtility util;

	@Operation(summary = "Get All Users", description = "Retrieve a list of all registered users")
	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}

	@Operation(summary = "Get User By ID", description = "Retrieve a user by their ID")
	@GetMapping("/getById/{id}")
	public User getById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@Operation(summary = "Get Logged-in User Info", description = "Retrieve details of the currently logged-in user",
			security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("/me")
	public ResponseEntity<User> getUser(@RequestHeader("Authorization") String token) {
		String email = token.replace("Bearer ", "");
		return ResponseEntity.ok(userService.getUserByEmail(email));
	}

	@Operation(summary = "Register User", description = "Register a new user and store their details securely")
	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		return userService.registerUser(user);
	}

	@Operation(summary = "Login User", description = "Authenticate user and return JWT token")
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request) {
		userService.loginUser(request.getEmail(), request.getPassword());
		String token = util.generateToken(request.getEmail());
		return ResponseEntity.ok(new UserResponse(token, "Success"));
	}

	//SECURE API TO UPDATE RECORD
	@Operation(summary = "Update User Profile", description = "Update the currently logged-in user's information & if the email of the user is updated then generate token again to access secure api's",
			security = @SecurityRequirement(name = "bearerAuth"))
	@PutMapping("/update")
	public ResponseEntity<User> updateUser(
			@RequestBody User updatedUser,
			@RequestHeader("Authorization") String token) {

		String extractedToken = token.replace("Bearer ", "");
		return ResponseEntity.ok(userService.updateUserProfile(updatedUser, extractedToken));
	}


	@Operation(summary = "Update User Profile By Id", description = "Update the user by id without token ")
	@PutMapping("/updateById/{id}")
	public ResponseEntity<User> updateUserById(@PathVariable Long id,@RequestBody User user){
		return ResponseEntity.ok(userService.updateRecordById(id,user));
	}


	@Operation(summary = "Delete User Account", description = "Delete the currently logged-in user's account",
			security = @SecurityRequirement(name = "bearerAuth"))
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token) {
		String extractedToken = token.replace("Bearer ", "");
		userService.deleteUser(extractedToken);
		return ResponseEntity.ok("User deleted successfully");
	}

	@Operation(summary = "Delete User Account", description = "Delete the user by id")
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
		userService.deleteUserById(id);
		return ResponseEntity.ok("User deleted successfully");
	}




}
