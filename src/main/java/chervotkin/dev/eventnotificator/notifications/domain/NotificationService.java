package chervotkin.dev.eventnotificator.notifications.domain;


import chervotkin.dev.eventnotificator.notifications.db.NotificationEntity;
import chervotkin.dev.eventnotificator.notifications.db.NotificationRepository;
import chervotkin.dev.eventnotificator.notifications.dto.NotificationResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class NotificationService {


    private final NotificationRepository repository;


    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }


    public List<NotificationResponseDto> getUnreadNotifications(Long userId) {
        return repository.findAllByUserIdAndReadFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }


    public void deleteOldNotifications() {
        var threshold = LocalDateTime.now().minusDays(7);
        repository.deleteAllByCreatedAtBefore(threshold);
    }

    @Transactional
    public void markNotificationsAsRead(Long userId, List<Long> ids) {
        List<NotificationEntity> notifications =
                repository.findAllByIdInAndUserId(ids, userId);

        notifications.stream()
                .filter(n -> n.getUserId().equals(userId))
                .forEach(n -> n.setRead(true));

        repository.saveAll(notifications);
    }

    @Transactional
    public void createNotification(Long userId, String text) {
        NotificationEntity entity = new NotificationEntity();
        entity.setUserId(userId);
        entity.setText(text);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setRead(false);

        repository.save(entity);
    }

    private NotificationResponseDto toDto(NotificationEntity entity) {
        NotificationResponseDto dto = new NotificationResponseDto();
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setRead(entity.isRead());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

}

