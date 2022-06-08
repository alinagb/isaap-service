package isapp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class PostFiletKey implements Serializable {

    @Column(name = "file_id")
    UUID fileId;

    @Column(name = "post_id")
    UUID postId;

    public UUID getUserId() {
        return fileId;
    }

    public void setUserId(UUID fileId) {
        this.fileId = fileId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }
}
