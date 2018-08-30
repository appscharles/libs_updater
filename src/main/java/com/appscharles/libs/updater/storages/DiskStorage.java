package com.appscharles.libs.updater.storages;

import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.updater.exceptions.UpdaterException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * The type Disk storage.
 */
public class DiskStorage extends Storage {


    /**
     * Instantiates a new Disk storage.
     *
     * @param storageDir the storage dir
     */
    public DiskStorage(String storageDir) {
        super(storageDir);
    }

    @Override
    public void save(List<RelativeFile> relativeFiles) throws UpdaterException {
        if (relativeFiles.size() == 0){
            throw new UpdaterException("Not files to save storage.");
        }
        for (RelativeFile relativeFile : relativeFiles) {
            File storageFile = new File(this.storageDir, relativeFile.getRelativePath());
            if (relativeFile.isDirectory()){
                storageFile.mkdirs();
            } else if (relativeFile.isFile()){
                storageFile.getParentFile().mkdirs();
            }
            try {
                Files.copy(relativeFile.toPath(), storageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new UpdaterException(e);
            }
        }
    }
}
