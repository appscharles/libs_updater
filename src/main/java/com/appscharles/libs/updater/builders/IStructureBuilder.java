package com.appscharles.libs.updater.builders;

import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.IFormatter;

import java.io.File;

/**
 * The interface Structure builder.
 */
public interface IStructureBuilder {

    /**
     * Build string.
     *
     * @return the string
     * @throws UpdaterException the updater exception
     */
    String build() throws UpdaterException;

    /**
     * Build.
     *
     * @param toFile the to file
     * @throws UpdaterException the updater exception
     */
    void build(File toFile) throws UpdaterException;

    /**
     * Sets formatter.
     *
     * @param formatter the formatter
     */
    void setFormatter(IFormatter formatter);
}
