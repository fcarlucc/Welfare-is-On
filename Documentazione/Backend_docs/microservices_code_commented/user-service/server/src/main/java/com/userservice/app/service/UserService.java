package com.userservice.app.service;

import com.userservice.app.dto.UpdateUserDto;
import com.userservice.app.exception.EmailNotFoundException;
import com.userservice.app.exception.UserIdNotFoundException;
import com.userservice.app.model.Interest;
import com.userservice.app.model.User;
import com.userservice.app.model.enumerator.InterestName;
import com.userservice.app.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for managing {@link User} entities.
 * <p>
 * This service provides methods to perform CRUD operations on {@link User} entities,
 * including creating, retrieving, updating, and deleting users. It also supports
 * operations related to user interests and surveys.
 * </p>
 *
 * <p>Key functionalities:</p>
 * <ul>
 *     <li>{@link #getAll()}: Retrieves all {@code User} entities from the database.</li>
 *     <li>{@link #getById(Long)}: Finds a {@code User} by its ID, throwing {@link UserIdNotFoundException} if not found.</li>
 *     <li>{@link #create(User)}: Creates and saves a new {@code User} entity.</li>
 *     <li>{@link #update(User, UpdateUserDto)}: Updates an existing {@code User} with new details from {@link UpdateUserDto}.</li>
 *     <li>{@link #update(User)}: Saves changes to an existing {@code User} entity.</li>
 *     <li>{@link #delete(Long)}: Deletes a {@code User} by its ID, returning {@code true} if the deletion was successful.</li>
 *     <li>{@link #findByEmail(String)}: Finds a {@code User} by email, throwing {@link EmailNotFoundException} if not found.</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *     <li>{@link IUserRepository}: Repository interface for accessing {@code User} data.</li>
 *     <li>{@link MaritalStatusService}: Service for managing MaritalStatus entities.</li>
 *     <li>{@link InterestService}: Service for managing {@link Interest} entities.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class UserService {

	private final IUserRepository userRepository;
	private final MaritalStatusService maritalStatusService;
	private final InterestService interestService;

	/**
	 * Retrieves all {@code User} entities from the database.
	 *
	 * @return a list of all {@code User} entities
	 */
	public List<User> getAll() {
		return userRepository.findAll();
	}

	/**
	 * Finds a {@code User} by its ID.
	 *
	 * @param id the ID of the user to retrieve
	 * @return the {@code User} with the given ID
	 * @throws UserIdNotFoundException if no user with the given ID exists
	 */
	public User getById(Long id) {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty()) {
			throw new UserIdNotFoundException("User not found");
		}

		return user.get();
	}

	/**
	 * Creates and saves a new {@code User} entity.
	 *
	 * @param user the {@code User} entity to create
	 * @return the created {@code User} entity
	 */
	public User create(User user) {
		return userRepository.save(user);
	}

	/**
	 * Updates an existing {@code User} with details from {@link UpdateUserDto}.
	 *
	 * @param user the {@code User} entity to update
	 * @param updateUserDto the DTO containing updated user information
	 * @return the updated {@code User} entity
	 */
	public User update(User user, UpdateUserDto updateUserDto) {

		user.getUserSurvey().setHasChildren(updateUserDto.getHasChildren());
		user.getUserSurvey().setHasElderlyParents(updateUserDto.getHasElderlyParents());

		Set<Interest> interests = new HashSet<>();
		for (InterestName interestName : updateUserDto.getInterests()) {
			Optional<Interest> interestOptional = interestService.findByName(interestName);
			interestOptional.ifPresent(interests::add);
		}

		user.getUserSurvey().setInterests(interests);
		user.getUserSurvey().setMaritalStatus(maritalStatusService.findByName(updateUserDto.getMaritalStatusName()).get());

		return userRepository.save(user);
	}

	/**
	 * Saves changes to an existing {@code User} entity.
	 *
	 * @param user the {@code User} entity to save
	 * @return the saved {@code User} entity
	 */
	public User update(User user) {
		return userRepository.save(user);
	}

	/**
	 * Deletes a {@code User} by its ID.
	 *
	 * @param id the ID of the user to delete
	 * @return {@code true} if the deletion was successful, {@code false} otherwise
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
	 * Finds a {@code User} by its email.
	 *
	 * @param email the email of the user to find
	 * @return the {@code User} with the given email
	 * @throws EmailNotFoundException if no user with the given email exists
	 */
	public User findByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);

		if (user.isEmpty()) {
			throw new EmailNotFoundException("user email does not exist");
		}
		return user.get();
	}
}
