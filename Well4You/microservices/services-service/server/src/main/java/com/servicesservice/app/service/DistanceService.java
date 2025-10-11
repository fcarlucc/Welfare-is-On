package com.servicesservice.app.service;

import com.servicesservice.app.dto.UserInfoDto;
import com.servicesservice.app.model.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for distance-related calculations and service filtering based on proximity.
 */
public class DistanceService {

    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    /**
     * Checks if given latitude and longitude values are valid.
     *
     * @param latitude  The latitude value to check
     * @param longitude The longitude value to check
     * @return true if latitude and longitude are within valid range, false otherwise
     */
    public static boolean isValid(double latitude, double longitude) {
        return latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180 && longitude != 0 && latitude != 0;
    }

    /**
     * Calculates the distance between two geographical points using Haversine formula.
     *
     * @param startLat  Latitude of the starting point
     * @param startLong Longitude of the starting point
     * @param endLat    Latitude of the ending point
     * @param endLong   Longitude of the ending point
     * @return The distance between the two points in kilometers
     */
    public static double calculateDistance(double startLat, double startLong, double endLat, double endLong) {

        double dLat = Math.toRadians(endLat - startLat);
        double dLong = Math.toRadians(endLong - startLong);

        double startLatRad = Math.toRadians(startLat);
        double endLatRad = Math.toRadians(endLat);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLong / 2) * Math.sin(dLong / 2) *
                        Math.cos(startLatRad) * Math.cos(endLatRad);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    /**
     * Filters and sorts a list of services based on proximity to a user's location.
     *
     * @param user     The user information including latitude and longitude
     * @param services The list of services to filter and sort
     * @return A filtered and sorted list of services based on proximity to the user
     */
    public static List<Service> filterAndSortServicesByProximity(UserInfoDto user, List<Service> services) {
        return services.stream()
                .filter(service -> isValid(service.getLatitude(), service.getLongitude()))
                .sorted((service1, service2) -> {
                    double distanceToService1 = calculateDistance(user.getLatitude(), user.getLongitude(), service1.getLatitude(), service1.getLongitude());
                    double distanceToService2 = calculateDistance(user.getLatitude(), user.getLongitude(), service2.getLatitude(), service2.getLongitude());
                    return Double.compare(distanceToService1, distanceToService2);
                })
                .collect(Collectors.toList());
    }
}
