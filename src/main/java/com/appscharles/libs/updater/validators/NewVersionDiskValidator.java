package com.appscharles.libs.updater.validators;

import com.appscharles.libs.ioer.downloaders.content.DiskContentDownloader;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.configurations.PathStorageConfig;
import com.appscharles.libs.updater.detectors.IStructureUpdateDetector;
import com.appscharles.libs.updater.detectors.StructureUpdateDetector;
import com.appscharles.libs.updater.formatters.StructureUpdateFormatter;

import java.io.File;

/**
 * The type New version detector.
 */
public class NewVersionDiskValidator {

    /**
     * Exist in disk boolean.
     *
     * @param name       the name
     * @param oldVersion the old version
     * @param storageDir the storage dir
     * @return the boolean
     */
    public static Boolean existInDisk(String name, String oldVersion, File storageDir){
        IPathStorageConfig pathStorageConfig = new PathStorageConfig(name, null);
        IStructureUpdateDetector updateDetector = new StructureUpdateDetector(
                name,
                oldVersion,
                new DiskContentDownloader(new File(storageDir, pathStorageConfig.getPathCurrentUpdate())),
                new StructureUpdateFormatter());
        return updateDetector.exist();
    }
}
