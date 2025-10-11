package com.coachservice.app.service;

import com.coachservice.app.dto.CoachInfoTransferDto;
import com.coachservice.app.dto.CoachShowcaseDto;
import com.coachservice.app.dto.InfoShowCaseDto;
import com.coachservice.app.dto.UserInfoDto;
import com.coachservice.app.exception.UserIdNotFoundException;
import com.coachservice.app.exception.CoachNotFoundException;
import com.coachservice.app.mapper.CoachMapper;
import com.coachservice.app.model.Coach;
import com.coachservice.app.model.Pillar;
import com.coachservice.app.model.enumerator.PillarName;
import com.coachservice.app.repository.ICoachRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link Coach} entities and generating coach-related data.
 * <p>
 * Provides operations for finding, creating, and updating {@link Coach} entities.
 * Includes functionality to generate a showcase of coaches based on user preferences and specializations.
 * </p>
 *
 * <p>Key methods:</p>
 * <ul>
 *     <li>{@link #findById(Long)}: Retrieves a {@link Coach} by its ID. Throws {@link CoachNotFoundException} if the coach does not exist.</li>
 *     <li>{@link #create(Coach)}: Saves a new {@link Coach} entity to the repository.</li>
 *     <li>{@link #update(Coach)}: Updates an existing {@link Coach} entity in the repository.</li>
 *     <li>{@link #createShowcase(Long)}: Generates a showcase of coaches categorized by specialization, based on the provided user ID.</li>
 *     <li>{@link #createCoach(CoachInfoTransferDto)}: Creates a new {@link Coach} from a {@link CoachInfoTransferDto} object.</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *     <li>{@link ICoachRepository}: Repository interface for performing CRUD operations on {@link Coach} entities.</li>
 *     <li>{@link WebClient}: Used for making HTTP requests to external services (e.g., fetching user information).</li>
 *     <li>{@link PillarService}: Service for managing {@link Pillar} entities, used for retrieving pillar details.</li>
 *     <li>{@link CoachMapper}: Mapper for converting between {@link Coach} entities and DTOs.</li>
 * </ul>
 *
 * <p>Configuration Properties:</p>
 * <ul>
 *     <li>{@code user.service.name}: The hostname or IP address of the user service.</li>
 *     <li>{@code user.service.port}: The port on which the user service is running.</li>
 *     <li>{@code image.service.name}: The hostname or IP address of the image service.</li>
 *     <li>{@code image.service.port}: The port on which the image service is running.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class CoachService {

    @Value("${user.service.name}")
    private String userServiceIp;

    @Value("${user.service.port}")
    private String userServicePort;

    @Value("${image.service.name}")
    private String imageServiceIp;

    @Value("${image.service.port}")
    private String imageServicePort;

    private final ICoachRepository coachRepository;
    private final WebClient webClient;
    private final PillarService pillarService;
    private final CoachMapper coachMapper;

    /**
     * Retrieves a {@link Coach} entity by its ID.
     *
     * @param id the ID of the coach to retrieve
     * @return the {@link Coach} entity if found
     * @throws CoachNotFoundException if no coach with the specified ID is found
     */
    public Coach findById(Long id) {
        Optional<Coach> coach = coachRepository.findById(id);
        if (coach.isEmpty()) {
            throw new CoachNotFoundException("Coach id does not exist");
        }
        return coach.get();
    }

    /**
     * Saves a new {@link Coach} entity to the repository.
     *
     * @param coach the {@link Coach} entity to save
     */
    public void create(Coach coach) {
        coachRepository.save(coach);
    }

    /**
     * Updates an existing {@link Coach} entity in the repository.
     *
     * @param coach the {@link Coach} entity with updated details
     */
    public void update(Coach coach) {
        coachRepository.save(coach);
    }

    /**
     * Generates a showcase of coaches categorized by specialization based on the provided user ID.
     *
     * @param userId the ID of the user for whom to generate the coach showcase
     * @return a list of {@link CoachShowcaseDto} objects containing coaches organized by category
     */
    public List<CoachShowcaseDto> createShowcase(Long userId) {
        UserInfoDto userInfoDto = getUserInfoDto(userId);
        List<CoachShowcaseDto> showcase = new ArrayList<>();

        addToShowcase(showcase, "Economic", getCoachListByPillar(PillarName.ECONOMIC, userInfoDto));
        addToShowcase(showcase, "Family", getCoachListByPillar(PillarName.FAMILY, userInfoDto));
        addToShowcase(showcase, "Physical", getCoachListByPillar(PillarName.PHYSICAL, userInfoDto));
        addToShowcase(showcase, "Psychological", getCoachListByPillar(PillarName.PSYCHOLOGICAL, userInfoDto));

        return showcase;
    }

    /**
     * Retrieves a list of coaches for a specified pillar and converts them to {@link InfoShowCaseDto} objects.
     *
     * @param pillarName the name of the pillar to filter coaches by
     * @param userInfoDto the user information used for further processing (not currently used)
     * @return a list of {@link InfoShowCaseDto} objects representing coaches for the specified pillar
     */
    private List<InfoShowCaseDto> getCoachListByPillar(PillarName pillarName, UserInfoDto userInfoDto) {
        Pillar pillar = pillarService.findByName(pillarName);
        List<Coach> servicesByPillar = coachRepository.findAllBySpecialization(pillar);
        return servicesByPillar.stream()
                .map(coachMapper::coachToInfoShowcaseDto)
                .toList();
    }

    /**
     * Adds a category of coaches to the showcase list if the category is not empty.
     *
     * @param showcase the list to add categories to
     * @param category the name of the category
     * @param services the list of {@link InfoShowCaseDto} objects to add
     */
    private void addToShowcase(List<CoachShowcaseDto> showcase, String category, List<InfoShowCaseDto> services) {
        if (!services.isEmpty()) {
            showcase.add(new CoachShowcaseDto(category, services));
        }
    }

    /**
     * Fetches user information from an external user service.
     *
     * @param userId the ID of the user to fetch information for
     * @return the {@link UserInfoDto} containing user details
     * @throws UserIdNotFoundException if the user information cannot be retrieved
     */
    private UserInfoDto getUserInfoDto(Long userId) {
        try {
            return webClient.get()
                    .uri("http://" + userServiceIp + ":" + userServicePort + "/api/user/info",
                            uriBuilder -> uriBuilder.queryParam("userId", userId).build())
                    .retrieve()
                    .bodyToMono(UserInfoDto.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UserIdNotFoundException("user id not found");
        }
    }

    /**
     * Creates a new {@link Coach} from the provided {@link CoachInfoTransferDto} and saves it to the repository.
     *
     * @param coachDto the DTO containing coach information to be created
     */
    @Transactional
    public void createCoach(CoachInfoTransferDto coachDto) {
        Coach coach = coachMapper.coachDtoToCoach(coachDto);
        create(coach);
    }
}
