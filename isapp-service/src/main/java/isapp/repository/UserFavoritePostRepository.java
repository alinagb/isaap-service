package isapp.repository;

import isapp.model.UserFavoritePost;
import isapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserFavoritePostRepository extends JpaRepository<UserFavoritePost, UUID> {
    Optional<List<UserFavoritePost>> findAllByUser(User user);

}
