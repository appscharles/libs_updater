package com.appscharles.libs.updater.builders;

/**
 * The type Ftp path builder.
 */
public class FTPPathBuilder {

    /**
     * Build absolute path string.
     *
     * @param path the path
     * @return the string
     */
    public static String buildAbsolutePath(String path){
        return buildAbsolutePath(path, "");
    }

    /**
     * Build absolute path string.
     *
     * @param parent the parent
     * @param child  the child
     * @return the string
     */
    public static String buildAbsolutePath(String parent, String child){
        String path = parent + "/" + child;
        path = path.replace("\\", "/");
        path = path.replace("//", "/");
        if (path.startsWith("/") == false){
            path += "/";
        }
        if (path.endsWith("/")){
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }
}
