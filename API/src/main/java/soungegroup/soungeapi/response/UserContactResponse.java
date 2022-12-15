package soungegroup.soungeapi.response;

import org.springframework.beans.factory.annotation.Value;

public class UserContactResponse {
    private Long id;
    private String name;
    private String profilePic;
    private Boolean leader;
    private String phoneNumber;

    public UserContactResponse(Long id, String name, String profilePic, Boolean leader, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.profilePic = profilePic;
        this.leader = leader;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
