package edu.ccrm.util;

public interface Persistable {
    String serialize();
    void deserialize(String s);
}
