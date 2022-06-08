package isapp.model;

import isapp.model.user.User;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="")
public class PostFile {

    @EmbeddedId
    PostFiletKey id;

    @ManyToOne
    @MapsId("fileId")
    @JoinColumn(name = "fileId")
    private FileRequest file;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    public PostFiletKey getId() {
        return id;
    }

    public void setId(PostFiletKey id) {
        this.id = id;
    }

    public FileRequest getFile() {
        return file;
    }

    public void setFile(FileRequest file) {
        this.file = file;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
