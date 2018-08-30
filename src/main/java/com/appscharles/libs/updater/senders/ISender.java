package com.appscharles.libs.updater.senders;

import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.storages.IStorage;

import java.util.List;

/**
 * The interface Sender.
 */
public interface ISender {

    /**
     * Send.
     *
     * @param relativeFiles the relative files
     * @param storage       the storage
     * @throws UpdaterException the updater exception
     */
    void send(List<RelativeFile> relativeFiles) throws UpdaterException;
}
