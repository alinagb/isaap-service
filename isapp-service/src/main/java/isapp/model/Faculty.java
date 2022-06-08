package isapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "faculties")
public class Faculty {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="faculty_id")
    private UUID facultyId;

    @ManyToOne
    @JoinColumn(name="university_id")
    private University university;

//    @ManyToMany(mappedBy = "facultySet", fetch = FetchType.LAZY)
//    @JsonIgnoreProperties("facultySet")
//    private Set<Post> postSet = new HashSet<>() ;

    private String name;

    public UUID getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(UUID facultyId) {
        this.facultyId = facultyId;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
