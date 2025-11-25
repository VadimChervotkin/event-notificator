package chervotkin.dev.eventnotificator.kafka;


import chervotkin.dev.eventmanager.kafka.dto.EventChangeKafkaMessage;
import chervotkin.dev.eventnotificator.notifications.domain.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class EventChangeConsumer {

    private static final Logger log = LoggerFactory.getLogger(EventChangeConsumer.class);

    private final NotificationService notificationService;

    public EventChangeConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "events.changes", groupId = "notificator-group")
    public void consume(EventChangeKafkaMessage msg) {
        log.info("Received Kafka message: {}", msg);


        String text = "Event " + msg.getEventId() + " was updated";

        notificationService.createNotification(msg.getOwnerId(), text);

        for (Long userId : msg.getUsers()) {
            notificationService.createNotification(userId, text);
        }
    }
}