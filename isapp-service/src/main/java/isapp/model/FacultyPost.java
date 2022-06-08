package isapp.model;

import isapp.model.user.User;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="faculties_posts_junction")
public class FacultyPost {

    @EmbeddedId
    FacultyPostKey id;

    @ManyToOne
    @MapsId("faculty_id")
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    public FacultyPostKey getId() {
        return id;
    }

    public void setId(FacultyPostKey id) {
        this.id = id;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}