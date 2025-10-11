package com.userservice.app.controller;

import com.userservice.app.dto.*;
import com.userservice.app.mapper.UserMapper;
import com.userservice.app.model.User;
import com.userservice.app.model.UserSurvey;
import com.userservice.app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user-related operations, including user creation,
 * updating user information, handling user surveys, and managing user location.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Creates a new user with the provided user details.
     *
     * @param userDto the details of the user to be created
     * @return a {@link ResponseEntity} indicating the result of the operation
     */
    @PostMapping("/secure/api/user/create")
    public ResponseEntity<ResponseDto> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.userDtotoUser(userDto);
        userService.create(user);
        return new ResponseEntity<>(new ResponseDto("success"), HttpStatus.OK);
    }

    /**
     * Updates the user's survey information based on the provided survey details.
     *
     * @param userSurveyDto the details of the user survey to be updated
     * @return a {@link ResponseEntity} indicating the result of the operation
     */
    @PostMapping("/api/user/survey-google")
    public ResponseEntity<ResponseDto> surveyGoogle(@RequestBody @Valid UserSurveyDto userSurveyDto) {
        User user = userService.findByEmail(userSurveyDto.getEmail());
        UserSurvey userSurvey = userMapper.userSurveyDtoToUserSurvey(userSurveyDto, user);
        user.setUserSurvey(userSurvey);
        userService.update(user);
        return new ResponseEntity<>(new ResponseDto("success"), HttpStatus.OK);
    }

    /**
     * Updates the user's location based on the provided location details.
     *
     * @param locationDto the location details to be updated
     * @return a {@link ResponseEntity} indicating the result of the operation
     */
    @PostMapping("/api/user/user-location")
    public ResponseEntity<ResponseDto> userLocation(@RequestBody @Valid LocationDto locationDto) {
        User user = userService.findByEmail(locationDto.getEmail());
        user.setLatitude(locationDto.getLatitude());
        user.setLongitude(locationDto.getLongitude());
        userService.update(user);
        return new ResponseEntity<>(new ResponseDto("success"), HttpStatus.OK);
    }

    /**
     * Checks if a user has a profile completed with a survey.
     *
     * @param email the email of the user to check
     * @return a {@link ResponseEntity} containing {@code true} if the user is profiled,
     *         otherwise {@code false}
     */
    @GetMapping("/api/user/is-user-profiled")
    public ResponseEntity<Boolean> isUserProfiled(@RequestParam String email) {
        User user = userService.findByEmail(email);
        return new ResponseEntity<>(user.getUserSurvey() != null, HttpStatus.OK);
    }

    /**
     * Retrieves detailed information about a user based on the user's ID.
     *
     * @param userId the ID of the user to retrieve information for
     * @return a {@link ResponseEntity} containing the user's information
     */
    @GetMapping("/api/user/info")
    public ResponseEntity<UserInfoDto> getUserInfo(@RequestParam Long userId) {
        User user = userService.getById(userId);
        UserInfoDto userInfoDto = userMapper.userToUserInfoDto(user);
        return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
    }

    /**
     * Updates the user's information based on the provided update details.
     *
     * @param updateUserDto the details of the user to be updated
     * @return a {@link ResponseEntity} containing the updated user
     */
    @PutMapping("/api/user/update-info")
    public ResponseEntity<User> updateUser(@RequestBody @Valid UpdateUserDto updateUserDto) {
        User user = userService.getById(updateUserDto.getUserId());
        userService.update(user, updateUserDto);
        user.getUserSurvey().setUser(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Updates the savings amount for a user based on the provided user ID and saved money.
     *
     * @param userId the ID of the user whose savings are to be updated
     * @param savedMoney the amount of money to add to the user's savings
     * @return a {@link ResponseEntity} containing the updated savings amount
     */
    @PutMapping("/secure/api/user/update-savings")
    public ResponseEntity<Double> updateSavings(@RequestParam Long userId, @RequestParam Double savedMoney) {
        User user = userService.getById(userId);
        user.setSavedMoney(user.getSavedMoney() + savedMoney);
        userService.update(user);
        return new ResponseEntity<>(user.getSavedMoney(), HttpStatus.OK);
    }
}
