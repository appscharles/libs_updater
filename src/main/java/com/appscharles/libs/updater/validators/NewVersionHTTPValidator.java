package com.appscharles.libs.updater.validators;

import com.appscharles.libs.ioer.downloaders.content.HttpContentDownloader;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.configurations.PathStorageConfig;
import com.appscharles.libs.updater.detectors.IStructureUpdateDetector;
import com.appscharles.libs.updater.detectors.StructureUpdateDetector;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.StructureUpdateFormatter;

import java.io.IOException;
import java.net.URL;

/**
 * The type New version detector.
 */
public class NewVersionHTTPValidator {

    /**
     * Exist in http boolean.
     *
     * @param name       the name
     * @param oldVersion the old version
     * @param storageURL the storage url
     * @return the boolean
     * @throws UpdaterException the updater exception
     */
    public static Boolean existInHTTP(String name, String oldVersion, URL storageURL) throws UpdaterException {
       try {
           IPathStorageConfig pathStorageConfig = new PathStorageConfig(name, null);
           IStructureUpdateDetector updateDetector = new StructureUpdateDetector(
                   name,
                   oldVersion,
                   new HttpContentDownloader(new URL(storageURL.toString() +  pathStorageConfig.getPathCurrentUpdate())),
                   new StructureUpdateFormatter());
           return updateDetector.exist();
       } catch (IOException e) {
        throw new UpdaterException(e);
       }
    }
}
