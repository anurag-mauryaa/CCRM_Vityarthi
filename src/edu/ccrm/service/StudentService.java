package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.List;

public interface StudentService {
    void add(Student s);
    List<Student> listAll();
    void updateName(String id, String nm);
    void deactivate(String id);
}
