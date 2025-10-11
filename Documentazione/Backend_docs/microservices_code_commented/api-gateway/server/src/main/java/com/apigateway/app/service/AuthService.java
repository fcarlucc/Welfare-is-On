package com.apigateway.app.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.apigateway.app.dto.*;
import com.apigateway.app.exception.*;
import com.apigateway.app.mapper.UserMapper;
import com.apigateway.app.model.User;

import com.apigateway.app.model.enumerator.RoleName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * Service class handling authentication and user-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${coach.service.name}")
    private String coachServiceIp;

    @Value("${coach.service.port}")
    private String coachServicePort;

    @Value("${user.service.name}")
    private String userServiceIp;

    @Value("${user.service.port}")
    private String userServicePort;

    @Value("${otp.service.name}")
    private String otpServiceIp;

    @Value("${otp.service.port}")
    private String otpServicePort;

    @Value("${image.service.name}")
    private String imageServiceIp;

    @Value("${image.service.port}")
    private String imageServicePort;

    private final UserService userService;
    private final WebClient webClient;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final EmailService emailService;

    /**
     * Registers a new user.
     * @param userDto DTO containing user information.
     * @return ResponseDto with the result of the sign-up operation.
     * @throws SignUpValidationException if validation fails (e.g., user is under 18 years old or email already exists).
     */
    @Transactional
    public ResponseDto signUp(UserDto userDto) {
        Date dobDate = userDto.getDob();
        LocalDate dob = dobDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        Period age = Period.between(dob, today);
        if (age.getYears() < 18) {
            throw new SignUpValidationException("You must be at least 18 years old");
        }
        try {
            userService.findByEmail(userDto.getEmail());
            throw new SignUpValidationException("Email already exists");
        } catch (EmailNotFoundException ignored) {
        }
        List<RoleName> roles = new ArrayList<>();
        roles.add(RoleName.ROLE_USER);
        userDto.setRoles(roles);
        User user = userMapper.userDtotoUser(userDto);
        ResponseDto responseDto = createUserTables(user, userDto);
        generateOtpAndSendEmail(user.getId(), user.getEmail(), "/verify-email");
        return responseDto;
    }

    /**
     * Validates and registers a new coach.
     * @param coachDto DTO containing coach information.
     * @param file MultipartFile of coach's image.
     * @throws SignUpValidationException if validation fails (e.g., email already exists).
     */
    @Transactional
    public void validateCoach(CoachDto coachDto, MultipartFile file) {
        try {
            userService.findByEmail(coachDto.getEmail());
            throw new SignUpValidationException("Email already exists");
        } catch (EmailNotFoundException ignored) {
        }

        User user = userMapper.coachDtoToUser(coachDto);
        userService.create(user);
        Long imageId = uploadImage(file);
        CoachInfoTransferDto coachInfoTransferDto = userMapper.coachDtoToCoachInfoTransferDto(coachDto, imageId, user.getId());
        createCoach(coachInfoTransferDto);
    }

    /**
     * Creates a coach in the coach service.
     * @param coachDto DTO containing coach information.
     * @throws CoachCreationException if an error occurs during coach creation.
     */
    private void createCoach(CoachInfoTransferDto coachDto) {
        try {
            CoachInfoTransferDto coach = webClient
                    .post()
                    .uri("http://" + coachServiceIp + ":" + coachServicePort + "/secure/api/coach/create")
                    .bodyValue(coachDto)
                    .retrieve()
                    .bodyToMono(CoachInfoTransferDto.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CoachCreationException("An error occurred while creating user");
        }
    }

    /**
     * Uploads an image file in the image service.
     * @param file MultipartFile of the image to upload.
     * @return Long representing the image ID.
     * @throws ImageUploadingFailException if an error occurs during image upload.
     */
    private Long uploadImage(MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        ImageResponseDto responseDto;

        try {
            responseDto = webClient
                    .post()
                    .uri("http://" + imageServiceIp + ":" + imageServicePort + "/api/image/upload")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(ImageResponseDto.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ImageUploadingFailException("An error occurred while uploading the image");
        }

        assert responseDto != null;
        return responseDto.getImageId();
    }

    /**
     * Creates user-related tables in the user service.
     * @param user User entity to create.
     * @param userDto DTO containing user information.
     * @return ResponseDto with the result of the user creation operation.
     * @throws SignUpValidationException if an error occurs during user creation.
     */
    @Transactional
    public ResponseDto createUserTables(User user, UserDto userDto) {
        userService.create(user);
        UserInfoTransferDto userInfoTransferDto = userMapper.userDtotoUserInfoTransferDto(userDto, user.getId());
        ResponseDto responseDto;
        try {
            responseDto = webClient
                    .post()
                    .uri("http://" + userServiceIp + ":" + userServicePort + "/secure/api/user/create")
                    .bodyValue(userInfoTransferDto)
                    .retrieve()
                    .bodyToMono(ResponseDto.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SignUpValidationException("An error occurred while creating user");
        }

        return responseDto;
    }

    /**
     * Creates user-related tables in the user service.
     * @param user User entity to create.
     * @return ResponseDto with the result of the user creation operation.
     * @throws SignUpValidationException if an error occurs during user creation.
     */
    @Transactional
    public ResponseDto createUserTables(User user) {
        userService.create(user);
        UserInfoTransferDto userInfoTransferDto = userMapper.userToUserInfoTransferDto(user);
        ResponseDto responseDto;
        try {
            responseDto = webClient
                    .post()
                    .uri("http://" + userServiceIp + ":" + userServicePort + "/secure/api/user/create")
                    .bodyValue(userInfoTransferDto)
                    .retrieve()
                    .bodyToMono(ResponseDto.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SignUpValidationException("An error occurred while creating user");
        }

        return responseDto;
    }

    /**
     * Refreshes the authentication token using the refresh token from cookies.
     * @param request HttpServletRequest containing the refresh token cookie.
     * @param response HttpServletResponse for setting the new access token.
     * @return true if token refresh is successful, false otherwise.
     * @throws IOException if an error occurs while handling input/output operations.
     */
    public boolean refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken;
        String userEmail;
        String accessToken;
        Cookie tokenCookie = WebUtils.getCookie(request, "refresh_token");

        if (tokenCookie == null) {
            System.out.println("No refresh token found");
            return false;
        }

        refreshToken = tokenCookie.getValue();
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var user = this.userService.findByEmail(userEmail);
            accessToken = jwtService.generateToken(userMapper.userToUserDetails(user));
            response.setHeader("Authorization", "Bearer " + accessToken);
            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            return true;
        }
        System.out.println("No email found");
        return false;
    }

    /**
     * Generates OTP from otp service and sends it via email to the user.
     * @param userId Long representing the user ID.
     * @param userEmail String containing the user's email address.
     * @param endpoint String representing the endpoint to verify the email.
     * @throws OtpServiceNotWorkingException if an error occurs during OTP generation or email sending.
     */
    public void generateOtpAndSendEmail(Long userId, String userEmail, String endpoint) {
        String otp;
        try {
            otp = generateOtp(userId);
        } catch (Exception e) {
            throw new OtpServiceNotWorkingException("Error generating OTP");
        }
        try {
            emailService.sendEmailOtp(userEmail, otp, endpoint);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generates OTP from otp service for the given user ID.
     * @param userId Long representing the user ID.
     * @return String containing the generated OTP.
     */
    public String generateOtp(Long userId) {
        return webClient.post()
                .uri("http://" + otpServiceIp + ":" + otpServicePort + "/otp/generate",
                        uriBuilder -> uriBuilder.queryParam("userId", userId).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    /**
     * Validates OTP from otp service for the given user ID and OTP code.
     * @param userId Long representing the user ID.
     * @param otpCode String containing the OTP to validate.
     * @return true if OTP validation is successful, false otherwise.
     */
    public Boolean validateOtp(Long userId, String otpCode) {
        return webClient.post()
                .uri("http://" + otpServiceIp + ":" + otpServicePort + "/otp/validate",
                        uriBuilder -> uriBuilder.queryParam("userId", userId).queryParam("otp", otpCode).build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
}
