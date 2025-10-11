package com.shopservice.app.controller;

import com.shopservice.app.dto.PurchaseDto;
import com.shopservice.app.exception.PurchaseAlreadyMadeException;
import com.shopservice.app.exception.PurchaseNotFoundException;
import com.shopservice.app.model.Purchase;
import com.shopservice.app.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller class handling shop-related endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
public class ShopController {

    private final PurchaseService purchaseService;

    /**
     * Endpoint for making a purchase.
     *
     * @param purchaseDto The purchase details.
     * @return ResponseEntity containing the PurchaseDto and HTTP status.
     */
    @PostMapping("/make-purchase")
    public ResponseEntity<PurchaseDto> makePurchase(@RequestBody @Valid PurchaseDto purchaseDto) {
        purchaseService.makePurchase(purchaseDto);
        return new ResponseEntity<>(purchaseDto, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a list of service IDs purchased by a user.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity containing the list of service IDs and HTTP status.
     */
    @GetMapping("/purchased-services-id")
    public ResponseEntity<List<Long>> purchasedServicesId(@RequestParam Long userId) {
        List<Long> idList = purchaseService.getPurchasedServicesIdList(userId);
        return new ResponseEntity<>(idList, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a map of the most purchased services.
     *
     * @return ResponseEntity containing the map of service IDs and their purchase counts, and HTTP status.
     */
    @GetMapping("/most-purchased-list")
    public ResponseEntity<Map<Long, Long>> mostPurchasedList() {
        Map<Long, Long> mostPurchasedList = purchaseService.getMostPurchasedList();
        return new ResponseEntity<>(mostPurchasedList, HttpStatus.OK);
    }

    /**
     * Endpoint to check if a specific service is purchased by a user.
     *
     * @param userId    The ID of the user.
     * @param serviceId The ID of the service.
     * @return ResponseEntity indicating whether the service is purchased and HTTP status.
     */
    @GetMapping("/is-service-purchased")
    public ResponseEntity<Boolean> serviceLikes(@RequestParam Long userId, @RequestParam Long serviceId) {
        try {
            purchaseService.findByUserIdAndServiceId(userId, serviceId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (PurchaseNotFoundException ignored) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
