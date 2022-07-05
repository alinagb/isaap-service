package isapp.repository;

import isapp.model.FacultyPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FacultyPostRepository  extends JpaRepository<FacultyPost, UUID> {
    @Query("SELECT m FROM FacultyPost m WHERE m.faculty.name LIKE %:faculty%")
    Optional<List<FacultyPost>> findByFacultyContainingIgnoreCase(@Param("faculty") String faculty);

}
