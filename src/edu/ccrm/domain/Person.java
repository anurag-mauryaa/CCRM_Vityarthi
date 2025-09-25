package edu.ccrm.domain;

import java.time.LocalDate;

public abstract class Person {
    protected final String i;
    protected String n;
    protected String e;
    protected LocalDate b;
    protected boolean s;
    protected Person(String id, String nm, String em, LocalDate bd) {
        this.i = id;
        this.n = nm;
        this.e = em;
        this.b = bd;
        this.s = true;
    }
    public String getId() { return i; }
    public String getName() { return n; }
    public void setName(String x) { n = x; }
    public String getEmail() { return e; }
    public void setEmail(String x) { e = x; }
    public LocalDate getDob() { return b; }
    public boolean isActive() { return s; }
    public void deactivate() { s = false; }
    public abstract String profile();
}
