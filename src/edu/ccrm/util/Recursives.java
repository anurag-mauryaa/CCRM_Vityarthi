package edu.ccrm.util;

import java.nio.file.*;
import java.io.IOException;

public class Recursives {
    public static long totalSize(Path p) {
        try {
            if (!Files.exists(p)) return 0;
            return Files.walk(p).filter(q -> Files.isRegularFile(q)).mapToLong(q -> {
                try { return Files.size(q); } catch (IOException e) { return 0L; }
            }).sum();
        } catch (IOException e) { return 0; }
    }
    public static void listByDepth(Path p, int depth) {
        try {
            Files.walk(p, depth).forEach(System.out::println);
        } catch (IOException e) {}
    }
}
