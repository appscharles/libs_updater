package com.appscharles.libs.updater.formatters;

import com.appscharles.libs.updater.exceptions.UpdaterException;

/**
 * The interface Formatter.
 */
public interface IFormatter {

    /**
     * Format object.
     *
     * @param object the object
     * @return the object
     * @throws UpdaterException the updater exception
     */
    Object format(Object object) throws UpdaterException;
}
