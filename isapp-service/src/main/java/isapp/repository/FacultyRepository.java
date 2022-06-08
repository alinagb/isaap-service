package isapp.repository;

import isapp.model.Faculty;
import isapp.model.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FacultyRepository  extends JpaRepository<Faculty, UUID> {
}
