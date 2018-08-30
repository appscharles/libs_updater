package com.appscharles.libs.updater.builders;

import com.appscharles.libs.updater.exceptions.UpdaterException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 02.07.2018
 * Time: 11:33
 * Project name: updater
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class ChangerJarBuilderTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void shouldBuildJarFile() throws IOException, UpdaterException {
        File jarFile = this.temp.newFile("changer.jar");
        ChangerJarBuilder.create(jarFile).build();
        Assert.assertTrue(jarFile.exists());
    }
}