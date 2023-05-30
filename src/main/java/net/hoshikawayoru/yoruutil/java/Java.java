package net.hoshikawayoru.yoruutil.java;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class Java {
    public static class application {
        public static String getRunningDirPath() throws URISyntaxException {
            Class<?> clazz = Java.class;
            CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());

            return jarFile.getParent();
        }
    }
}
