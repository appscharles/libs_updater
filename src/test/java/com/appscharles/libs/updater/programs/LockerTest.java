package com.appscharles.libs.updater.programs;

import com.appscharles.libs.generator.exceptions.GeneratorException;
import com.appscharles.libs.ioer.services.FileWriter;
import com.appscharles.libs.processer.callers.CommanderCaller;
import com.appscharles.libs.processer.callers.CommanderResult;
import com.appscharles.libs.processer.callers.ICommanderCaller;
import com.appscharles.libs.processer.exceptions.ProcesserException;
import com.appscharles.libs.updater.builders.LockerJarBuilder;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.services.ThreadSleeper;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 02.07.2018
 * Time: 11:56
 * Project name: updater
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class LockerTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void shouldLockFile() throws IOException, UpdaterException, ProcesserException, GeneratorException {
        File jarFile = this.temp.newFile("locker.jar");
        File fileTxt = this.temp.newFile("file.txt");
        FileWriter.write(fileTxt, "content");
        LockerJarBuilder.create(jarFile).build();
        Executors.newSingleThreadExecutor().submit(()->{
            try {
                ICommanderCaller commanderCaller = new CommanderCaller();
                CommanderResult result = commanderCaller.call("java -jar " + jarFile.getPath()+
                        " --filePath=" + fileTxt.getPath() + " --time=30000");
            } catch (ProcesserException e) {
                e.printStackTrace();
            }
        });
        ThreadSleeper.sleep(2000);
        Assert.assertFalse(fileTxt.delete());
    }
}