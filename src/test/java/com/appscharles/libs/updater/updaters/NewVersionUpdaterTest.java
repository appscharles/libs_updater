package com.appscharles.libs.updater.updaters;

import com.appscharles.libs.generator.exceptions.GeneratorException;
import com.appscharles.libs.generator.services.TreeFilesGenerator;
import com.appscharles.libs.ioer.services.DirReader;
import com.appscharles.libs.ioer.services.FileReader;
import com.appscharles.libs.ioer.services.FileWriter;
import com.appscharles.libs.logger.configurators.Log4j2Console;
import com.appscharles.libs.logger.services.LoggerConfigurator;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.senders.NewVersionSender;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.logging.log4j.Level;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.appscharles.libs.updater.storages.FTPStorageTest.startServerFtp;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 06.07.2018
 * Time: 16:48
 * Project name: updater
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class NewVersionUpdaterTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void shouldUpdateFilesByDiskStorage() throws IOException, GeneratorException, UpdaterException, InterruptedException {
        LoggerConfigurator.config(new Log4j2Console(Level.DEBUG));

        File oldFilesDir = this.temp.newFolder("oldFilesDir");
        File newFilesDir = this.temp.newFolder("newFilesDir");
        File storageDir = this.temp.newFolder("storageDir");
        new TreeFilesGenerator(oldFilesDir.getPath(), 12, 12, 3, 3).random();
        new TreeFilesGenerator(newFilesDir.getPath(), 12, 12, 3, 3).random();

        File oldCheckFile = new File(oldFilesDir, "checkFile.txt");
        FileWriter.write(oldCheckFile, "old content");

        File newCheckFile = new File(newFilesDir, "checkFile.txt");
        FileWriter.write(newCheckFile, "new content");

        NewVersionSender.sendToDisk("MyApp", "1.0.0.0-dev1", newFilesDir, storageDir);
        NewVersionUpdater.updateFromDisk("MyApp", oldFilesDir, storageDir, 123123L, null, null, null);
       Thread.sleep(5000);
        Assert.assertEquals(FileReader.read(oldCheckFile), "new content");
        Assert.assertTrue(DirReader.getFiles(oldFilesDir).size() > DirReader.getFiles(newFilesDir).size() + 20);
    }

    @Test
    public void shouldUpdateFilesByHTTPStorage() throws IOException, GeneratorException, UpdaterException, InterruptedException, FtpException {
        LoggerConfigurator.config(new Log4j2Console(Level.DEBUG));

        File oldFilesDir = this.temp.newFolder("oldFilesDir");
        File newFilesDir = this.temp.newFolder("newFilesDir");
        File storageDir = this.temp.newFolder("storageDir");
        new TreeFilesGenerator(oldFilesDir.getPath(), 12, 12, 3, 3).random();
        new TreeFilesGenerator(newFilesDir.getPath(), 12, 12, 3, 3).random();

        File oldCheckFile = new File(oldFilesDir, "checkFile.txt");
        FileWriter.write(oldCheckFile, "old content");

        File newCheckFile = new File(newFilesDir, "checkFile.txt");
        FileWriter.write(newCheckFile, "new content");

        FtpServer ftpServer = startServerFtp(storageDir, 2221);

        WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort().usingFilesUnderDirectory(storageDir.getPath()));
        wireMockRule.start();


        URL urlStorage = new URL("http://localhost:"+ wireMockRule.port() + "/test");
        NewVersionSender.sendToFTP("MyApp", "1.0.0.0-dev1", newFilesDir, "localhost", 2221, "root", "secret", "__files/test/", true);
        NewVersionUpdater.updateFromHTTP("MyApp", oldFilesDir, urlStorage, 123123L, null, null, null);

        Thread.sleep(5000);
        Assert.assertEquals(FileReader.read(oldCheckFile), "new content");
        Assert.assertTrue(DirReader.getFiles(oldFilesDir).size() > DirReader.getFiles(newFilesDir).size() + 20);

        wireMockRule.stop();
        ftpServer.stop();
    }
}