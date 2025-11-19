package chervotkin.dev.eventmanager.kafka.dto;

import chervotkin.dev.eventnotificator.kafka.dto.FieldChange;

import java.util.List;

public class EventChangeKafkaMessage {

    private Long eventId;
    private Long ownerId;
    private Long changedById;
    private List<Long> users;

    private List<FieldChange> changes;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getChangedById() {
        return changedById;
    }

    public void setChangedById(Long changedById) {
        this.changedById = changedById;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }

    public List<FieldChange> getChanges() {
        return changes;
    }

    public void setChanges(List<FieldChange> changes) {
        this.changes = changes;
    }
}