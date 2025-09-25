package edu.ccrm.domain;

public final class CourseCode {
    private final String v;
    public CourseCode(String s) {
        if (s == null) throw new IllegalArgumentException("null");
        v = s;
    }
    public String value() { return v; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseCode)) return false;
        CourseCode c = (CourseCode) o;
        return v.equals(c.v);
    }
    @Override
    public int hashCode() { return v.hashCode(); }
    @Override
    public String toString() { return v; }
}
