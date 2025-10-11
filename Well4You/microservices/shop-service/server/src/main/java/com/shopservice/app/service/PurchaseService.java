package com.shopservice.app.service;

import com.shopservice.app.dto.PurchaseDto;
import com.shopservice.app.dto.UserInfoDto;
import com.shopservice.app.exception.PurchaseAlreadyMadeException;
import com.shopservice.app.exception.PurchaseNotFoundException;
import com.shopservice.app.exception.UserUpdateFailException;
import com.shopservice.app.model.Purchase;
import com.shopservice.app.repository.IPurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class providing operations related to purchases.
 */
@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final EmailService emailService;

    @Value("${user.service.name}")
    private String userServiceIp;

    @Value("${user.service.port}")
    private String userServicePort;

    @Value("${services.service.name}")
    private String servicesServiceIp;

    @Value("${services.service.port}")
    private String servicesServicePort;

    private final WebClient webClient;

    private final IPurchaseRepository purchaseRepository;

    /**
     * Retrieves a purchase by its ID.
     *
     * @param id The ID of the purchase to retrieve.
     * @return The Purchase object if found.
     * @throws PurchaseNotFoundException If the purchase ID does not exist.
     */
    public Purchase findById(Long id) {
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        if (purchase.isEmpty()) {
            throw new PurchaseNotFoundException("Purchase id does not exist");
        }
        return purchase.get();
    }

    /**
     * Retrieves a purchase by user ID and service ID.
     *
     * @param userId    The ID of the user.
     * @param serviceId The ID of the service.
     * @return The Purchase object if found.
     * @throws PurchaseNotFoundException If the purchase for the user and service does not exist.
     */
    public Purchase findByUserIdAndServiceId(Long userId, Long serviceId) {
        Optional<Purchase> purchase = purchaseRepository.findByUserIdAndServiceId(userId, serviceId);
        if (purchase.isEmpty()) {
            throw new PurchaseNotFoundException("Purchase id does not exist");
        }
        return purchase.get();
    }

    /**
     * Retrieves all purchases for a specific service.
     *
     * @param serviceId The ID of the service.
     * @return A list of Purchase objects.
     */
    public List<Purchase> getPurchasesByService(Long serviceId) {
        return purchaseRepository.findAllByServiceId(serviceId);
    }

    /**
     * Creates a new purchase record.
     *
     * @param purchase The Purchase object to be created.
     */
    public void create(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    /**
     * Updates an existing purchase record.
     *
     * @param purchase The Purchase object to be updated.
     */
    public void update(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    /**
     * Retrieves IDs of services purchased by a user.
     *
     * @param userId The ID of the user.
     * @return A list of service IDs.
     */
    public List<Long> getPurchasedServicesIdList(Long userId) {
        List<Purchase> allPurchase = purchaseRepository.findAll();
        List<Long> purchasedServicesId = new ArrayList<>();

        for (Purchase purchase : allPurchase) {
            if (purchase.getUserId().equals(userId)) {
                purchasedServicesId.add(purchase.getServiceId());
            }
        }

        return purchasedServicesId;
    }

    /**
     * Retrieves a map of service IDs and their purchase counts.
     *
     * @return A map where keys are service IDs and values are purchase counts.
     */
    public Map<Long, Long> getMostPurchasedList() {
        return purchaseRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Purchase::getServiceId, Collectors.counting()));
    }

    /**
     * Performs the purchase transaction.
     *
     * @param purchaseDto The PurchaseDto containing user ID and service ID for the purchase.
     * @throws PurchaseAlreadyMadeException If the user has already purchased the service.
     */
    @Transactional
    public void makePurchase(PurchaseDto purchaseDto) {
        Optional<Purchase> purchase = purchaseRepository.findByUserIdAndServiceId(purchaseDto.getUserId(), purchaseDto.getServiceId());

        if (purchase.isPresent()) {
            throw new PurchaseAlreadyMadeException("You have already purchased this service");
        }

        updateSavedMoney(purchaseDto);
        create(new Purchase(purchaseDto.getUserId(), purchaseDto.getServiceId()));
        String userEmail = getUserEmail(purchaseDto.getUserId());
        emailService.sendEmail(userEmail, "You have successfully purchased the service. You can view all your purchased services in the 'purchased' section.", "Successful Purchase");
    }

    /**
     * Retrieves the email address of a user by their user ID through user service.
     *
     * @param userId The ID of the user.
     * @return The email address of the user.
     */
    public String getUserEmail(Long userId) {
        UserInfoDto userInfoDto;

        try {
            userInfoDto = webClient
                    .get()
                    .uri("http://" + userServiceIp + ":" + userServicePort + "/api/user/info",
                            uriBuilder -> uriBuilder.queryParam("userId", userId).build())
                    .retrieve()
                    .bodyToMono(UserInfoDto.class)
                    .block();
            return userInfoDto.getEmail();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Updates the saved money for a user after making a purchase through user service.
     *
     * @param purchaseDto The PurchaseDto containing service ID and user ID for the purchase.
     * @throws UserUpdateFailException If the update of saved money fails.
     */
    private void updateSavedMoney(PurchaseDto purchaseDto) {
        Double savedMoney = getSavedMoneyByServiceId(purchaseDto.getServiceId());

        try {
            webClient.put()
                    .uri("http://" + userServiceIp + ":" + userServicePort + "/secure/api/user/update-savings",
                            uriBuilder -> uriBuilder.queryParam("userId", purchaseDto.getUserId()).queryParam("savedMoney", savedMoney).build())
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UserUpdateFailException("Update saved money failed");
        }
    }

    /**
     * Retrieves the saved money for a user from user service.
     *
     * @param serviceId The ID of the service.
     * @return The saved money amount for the service.
     * @throws UserUpdateFailException If the retrieval of saved money fails.
     */
    private Double getSavedMoneyByServiceId(Long serviceId) {
        try {
            return webClient.get()
                    .uri("http://" + servicesServiceIp + ":" + servicesServicePort + "/api/services/get-saved-money",
                            uriBuilder -> uriBuilder.queryParam("serviceId", serviceId).build())
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UserUpdateFailException("Get saved money failed");
        }
    }
}
