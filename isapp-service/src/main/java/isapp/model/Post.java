package isapp.model;

import isapp.model.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="post_id")
    private UUID postId;

//    @OneToMany(mappedBy = "post")
//    Set<UserFavoritePost> favorites;
//

    @ManyToMany
    @JoinTable(name = "users_posts_junction", joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userSet = new HashSet<>();

    private UUID owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_photo", referencedColumnName = "file_id")
    private FileRequest mainPhoto;

//    @OneToMany(mappedBy="post")
//    private Set<FileRequest> files;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "posts_files_junction", joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<FileRequest> fileSet = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "faculties_posts_junction", joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "faculty_id"))
    private Set<Faculty> facultySet = new HashSet<>();

    private double lat;
    private double lng;
    private String description;
    private long price;
    private String title;

    private int noRooms;
    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

//    public Set<UserFavoritePost> getFavorites() {
//        return favorites;
//    }
//
//    public void setFavorites(Set<UserFavoritePost> favorites) {
//        this.favorites = favorites;
//    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public FileRequest getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(FileRequest mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public Set<FileRequest> getFileSet() {
        return fileSet;
    }

    public void setFileSet(Set<FileRequest> fileSet) {
        this.fileSet = fileSet;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Set<Faculty> getFacultySet() {
        return facultySet;
    }

    public void setFacultySet(Set<Faculty> facultySet) {
        this.facultySet = facultySet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNoRooms() {
        return noRooms;
    }

    public void setNoRooms(int noRooms) {
        this.noRooms = noRooms;
    }
}
