package edu.ccrm.cli;

import edu.ccrm.config.AppCfg;
import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.io.ImportExport;
import edu.ccrm.util.Recursives;
import edu.ccrm.service.exceptions.*;
import java.util.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class MainMenu {
    private final Scanner sc = new Scanner(System.in);
    private final StudentService ss = new StudentServiceImpl();
    private final CourseService cs = new CourseServiceImpl();
    private final EnrollmentService es = new EnrollmentServiceImpl();
    private final DataStore ds = DataStore.get();
    private final ImportExport io = new ImportExport();
    public static void main(String[] a) {
        new MainMenu().run();
    }
    private void run() {
        AppCfg.get();
        loop: while (true) {
            System.out.println("1-stu 2-course 3-enrl 4-io 5-rep 0-exit");
            int c = readInt();
            switch (c) {
                case 1 -> studentsMenu();
                case 2 -> coursesMenu();
                case 3 -> enrollMenu();
                case 4 -> ioMenu();
                case 5 -> reports();
                case 0 -> { break loop; }
                default -> System.out.println("bad");
            }
        }
    }
    private int readInt() {
        try { return Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { return -1; }
    }
    private void studentsMenu() {
        outer: for (;;) {
            System.out.println("s1-add s2-list s3-update s4-deact b-back");
            String k = sc.nextLine().trim();
            switch (k) {
                case "s1" -> {
                    String id = "stu" + System.currentTimeMillis();
                    System.out.println("name");
                    String nm = sc.nextLine();
                    System.out.println("reg");
                    String rg = sc.nextLine();
                    Student s = new Student(id, rg, nm, nm.toLowerCase()+"@x", LocalDate.now());
                    ss.add(s);
                    System.out.println("ok");
                }
                case "s2" -> {
                    ss.listAll().forEach(System.out::println);
                }
                case "s3" -> {
                    System.out.println("id");
                    String id = sc.nextLine();
                    System.out.println("name");
                    String nm = sc.nextLine();
                    ss.updateName(id, nm);
                }
                case "s4" -> {
                    System.out.println("id");
                    String id = sc.nextLine();
                    ss.deactivate(id);
                }
                case "b" -> { break outer; }
                default -> System.out.println("bad");
            }
        }
    }
    private void coursesMenu() {
        while (true) {
            System.out.println("c1-add c2-list c3-assign b-back");
            String k = sc.nextLine().trim();
            if ("c1".equals(k)) {
                System.out.println("code");
                CourseCode cc = new CourseCode(sc.nextLine().trim());
                System.out.println("title");
                String t = sc.nextLine();
                Course c = new Course.Builder().code(cc).title(t).credits(3).instr("none").sem(Semester.SPRING).dept("GEN").build();
                cs.add(c);
                System.out.println("ok");
            } else if ("c2".equals(k)) {
                cs.listAll().forEach(System.out::println);
            } else if ("c3".equals(k)) {
                System.out.println("code");
                CourseCode cc = new CourseCode(sc.nextLine().trim());
                System.out.println("instr");
                String in = sc.nextLine();
                cs.assignInstructor(cc, in);
            } else {
                break;
            }
        }
    }
    private void enrollMenu() {
        while (true) {
            System.out.println("e1-enroll e2-unen e3-record b-back");
            String k = sc.nextLine().trim();
            if ("e1".equals(k)) {
                System.out.println("stuId");
                String sid = sc.nextLine();
                System.out.println("courseCode");
                CourseCode cc = new CourseCode(sc.nextLine().trim());
                String id = "en" + System.currentTimeMillis();
                Enrollment e = new Enrollment(id, cc, sid);
                try { es.enroll(e); System.out.println("ok"); } catch (Exception ex) { System.out.println("err:"+ex.getMessage()); }
            } else if ("e2".equals(k)) {
                System.out.println("enId");
                String id = sc.nextLine();
                es.unenroll(id);
            } else if ("e3".equals(k)) {
                System.out.println("enId");
                String id = sc.nextLine();
                System.out.println("marks");
                int m = readInt();
                es.recordMarks(id, m);
            } else break;
        }
    }
    private void ioMenu() {
        System.out.println("i1-import-stu i2-import-course i3-export i4-backup i0-back");
        String k = sc.nextLine().trim();
        try {
            if ("i1".equals(k)) {
                io.importStudentsCsv(Paths.get("testdata/students.csv"));
            } else if ("i2".equals(k)) {
                io.importCoursesCsv(Paths.get("testdata/courses.csv"));
            } else if ("i3".equals(k)) {
                io.exportAll();
            } else if ("i4".equals(k)) {
                System.out.println("backing");
                System.out.println(io.backupNow());
                System.out.println("size:");
                System.out.println(Recursives.totalSize(AppCfg.get().getDataDir()));
            }
        } catch (Exception e) { System.out.println("ioerr"); }
    }
    private void reports() {
        Map<String, Double> mp = ds.gpaDistribution();
        mp.entrySet().stream().sorted((a,b) -> Double.compare(b.getValue(), a.getValue())).limit(5).forEach(en -> System.out.println(en.getKey()+":"+en.getValue()));
    }
}
