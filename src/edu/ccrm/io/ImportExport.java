package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.DataStore;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ImportExport {
    private final DataStore ds = DataStore.get();
    public void importStudentsCsv(Path p) throws IOException {
        try (Stream<String> st = Files.lines(p, StandardCharsets.UTF_8)) {
            st.map(l -> l.split(",")).forEach(arr -> {
                String id = arr[0];
                String reg = arr[1];
                String nm = arr[2];
                String em = arr.length>3?arr[3]:"x@x";
                Student s = new Student(id, reg, nm, em, java.time.LocalDate.now());
                ds.saveStudent(s);
            });
        }
    }
    public void importCoursesCsv(Path p) throws IOException {
        try (Stream<String> st = Files.lines(p, StandardCharsets.UTF_8)) {
            st.map(l -> l.split(",")).forEach(arr -> {
                CourseCode cc = new CourseCode(arr[0]);
                Course c = new Course.Builder().code(cc).title(arr.length>1?arr[1]:"T").credits(Integer.parseInt(arr.length>2?arr[2]:"3")).instr(arr.length>3?arr[3]:"i").sem(Semester.valueOf(arr.length>4?arr[4]:"SPRING")).dept(arr.length>5?arr[5]:"GEN").build();
                ds.saveCourse(c);
            });
        }
    }
    public void exportAll() {
        ds.exportCsvAll();
    }
    public Path backupNow() throws IOException {
        Path root = ds.dataRoot();
        String t = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path tgt = root.resolveSibling(root.getFileName().toString() + "_bak_" + t);
        Files.createDirectories(tgt);
        try (Stream<Path> st = Files.walk(root)) {
            st.forEach(p -> {
                try {
                    Path rel = root.relativize(p);
                    Path dest = tgt.resolve(rel.toString());
                    if (Files.isDirectory(p)) {
                        Files.createDirectories(dest);
                    } else {
                        Files.copy(p, dest, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {}
            });
        }
        return tgt;
    }
    public void moveBackup(Path src, Path dst) throws IOException {
        Files.move(src, dst, StandardCopyOption.REPLACE_EXISTING);
    }
}
