package soungegroup.soungeapi.response;

import soungegroup.soungeapi.enums.SkillLevel;
import soungegroup.soungeapi.model.Post;

import java.util.List;

public class UserProfileResponse {
    UserSimpleResponse user;
    List<Post> posts;
    String profilePic;
    String spotifyID;
    String description;
    Boolean isOnline;
    SkillLevel skillLevel;
    GroupSimpleResponse group;
    List<GenreSimpleResponse> likedGenres;
    List<RoleSimpleResponse> roles;
    private Integer age;










    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    public GroupSimpleResponse getGroup() {
        return group;
    }

    public void setGroup(GroupSimpleResponse group) {
        this.group = group;
    }

    public List<GenreSimpleResponse> getLikedGenres() {
        return likedGenres;
    }

    public void setLikedGenres(List<GenreSimpleResponse> likedGenres) {
        this.likedGenres = likedGenres;
    }

    public List<RoleSimpleResponse> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleSimpleResponse> roles) {
        this.roles = roles;
    }

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
