package isapp.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import isapp.model.FileRequest;
import isapp.model.Post;
import isapp.model.UserFavoritePost;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "user_id")
    private UUID userId;

    private String name;

    private String email;

    private String phone;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_photo", referencedColumnName = "file_id")
    private FileRequest profilePhoto;

    private String role;

    private UUID facultyId;

//    @ManyToMany(mappedBy = "userSet")
//    @JsonIgnoreProperties("userSet")
//    private Set<Post> postSet = new HashSet<>() ;

//    @OneToMany(mappedBy = "user")
//    Set<UserPost> posts;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "users_favorite_posts_junction", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private Set<Post> favoriteSet = new HashSet<>();

    private String description;

    public User() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UUID getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(UUID facultyId) {
        this.facultyId = facultyId;
    }

    public FileRequest getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(FileRequest profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Set<Post> getFavoriteSet() {
        return favoriteSet;
    }

    public void setFavoriteSet(Set<Post> favoriteSet) {
        this.favoriteSet = favoriteSet;
    }
}
