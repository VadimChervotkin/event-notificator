package chervotkin.dev.eventnotificator.notifications.domain;


import chervotkin.dev.eventnotificator.notifications.bo.NotificationBO;
import chervotkin.dev.eventnotificator.notifications.bo.NotificationMapper;
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
    private final NotificationMapper mapper;

    public NotificationService(NotificationRepository repository,
                               NotificationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<NotificationResponseDto> getUnreadNotifications(Long userId) {
        return repository.findAllByUserIdAndReadFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(mapper::toBO)
                .map(mapper::toDto)
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
        NotificationBO bo = new NotificationBO();
        bo.setUserId(userId);
        bo.setText(text);
        bo.setRead(false);
        bo.setCreatedAt(LocalDateTime.now());

        repository.save(mapper.toEntity(bo));
    }
}


