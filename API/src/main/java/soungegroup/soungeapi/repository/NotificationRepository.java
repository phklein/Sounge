package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import soungegroup.soungeapi.model.Notification;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.response.NotificationSimpleResponse;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.NotificationSimpleResponse(" +
            "n.id, n.text, n.type, n.creationDateTime, n.sender) " +
            "FROM Notification n " +
            "WHERE n.receiver = :user " +
            "AND n.type <> 0 " +
            "ORDER BY n.creationDateTime DESC")
    List<NotificationSimpleResponse> findByUser(User user, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n " +
            "SET n.viewed = TRUE " +
            "WHERE n.receiver = :user " +
            "AND n.type <> 0")
    void setViewedByUser(User user);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n " +
            "SET n.viewed = TRUE " +
            "WHERE n.receiver = :user " +
            "AND n.type = 0")
    void setMatchesViewedByUser(User user);
}
