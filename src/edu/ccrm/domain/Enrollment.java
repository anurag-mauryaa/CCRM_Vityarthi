package edu.ccrm.domain;

import java.time.LocalDateTime;

public class Enrollment {
    private final String i;
    private final CourseCode c;
    private final String sId;
    private LocalDateTime d;
    private int m;
    private Grade g;
    public Enrollment(String id, CourseCode cc, String sid) {
        i = id;
        c = cc;
        sId = sid;
        d = LocalDateTime.now();
        m = -1;
        g = null;
    }
    public String getId() { return i; }
    public CourseCode getCourse() { return c; }
    public String getStudentId() { return sId; }
    public LocalDateTime getDate() { return d; }
    public int getMarks() { return m; }
    public void setMarks(int x) {
        m = x;
        g = Grade.fromMarks(x);
    }
    public Grade getGrade() { return g; }
    @Override
    public String toString() {
        return i+":"+c.toString()+":"+sId+":"+m+":"+ (g==null?"-":g.name());
    }
}
