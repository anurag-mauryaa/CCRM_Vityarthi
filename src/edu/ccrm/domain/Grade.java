package edu.ccrm.domain;

public enum Grade {
    S(10), A(9), B(8), C(7), D(6), E(5), F(0);
    private final int p;
    Grade(int x) { p = x; }
    public int points() { return p; }
    public static Grade fromMarks(int m) {
        if (m >= 90) return S;
        if (m >= 80) return A;
        if (m >= 70) return B;
        if (m >= 60) return C;
        if (m >= 50) return D;
        if (m >= 40) return E;
        return F;
    }
}
