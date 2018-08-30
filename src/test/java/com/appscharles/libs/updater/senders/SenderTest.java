package com.appscharles.libs.updater.senders;

import com.appscharles.libs.generator.exceptions.GeneratorException;
import com.appscharles.libs.generator.services.IGenerator;
import com.appscharles.libs.generator.services.TreeFilesGenerator;
import com.appscharles.libs.ioer.converters.RelativeFileConverter;
import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.updater.builders.IStructureBuilder;
import com.appscharles.libs.updater.builders.StructureUpdateBuilder;
import com.appscharles.libs.updater.builders.StructureVersionBuilder;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.configurations.PathStorageConfig;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.JSONPrettyFormatter;
import com.appscharles.libs.updater.storages.FTPStorage;
import com.appscharles.libs.updater.storages.IStorage;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.appscharles.libs.updater.storages.FTPStorageTest.startServerFtp;

/**
 * The type Sender test.
 */
public class SenderTest {

    /**
     * The Temp.
     */
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    /**
     * Should send files with structure directory file.
     *
     * @throws IOException        the io exception
     * @throws GeneratorException the generator exception
     * @throws UpdaterException   the updater exception
     * @throws FtpException       the ftp exception
     */
    @Test
    public void shouldSendFilesWithStructureDirectoryFile() throws IOException, GeneratorException, UpdaterException, FtpException {
        File rootDir = this.temp.newFolder();
        File ftpDir = this.temp.newFolder("ftp");
        FtpServer ftpServer = startServerFtp(ftpDir, 2221);
        IGenerator generator = new TreeFilesGenerator(rootDir.getPath(), 12, 12, 3, 3);
        List<File> files = generator.random();
        List<RelativeFile> relativeFiles = RelativeFileConverter.convert(rootDir, files);
        String name = "MyApp";
        String version = "1.0.0.0-dev0";
        IStructureBuilder sDB = StructureVersionBuilder.create(name, relativeFiles, new JSONPrettyFormatter())
                .addProperty("version", version)
                .addProperty("latinCharacters", "ążćźńęł");
        IStructureBuilder sUB = StructureUpdateBuilder.create(name, version, new JSONPrettyFormatter())
                .addProperty("latinCharacters", "ążćźńęł");

        IPathStorageConfig pathStorageConfig = new PathStorageConfig(name, version);
        IStorage storage = new FTPStorage("localhost", 2221, "root", "secret", "/apps", true);

        ISender sender = new Sender(pathStorageConfig, sDB, sUB, storage);
        sender.send(relativeFiles);
        ftpServer.stop();
    }

}