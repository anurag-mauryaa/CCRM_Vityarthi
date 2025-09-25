package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {
    private final String c;
    private final List<String> M;
    public Instructor(String id, String code, String nm, String em, LocalDate bd) {
        super(id, nm, em, bd);
        c = code;
        M = new ArrayList<>();
    }
    public String getCode() { return c; }
    public List<String> getMeta() { return M; }
    public void assign(String x) { M.add(x); }
    @Override
    public String profile() {
        return "Instr:"+i+"|"+c+"|"+n;
    }
    @Override
    public String toString() { return profile(); }
}
