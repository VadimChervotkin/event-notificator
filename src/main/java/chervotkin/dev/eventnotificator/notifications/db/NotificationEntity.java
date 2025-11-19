package chervotkin.dev.eventnotificator.notifications.db;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "notifications")
public class NotificationEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long userId;


    @Column(nullable = false, length = 500)
    private String text;


    @Column(nullable = false)
    private boolean read;


    @Column(nullable = false)
    private LocalDateTime createdAt;


    public NotificationEntity() {}


    public NotificationEntity(Long id, Long userId, String text, boolean read, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.read = read;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}