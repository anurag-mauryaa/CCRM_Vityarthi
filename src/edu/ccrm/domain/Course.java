package edu.ccrm.domain;

import java.util.Objects;

public class Course {
    private final CourseCode c;
    private String t;
    private int cr;
    private String instr;
    private Semester sem;
    private String dep;
    public Course(CourseCode code, String title, int credits, String inst, Semester s, String d) {
        this.c = code;
        this.t = title;
        this.cr = credits;
        this.instr = inst;
        this.sem = s;
        this.dep = d;
    }
    public CourseCode getCode() { return c; }
    public String getTitle() { return t; }
    public void setTitle(String x) { t = x; }
    public int getCredits() { return cr; }
    public void setCredits(int x) { cr = x; }
    public String getInstructor() { return instr; }
    public void setInstructor(String x) { instr = x; }
    public Semester getSemester() { return sem; }
    public void setSemester(Semester s) { sem = s; }
    public String getDepartment() { return dep; }
    public void setDepartment(String x) { dep = x; }
    @Override
    public String toString() {
        return c.toString()+":"+t+":"+cr+":"+instr+":"+sem+":"+dep;
    }
    public static class Syllabus {
        private final String s;
        public Syllabus(String x) { s = x; }
        public String text() { return s; }
    }
    private class Monitor {
        public String ping() { return "ok-"+c.toString(); }
    }
    public Monitor monitor() { return new Monitor(); }
    public static class Builder {
        private CourseCode a;
        private String b;
        private int d;
        private String e;
        private Semester f;
        private String g;
        public Builder code(CourseCode x) { a = x; return this; }
        public Builder title(String x) { b = x; return this; }
        public Builder credits(int x) { d = x; return this; }
        public Builder instr(String x) { e = x; return this; }
        public Builder sem(Semester x) { f = x; return this; }
        public Builder dept(String x) { g = x; return this; }
        public Course build() {
            Objects.requireNonNull(a);
            if (b == null) b = "UNTITLED";
            if (f == null) f = Semester.SPRING;
            return new Course(a,b,d,e,f,g);
        }
    }
}
