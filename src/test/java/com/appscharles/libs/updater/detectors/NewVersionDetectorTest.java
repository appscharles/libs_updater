package com.appscharles.libs.updater.detectors;

import com.appscharles.libs.ioer.services.FileWriter;
import com.appscharles.libs.updater.builders.StructureUpdateBuilder;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.JSONPrettyFormatter;
import com.appscharles.libs.updater.validators.NewVersionValidator;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.appscharles.libs.updater.exctractors.HttpContentDownloaderTest.addRoute;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 06.07.2018
 * Time: 14:57
 * Project name: updater
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class NewVersionDetectorTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void shouldDetectNewVersion() throws UpdaterException, IOException {
        WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());
        String json = StructureUpdateBuilder.create(
                "MyApp", "1.0.0.0-dev2", new JSONPrettyFormatter()).build();
        addRoute("/MyApp/update.json", json, wireMockRule);
        wireMockRule.start();
        Assert.assertTrue(NewVersionValidator.existInHTTP("MyApp", "1.0.0.0-dev1", new URL("http://localhost:" + wireMockRule.port())));
        Assert.assertTrue(NewVersionDetector.versionInHTTP("MyApp", new URL("http://localhost:" + wireMockRule.port())).equals("1.0.0.0-dev2"));
        wireMockRule.stop();

        File storageDir = this.temp.newFolder();
        File jsonFile = new File(storageDir, "MyApp/update.json");
        FileWriter.write(jsonFile, json);
        Assert.assertTrue(NewVersionValidator.existInDisk("MyApp", "1.0.0.0-dev1", storageDir));
        Assert.assertTrue(NewVersionDetector.versionInDisk("MyApp", storageDir).equals("1.0.0.0-dev2"));
    }

}