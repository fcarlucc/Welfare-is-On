package com.coachservice.app.controller;

import com.coachservice.app.dto.AggregatedCoachDto;
import com.coachservice.app.dto.CoachInfoTransferDto;
import com.coachservice.app.dto.ShowCaseDto;
import com.coachservice.app.mapper.CoachMapper;
import com.coachservice.app.model.Coach;
import com.coachservice.app.service.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link Coach} entities.
 * <p>
 * This controller exposes endpoints to create coaches, retrieve individual coach information,
 * and generate showcase data for coaches. It interacts with the {@link CoachService} to perform
 * the necessary business logic and uses {@link CoachMapper} to map between entities and DTOs.
 * </p>
 *
 * <p>Available Endpoints:</p>
 * <ul>
 *     <li><strong>POST /secure/api/coach/create</strong>: Creates a new coach based on the provided {@link CoachInfoTransferDto}.</li>
 *     <li><strong>GET /api/coach/showcase-coach</strong>: Retrieves a showcase of the coach associated with the given user ID, returning a {@link ShowCaseDto}.</li>
 *     <li><strong>GET /api/coach/get-coach</strong>: Fetches detailed information about a coach using the provided coach ID, returning an {@link AggregatedCoachDto}.</li>
 * </ul>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
public class CoachController {

    private final CoachService coachService;
    private final CoachMapper coachMapper;

    /**
     * Creates a new coach based on the provided {@link CoachInfoTransferDto}.
     *
     * @param coachDto the DTO containing information to create a new coach
     * @return a {@link ResponseEntity} containing the created coach information in {@link CoachInfoTransferDto} format and HTTP status 200 OK
     */
    @PostMapping("/secure/api/coach/create")
    public ResponseEntity<CoachInfoTransferDto> createCoach(@RequestBody CoachInfoTransferDto coachDto) {
        coachService.createCoach(coachDto);
        return new ResponseEntity<>(coachDto, HttpStatus.OK);
    }

    /**
     * Retrieves a showcase of the coach associated with the given user ID.
     *
     * @param userId the ID of the user whose coach showcase is to be retrieved
     * @return a {@link ResponseEntity} containing the coach showcase information in {@link ShowCaseDto} format and HTTP status 200 OK
     */
    @GetMapping("/api/coach/showcase-coach")
    public ResponseEntity<ShowCaseDto> showcaseCoach(@RequestParam Long userId) {
        ShowCaseDto showCaseDto = new ShowCaseDto(coachService.createShowcase(userId));
        return new ResponseEntity<>(showCaseDto, HttpStatus.OK);
    }

    /**
     * Retrieves detailed information about a coach using the provided coach ID.
     *
     * @param coachId the ID of the coach to be retrieved
     * @return a {@link ResponseEntity} containing the coach details in {@link AggregatedCoachDto} format and HTTP status 200 OK
     */
    @GetMapping("/api/coach/get-coach")
    public ResponseEntity<AggregatedCoachDto> getCoach(@RequestParam Long coachId) {
        Coach coach = coachService.findById(coachId);
        AggregatedCoachDto aggregatedCoachDto = coachMapper.coachToAggregatedCoachDto(coach);
        return new ResponseEntity<>(aggregatedCoachDto, HttpStatus.OK);
    }
}
