package com.appscharles.libs.updater.detectors;

import com.appscharles.libs.ioer.downloaders.content.HttpContentDownloader;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.configurations.PathStorageConfig;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.StructureUpdateFormatter;

import java.io.IOException;
import java.net.URL;

/**
 * The type New version detector.
 */
public class NewVersionHTTPDetector {

    /**
     * Version in http string.
     *
     * @param name       the name
     * @param storageURL the storage url
     * @return the string
     * @throws UpdaterException the updater exception
     */
    public static String versionInHTTP(String name, URL storageURL) throws UpdaterException {
        try {
            IPathStorageConfig pathStorageConfig = new PathStorageConfig(name, null);
            IStructureUpdateDetector updateDetector = new StructureUpdateDetector(
                    name,
                    null,
                    new HttpContentDownloader(new URL(storageURL.toString() + pathStorageConfig.getPathCurrentUpdate())),
                    new StructureUpdateFormatter());
            return updateDetector.getVersion();
        } catch (IOException e) {
            throw new UpdaterException(e);
        }
    }
}
