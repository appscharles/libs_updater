package com.appscharles.libs.updater.detectors;

import com.appscharles.libs.ioer.downloaders.content.DiskContentDownloader;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.configurations.PathStorageConfig;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.StructureUpdateFormatter;

import java.io.File;

/**
 * The type New version detector.
 */
public class NewVersionDiskDetector {

    /**
     * Version in disk string.
     *
     * @param name       the name
     * @param storageDir the storage dir
     * @return the string
     * @throws UpdaterException the updater exception
     */
    public static String versionInDisk(String name, File storageDir) throws UpdaterException {
        IPathStorageConfig pathStorageConfig = new PathStorageConfig(name, null);
        IStructureUpdateDetector updateDetector = new StructureUpdateDetector(
                name,
                null,
                new DiskContentDownloader(new File(storageDir, pathStorageConfig.getPathCurrentUpdate())),
                new StructureUpdateFormatter());
        return updateDetector.getVersion();
    }
}
