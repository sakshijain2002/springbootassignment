package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.exception.CountryNotFoundException;
import com.example.demo.exception.InvalidCredentialException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.util.JwtUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Autowired
	private  AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtility util;
	
	//get all users
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}


	//get user by id
	public User getUserById(Long id) {

		return userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User with ID  + id +  not found"));
	}

	//register user
	public User registerUser(User user) {
		
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	//update record by id
	public User updateRecordById(Long id, User record) {
		User userRecord = userRepo.findById(id).orElseThrow(()->new UserNotFoundException("Country with ID " + id + " not found"));
		if (record.getPassword() != null) {
			throw new IllegalArgumentException("Password update is not allowed through this method.");
		}

		// Update only email and name if provided
		if (record.getEmail() != null) {
			userRecord.setEmail(record.getEmail());
		}
		if (record.getName() != null) {
			userRecord.setName(record.getName());
		}
		return userRepo.save(userRecord);
    }

	//delete user
	public void deleteUserById(Long id) {
		if (!userRepo.existsById(id)) {
			throw new UserNotFoundException("User with ID " + id + " not found");
		}
		userRepo.deleteById(id);
	}


	public User getUserByEmail(String token) {
		String emailFromToken = util.getUsername(token);
		return userRepo.findByEmail(emailFromToken).orElseThrow(() -> new RuntimeException("User Not Found"));
	}

	public void loginUser(String email, String password) {
		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));

		if (!pwdEncoder.matches(password, user.getPassword())) {
			throw new InvalidCredentialException("Invalid email or password");
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(email, password)
		);

		if (!authentication.isAuthenticated()) {
			throw new InvalidCredentialException("Authentication failed");
		}
	}

	//secure update method
	public User updateUserProfile(User updatedUser, String token) {
		// Extract email from token
		String emailFromToken = util.getUsername(token);

		// Fetch user from database by email
		User loggedInUser = userRepo.findByEmail(emailFromToken)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

		if (updatedUser.getPassword() != null) {
			throw new IllegalArgumentException("Password update is not allowed through this method.");
		}

		if (updatedUser.getName() != null) {
			loggedInUser.setName(updatedUser.getName());
		}
		if (updatedUser.getEmail() != null) {
			loggedInUser.setEmail(updatedUser.getEmail());
		}


		return userRepo.save(loggedInUser);
	}


	public void deleteUser(String token) {
		// Extract email from token
		String emailFromToken = util.getUsername(token);;

		// Fetch user from database by email
		User loggedInUser = userRepo.findByEmail(emailFromToken)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));


		userRepo.delete(loggedInUser);
	}
}
