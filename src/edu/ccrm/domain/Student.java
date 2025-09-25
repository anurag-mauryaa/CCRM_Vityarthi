package edu.ccrm.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private final String r;
    private LocalDateTime d;
    private final List<Enrollment> L;
    public Student(String id, String reg, String nm, String em, LocalDate bd) {
        super(id, nm, em, bd);
        r = reg;
        d = LocalDateTime.now();
        L = new ArrayList<>();
    }
    public String getReg() { return r; }
    public LocalDateTime getCreated() { return d; }
    public List<Enrollment> getEnrollments() { return L; }
    public void enroll(Enrollment x) { L.add(x); }
    public void unenroll(Enrollment x) { L.remove(x); }
    @Override
    public String profile() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student:").append(i).append("|").append(r).append("|").append(n);
        return sb.toString();
    }
    @Override
    public String toString() {
        return profile();
    }
}
