package com.appscharles.libs.updater.downloaders.structure;

import com.appscharles.libs.updater.exceptions.UpdaterException;

/**
 * The interface Structure version downloader.
 */
public interface IStructureVersionDownloader {

    /**
     * Download.
     *
     * @throws UpdaterException the updater exception
     */
    void download() throws UpdaterException;
}
