package com.appscharles.libs.updater.detectors;

import com.appscharles.libs.ioer.downloaders.content.DiskContentDownloader;
import com.appscharles.libs.ioer.downloaders.content.HttpContentDownloader;
import com.appscharles.libs.ioer.downloaders.content.IContentDownloader;
import com.appscharles.libs.ioer.services.FileWriter;
import com.appscharles.libs.logger.configurators.Log4j2Console;
import com.appscharles.libs.logger.services.LoggerConfigurator;
import com.appscharles.libs.updater.builders.StructureUpdateBuilder;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.IFormatter;
import com.appscharles.libs.updater.formatters.JSONPrettyFormatter;
import com.appscharles.libs.updater.formatters.StructureUpdateFormatter;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.logging.log4j.Level;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.appscharles.libs.updater.exctractors.HttpContentDownloaderTest.addRoute;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * The type Update detector test.
 */
public class UpdateDetectorTest {

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
     * Should detect new version files by http protocol.
     *
     * @throws IOException      the io exception
     * @throws UpdaterException the updater exception
     */
    @Test
    public void shouldDetectNewVersionFilesByHttpProtocol() throws IOException, UpdaterException {
        WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());
        String json = StructureUpdateBuilder.create(
                "MyApp", "1.0.0.0-dev2", new JSONPrettyFormatter()).build();
        addRoute("/json", json, wireMockRule);
        wireMockRule.start();
        IStructureUpdateDetector updateDetector = new StructureUpdateDetector(
                "MyApp",
                "1.0.0.0-dev0",
                new HttpContentDownloader(new URL("http://localhost:" + wireMockRule.port() + "/json")),
                new StructureUpdateFormatter());
        Assert.assertTrue(updateDetector.exist());
        updateDetector = new StructureUpdateDetector(
                "MyApp",
                "1.0.0.0-dev2",
                new HttpContentDownloader(new URL("http://localhost:" + wireMockRule.port() + "/json")),
                new StructureUpdateFormatter());
        Assert.assertFalse(updateDetector.exist());
        updateDetector = new StructureUpdateDetector(
                "MyApp",
                "1.0.0.0-dev3",
                new HttpContentDownloader(new URL("http://localhost:" + wireMockRule.port() + "/json")),
                new StructureUpdateFormatter());
        Assert.assertFalse(updateDetector.exist());
        wireMockRule.stop();
    }

    /**
     * Should detect new version files by disk file.
     *
     * @throws IOException      the io exception
     * @throws UpdaterException the updater exception
     */
    @Test
    public void shouldDetectNewVersionFilesByDiskFile() throws IOException, UpdaterException {
        String json = StructureUpdateBuilder.create(
                "MyApp", "1.0.0.0-dev2", new JSONPrettyFormatter()).build();
        File file = this.temp.newFile();
        FileWriter.write(file, json);
        IFormatter structureUpdateFormatter = new StructureUpdateFormatter();
        IContentDownloader contentDownloader = new DiskContentDownloader(file);
        IStructureUpdateDetector updateDetector = new StructureUpdateDetector("MyApp", "1.0.0.0-dev0", contentDownloader,structureUpdateFormatter);
        Assert.assertTrue(updateDetector.exist());
        updateDetector = new StructureUpdateDetector("MyApp", "1.0.0.0-dev2", contentDownloader, structureUpdateFormatter);
        Assert.assertFalse(updateDetector.exist());
        updateDetector = new StructureUpdateDetector("MyApp", "1.0.0.0-dev3", contentDownloader,structureUpdateFormatter);
        Assert.assertFalse(updateDetector.exist());

    }
}