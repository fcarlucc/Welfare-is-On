package com.userservice.app.mapper;

import com.userservice.app.dto.UserDto;
import com.userservice.app.dto.UserInfoDto;
import com.userservice.app.dto.UserSurveyDto;
import com.userservice.app.model.*;
import com.userservice.app.model.enumerator.InterestName;
import com.userservice.app.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * A component responsible for mapping between User and various DTOs.
 *
 * <p>This class provides methods to convert between domain model objects (e.g., {@link User}, {@link UserSurvey})
 * and their corresponding Data Transfer Objects (DTOs) such as {@link UserDto}, {@link UserInfoDto}, and {@link UserSurveyDto}.</p>
 *
 * <p>The mapping involves:</p>
 * <ul>
 *     <li>Transforming a {@link UserDto} into a {@link User} object and vice versa, while handling related entities.</li>
 *     <li>Mapping a {@link UserSurveyDto} to a {@link UserSurvey} object, setting relevant attributes.</li>
 *     <li>Converting a {@link User} object to a {@link UserInfoDto} including user-specific details.</li>
 * </ul>
 *
 * <p>This class relies on various service components for fetching related data:</p>
 * <ul>
 *     <li>{@link InterestService} for retrieving interests.</li>
 *     <li>{@link MaritalStatusService} for retrieving marital status.</li>
 *     <li>{@link TitleService} for retrieving titles.</li>
 * </ul>
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final InterestService interestService;
    private final MaritalStatusService maritalStatusService;
    private final TitleService titleService;

    /**
     * Maps a {@link UserDto} to a {@link User} entity.
     * <p>
     * This method populates a new {@link User} object using the provided {@link UserDto}. It also constructs
     * a {@link UserSurvey} object if necessary and associates it with the {@link User}. Related entities like
     * interests, marital status, and title are resolved using respective service methods.
     * </p>
     *
     * @param userDto the Data Transfer Object containing user information
     * @return a {@link User} entity populated with the data from the DTO
     */
    public User userDtotoUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setDob(userDto.getDob());
        user.setId(userDto.getId());

        if (userDto.getHasChildren() != null) {
            UserSurvey userSurvey = new UserSurvey();

            userSurvey.setHasChildren(userDto.getHasChildren());
            userSurvey.setHasElderlyParents(userDto.getHasElderlyParents());

            maritalStatusService.findByName(userDto.getMaritalStatusName())
                    .ifPresent(userSurvey::setMaritalStatus);

            titleService.findByName(userDto.getTitleName())
                    .ifPresent(userSurvey::setTitle);

            Set<Interest> interests = new HashSet<>();
            for (InterestName interestName : userDto.getInterests()) {
                interestService.findByName(interestName)
                        .ifPresent(interests::add);
            }

            userSurvey.setInterests(interests);
            userSurvey.setUser(user);
            user.setUserSurvey(userSurvey);
        }

        return user;
    }

    /**
     * Maps a {@link UserSurveyDto} to a {@link UserSurvey} entity.
     * <p>
     * This method populates a new {@link UserSurvey} object using the provided {@link UserSurveyDto}.
     * It sets related attributes including interests, marital status, and title. The {@link UserSurvey}
     * is then associated with the given {@link User}.
     * </p>
     *
     * @param userSurveyDto the Data Transfer Object containing user survey information
     * @param user the {@link User} entity to associate with the survey
     * @return a {@link UserSurvey} entity populated with the data from the DTO
     */
    public UserSurvey userSurveyDtoToUserSurvey(UserSurveyDto userSurveyDto, User user) {
        UserSurvey userSurvey = new UserSurvey();

        userSurvey.setHasChildren(userSurveyDto.getHasChildren());
        userSurvey.setHasElderlyParents(userSurveyDto.getHasElderlyParents());

        maritalStatusService.findByName(userSurveyDto.getMaritalStatusName())
                .ifPresent(userSurvey::setMaritalStatus);

        titleService.findByName(userSurveyDto.getTitleName())
                .ifPresent(userSurvey::setTitle);

        Set<Interest> interests = new HashSet<>();
        for (InterestName interestName : userSurveyDto.getInterests()) {
            interestService.findByName(interestName)
                    .ifPresent(interests::add);
        }
        userSurvey.setInterests(interests);
        userSurvey.setUser(user);
        return userSurvey;
    }

    /**
     * Maps a {@link User} entity to a {@link UserInfoDto}.
     * <p>
     * This method extracts information from a {@link User} object and populates a {@link UserInfoDto} with
     * user-specific details such as name, email, location, survey responses, and interests.
     * </p>
     *
     * @param user the {@link User} entity to be mapped
     * @return a {@link UserInfoDto} populated with the user's information
     */
    public UserInfoDto userToUserInfoDto(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();

        userInfoDto.setId(user.getId());
        userInfoDto.setDob(user.getDob());
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setLatitude(user.getLatitude());
        userInfoDto.setLongitude(user.getLongitude());
        userInfoDto.setHasChildren(user.getUserSurvey().isHasChildren());
        userInfoDto.setHasElderlyParents(user.getUserSurvey().isHasElderlyParents());
        userInfoDto.setLastName(user.getLastName());
        userInfoDto.setFirstName(user.getFirstName());
        userInfoDto.setMaritalStatusName(user.getUserSurvey().getMaritalStatus().getName());
        userInfoDto.setTitleName(user.getUserSurvey().getTitle().getName());
        userInfoDto.setSavedMoney(user.getSavedMoney());

        List<InterestName> interests = new ArrayList<>();
        for (Interest interest: user.getUserSurvey().getInterests()) {
            interests.add(interest.getName());
        }

        userInfoDto.setInterests(interests);

        return userInfoDto;
    }
}
