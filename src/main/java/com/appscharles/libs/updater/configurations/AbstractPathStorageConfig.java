package com.appscharles.libs.updater.configurations;

/**
 * The type Abstract path storage config.
 */
public abstract class AbstractPathStorageConfig implements IPathStorageConfig {

    /**
     * The Name.
     */
    protected String name;

    /**
     * The Version.
     */
    protected String version;

    /**
     * Instantiates a new Abstract path storage config.
     *
     * @param name    the name
     * @param version the version
     */
    public AbstractPathStorageConfig(String name, String version) {
        this.name = name;
        this.version = version;
    }
}
