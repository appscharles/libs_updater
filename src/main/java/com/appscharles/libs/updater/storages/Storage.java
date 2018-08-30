package com.appscharles.libs.updater.storages;

/**
 * The type Storage.
 */
public abstract class Storage implements IStorage {

    /**
     * The Storage dir.
     */
    protected String storageDir;

    /**
     * Instantiates a new Storage.
     *
     * @param storageDir the storage dir
     */
    public Storage(String storageDir) {
        this.storageDir = storageDir;
    }


    public void setStorageDir(String storageDir) {
        this.storageDir = storageDir;
    }

    public String getStorageDir() {
        return this.storageDir;
    }
}
