package isapp.repository;

import isapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByNoRooms(int noRooms);

    List<Post> findByPrice(long price);
}
