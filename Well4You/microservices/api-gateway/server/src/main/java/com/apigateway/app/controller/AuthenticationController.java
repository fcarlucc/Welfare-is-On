package com.apigateway.app.controller;

import com.apigateway.app.dto.*;
import com.apigateway.app.exception.*;
import com.apigateway.app.mapper.UserMapper;
import com.apigateway.app.model.User;
import com.apigateway.app.security.model.MyUserDetails;
import com.apigateway.app.service.AuthService;
import com.apigateway.app.service.JwtService;
import com.apigateway.app.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller class for handling authentication-related endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthenticationController {

    private final UserService userService;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    /**
     * Endpoint for user sign-up.
     *
     * @param userDto The UserDto containing sign-up information.
     * @return ResponseEntity containing the response status and message.
     */
    @PostMapping("/api/auth/sign-up")
    public ResponseEntity<ResponseDto> signup(@RequestBody @Valid UserDto userDto) {
        ResponseDto responseDto = authService.signUp(userDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * Endpoint for creating a coach.
     *
     * @param coachDto The CoachDto containing coach information.
     * @param file     The MultipartFile containing coach's profile image.
     * @return ResponseEntity containing the response status and message.
     */
    @PostMapping(path = "/secure/api/auth/create-coach", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> createCoach(@RequestPart("coach") @Valid CoachDto coachDto, @RequestPart("file") MultipartFile file) {
        authService.validateCoach(coachDto, file);
        return new ResponseEntity<>(new ResponseDto("Coach successfully created"), HttpStatus.OK);
    }

    /**
     * Endpoint for two-factor authentication.
     *
     * @param otpDto   The OtpDto containing OTP information.
     * @param response The HttpServletResponse to set the refresh token cookie.
     * @return ResponseEntity containing the TokenDto with authentication details.
     */
    @PostMapping("/api/auth/2FA")
    public ResponseEntity<TokenDto> auth2fa(@RequestBody @Valid OtpDto otpDto, HttpServletResponse response) {

        User user = userService.findByEmail(otpDto.getEmail());

        if (!user.isEnabled()) {
            throw new UserNotEnabledException("User is not enabled");
        }
        OtpValidation(otpDto.getOtp(), user.getId());
        MyUserDetails userDetails = userMapper.userToUserDetails(user);
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setAttribute("SameSite", "Strict");
        refreshTokenCookie.setSecure(true);
        response.addCookie(refreshTokenCookie);

        return new ResponseEntity<>(new TokenDto("User authenticated", accessToken), HttpStatus.OK);
    }

    /**
     * Endpoint for verifying user with OTP.
     *
     * @param otpDto The OtpDto containing OTP information.
     * @return ResponseEntity containing the response status and message.
     */
    @PostMapping("/api/auth/verify-user")
    public ResponseEntity<ResponseDto> verifyUser(@RequestBody @Valid OtpDto otpDto) {

        User user = userService.findByEmail(otpDto.getEmail());
        OtpValidation(otpDto.getOtp(), user.getId());

        user.setEnabled(true);
        userService.update(user);
        return new ResponseEntity<>(new ResponseDto("User successfully verified"), HttpStatus.OK);
    }

    /**
     * Endpoint for refreshing OTP sent to user's email.
     *
     * @param email The email address to which OTP needs to be refreshed.
     * @return ResponseEntity containing the response status and message.
     */
    @PostMapping("/api/auth/refresh-otp")
    public ResponseEntity<ResponseDto> refreshVerifyEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);

        authService.generateOtpAndSendEmail(user.getId(), email, "/verify-email");
        return new ResponseEntity<>(new ResponseDto("Email to verify email refreshed"), HttpStatus.OK);
    }

    /**
     * Endpoint for initiating forgot password process.
     *
     * @param email The email address for which password reset is requested.
     * @return ResponseEntity containing the response status and message.
     */
    @PostMapping("/api/auth/forgot-password")
    public ResponseEntity<ResponseDto> resetPasswordRequest(@RequestParam String email) {
        User user = userService.findByEmail(email);

        authService.generateOtpAndSendEmail(user.getId(), user.getEmail(), "/reset-password");
        return new ResponseEntity<>(new ResponseDto("Email to reset password sent"), HttpStatus.OK);
    }

    /**
     * Endpoint for resetting password based on OTP validation.
     *
     * @param resetPasswordDto The ResetPasswordDto containing reset password information.
     * @return ResponseEntity containing the response status and message.
     */
    @PostMapping("/api/auth/reset-password")
    public ResponseEntity<ResponseDto> resetPasswordRequestValidation(@RequestBody @Valid ResetPasswordDto resetPasswordDto) {

        User user = userService.findByEmail(resetPasswordDto.getEmail());

        authService.validateOtp(user.getId(), resetPasswordDto.getOtp());
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encryptedPwd = bcrypt.encode(resetPasswordDto.getPassword());
        user.setPassword(encryptedPwd);
        userService.update(user);
        return new ResponseEntity<>(new ResponseDto("Reset password done correctly"), HttpStatus.OK);
    }

    /**
     * Utility method for OTP validation.
     *
     * @param otp    The OTP to validate.
     * @param userId The ID of the user for whom OTP validation is performed.
     */
    private void OtpValidation(String otp, Long userId) {
        Boolean isOtpValid;
        try {
            isOtpValid = authService.validateOtp(userId, otp);
        } catch (Exception e) {
            throw new OtpServiceNotWorkingException("Error validating OTP");
        }
        if (!isOtpValid) {
            throw new InvalidOtpException("OTP is not valid");
        }
    }

}
