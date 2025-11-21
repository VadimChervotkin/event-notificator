package chervotkin.dev.eventnotificator.notifications.db;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;


public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {


    List<NotificationEntity> findAllByUserIdAndReadFalseOrderByCreatedAtDesc(Long userId);


    List<NotificationEntity> findAllByIdInAndUserId(List<Long> ids, Long userId);


    void deleteAllByCreatedAtBefore(LocalDateTime threshold);
}