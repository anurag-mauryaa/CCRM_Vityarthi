package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.CourseCode;
import edu.ccrm.domain.Student;
import edu.ccrm.service.exceptions.DuplicateEnrollmentException;
import edu.ccrm.service.exceptions.MaxCreditLimitExceededException;
import java.util.List;

public class EnrollmentServiceImpl implements EnrollmentService {
    private final DataStore ds = DataStore.get();
    private final int MAX = 18;
    public void enroll(Enrollment e) throws Exception {
        boolean dup = ds.allEnrollments().stream().anyMatch(x -> x.getStudentId().equals(e.getStudentId()) && x.getCourse().equals(e.getCourse()));
        if (dup) throw new DuplicateEnrollmentException("dup");
        List<Enrollment> cur = ds.enrollmentsForStudent(e.getStudentId());
        int cr = cur.stream().mapToInt(x -> {
            Course c = ds.courseByCode(x.getCourse()).orElse(null);
            return c==null?0:c.getCredits();
        }).sum();
        Course cnew = ds.courseByCode(e.getCourse()).orElse(null);
        int add = cnew==null?0:cnew.getCredits();
        if (cr + add > MAX) throw new MaxCreditLimitExceededException("max");
        ds.saveEnrollment(e);
        ds.studentById(e.getStudentId()).ifPresent(s -> s.enroll(e));
    }
    public void unenroll(String id) {
        ds.enrollById(id).ifPresent(e -> {
            ds.studentById(e.getStudentId()).ifPresent(s -> s.unenroll(e));
            ds.removeEnrollment(id);
        });
    }
    public void recordMarks(String id, int marks) {
        ds.enrollById(id).ifPresent(e -> e.setMarks(marks));
    }
}
