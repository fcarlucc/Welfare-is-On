package com.apigateway.app.mapper;

import com.apigateway.app.dto.CoachDto;
import com.apigateway.app.dto.CoachInfoTransferDto;
import com.apigateway.app.dto.UserDto;
import com.apigateway.app.dto.UserInfoTransferDto;
import com.apigateway.app.model.*;
import com.apigateway.app.model.enumerator.RoleName;
import com.apigateway.app.security.model.MyUserDetails;
import com.apigateway.app.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Mapper class to convert between DTOs and entity objects for users and coaches.
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleService roleService;

    /**
     * Converts UserDto to User entity.
     *
     * @param userDto The UserDto object containing user data.
     * @return User entity object converted from UserDto.
     */
    public User userDtotoUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        Set<Role> roles = new HashSet<>();
        for (RoleName roleName : userDto.getRoles()) {
            Optional<Role> roleOptional = roleService.findByName(roleName);
            roleOptional.ifPresent(roles::add);
        }
        user.setRoles(roles);

        return user;
    }

    /**
     * Converts UserDto to UserInfoTransferDto.
     *
     * @param userDto The UserDto object containing user data.
     * @param id      The ID of the user.
     * @return UserInfoTransferDto object containing user info.
     */
    public UserInfoTransferDto userDtotoUserInfoTransferDto(UserDto userDto, Long id) {
        UserInfoTransferDto userInfoTransferDto = new UserInfoTransferDto();

        userInfoTransferDto.setId(id);
        userInfoTransferDto.setDob(userDto.getDob());
        userInfoTransferDto.setEmail(userDto.getEmail());
        userInfoTransferDto.setFirstName(userDto.getFirstName());
        userInfoTransferDto.setLastName(userDto.getLastName());
        userInfoTransferDto.setInterests(userDto.getInterests());
        userInfoTransferDto.setMaritalStatusName(userDto.getMaritalStatusName());
        userInfoTransferDto.setTitleName(userDto.getTitleName());
        userInfoTransferDto.setHasChildren(userDto.getHasChildren());
        userInfoTransferDto.setHasElderlyParents(userDto.getHasElderlyParents());

        return userInfoTransferDto;
    }

    /**
     * Converts User entity to MyUserDetails for security.
     *
     * @param user The User entity object.
     * @return MyUserDetails object created from User entity.
     */
    public MyUserDetails userToUserDetails(User user) {
        return new MyUserDetails(user.getEmail(), user.getPassword(), user.getAuthorities(),
                user.isExpired(), user.isBlocked(), user.isEnabled(), user.getId(),
                user.getFirstName() + " " + user.getLastName());
    }

    /**
     * Converts User entity to UserInfoTransferDto.
     *
     * @param user The User entity object.
     * @return UserInfoTransferDto object containing user info.
     */
    public UserInfoTransferDto userToUserInfoTransferDto(User user) {
        UserInfoTransferDto userInfoTransferDto = new UserInfoTransferDto();

        userInfoTransferDto.setFirstName(user.getFirstName());
        userInfoTransferDto.setLastName(user.getLastName());
        userInfoTransferDto.setId(user.getId());
        userInfoTransferDto.setEmail(user.getEmail());

        return userInfoTransferDto;
    }

    /**
     * Converts CoachDto to User entity with coach role.
     *
     * @param coachDto The CoachDto object containing coach data.
     * @return User entity object converted from CoachDto.
     */
    public User coachDtoToUser(CoachDto coachDto) {
        User user = new User();

        Role coachRole = roleService.findByName(RoleName.ROLE_COACH).get();
        Set<Role> rolesCoach = new HashSet<>();
        rolesCoach.add(coachRole);

        user.setRoles(rolesCoach);
        user.setFirstName(coachDto.getFirstName());
        user.setLastName(coachDto.getLastName());
        user.setEmail(coachDto.getEmail());
        user.setPassword(coachDto.getPassword());
        user.setEnabled(true);
        user.setExpired(false);
        user.setBlocked(false);

        return user;
    }

    /**
     * Converts CoachDto to CoachInfoTransferDto.
     *
     * @param coachDto The CoachDto object containing coach data.
     * @param imageId  The ID of the image associated with the coach.
     * @param userId   The ID of the user (coach).
     * @return CoachInfoTransferDto object containing coach info.
     */
    public CoachInfoTransferDto coachDtoToCoachInfoTransferDto(CoachDto coachDto, Long imageId, Long userId) {
        CoachInfoTransferDto newCoach = new CoachInfoTransferDto();

        newCoach.setId(userId);
        newCoach.setImageId(imageId);
        newCoach.setEmail(coachDto.getEmail());
        newCoach.setFirstName(coachDto.getFirstName());
        newCoach.setLastName(coachDto.getLastName());
        newCoach.setSpecialization(coachDto.getSpecialization());
        newCoach.setDivision(coachDto.getDivision());
        newCoach.setLatitude(coachDto.getLatitude());
        newCoach.setLongitude(coachDto.getLongitude());
        newCoach.setPhoneNumber(coachDto.getPhoneNumber());

        return newCoach;
    }
}
