package chervotkin.dev.eventnotificator.notifications.bo;


import chervotkin.dev.eventnotificator.notifications.db.NotificationEntity;
import chervotkin.dev.eventnotificator.notifications.dto.NotificationResponseDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationBO toBO(NotificationEntity entity) {
        NotificationBO bo = new NotificationBO();
        bo.setId(entity.getId());
        bo.setUserId(entity.getUserId());
        bo.setText(entity.getText());
        bo.setRead(entity.isRead());
        bo.setCreatedAt(entity.getCreatedAt());
        return bo;
    }

    public NotificationEntity toEntity(NotificationBO bo) {
        NotificationEntity entity = new NotificationEntity();
        entity.setId(bo.getId());
        entity.setUserId(bo.getUserId());
        entity.setText(bo.getText());
        entity.setRead(bo.isRead());
        entity.setCreatedAt(bo.getCreatedAt());
        return entity;
    }

    public NotificationResponseDto toDto(NotificationBO bo) {
        NotificationResponseDto dto = new NotificationResponseDto();
        dto.setId(bo.getId());
        dto.setText(bo.getText());
        dto.setRead(bo.isRead());
        dto.setCreatedAt(bo.getCreatedAt());
        return dto;
    }
}

