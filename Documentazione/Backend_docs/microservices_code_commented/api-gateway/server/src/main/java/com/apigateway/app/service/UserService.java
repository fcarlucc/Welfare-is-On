package com.apigateway.app.service;

import java.util.Optional;

import com.apigateway.app.exception.EmailNotFoundException;
import com.apigateway.app.exception.UserIdNotFoundException;
import com.apigateway.app.model.User;
import com.apigateway.app.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Service class for managing User entities.
 */
@Service
@RequiredArgsConstructor
public class UserService {

	private final IUserRepository userRepository;
	private final WebClient webClient;

	@Value("${user.service.name}")
	private String userServiceIp;

	@Value("${user.service.port}")
	private String userServicePort;

	/**
	 * Retrieves all User entities.
	 * @return Iterable User containing all User entities.
	 */
	public Iterable<User> getAll() {
		return userRepository.findAll();
	}

	/**
	 * Retrieves a User by its ID.
	 * @param id Long representing the ID of the user to retrieve.
	 * @return User object corresponding to the provided ID.
	 * @throws UserIdNotFoundException if the user with the given ID is not found.
	 */
	public User getById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new UserIdNotFoundException("User not found");
		}
		return user.get();
	}

	/**
	 * Creates a new User.
	 * @param user User object representing the user to create.
	 * @return User object that was created.
	 */
	public User create(User user) {
		if (user.getPassword() != null) {
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			String encryptedPwd = bcrypt.encode(user.getPassword());
			user.setPassword(encryptedPwd);
		}
		return userRepository.save(user);
	}

	/**
	 * Updates an existing User.
	 * @param user User object representing the user to update.
	 * @return User object that was updated.
	 */
	public User update(User user) {
		return userRepository.save(user);
	}

	/**
	 * Deletes a User by its ID.
	 * @param id Long representing the ID of the user to delete.
	 * @return true if the user was successfully deleted, false otherwise.
	 */
	public Boolean delete(Long id) {
		Optional<User> foundUser = userRepository.findById(id);
		if (foundUser.isEmpty()) {
			return false;
		}
		userRepository.delete(foundUser.get());
		return true;
	}

	/**
	 * Finds a User by their email address.
	 * @param email String representing the email address of the user to find.
	 * @return User object corresponding to the provided email address.
	 * @throws EmailNotFoundException if the user with the given email address is not found.
	 */
	public User findByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isEmpty()) {
			throw new EmailNotFoundException("User email does not exist");
		}
		return user.get();
	}

	/**
	 * Checks if a user is correctly profiled thanks to the user service.
	 * @param email String representing the email address of the user to check.
	 * @return true if the user profile is completed, false otherwise or on error.
	 */
	public boolean isUserProfiled(String email) {
		try {
			return Boolean.TRUE.equals(webClient.get()
					.uri("http://" + userServiceIp + ":" + userServicePort + "/api/user/is-user-profiled",
							uriBuilder -> uriBuilder.queryParam("email", email).build())
					.retrieve()
					.bodyToMono(Boolean.class)
					.block());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
