package chervotkin.dev.eventnotificator.notifications.api;


import chervotkin.dev.eventnotificator.notifications.db.NotificationEntity;
import chervotkin.dev.eventnotificator.notifications.domain.NotificationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/notifications")
public class NotificationController {


    private final NotificationService notificationService;


    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @GetMapping
    public List<NotificationEntity> getMyNotifications(@RequestAttribute("userId") Long userId) {
        return notificationService.getUserNotifications(userId);
    }


    @PostMapping
    public NotificationEntity create(@RequestAttribute("userId") Long userId,
                                     @RequestBody String text) {
        return notificationService.createNotification(userId, text);
    }
}
