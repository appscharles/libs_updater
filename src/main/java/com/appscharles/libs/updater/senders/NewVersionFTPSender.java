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
import com.appscharles.libs.updater.storages.FTPStorage;
import com.appscharles.libs.updater.storages.IStorage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 06.07.2018
 * Time: 20:39
 * Project name: updater
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class NewVersionFTPSender {

    public static void sendToFTP(String name, String version, File fromDir, String hostFTP, Integer portFTP, String nameFTP, String passwordFTP, String storageDirFTP, Boolean passiveModeFTP) throws UpdaterException {
        try {
            List<RelativeFile> relativeFiles = RelativeFileConverter.convert(fromDir, DirReader.getFilesWithoutRoot(fromDir));
            IStructureBuilder sDB = StructureVersionBuilder.create(name, relativeFiles, new JSONPrettyFormatter());
            IStructureBuilder sUB = StructureUpdateBuilder.create(name, version, new JSONPrettyFormatter());
            IPathStorageConfig pathStorageConfig = new PathStorageConfig(name, version);
            IStorage storage = new FTPStorage(hostFTP, portFTP, nameFTP, passwordFTP, storageDirFTP, passiveModeFTP);
            ISender sender = new Sender(pathStorageConfig, sDB, sUB, storage);
            sender.send(relativeFiles);
        } catch (IOException e) {
            throw new UpdaterException(e);
        }
    }
}
