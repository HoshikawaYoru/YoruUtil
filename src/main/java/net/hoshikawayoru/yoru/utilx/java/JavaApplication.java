package net.hoshikawayoru.yoru.utilx.java;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class JavaApplication {

    public static String getRunningDirPath() throws URISyntaxException {
        Class<?> clazz = JavaApplication.class;
        CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        return jarFile.getParent();
    }

    public static void runJavaApplication(){

    }
}
