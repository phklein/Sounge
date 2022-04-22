package soungegroup.soungeapi.response;

import soungegroup.soungeapi.model.Post;

import java.util.List;

public class UserProfileResponse {
    UserSimpleResponse user;
    List<Post> posts;
    String profilePic;
    String spotifyID;
    String description;
    Boolean isOnline;



    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getSpotifyID() {
        return spotifyID;
    }

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }

    public UserSimpleResponse getUser() {
        return user;
    }

    public void setUser(UserSimpleResponse user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
