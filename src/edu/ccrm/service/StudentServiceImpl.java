package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private final DataStore ds = DataStore.get();
    public void add(Student s) { ds.saveStudent(s); }
    public List<Student> listAll() { return ds.allStudents().stream().collect(Collectors.toList()); }
    public void updateName(String id, String nm) {
        ds.studentById(id).ifPresent(s -> s.setName(nm));
    }
    public void deactivate(String id) {
        ds.studentById(id).ifPresent(s -> s.deactivate());
    }
}
