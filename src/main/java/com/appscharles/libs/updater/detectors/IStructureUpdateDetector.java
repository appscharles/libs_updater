package com.appscharles.libs.updater.detectors;

import com.appscharles.libs.updater.exceptions.UpdaterException;

/**
 * The interface Structure update detector.
 */
public interface IStructureUpdateDetector {

    /**
     * Exist boolean.
     *
     * @return the boolean
     */
    Boolean exist();

    String getVersion() throws UpdaterException;
}
