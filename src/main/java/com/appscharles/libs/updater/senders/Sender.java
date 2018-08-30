package com.appscharles.libs.updater.senders;

import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.updater.builders.IStructureBuilder;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.storages.IStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Sender.
 */
public class Sender implements ISender {

    private IPathStorageConfig pathStorageConfig;

    private IStructureBuilder structureBuilder;

    private IStructureBuilder structureUpdateBuilder;

    private IStorage storage;

    /**
     * Instantiates a new Sender.
     *
     * @param pathStorageConfig      the path storage config
     * @param structureBuilder       the structure builder
     * @param structureUpdateBuilder the structure update builder
     */
    public Sender(IPathStorageConfig pathStorageConfig, IStructureBuilder structureBuilder, IStructureBuilder structureUpdateBuilder, IStorage storage) {
        this.pathStorageConfig = pathStorageConfig;
        this.structureBuilder = structureBuilder;
        this.structureUpdateBuilder = structureUpdateBuilder;
        this.storage = storage;
    }

    @Override
    public void send(List<RelativeFile> relativeFiles) throws UpdaterException {
        final String dirStorage = this.storage.getStorageDir();
        sendFiles(relativeFiles, this.storage, dirStorage);
        try {
            sendStructureDirectory(this.storage, dirStorage);
            sendStructureUpdate(this.storage, dirStorage);
        } catch (IOException e) {
            throw new UpdaterException(e);
        }

    }

    private void sendFiles(List<RelativeFile> relativeFiles, IStorage storage, String dirStorage) throws UpdaterException {
        storage.setStorageDir(dirStorage + this.pathStorageConfig.getPathFiles());
        storage.save(relativeFiles);
    }

    private void sendStructureDirectory(IStorage storage, String dirStorage) throws IOException, UpdaterException {
        File structureDirectory = new File(Files.createTempDirectory("updater_").toFile(), this.pathStorageConfig.getStructureFileName());
        this.structureBuilder.build(structureDirectory);
        storage.setStorageDir(dirStorage + this.pathStorageConfig.getPathVersion());
        storage.save(new ArrayList<>(Arrays.asList(new RelativeFile(structureDirectory.getParentFile().getPath(), structureDirectory.getName()))));
    }

    private void sendStructureUpdate(IStorage storage, String dirStorage) throws IOException, UpdaterException {
        File structureUpdate = new File(Files.createTempDirectory("updater_").toFile(), this.pathStorageConfig.getCurrentUpdateFileName());
        this.structureUpdateBuilder.build(structureUpdate);
        storage.setStorageDir(dirStorage + this.pathStorageConfig.getPathName());
        storage.save(new ArrayList<>(Arrays.asList(new RelativeFile(structureUpdate.getParentFile().getPath(), structureUpdate.getName()))));
    }
}
