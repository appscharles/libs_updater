package com.appscharles.libs.updater.downloaders.structure;

import com.appscharles.libs.ioer.downloaders.content.HttpContentDownloader;
import com.appscharles.libs.ioer.downloaders.file.HttpFileDownloader;
import com.appscharles.libs.ioer.exceptions.IoerException;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.StructureDirectoryFormatter;
import com.appscharles.libs.updater.models.StructureDirectory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * The type Http structure version downloader.
 */
public class HttpStructureVersionDownloader extends AbstractStructureVersionDownloader {

    private final URL storageUrl;


    /**
     * Instantiates a new Http structure version downloader.
     *
     * @param pathStorageConfig the path storage config
     * @param storageUrl        the storage url
     * @param toDir             the to dir
     */
    public HttpStructureVersionDownloader(IPathStorageConfig pathStorageConfig, URL storageUrl, File toDir) {
        super(pathStorageConfig, toDir);
        this.storageUrl = storageUrl;
    }

    @Override
    public void download() throws UpdaterException {
        try {
            StructureDirectory structureDirectory = getStructureUpdate(new HttpContentDownloader(new URL(this.storageUrl.toString() + this.pathStorageConfig.getPathVersion() + "/" + this.pathStorageConfig.getStructureFileName())), new StructureDirectoryFormatter());
            for (Map<String, String> structureFile : structureDirectory.getFiles()) {
                if (structureFile.containsKey("checksum")){
                    String checksum = structureFile.get("checksum");
                    if (this.ignoreChecksums.contains(checksum) == false){
                        new HttpFileDownloader(new URL(this.storageUrl.toString() + this.pathStorageConfig.getPathFiles() + structureFile.get("relativePath").replace("\\", "/")), 3)
                                .download(new File(this.toDir, structureFile.get("relativePath")));
                    }
                } else {
                    new File(this.toDir, structureFile.get("relativePath")).mkdirs();
                }
            }
        } catch (IoerException | IOException e) {
           throw new UpdaterException(e);
        }
    }
}