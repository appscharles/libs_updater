package com.appscharles.libs.updater.changers;

import com.appscharles.libs.updater.exceptions.UpdaterException;

/**
 * The interface File changer.
 */
public interface IFileChanger {

    /**
     * Change.
     *
     * @throws UpdaterException the updater exception
     */
    void change() throws UpdaterException;
}
