package com.appscharles.libs.updater.builders;

import com.appscharles.libs.generator.exceptions.GeneratorException;
import com.appscharles.libs.generator.services.IGenerator;
import com.appscharles.libs.generator.services.TreeFilesGenerator;
import com.appscharles.libs.ioer.converters.RelativeFileConverter;
import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.logger.configurators.Log4j2Console;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.senders.ISender;
import com.appscharles.libs.updater.storages.FTPStorage;
import com.appscharles.libs.updater.storages.IStorage;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.logging.log4j.Level;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.appscharles.libs.updater.storages.FTPStorageTest.startServerFtp;

/**
 * The type Structure sender builder test.
 */
public class StructureSenderBuilderTest {

    /**
     * The Temp.
     */
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    /**
     * Should build file sender and send file to storage.
     *
     * @throws FtpException       the ftp exception
     * @throws GeneratorException the generator exception
     * @throws IOException        the io exception
     * @throws UpdaterException   the updater exception
     */
    @Test
    public void shouldBuildFileSenderAndSendFileToStorage() throws FtpException, GeneratorException, IOException, UpdaterException {
        new Log4j2Console(Level.DEBUG).config();
        File rootDir = this.temp.newFolder();
        File ftpDir = this.temp.newFolder("ftp");
        FtpServer ftpServer = startServerFtp(ftpDir, 2221);
        IGenerator generator = new TreeFilesGenerator(rootDir.getPath(), 12, 12, 3, 3);
        List<File> files = generator.random();
        List<RelativeFile> relativeFiles = RelativeFileConverter.convert(rootDir, files);
        IStorage storage = new FTPStorage("localhost", 2221, "root", "secret", "/apps", true);
        ISender sender = SenderBuilder
                .create("MyApp", "1.0.0.0-dev1", relativeFiles, storage)
                .build();
         sender.send(relativeFiles);
        ftpServer.stop();
    }
}