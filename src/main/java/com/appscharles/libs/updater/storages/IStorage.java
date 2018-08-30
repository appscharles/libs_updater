package com.appscharles.libs.updater.storages;


import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.updater.exceptions.UpdaterException;

import java.util.List;

/**
 * The interface Storage.
 */
public interface IStorage {

    /**
     * Save.
     *
     * @param relativeFiles the relative files
     * @throws UpdaterException the updater exception
     */
    void save(List<RelativeFile> relativeFiles) throws UpdaterException;

    /**
     * Sets storage dir.
     *
     * @param storageDir the storage dir
     */
    void setStorageDir(String storageDir);

    /**
     * Gets storage dir.
     *
     * @return the storage dir
     */
    String getStorageDir();
}
