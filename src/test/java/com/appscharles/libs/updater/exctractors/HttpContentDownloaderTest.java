package com.appscharles.libs.updater.exctractors;

import com.appscharles.libs.ioer.downloaders.content.HttpContentDownloader;
import com.appscharles.libs.ioer.downloaders.content.IContentDownloader;
import com.appscharles.libs.ioer.exceptions.IoerException;
import com.appscharles.libs.logger.configurators.Log4j2Console;
import com.appscharles.libs.logger.services.LoggerConfigurator;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.logging.log4j.Level;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * The type Http content downloader test.
 */
public class HttpContentDownloaderTest {

    /**
     * Sets logger.
     */
    @Before
    public void setLogger() {
        LoggerConfigurator.config(new Log4j2Console(Level.DEBUG));
    }

    /**
     * Should get content from http protocol.
     *
     * @throws IOException      the io exception
     * @throws UpdaterException the updater exception
     */
    @Test
    public void shouldGetContentFromHttpProtocol() throws IOException, UpdaterException, IoerException {
        WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());
        String content = "this is example content";
        addRoute("/content", content, wireMockRule);
        wireMockRule.start();
        IContentDownloader contentDownloader = new HttpContentDownloader(
                new URL("http://localhost:" + wireMockRule.port() + "/content"));
        Assert.assertEquals(contentDownloader.getContent(), content);

        wireMockRule.stop();
    }

    /**
     * Add route.
     *
     * @param route        the route
     * @param content      the content
     * @param wireMockRule the wire mock rule
     */
    public static void addRoute(String route, String content, WireMockRule wireMockRule){
        wireMockRule.stubFor(get(urlEqualTo(route))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/html; charset=utf-8")
                        .withBody(content)));
    }
}