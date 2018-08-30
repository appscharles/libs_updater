package com.appscharles.libs.updater.downloaders.structure;

import com.appscharles.libs.ioer.downloaders.content.IContentDownloader;
import com.appscharles.libs.ioer.exceptions.IoerException;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.IFormatter;
import com.appscharles.libs.updater.models.StructureDirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Abstract structure version downloader.
 */
public abstract class AbstractStructureVersionDownloader implements IStructureVersionDownloader, IChecksumIgnorable {


    /**
     * The Path storage config.
     */
    protected IPathStorageConfig pathStorageConfig;

    /**
     * The To dir.
     */
    protected File toDir;

    /**
     * The Ignore checksums.
     */
    protected List<String> ignoreChecksums;

    /**
     * Instantiates a new Abstract structure version downloader.
     *
     * @param pathStorageConfig the path storage config
     * @param toDir             the to dir
     */
    public AbstractStructureVersionDownloader(IPathStorageConfig pathStorageConfig, File toDir) {
        this.pathStorageConfig = pathStorageConfig;
        this.toDir = toDir;
        this.ignoreChecksums = new ArrayList<>();
    }

    /**
     * Gets structure update.
     *
     * @param contentDownloader the content downloader
     * @param formatter         the formatter
     * @return the structure update
     * @throws UpdaterException the updater exception
     */
    protected StructureDirectory getStructureUpdate(IContentDownloader contentDownloader, IFormatter formatter) throws UpdaterException {
        String structureDirectoryJson;
        try {
            structureDirectoryJson = contentDownloader.getContent();
        } catch (IoerException e) {
           throw new UpdaterException(e);
        }
        return (StructureDirectory) formatter.format(structureDirectoryJson);
    }

    @Override
    public List<String> getIgnoreChecksums() {
        return this.ignoreChecksums;
    }
}
