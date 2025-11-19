package chervotkin.dev.eventnotificator.notifications.domain;


import chervotkin.dev.eventnotificator.notifications.db.NotificationEntity;
import chervotkin.dev.eventnotificator.notifications.db.NotificationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class NotificationService {


    private final NotificationRepository repository;


    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }


    public List<NotificationEntity> getUserNotifications(Long userId) {
        return repository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }


    public NotificationEntity createNotification(Long userId, String text) {
        var entity = new NotificationEntity(
                null,
                userId,
                text,
                false,
                LocalDateTime.now()
        );
        return repository.save(entity);
    }


    public void deleteOldNotifications() {
        var threshold = LocalDateTime.now().minusDays(7);
        repository.deleteAllByCreatedAtBefore(threshold);
    }
}

