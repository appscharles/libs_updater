package com.appscharles.libs.updater.validators;

import com.appscharles.libs.updater.exceptions.UpdaterException;

import java.io.File;
import java.net.URL;

/**
 * The type New version detector.
 */
public class NewVersionValidator {

    /**
     * Exist in disk boolean.
     *
     * @param name       the name
     * @param oldVersion the old version
     * @param storageDir the storage dir
     * @return the boolean
     */
    public static Boolean existInDisk(String name, String oldVersion, File storageDir){
        return NewVersionDiskValidator.existInDisk(name, oldVersion, storageDir);
    }

    /**
     * Exist in http boolean.
     *
     * @param name       the name
     * @param oldVersion the old version
     * @param storageURL the storage url
     * @return the boolean
     * @throws UpdaterException the updater exception
     */
    public static Boolean existInHTTP(String name, String oldVersion, URL storageURL) throws UpdaterException {
       return NewVersionHTTPValidator.existInHTTP(name, oldVersion, storageURL);
    }
}
