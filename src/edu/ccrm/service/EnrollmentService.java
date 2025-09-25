package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.CourseCode;

public interface EnrollmentService {
    void enroll(Enrollment e) throws Exception;
    void unenroll(String id);
    void recordMarks(String id, int marks);
}
