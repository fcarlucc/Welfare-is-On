package com.servicesservice.app.mapper;

import com.servicesservice.app.dto.InfoShowCaseDto;
import com.servicesservice.app.dto.ServiceDto;
import com.servicesservice.app.model.Service;
import com.servicesservice.app.service.PillarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for mapping between ServiceDto, Service, and InfoShowCaseDto.
 */
@Component
@RequiredArgsConstructor
public class ServiceMapper {

    private final PillarService pillarService;

    /**
     * Maps a ServiceDto and an imageId to a Service entity.
     *
     * @param serviceDto The ServiceDto object containing service details
     * @param imageId    The ID of the image associated with the service
     * @return Service entity mapped from ServiceDto
     */
    public Service serviceDtoToService(ServiceDto serviceDto, Long imageId) {
        Service service = new Service();

        service.setDescription(serviceDto.getDescription());
        service.setUrl(serviceDto.getUrl());
        service.setPrice(serviceDto.getPrice());
        service.setTitle(serviceDto.getTitle());
        service.setImageId(imageId);
        service.setDiscount(serviceDto.getDiscount());
        service.setPillar(pillarService.findByName(serviceDto.getPillarName()));

        // Optional: Set latitude and longitude if provided
        if (serviceDto.getLatitude() != null && serviceDto.getLongitude() != null) {
            service.setLatitude(serviceDto.getLatitude());
            service.setLongitude(serviceDto.getLongitude());
        }

        return service;
    }

    /**
     * Maps a Service entity to an InfoShowCaseDto.
     *
     * @param service The Service entity to map
     * @return InfoShowCaseDto mapped from Service
     */
    public InfoShowCaseDto serviceToInfoShowCaseDto(Service service) {
        InfoShowCaseDto infoShowCaseDto = new InfoShowCaseDto();

        infoShowCaseDto.setId(service.getId());
        infoShowCaseDto.setTitle(service.getTitle());
        infoShowCaseDto.setImageId(service.getImageId());
        infoShowCaseDto.setPillarName(service.getPillar().getName());

        return infoShowCaseDto;
    }

}
