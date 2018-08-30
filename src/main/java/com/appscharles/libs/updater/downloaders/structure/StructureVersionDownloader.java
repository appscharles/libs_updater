package com.appscharles.libs.updater.downloaders.structure;

import com.appscharles.libs.updater.exceptions.UpdaterException;

/**
 * The type Structure version downloader.
 */
public class StructureVersionDownloader implements IStructureVersionDownloader {

    private IStructureVersionDownloader structureDirectoryDownloader;

    /**
     * Instantiates a new Structure version downloader.
     *
     * @param structureDirectoryDownloader the structure directory downloader
     */
    public StructureVersionDownloader(IStructureVersionDownloader structureDirectoryDownloader) {
        this.structureDirectoryDownloader = structureDirectoryDownloader;
    }

    @Override
    public void download() throws UpdaterException {
        this.structureDirectoryDownloader.download();
    }
}
