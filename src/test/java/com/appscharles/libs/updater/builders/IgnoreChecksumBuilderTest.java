package com.appscharles.libs.updater.builders;

import com.appscharles.libs.generator.exceptions.GeneratorException;
import com.appscharles.libs.generator.services.IGenerator;
import com.appscharles.libs.generator.services.TreeFilesGenerator;
import com.appscharles.libs.ioer.converters.RelativeFileConverter;
import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.ioer.services.DirReader;
import com.appscharles.libs.logger.configurators.Log4j2Console;
import com.appscharles.libs.logger.services.LoggerConfigurator;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.configurations.PathStorageConfig;
import com.appscharles.libs.updater.downloaders.structure.DiskStructureVersionDownloader;
import com.appscharles.libs.updater.downloaders.structure.IStructureVersionDownloader;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.JSONPrettyFormatter;
import com.appscharles.libs.updater.senders.ISender;
import com.appscharles.libs.updater.senders.Sender;
import com.appscharles.libs.updater.storages.DiskStorage;
import org.apache.logging.log4j.Level;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The type Ignore checksum builder test.
 */
public class IgnoreChecksumBuilderTest {

    /**
     * The Temp.
     */
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    /**
     * Sets logger.
     */
    @Before
    public void setLogger() {
        LoggerConfigurator.config(new Log4j2Console(Level.DEBUG));
    }

    /**
     * Should download files without ignore checksums.
     *
     * @throws IOException        the io exception
     * @throws GeneratorException the generator exception
     * @throws UpdaterException   the updater exception
     */
    @Test
    public void shouldDownloadFilesWithoutIgnoreChecksums() throws IOException, GeneratorException, UpdaterException {
        String name = "MyApp";
        String version = "1.0.0.0-dev1";
        File releaseDir = this.temp.newFolder();
        File storageDir = this.temp.newFolder();
        File downloadedDir = this.temp.newFolder();
        IGenerator generator = new TreeFilesGenerator(releaseDir.getPath(), 12, 12, 3, 3);
        List<RelativeFile> relativeFiles = RelativeFileConverter.convert(releaseDir, generator.random());
        IStructureBuilder sDB = StructureVersionBuilder.create(name, relativeFiles, new JSONPrettyFormatter())
                .addProperty("version", version)
                .addProperty("latinCharacters", "ążćźńęł");
        IStructureBuilder sUB = StructureUpdateBuilder.create(name, version, new JSONPrettyFormatter())
                .addProperty("latinCharacters", "ążćźńęł");
        IPathStorageConfig pathStorageConfig = new PathStorageConfig(name, version);
        ISender sender = new Sender(pathStorageConfig, sDB, sUB, new DiskStorage(storageDir.getPath()));
        sender.send(relativeFiles);

        List<String> ignoreChecksums = IgnoreChecksumBuilder.create(releaseDir).build();
        ignoreChecksums.remove(ignoreChecksums.get(ignoreChecksums.size() -1));
        IStructureVersionDownloader structureDirectoryDownloader = new DiskStructureVersionDownloader(pathStorageConfig, storageDir, downloadedDir);
        ((DiskStructureVersionDownloader) structureDirectoryDownloader).getIgnoreChecksums().addAll(ignoreChecksums);
        structureDirectoryDownloader.download();
        List<File> downloadedFiles = DirReader.getRegularFiles(downloadedDir);
        Assert.assertEquals(downloadedFiles.size(), 1);
    }
}