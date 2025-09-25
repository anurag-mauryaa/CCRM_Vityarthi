package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public final class AppCfg {
    private static AppCfg s;
    private final Path d;
    private final LocalDateTime t;
    private AppCfg() {
        d = Paths.get(System.getProperty("user.dir"), "ccrm_data");
        t = LocalDateTime.now();
    }
    public static synchronized AppCfg get() {
        if (s == null) s = new AppCfg();
        return s;
    }
    public Path getDataDir() { return d; }
    public LocalDateTime getStartupTime() { return t; }
}
