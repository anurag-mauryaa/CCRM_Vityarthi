package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.CourseCode;
import java.util.List;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService {
    private final DataStore ds = DataStore.get();
    public void add(Course c) { ds.saveCourse(c); }
    public List<Course> listAll() { return ds.allCourses().stream().collect(Collectors.toList()); }
    public List<Course> searchByInstructor(String instr) {
        return ds.allCourses().stream().filter(c -> instr.equals(c.getInstructor())).collect(Collectors.toList());
    }
    public void assignInstructor(CourseCode code, String instr) {
        ds.courseByCode(code).ifPresent(c -> c.setInstructor(instr));
    }
}
