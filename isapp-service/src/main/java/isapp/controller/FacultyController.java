package isapp.controller;

import isapp.model.Faculty;
import isapp.model.University;
import isapp.service.FacultyService;
import isapp.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping
    private ResponseEntity<List<Faculty>> getAllFaculties(){
        return ResponseEntity.status(HttpStatus.OK).body(facultyService.getAllFaculties());
    }
}