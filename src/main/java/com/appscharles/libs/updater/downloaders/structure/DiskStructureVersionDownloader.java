package com.appscharles.libs.updater.downloaders.structure;

import com.appscharles.libs.ioer.downloaders.content.DiskContentDownloader;
import com.appscharles.libs.ioer.downloaders.file.DiskFileDownloader;
import com.appscharles.libs.ioer.exceptions.IoerException;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.StructureDirectoryFormatter;
import com.appscharles.libs.updater.models.StructureDirectory;

import java.io.File;
import java.util.Map;

/**
 * The type Disk structure version downloader.
 */
public class DiskStructureVersionDownloader extends AbstractStructureVersionDownloader {

    private final File storageDir;

    /**
     * Instantiates a new Disk structure version downloader.
     *
     * @param pathStorageConfig the path storage config
     * @param storageDir        the storage dir
     * @param toDir             the to dir
     */
    public DiskStructureVersionDownloader(IPathStorageConfig pathStorageConfig, File storageDir, File toDir) {
        super(pathStorageConfig, toDir);
        this.storageDir = storageDir;
    }

    @Override
    public void download() throws UpdaterException {
        StructureDirectory structureDirectory = getStructureUpdate(new DiskContentDownloader(new File(this.storageDir, this.pathStorageConfig.getPathVersion() + "/" + this.pathStorageConfig.getStructureFileName())), new StructureDirectoryFormatter());
        try {
            for (Map<String, String> structureFile : structureDirectory.getFiles()) {
                if (structureFile.containsKey("checksum")){
                    String checksum = structureFile.get("checksum");
                    if (this.ignoreChecksums.contains(checksum) == false){
                        new DiskFileDownloader(new File(this.storageDir + this.pathStorageConfig.getPathFiles(), structureFile.get("relativePath")))
                                .download( new File(this.toDir, structureFile.get("relativePath")));
                    }
                } else {
                    new File(this.toDir, structureFile.get("relativePath")).mkdirs();
                }
            }
        } catch (IoerException e) {
            throw new UpdaterException(e);
        }
    }
}