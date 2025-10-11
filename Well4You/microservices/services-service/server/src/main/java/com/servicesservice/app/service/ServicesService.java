package com.servicesservice.app.service;

import com.servicesservice.app.dto.*;
import com.servicesservice.app.exception.*;
import com.servicesservice.app.mapper.ServiceMapper;
import com.servicesservice.app.model.Pillar;
import com.servicesservice.app.model.Service;
import com.servicesservice.app.model.enumerator.PillarName;
import com.servicesservice.app.repository.IServicesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing Service entities and integrating with external services.
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServicesService {

    private final PillarService pillarService;

    @Value("${user.service.name}")
    private String userServiceIp;

    @Value("${user.service.port}")
    private String userServicePort;

    @Value("${shop.service.name}")
    private String shopServiceIp;

    @Value("${shop.service.port}")
    private String shopServicePort;

    @Value("${image.service.name}")
    private String imageServiceIp;

    @Value("${image.service.port}")
    private String imageServicePort;

    @Value("${comment.service.name}")
    private String commentServiceIp;

    @Value("${comment.service.port}")
    private String commentServicePort;

    @Value("${like.service.name}")
    private String likeServiceIp;

    @Value("${like.service.port}")
    private String likeServicePort;

    private final IServicesRepository servicesRepository;
    private final ServiceMapper serviceMapper;
    private final WebClient webClient;

    /**
     * Retrieves a Service entity by its ID.
     *
     * @param id The ID of the Service to retrieve
     * @return The found Service entity
     * @throws ServiceNotFoundException if no Service with the given ID is found
     */
    public Service findById(Long id) {
        Optional<Service> service = servicesRepository.findById(id);
        if (service.isEmpty()) {
            throw new ServiceNotFoundException("Service not found");
        }
        return service.get();
    }

    /**
     * Saves a Service entity.
     *
     * @param service The Service entity to save
     */
    public void create(Service service) {
        servicesRepository.save(service);
    }

    /**
     * Uploads an image file to the image service.
     *
     * @param file The MultipartFile containing the image file
     * @return The ID of the uploaded image
     * @throws ImageUploadingFailException if there's an error during image uploading
     */
    public Long uploadImage(MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        ResponseDto responseDto;
        try {
            responseDto = webClient
                    .post()
                    .uri("http://" + imageServiceIp + ":" + imageServicePort + "/api/image/upload")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(ResponseDto.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ImageUploadingFailException("An error occurred while uploading the image");
        }

        assert responseDto != null;
        return responseDto.getImageId();
    }

    /**
     * Retrieves aggregated information about a Service, including likes, comments, and user interactions.
     *
     * @param serviceId The ID of the Service
     * @param userId    The ID of the User
     * @return An AggregatedServiceDto containing aggregated information
     */
    public AggregatedServiceDto retrieveAggregatedServices(Long serviceId, Long userId) {
        Service service = findById(serviceId);
        Long likes;
        Boolean isLiked;
        Boolean isPurchased;
        List<CommentDto> comments;
        double distance = 0.0;
        UserInfoDto userInfoDto;

        likes = getLikes(serviceId);
        isLiked = getIsLiked(new Like2Dto(serviceId, userId));
        isPurchased = getIsPurchased(serviceId, userId);
        comments = getCommentsByService(serviceId);

        try {
            userInfoDto = getUserInfoDto(userId);
            if (service.getLatitude() != 0 && service.getLongitude() != 0 && userInfoDto.getLongitude() != 0 && userInfoDto.getLatitude() != 0) {
                distance = DistanceService.calculateDistance(userInfoDto.getLatitude(), userInfoDto.getLongitude(), service.getLatitude(), service.getLongitude());
            }
            System.out.println("Aggregated:\nService = " + service + "\nLikes = " + likes + "\nIsLiked = " + isLiked + "\nComments = " + comments + "\nDistance = " + distance);
            return new AggregatedServiceDto(likes, comments, isLiked, isPurchased, distance, service.getDescription(), service.getPrice(), service.getDiscount(), new LocationDto(service.getLongitude(), service.getLatitude()), service.getUrl());

        } catch (Exception e) {
            return new AggregatedServiceDto(likes, comments, isLiked, isPurchased, distance, service.getDescription(), service.getPrice(), service.getDiscount(), new LocationDto(0, 0), service.getUrl());
        }
    }

    /**
     * Retrieves the number of likes for a Service from the like service.
     *
     * @param serviceId The ID of the Service
     * @return The number of likes
     */
    private Long getLikes(Long serviceId) {
        try {
            return webClient.get()
                    .uri("http://" + likeServiceIp + ":" + likeServicePort + "/api/like/service-likes",
                            uriBuilder -> uriBuilder.queryParam("serviceId", serviceId).build())
                    .retrieve()
                    .bodyToMono(Long.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0L;
        }
    }

    /**
     * Checks if a Service is liked by a User.
     *
     * @param like2Dto The Like2Dto containing Service ID and User ID
     * @return true if the Service is liked by the User, false otherwise
     */
    private Boolean getIsLiked(Like2Dto like2Dto) {
        try {
            return webClient
                    .post()
                    .uri("http://" + likeServiceIp + ":" + likeServicePort + "/api/like/is-service-liked")
                    .bodyValue(like2Dto)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Checks if a Service is purchased by a User.
     *
     * @param serviceId The ID of the Service
     * @param userId    The ID of the User
     * @return true if the Service is purchased by the User, false otherwise
     */
    private Boolean getIsPurchased(Long serviceId, Long userId) {
        try {
            return webClient
                    .get()
                    .uri("http://" + shopServiceIp + ":" + shopServicePort + "/api/shop/is-service-purchased",
                            uriBuilder -> uriBuilder.queryParam("serviceId", serviceId).queryParam("userId", userId).build())
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves comments for a Service from the comment service.
     *
     * @param serviceId The ID of the Service
     * @return List of CommentDto objects
     */
    private List<CommentDto> getCommentsByService(Long serviceId) {
        try {
            return webClient
                    .get()
                    .uri("http://" + commentServiceIp + ":" + commentServicePort + "/api/comment/service-comments",
                            uriBuilder -> uriBuilder.queryParam("serviceId", serviceId).build())
                    .retrieve()
                    .bodyToFlux(CommentDto.class)
                    .collectList() // Convert Flux<CommentDto> to List<CommentDto>
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new LinkedList<>();
        }
    }

    /**
     * Updates an existing Service entity.
     *
     * @param service The updated Service entity
     */
    public void update(Service service) {
        servicesRepository.save(service);
    }

    /**
     * Creates a showcase of services based on user preferences.
     *
     * @param userId The ID of the User
     * @return List of ServiceShowcaseDto objects
     */
    public List<ServiceShowcaseDto> createShowcase(Long userId) {
        UserInfoDto userInfoDto = getUserInfoDto(userId);
        List<ServiceShowcaseDto> showcase = new ArrayList<>();

        addToShowcase(showcase, "For Me", getForMeServices(userInfoDto));
        addToShowcase(showcase, "Near Me", getNearMeServices(userInfoDto));
        addToShowcase(showcase, "Most Popular", getMostPopularServices());
        addToShowcase(showcase, "Most Purchased", getMostPurchased());
        addToShowcase(showcase, "Economic", getServicesByPillar(PillarName.ECONOMIC));
        addToShowcase(showcase, "Family", getServicesByPillar(PillarName.FAMILY));
        addToShowcase(showcase, "Physical", getServicesByPillar(PillarName.PHYSICAL));
        addToShowcase(showcase, "Psychological", getServicesByPillar(PillarName.PSYCHOLOGICAL));

        return showcase;
    }

    /**
     * Creates a showcase of purchased services for a User.
     *
     * @param userId The ID of the User
     * @return List of ServiceShowcaseDto objects
     */
    public List<ServiceShowcaseDto> createPurchaseShowcase(Long userId) {
        List<ServiceShowcaseDto> showcase = new ArrayList<>();
        addToShowcase(showcase, "Purchased", getPurchasedById(userId));
        return showcase;
    }

    /**
     * Creates a filtered showcase of purchased services based on pillars for a User.
     *
     * @param userId The ID of the User
     * @return List of ServiceShowcaseDto objects
     */
    public List<ServiceShowcaseDto> createFilteredPurchaseShowcase(Long userId) {
        List<ServiceShowcaseDto> showcase = new ArrayList<>();
        List<InfoShowCaseDto> allPurchasedServices = getPurchasedById(userId);

        addToShowcase(showcase, "Family", filterServicesByPillar(allPurchasedServices, "FAMILY"));
        addToShowcase(showcase, "Economic", filterServicesByPillar(allPurchasedServices, "ECONOMIC"));
        addToShowcase(showcase, "Psychological", filterServicesByPillar(allPurchasedServices, "PSYCHOLOGICAL"));
        addToShowcase(showcase, "Physical", filterServicesByPillar(allPurchasedServices, "PHYSICAL"));

        return showcase;
    }

    /**
     * Adds services to a showcase category if they are not empty.
     *
     * @param showcase The list of ServiceShowcaseDto to add to
     * @param category The category name
     * @param services The list of InfoShowCaseDto services to add
     */
    private void addToShowcase(List<ServiceShowcaseDto> showcase, String category, List<InfoShowCaseDto> services) {
        if (!services.isEmpty()) {
            showcase.add(new ServiceShowcaseDto(category, services));
        }
    }

    /**
     * Filters services by pillar name from a list of InfoShowCaseDto services.
     *
     * @param services   The list of InfoShowCaseDto services to filter
     * @param pillarName The pillar name to filter by
     * @return List of filtered InfoShowCaseDto services
     */
    private List<InfoShowCaseDto> filterServicesByPillar(List<InfoShowCaseDto> services, String pillarName) {
        return services.stream()
                .filter(service -> pillarName.equalsIgnoreCase(service.getPillarName().toString()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves purchased services by User ID.
     *
     * @param userId The ID of the User
     * @return List of InfoShowCaseDto representing purchased services
     */
    private List<InfoShowCaseDto> getPurchasedById(Long userId) {
        List<Long> purchasedServicesId;
        purchasedServicesId = getPurchasedServicesId(userId);
        assert purchasedServicesId != null;
        List<Service> purchasedServices = servicesRepository.findAllById(purchasedServicesId);

        return purchasedServices.stream()
                .map(serviceMapper::serviceToInfoShowCaseDto)
                .toList();
    }

    /**
     * Retrieves IDs of purchased services by User ID.
     *
     * @param userId The ID of the User
     * @return List of Long representing purchased service IDs
     */
    private List<Long> getPurchasedServicesId(Long userId) {
        try {
            return webClient
                    .get()
                    .uri("http://" + shopServiceIp + ":" + shopServicePort + "/api/shop/purchased-services-id",
                            uriBuilder -> uriBuilder.queryParam("userId", userId).build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Long>>() {
                    })
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves User information by User ID.
     *
     * @param userId The ID of the User
     * @return UserInfoDto containing user information
     * @throws UserIdNotFoundException if no User with the given ID is found
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
            throw new UserIdNotFoundException("User ID not found");
        }
    }

    /**
     * Retrieves the most popular services based on likes.
     *
     * @return List of InfoShowCaseDto representing most popular services
     */
    public List<InfoShowCaseDto> getMostPopularServices() {
        Map<Long, Long> serviceLikeCounts = new HashMap<>();
        List<Service> mostPopularServices = new ArrayList<>();

        serviceLikeCounts = getServiceLikeCounts();
        if (serviceLikeCounts != null && !serviceLikeCounts.isEmpty()) {
            List<Long> servicesIdList = serviceLikeCounts.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .map(Map.Entry::getKey)
                    .toList();

            for (Long serviceId : servicesIdList) {
                Service service = findById(serviceId);
                mostPopularServices.add(service);
            }
        }
        return mostPopularServices.stream()
                .map(serviceMapper::serviceToInfoShowCaseDto)
                .toList();
    }

    /**
     * Retrieves like counts for all services.
     *
     * @return Map of Service ID to like count
     */
    private Map<Long, Long> getServiceLikeCounts() {
        try {
            return webClient
                    .get()
                    .uri("http://" + likeServiceIp + ":" + likeServicePort + "/api/like/most-likes-list")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<Long, Long>>() {
                    })
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves services tailored for a specific User based on their profile information.
     *
     * @param user The UserInfoDto containing User's profile information
     * @return List of InfoShowCaseDto representing services tailored for the User
     */
    private List<InfoShowCaseDto> getForMeServices(UserInfoDto user) {

        List<Service> allServices = servicesRepository.findAll();
        Map<Service, Integer> serviceScores = new HashMap<>();

        for (Service service : allServices) {
            int score = 0;

            if (user.getMaritalStatusName().equals("MARRIED") && service.getPillar().getName().equals(PillarName.FAMILY)) {
                score += 5;
            }
            if (user.getHasChildren() && service.getPillar().getName().equals(PillarName.FAMILY)) {
                score += 5;
            }
            if (user.getHasElderlyParents() && service.getPillar().getName().equals(PillarName.FAMILY)) {
                score += 5;
            }

            serviceScores.put(service, score);
        }

        allServices = serviceScores.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue())
                .map(Map.Entry::getKey)
                .limit(8)
                .toList();

        return allServices.stream()
                .map(serviceMapper::serviceToInfoShowCaseDto)
                .toList();
    }


    /**
     * Retrieves services near a User's location.
     *
     * @param userInfoDto The UserInfoDto containing User's location information
     * @return List of InfoShowCaseDto representing services near the User
     */
    public List<InfoShowCaseDto> getNearMeServices(UserInfoDto userInfoDto) {
        List<Service> allServices = servicesRepository.findAll();
        List<Service> sortedServices = DistanceService.filterAndSortServicesByProximity(userInfoDto, allServices);
        return sortedServices.stream()
                .map(serviceMapper::serviceToInfoShowCaseDto)
                .toList();
    }

    /**
     * Retrieves services by a specific Pillar.
     *
     * @param pillarName The name of the Pillar
     * @return List of InfoShowCaseDto representing services in the Pillar
     */
    public List<InfoShowCaseDto> getServicesByPillar(PillarName pillarName) {
        Pillar pillar = pillarService.findByName(pillarName);
        List<Service> servicesByPillar = servicesRepository.findAllByPillar(pillar);
        return servicesByPillar.stream()
                .map(serviceMapper::serviceToInfoShowCaseDto)
                .toList();
    }

    /**
     * Retrieves the most purchased services.
     *
     * @return List of InfoShowCaseDto representing most purchased services
     */
    private List<InfoShowCaseDto> getMostPurchased() {
        Map<Long, Long> servicePurchaseCounts = new HashMap<>();
        List<Service> mostPurchasedServices = new ArrayList<>();

        servicePurchaseCounts = getServicePurchaseCounts();
        if (servicePurchaseCounts != null && !servicePurchaseCounts.isEmpty()) {
            List<Long> servicesIdList = servicePurchaseCounts.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .map(Map.Entry::getKey)
                    .toList();

            for (Long serviceId : servicesIdList) {
                Service service = findById(serviceId);
                mostPurchasedServices.add(service);
            }
        }
        return mostPurchasedServices.stream()
                .map(serviceMapper::serviceToInfoShowCaseDto)
                .toList();
    }

    /**
     * Retrieves purchase counts for all services.
     *
     * @return Map of Service ID to purchase count
     */
    private Map<Long, Long> getServicePurchaseCounts() {
        try {
            return webClient
                    .get()
                    .uri("http://" + shopServiceIp + ":" + shopServicePort + "/api/shop/most-purchased-list")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<Long, Long>>() {
                    })
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Calculates the amount of money saved for a service.
     *
     * @param serviceId The ID of the Service
     * @return The amount of money saved
     */
    public Double getSavedMoneyById(Long serviceId) {
        Service service = findById(serviceId);
        return (service.getPrice() * service.getDiscount()) / 100;
    }

    /**
     * Creates a new Service entity from a ServiceDto and uploads an associated image.
     *
     * @param serviceDto The ServiceDto containing service information
     * @param file       The MultipartFile containing the image file
     * @return The created Service entity
     */
    @Transactional
    public Service createService(ServiceDto serviceDto, MultipartFile file) {
        Long imageId = uploadImage(file);
        Service service = serviceMapper.serviceDtoToService(serviceDto, imageId);
        create(service);
        return service;
    }

    /**
     * Retrieves services for a specific section based on a section name and User ID.
     *
     * @param section The name of the section
     * @param userId  The ID of the User
     * @return ServiceShowcaseDto representing services for the section
     */
    public ServiceShowcaseDto getSectionServices(String section, Long userId) {
        if (section.equals("Family") || section.equals("Economic") || section.equals("Psychological") || section.equals("Physical")) {
            return new ServiceShowcaseDto(section, this.getServicesByPillar(PillarName.valueOf(section.toUpperCase())));
        }
        if (section.equals("Most-purchased")) {
            return new ServiceShowcaseDto("Most Purchased", this.getMostPurchased());
        }
        if (section.equals("Near-me")) {
            UserInfoDto userInfoDto = getUserInfoDto(userId);
            return new ServiceShowcaseDto("Near Me", this.getNearMeServices(userInfoDto));
        }
        if (section.equals("Most-popular")) {
            return new ServiceShowcaseDto("Most Popular", this.getMostPopularServices());
        }
        if (section.equals("For-me")) {
            UserInfoDto userInfoDto = getUserInfoDto(userId);
            return new ServiceShowcaseDto("For Me", this.getForMeServices(userInfoDto));
        }
        return null;
    }
}
