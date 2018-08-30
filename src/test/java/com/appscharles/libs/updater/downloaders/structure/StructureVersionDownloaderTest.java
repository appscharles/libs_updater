package com.appscharles.libs.updater.downloaders.structure;

import com.appscharles.libs.generator.exceptions.GeneratorException;
import com.appscharles.libs.generator.services.IGenerator;
import com.appscharles.libs.generator.services.TreeFilesGenerator;
import com.appscharles.libs.ioer.converters.RelativeFileConverter;
import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.ioer.services.DirReader;
import com.appscharles.libs.logger.configurators.Log4j2Console;
import com.appscharles.libs.logger.services.LoggerConfigurator;
import com.appscharles.libs.updater.builders.IStructureBuilder;
import com.appscharles.libs.updater.builders.StructureUpdateBuilder;
import com.appscharles.libs.updater.builders.StructureVersionBuilder;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.configurations.PathStorageConfig;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.exctractors.ChecksumExtractor;
import com.appscharles.libs.updater.exctractors.IHashExtractor;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Structure version downloader test.
 */
public class StructureVersionDownloaderTest {

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
     * Should download structure directory.
     *
     * @throws IOException          the io exception
     * @throws GeneratorException   the generator exception
     * @throws InterruptedException the interrupted exception
     * @throws UpdaterException     the updater exception
     */
    @Test
    public void shouldDownloadStructureDirectory() throws IOException, GeneratorException, InterruptedException, UpdaterException {
        String name = "MyApp";
        String version = "1.0.0.0-dev1";
        File temp = this.temp.newFolder();
        File storageDir = this.temp.newFolder();
        File downloadedDir = this.temp.newFolder();
        IGenerator generator = new TreeFilesGenerator(temp.getPath(), 12, 12, 3, 3);
        List<RelativeFile> relativeFiles = RelativeFileConverter.convert(temp, generator.random());

        IPathStorageConfig pathStorageConfig = new PathStorageConfig(name, version);
        IStructureBuilder sDB = StructureVersionBuilder.create(name, relativeFiles, new JSONPrettyFormatter());
        IStructureBuilder sUB = StructureUpdateBuilder.create(name, version, new JSONPrettyFormatter())
                .addProperty("latinCharacters", "ążćźńęł");
        ISender sender = new Sender(pathStorageConfig, sDB, sUB, new DiskStorage(storageDir.getPath()));
        sender.send(relativeFiles);

        List<String> relativeFileChecksums = new ArrayList<>();
        IHashExtractor hashExtractor = new ChecksumExtractor("MD5");
        for (RelativeFile relativeFile : relativeFiles) {
            if (relativeFile.isFile()){
                relativeFileChecksums.add(hashExtractor.extract(relativeFile));
            }
        }
        IStructureVersionDownloader structureDirectoryDownloader = new DiskStructureVersionDownloader(pathStorageConfig, storageDir, downloadedDir);
        structureDirectoryDownloader.download();
        List<File> downloadedFiles = DirReader.getRegularFiles(downloadedDir);
        for (File downloadedFile : downloadedFiles) {
            String checksum = hashExtractor.extract(downloadedFile);
            Assert.assertTrue(relativeFileChecksums.contains(checksum));
        }
    }

    /**
     * Should download files without files with checksum ignores.
     *
     * @throws IOException        the io exception
     * @throws GeneratorException the generator exception
     * @throws UpdaterException   the updater exception
     */
    @Test
    public void shouldDownloadFilesWithoutFilesWithChecksumIgnores() throws IOException, GeneratorException, UpdaterException {
        String name = "MyApp";
        String version = "1.0.0.0-dev1";
        File releaseDir = this.temp.newFolder();
        File storageDir = this.temp.newFolder();
        File downloadedDir = this.temp.newFolder();
        IGenerator generator = new TreeFilesGenerator(releaseDir.getPath(), 12, 12, 3, 3);
        List<RelativeFile> relativeFiles = RelativeFileConverter.convert(releaseDir, generator.random());
        IPathStorageConfig pathStorageConfig = new PathStorageConfig(name, version);
        IStructureBuilder sDB = StructureVersionBuilder.create(name, relativeFiles, new JSONPrettyFormatter())
                .addProperty("version", version)
                .addProperty("latinCharacters", "ążćźńęł");
        IStructureBuilder sUB = StructureUpdateBuilder.create(name, version, new JSONPrettyFormatter())
                .addProperty("latinCharacters", "ążćźńęł");
        ISender sender = new Sender(pathStorageConfig, sDB, sUB, new DiskStorage(storageDir.getPath()));
        sender.send(relativeFiles);

        List<String> ignoreChecksums = new ArrayList<>();
        List<String> relativeFileChecksums = new ArrayList<>();
        IHashExtractor hashExtractor = new ChecksumExtractor("MD5");
        for (int i = 0; i < relativeFiles.size(); i++) {
            if (relativeFiles.get(i).isFile()){
                relativeFileChecksums.add(hashExtractor.extract(relativeFiles.get(i)));
                if (ignoreChecksums.size() < 3){
                    ignoreChecksums.add(hashExtractor.extract(relativeFiles.get(i)));
                }
            }
        }
        IStructureVersionDownloader structureDirectoryDownloader = new DiskStructureVersionDownloader(pathStorageConfig, storageDir, downloadedDir);
        ((DiskStructureVersionDownloader) structureDirectoryDownloader).getIgnoreChecksums().addAll(ignoreChecksums);
        structureDirectoryDownloader.download();
        List<File> downloadedFiles = Files.walk(Paths.get(downloadedDir.getPath()))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        Assert.assertEquals(downloadedFiles.size(), 9);
    }
}