package com.appscharles.libs.updater.senders;

import com.appscharles.libs.ioer.converters.RelativeFileConverter;
import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.ioer.services.DirReader;
import com.appscharles.libs.updater.builders.IStructureBuilder;
import com.appscharles.libs.updater.builders.StructureUpdateBuilder;
import com.appscharles.libs.updater.builders.StructureVersionBuilder;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.configurations.PathStorageConfig;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.JSONPrettyFormatter;
import com.appscharles.libs.updater.storages.DiskStorage;
import com.appscharles.libs.updater.storages.IStorage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 06.07.2018
 * Time: 20:38
 * Project name: updater
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class NewVersionDiskSender {

    /**
     * Send to disk.
     *
     * @param name       the name
     * @param version    the version
     * @param fromDir    the from dir
     * @param storageDir the storage dir
     * @throws UpdaterException the updater exception
     */
    public static void sendToDisk(String name, String version, File fromDir, File storageDir) throws UpdaterException {
        try {
            List<RelativeFile> relativeFiles = RelativeFileConverter.convert(fromDir, DirReader.getFilesWithoutRoot(fromDir));
            IStructureBuilder sDB = StructureVersionBuilder.create(name, relativeFiles, new JSONPrettyFormatter());
            IStructureBuilder sUB = StructureUpdateBuilder.create(name, version, new JSONPrettyFormatter());
            IPathStorageConfig pathStorageConfig = new PathStorageConfig(name, version);
            IStorage storage = new DiskStorage(storageDir.getAbsolutePath());
            ISender sender = new Sender(pathStorageConfig, sDB, sUB, storage);
            sender.send(relativeFiles);
        } catch (IOException e) {
            throw new UpdaterException(e);
        }
    }
}
