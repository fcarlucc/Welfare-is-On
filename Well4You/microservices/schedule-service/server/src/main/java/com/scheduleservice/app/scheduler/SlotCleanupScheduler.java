package com.scheduleservice.app.scheduler;

import com.scheduleservice.app.service.AvailabilityService;
import com.scheduleservice.app.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * A scheduler component responsible for periodically cleaning up expired slots and availabilities.
 * This class uses Spring's {@link Scheduled} annotation to execute tasks at regular intervals.
 *
 * <p>The scheduler performs two main functions:</p>
 * <ul>
 *     <li>Deletes expired availabilities by invoking the {@link AvailabilityService#deleteExpiredAvailabilities()} method.</li>
 *     <li>Deletes expired slots by invoking the {@link SlotService#deleteExpiredSlots()} method.</li>
 * </ul>
 *
 * <p>The cleanup operation is scheduled to run every 5 minutes as defined by the cron expression.</p>
 */
@Component
@RequiredArgsConstructor
public class SlotCleanupScheduler {

    private final SlotService slotService;
    private final AvailabilityService availabilityService;

    /**
     * Executes the cleanup process for expired slots and availabilities.
     * <p>
     * This method is scheduled to run every 5 minutes as defined by the cron expression:
     * <code>0 0/5 * * * *</code>. It performs the following actions:
     * </p>
     * <ul>
     *     <li>Calls {@link AvailabilityService#deleteExpiredAvailabilities()} to remove expired availabilities.</li>
     *     <li>Calls {@link SlotService#deleteExpiredSlots()} to remove expired slots.</li>
     * </ul>
     */
    @Scheduled(cron = "0 0/5 * * * *") // Run every 5 minutes
    public void cleanupExpiredSlots() {
        availabilityService.deleteExpiredAvailabilities();
        slotService.deleteExpiredSlots();
    }
}
