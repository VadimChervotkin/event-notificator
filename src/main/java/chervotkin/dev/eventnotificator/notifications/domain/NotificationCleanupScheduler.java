package chervotkin.dev.eventnotificator.notifications.domain;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class NotificationCleanupScheduler {


    private static final Logger log = LoggerFactory.getLogger(NotificationCleanupScheduler.class);


    private final NotificationService service;


    public NotificationCleanupScheduler(NotificationService service) {
        this.service = service;
    }


    @Scheduled(cron = "0 */10 * * * *")
    public void cleanOld() {
        log.info("Cleaning old notifications...");
        service.deleteOldNotifications();
    }
}