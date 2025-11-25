package chervotkin.dev.eventnotificator.notifications.api;

import chervotkin.dev.eventnotificator.notifications.domain.NotificationService;
import chervotkin.dev.eventnotificator.notifications.dto.MarkNotificationsReadRequest;
import chervotkin.dev.eventnotificator.notifications.dto.NotificationResponseDto;
import chervotkin.dev.user.AuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<NotificationResponseDto> getMyNotifications() {

        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return notificationService.getUnreadNotifications(user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markAsRead(@RequestBody MarkNotificationsReadRequest request) {

        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        notificationService.markNotificationsAsRead(user.getId(), request.getNotificationIds());
    }
}
