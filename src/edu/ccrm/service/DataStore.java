package edu.ccrm.service;

import edu.ccrm.domain.*;
import edu.ccrm.config.AppCfg;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

public final class DataStore {
    private static DataStore s;
    private final Map<String, Student> ms;
    private final Map<String, Course> mc;
    private final Map<String, Enrollment> me;
    private final Path root;
    private DataStore() {
        ms = new ConcurrentHashMap<>();
        mc = new ConcurrentHashMap<>();
        me = new ConcurrentHashMap<>();
        root = AppCfg.get().getDataDir();
        try { Files.createDirectories(root); } catch (IOException e) {}
    }
    public static synchronized DataStore get() {
        if (s == null) s = new DataStore();
        return s;
    }
    public Collection<Student> allStudents() { return ms.values(); }
    public Collection<Course> allCourses() { return mc.values(); }
    public Collection<Enrollment> allEnrollments() { return me.values(); }
    public Optional<Student> studentById(String id) { return Optional.ofNullable(ms.get(id)); }
    public Optional<Course> courseByCode(CourseCode c) { return Optional.ofNullable(mc.get(c.value())); }
    public Optional<Enrollment> enrollById(String id) { return Optional.ofNullable(me.get(id)); }
    public void saveStudent(Student s0) { ms.put(s0.getId(), s0); }
    public void saveCourse(Course c0) { mc.put(c0.getCode().toString(), c0); }
    public void saveEnrollment(Enrollment e0) { me.put(e0.getId(), e0); }
    public void removeEnrollment(String id) { me.remove(id); }
    public List<Enrollment> enrollmentsForStudent(String sid) {
        return me.values().stream().filter(e -> e.getStudentId().equals(sid)).collect(Collectors.toList());
    }
    public List<Enrollment> enrollmentsForCourse(CourseCode cc) {
        return me.values().stream().filter(e -> e.getCourse().equals(cc)).collect(Collectors.toList());
    }
    public Path dataRoot() { return root; }
    public void exportCsvAll() {
        Path p1 = root.resolve("students.csv");
        Path p2 = root.resolve("courses.csv");
        Path p3 = root.resolve("enrolls.csv");
        try {
            List<String> ss = ms.values().stream().map(s0 ->
                String.join(",", s0.getId(), s0.getReg(), s0.getName(), s0.getEmail(), Boolean.toString(s0.isActive()))
            ).collect(Collectors.toList());
            Files.write(p1, ss, StandardCharsets.UTF_8);
            List<String> cs = mc.values().stream().map(c0 ->
                String.join(",", c0.getCode().toString(), c0.getTitle(), Integer.toString(c0.getCredits()), c0.getInstructor(), c0.getSemester().name(), c0.getDepartment())
            ).collect(Collectors.toList());
            Files.write(p2, cs, StandardCharsets.UTF_8);
            List<String> es = me.values().stream().map(e0 ->
                String.join(",", e0.getId(), e0.getCourse().toString(), e0.getStudentId(), Integer.toString(e0.getMarks()))
            ).collect(Collectors.toList());
            Files.write(p3, es, StandardCharsets.UTF_8);
        } catch (IOException ex) {}
    }
    public Map<String, Double> gpaDistribution() {
        return ms.values().stream().collect(Collectors.toMap(
            Student::getId,
            s0 -> {
                List<Enrollment> le = enrollmentsForStudent(s0.getId());
                int totCr = le.stream().mapToInt(e -> {
                    Course c = mc.get(e.getCourse().toString());
                    return c==null?0:c.getCredits();
                }).sum();
                double sum = le.stream().mapToDouble(e -> {
                    Course c = mc.get(e.getCourse().toString());
                    int cr = c==null?0:c.getCredits();
                    Grade g = e.getGrade();
                    int gp = g==null?0:g.points();
                    return gp * cr;
                }).sum();
                return totCr==0?0.0: (sum / totCr);
            }
        ));
    }
}
