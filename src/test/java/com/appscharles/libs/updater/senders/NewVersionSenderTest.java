package com.appscharles.libs.updater.senders;

import com.appscharles.libs.generator.exceptions.GeneratorException;
import com.appscharles.libs.generator.services.IGenerator;
import com.appscharles.libs.generator.services.TreeFilesGenerator;
import com.appscharles.libs.ioer.services.DirReader;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static com.appscharles.libs.updater.storages.FTPStorageTest.startServerFtp;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 06.07.2018
 * Time: 15:24
 * Project name: updater
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class NewVersionSenderTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void shouldSendFilesToDiskServer() throws IOException, FtpException, GeneratorException, UpdaterException {
        File rootDir = this.temp.newFolder();
        File disk = this.temp.newFolder("disk");
        IGenerator generator = new TreeFilesGenerator(rootDir.getPath(), 12, 12, 3, 3);
        generator.random();
        NewVersionSender.sendToDisk("MyApp", "1.0.0.0-dev1", rootDir, disk);
        Assert.assertEquals(DirReader.getFiles(rootDir).size(), DirReader.getFiles(disk).size() - 5);
    }

    @Test
    public void shouldSendFilesToFTPServer() throws IOException, FtpException, GeneratorException, UpdaterException {
        File rootDir = this.temp.newFolder();
        File ftpDir = this.temp.newFolder("ftp");
        FtpServer ftpServer = startServerFtp(ftpDir, 2221);
        IGenerator generator = new TreeFilesGenerator(rootDir.getPath(), 12, 12, 3, 3);
        generator.random();
        NewVersionSender.sendToFTP("MyApp", "1.0.0.0-dev1", rootDir, "localhost", 2221, "root", "secret", "/appscharles", true);
        ftpServer.stop();
        Assert.assertEquals(DirReader.getFiles(rootDir).size(), DirReader.getFiles(ftpDir).size() - 6);
    }
}