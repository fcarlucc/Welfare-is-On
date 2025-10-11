package com.servicesservice.app.controller;

import com.servicesservice.app.dto.*;
import com.servicesservice.app.model.Service;
import com.servicesservice.app.service.ServicesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller handling service-related endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/services")
public class ServicesController {

    private final ServicesService servicesService;

    /**
     * Endpoint for creating a new service with associated file upload.
     *
     * @param serviceDto DTO containing service details
     * @param file       MultipartFile containing service file
     * @return ResponseEntity containing created Service object
     */
    @PostMapping(path = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Service> createService(@RequestPart("service") @Valid ServiceDto serviceDto, @RequestPart("file") MultipartFile file) {
        Service service = servicesService.createService(serviceDto, file);
        return new ResponseEntity<>(service, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving aggregated service details.
     *
     * @param serviceId ID of the service to retrieve
     * @param userId    ID of the user requesting the service
     * @return ResponseEntity containing AggregatedServiceDto
     */
    @GetMapping("/get-service")
    public ResponseEntity<AggregatedServiceDto> getService(@RequestParam Long serviceId, @RequestParam Long userId) {
        AggregatedServiceDto aggregatedServiceDto = servicesService.retrieveAggregatedServices(serviceId, userId);
        return new ResponseEntity<>(aggregatedServiceDto, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving saved money associated with a service.
     *
     * @param serviceId ID of the service to retrieve saved money for
     * @return ResponseEntity containing saved money amount
     */
    @GetMapping("/get-saved-money")
    public ResponseEntity<Double> getSavedMoney(@RequestParam Long serviceId) {
        Double savedMoney = servicesService.getSavedMoneyById(serviceId);
        return new ResponseEntity<>(savedMoney, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving showcase details based on user ID.
     *
     * @param userId ID of the user
     * @return ResponseEntity containing ShowCaseDto
     */
    @GetMapping("/get-showcase")
    public ResponseEntity<ShowCaseDto> getShowCase(@RequestParam Long userId) {
        ShowCaseDto showCaseDto = new ShowCaseDto(servicesService.createShowcase(userId));
        return new ResponseEntity<>(showCaseDto, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving purchased showcase details based on user ID.
     *
     * @param userId ID of the user
     * @return ResponseEntity containing ShowCaseDto
     */
    @GetMapping("/get-purchased-showcase")
    public ResponseEntity<ShowCaseDto> getPurchasedShowCase(@RequestParam Long userId) {
        ShowCaseDto showCaseDto = new ShowCaseDto(servicesService.createPurchaseShowcase(userId));
        return new ResponseEntity<>(showCaseDto, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving filtered purchased showcase details based on user ID.
     *
     * @param userId ID of the user
     * @return ResponseEntity containing ShowCaseDto
     */
    @GetMapping("/get-filtered-purchased-showcase")
    public ResponseEntity<ShowCaseDto> getFilteredPurchasedShowCase(@RequestParam Long userId) {
        ShowCaseDto showCaseDto = new ShowCaseDto(servicesService.createFilteredPurchaseShowcase(userId));
        return new ResponseEntity<>(showCaseDto, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving services based on section and user ID.
     *
     * @param section Section name to filter services
     * @param userId  ID of the user
     * @return ResponseEntity containing ServiceShowcaseDto
     */
    @GetMapping("/get-section-services")
    public ResponseEntity<ServiceShowcaseDto> getSectionServices(@RequestParam String section, @RequestParam Long userId) {
        ServiceShowcaseDto serviceShowcaseDto = servicesService.getSectionServices(section, userId);
        return new ResponseEntity<>(serviceShowcaseDto, HttpStatus.OK);
    }
}
