package soungegroup.soungeapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import soungegroup.soungeapi.enums.NotificationType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Notification")
@Table(name = "tb_notification")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id") private Long id;
    @Column(name = "notification_text") private String text;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "notification_type") private NotificationType type;
    @Column(name = "notification_viewed") private boolean viewed;
    @Column(name = "notification_creation_date_time") private LocalDateTime creationDateTime;

    // Many notifications can be received by one user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_fk")
    private User receiver;

    // Many notifications can be sent by one user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_fk")
    private User sender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
