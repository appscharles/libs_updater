package com.appscharles.libs.updater.exctractors;

import com.appscharles.libs.updater.exceptions.UpdaterException;

import java.io.File;

/**
 * The interface Hash extractor.
 */
public interface IHashExtractor {

    /**
     * Extract string.
     *
     * @param file the file
     * @return the string
     * @throws UpdaterException the updater exception
     */
    String extract(File file) throws UpdaterException;
}
