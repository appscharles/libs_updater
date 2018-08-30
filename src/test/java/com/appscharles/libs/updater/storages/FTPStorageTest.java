package com.appscharles.libs.updater.storages;

import com.appscharles.libs.generator.exceptions.GeneratorException;
import com.appscharles.libs.generator.services.IGenerator;
import com.appscharles.libs.generator.services.TreeFilesGenerator;
import com.appscharles.libs.ioer.converters.RelativeFileConverter;
import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.ioer.services.DirReader;
import com.appscharles.libs.logger.configurators.Log4j2Console;
import com.appscharles.libs.logger.services.LoggerConfigurator;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.apache.logging.log4j.Level;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Ftp storage test.
 */
public class FTPStorageTest {

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
     * Should sent files to server ftp.
     *
     * @throws FtpException       the ftp exception
     * @throws IOException        the io exception
     * @throws GeneratorException the generator exception
     * @throws UpdaterException   the updater exception
     */
    @Test
    public void shouldSentFilesToServerFTP() throws FtpException, IOException, GeneratorException, UpdaterException {
        File rootDir = this.temp.newFolder("root");
        File ftpDir = this.temp.newFolder("ftp");
        FtpServer ftpServer = startServerFtp(ftpDir, 2221);

        IGenerator generator = new TreeFilesGenerator(rootDir.getPath(), 12, 12, 3, 3);
        List<File> files = generator.random();
        List<RelativeFile> relativeFiles = RelativeFileConverter.convert(rootDir, files);

        IStorage storage = new FTPStorage("localhost", 2221, "root", "secret", "/test/", true);
        storage.save(relativeFiles);
        Assert.assertEquals(DirReader.getFiles(rootDir).size(), DirReader.getFiles(ftpDir).size() -1);
        ftpServer.stop();

    }

    /**
     * Start server ftp ftp server.
     *
     * @param ftpDir the ftp dir
     * @param port   the port
     * @return the ftp server
     * @throws FtpException the ftp exception
     */
    public static FtpServer startServerFtp(File ftpDir, Integer port) throws FtpException {
        FtpServerFactory sf = new FtpServerFactory();
        ListenerFactory ls = new ListenerFactory();
        ls.setPort(port);
        sf.addListener("default", ls.createListener());
        PropertiesUserManagerFactory usm = new PropertiesUserManagerFactory();
        UserManager um = usm.createUserManager();
        BaseUser bu = new BaseUser();
        bu.setName("root");
        bu.setPassword("secret");
        bu.setHomeDirectory(ftpDir.getPath());
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new WritePermission());
        bu.setAuthorities(authorities);
        um.save(bu);
        sf.setUserManager(um);
        FtpServer server = sf.createServer();
        server.start();
        return server;
    }
}