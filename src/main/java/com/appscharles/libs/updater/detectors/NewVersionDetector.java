package com.appscharles.libs.updater.detectors;

import com.appscharles.libs.updater.exceptions.UpdaterException;

import java.io.File;
import java.net.URL;

/**
 * The type New version detector.
 */
public class NewVersionDetector {

    /**
     * Version in disk string.
     *
     * @param name       the name
     * @param storageDir the storage dir
     * @return the string
     * @throws UpdaterException the updater exception
     */
    public static String versionInDisk(String name, File storageDir) throws UpdaterException {
        return NewVersionDiskDetector.versionInDisk(name, storageDir);
    }

    /**
     * Version in http string.
     *
     * @param name       the name
     * @param storageURL the storage url
     * @return the string
     * @throws UpdaterException the updater exception
     */
    public static String versionInHTTP(String name, URL storageURL) throws UpdaterException {
        return NewVersionHTTPDetector.versionInHTTP(name, storageURL);
    }
}
