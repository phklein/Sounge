package soungegroup.soungeapi.response;

import org.springframework.beans.factory.annotation.Value;

public interface UserContactResponse {
    @Value("#{target.user_id}")
    Long getId();
    @Value("#{target.user_name}")
    String getName();
    @Value("#{target.user_photo}")
    String getProfilePic();
    @Value("#{target.user_leader}")
    Boolean getLeader();
}
