package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.CourseCode;
import java.util.List;

public interface CourseService {
    void add(Course c);
    List<Course> listAll();
    List<Course> searchByInstructor(String instr);
    void assignInstructor(CourseCode code, String instr);
}
