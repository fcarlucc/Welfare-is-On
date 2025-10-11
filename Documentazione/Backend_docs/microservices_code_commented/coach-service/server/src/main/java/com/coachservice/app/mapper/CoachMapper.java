package com.coachservice.app.mapper;

import com.coachservice.app.dto.AggregatedCoachDto;
import com.coachservice.app.dto.CoachInfoTransferDto;
import com.coachservice.app.dto.InfoShowCaseDto;
import com.coachservice.app.dto.LocationDto;
import com.coachservice.app.model.Pillar;
import com.coachservice.app.service.DivisionService;
import com.coachservice.app.service.PillarService;
import com.coachservice.app.model.Coach;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting between {@link Coach} entities and their corresponding Data Transfer Objects (DTOs).
 * <p>
 * This class provides methods to transform {@link CoachInfoTransferDto} into a {@link Coach} entity and vice versa.
 * It also converts {@link Coach} entities into various DTOs used for different purposes such as showcasing
 * information or aggregating coach details.
 * </p>
 *
 * <p>The {@link CoachMapper} uses services {@link PillarService} and {@link DivisionService} to retrieve additional
 * details required for mapping. These services help in resolving entities like {@link Pillar} based on provided DTO data.</p>
 */
@Component
@RequiredArgsConstructor
public class CoachMapper {

    private final PillarService pillarService;
    private final DivisionService divisionService;

    /**
     * Converts a {@link CoachInfoTransferDto} to a {@link Coach} entity.
     * <p>
     * This method maps the fields from the DTO to the corresponding fields in the {@link Coach} entity.
     * It also resolves the {@link Pillar} and Division using their respective services based on the
     * specialization and division names provided in the DTO.
     * </p>
     *
     * @param coachDto the {@link CoachInfoTransferDto} containing coach details
     * @return a {@link Coach} entity populated with details from the DTO
     */
    public Coach coachDtoToCoach(CoachInfoTransferDto coachDto) {

        Pillar specialization = pillarService.findByName(coachDto.getSpecialization());

        return new Coach(coachDto.getId(),
                coachDto.getFirstName(),
                coachDto.getLastName(),
                coachDto.getEmail(),
                coachDto.getImageId(),
                specialization,
                coachDto.getLongitude(),
                coachDto.getLatitude(),
                coachDto.getPhoneNumber(),
                divisionService.findByName(coachDto.getDivision()));
    }

    /**
     * Converts a {@link Coach} entity to an {@link InfoShowCaseDto} for showcasing purposes.
     * <p>
     * This method extracts essential details from the {@link Coach} entity and maps them to the {@link InfoShowCaseDto}.
     * It includes basic information such as name, image, division, and location details.
     * </p>
     *
     * @param coach the {@link Coach} entity to convert
     * @return an {@link InfoShowCaseDto} containing essential information about the coach
     */
    public InfoShowCaseDto coachToInfoShowcaseDto(Coach coach) {
        return new InfoShowCaseDto(coach.getId(),
                coach.getFirstName(),
                coach.getLastName(),
                coach.getImageId(),
                coach.getDivision().getName(),
                new LocationDto(coach.getLongitude(), coach.getLatitude()));
    }

    /**
     * Converts a {@link Coach} entity to an {@link AggregatedCoachDto} for aggregated data representation.
     * <p>
     * This method extracts specific details from the {@link Coach} entity and maps them to the {@link AggregatedCoachDto},
     * which includes specialization, phone number, and email.
     * </p>
     *
     * @param coach the {@link Coach} entity to convert
     * @return an {@link AggregatedCoachDto} containing aggregated details about the coach
     */
    public AggregatedCoachDto coachToAggregatedCoachDto(Coach coach) {
        return new AggregatedCoachDto(
                coach.getSpecialization().getName(),
                coach.getPhoneNumber(),
                coach.getEmail());
    }
}
