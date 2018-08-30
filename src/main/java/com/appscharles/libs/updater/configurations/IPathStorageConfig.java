package com.appscharles.libs.updater.configurations;

/**
 * The interface Path storage config.
 */
public interface IPathStorageConfig {
    /**
     * Gets path version.
     *
     * @return the path version
     */
    String getPathVersion();

    /**
     * Gets path files.
     *
     * @return the path files
     */
    String getPathFiles();

    /**
     * Gets path name.
     *
     * @return the path name
     */
    String getPathName();

    /**
     * Gets structure file name.
     *
     * @return the structure file name
     */
    String getStructureFileName();

    /**
     * Gets current update file name.
     *
     * @return the current update file name
     */
    String getCurrentUpdateFileName();

    String getPathCurrentUpdate();
}
