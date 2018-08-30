package com.appscharles.libs.updater.programs;

import com.appscharles.libs.generator.exceptions.GeneratorException;
import com.appscharles.libs.generator.services.IGenerator;
import com.appscharles.libs.generator.services.TreeFilesGenerator;
import com.appscharles.libs.ioer.services.DirCopier;
import com.appscharles.libs.ioer.services.DirReader;
import com.appscharles.libs.ioer.services.FileReader;
import com.appscharles.libs.ioer.services.FileWriter;
import com.appscharles.libs.processer.callers.CommanderCaller;
import com.appscharles.libs.processer.callers.CommanderResult;
import com.appscharles.libs.processer.callers.ICommanderCaller;
import com.appscharles.libs.processer.exceptions.ProcesserException;
import com.appscharles.libs.updater.builders.ChangerJarBuilder;
import com.appscharles.libs.updater.builders.LockerJarBuilder;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.services.ThreadSleeper;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.jvnet.winp.WinProcess;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.List;
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
public class ChangerTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void shouldRunChangerJar() throws IOException, UpdaterException, ProcesserException, GeneratorException, InterruptedException {
        File jarFile = this.temp.newFile("changer.jar");
        File fromDir = this.temp.newFolder();
        File toDir = this.temp.newFolder();
        IGenerator generator = new TreeFilesGenerator(fromDir.getPath(), 12, 12, 3, 3);
        List<File> fromFiles = generator.random();
        ChangerJarBuilder.create(jarFile).build();
        ICommanderCaller commanderCaller = new CommanderCaller();
        CommanderResult result = commanderCaller.call("java -jar " + jarFile.getPath()+
        " --callerPID=1548445 --fromDir=" + fromDir.getPath() + " --toDir=" + toDir.getPath());
        Assert.assertFalse(result.getOutput(), result.isError());
        Assert.assertEquals(DirReader.getFiles(fromDir).size() +3, DirReader.getFiles(toDir).size());
        Thread.sleep(2000);
    }

    @Test
    public void shouldKillProcessAndChangeFile() throws IOException, GeneratorException, ProcesserException, UpdaterException {
        File jarFile = this.temp.newFile("changer.jar");
        File fromDir = this.temp.newFolder("from");
        File toDir = this.temp.newFolder("to");
        DirCopier.copy(fromDir, toDir, StandardCopyOption.REPLACE_EXISTING);
        FileWriter.write(new File(toDir, "file.txt"), "content");
        FileWriter.write(new File(fromDir, "file.txt"), "new content");
        IGenerator generator = new TreeFilesGenerator(fromDir.getPath(), 12, 12, 3, 3);
        generator.random();
        File changeFile = new File(toDir, "file.txt");
        Integer pIDLocker = lockFileAndGetPIDLocker(changeFile);
        ChangerJarBuilder.create(jarFile).build();
        ICommanderCaller commanderCaller = new CommanderCaller();
        CommanderResult result = commanderCaller.call("java -jar " + jarFile.getPath()+
                " --callerPID=" + pIDLocker + " --fromDir=" + fromDir.getPath() + " --toDir=" + toDir.getPath()
        + " --timeoutPIDCloseWaiter=5000");
        Assert.assertFalse(result.getOutput(), result.isError());
        Assert.assertEquals(FileReader.read(new File(toDir, "file.txt")), "new content");
    }

    @Test
    public void shouldThrowError() throws IOException, GeneratorException, ProcesserException, UpdaterException, InterruptedException {
        File jarFile = this.temp.newFile("changer.jar");
        File fromDir = this.temp.newFolder();
        File toDir = this.temp.newFolder();
        DirCopier.copy(fromDir, toDir, StandardCopyOption.REPLACE_EXISTING);
        FileWriter.write(new File(toDir, "file.txt"), "content");
        FileWriter.write(new File(fromDir, "file.txt"), "new content");
        IGenerator generator = new TreeFilesGenerator(fromDir.getPath(), 12, 12, 3, 3);
        generator.random();
        File changeFile = new File(toDir, "file.txt");
        Integer pIDLocker = lockFileAndGetPIDLocker(changeFile);
        ChangerJarBuilder.create(jarFile).build();
        ICommanderCaller commanderCaller = new CommanderCaller();
        Process process = Runtime.getRuntime().exec("cmd notepad.exe");
        WinProcess wp = new WinProcess(process);
        CommanderResult result = commanderCaller.call("java -jar " + jarFile.getPath()+
                " --callerPID=" + wp.getPid() + " --fromDir=" + fromDir.getPath() + " --toDir=" + toDir.getPath()
                + " --timeoutPIDCloseWaiter=2000 --test=true");
        Assert.assertTrue(result.getOutput(), result.getOutput().contains("ERROR"));
        System.out.println(result.getOutput());
        Thread.sleep(2000);
        File logFile = new File(toDir, "/logs/changer/logs.log");
        Assert.assertTrue(FileReader.read(logFile).contains("ERROR"));
        //process.destroyForcibly();
    }

    private Integer lockFileAndGetPIDLocker(File file) throws IOException, UpdaterException {
        File lockerJarFile = this.temp.newFile("locker.jar");
        LockerJarBuilder.create(lockerJarFile).build();
        SimpleIntegerProperty pIDLockerProperty = new SimpleIntegerProperty();
        Executors.newSingleThreadExecutor().submit(()->{
            try {
                Process process = Runtime.getRuntime().exec("java -jar " + lockerJarFile.getPath()+
                        " --filePath=" + file.getPath() + " --time=300000");
                WinProcess wp = new WinProcess(process);
                pIDLockerProperty.set(wp.getPid());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ThreadSleeper.sleep(2000);
        if (pIDLockerProperty.getValue() == null){
            throw new UpdaterException("PID locker is null.");
        }
        return pIDLockerProperty.getValue();
    }

    @Test
    public void shouldRunCallbackCommandWithoutExceptions() throws IOException, UpdaterException, ProcesserException, GeneratorException, InterruptedException {
        File jarFile = this.temp.newFile("changer.jar");
        File fromDir = this.temp.newFolder();
        File toDir = this.temp.newFolder();
        IGenerator generator = new TreeFilesGenerator(fromDir.getPath(), 12, 12, 3, 3);
        List<File> fromFiles = generator.random();
        ChangerJarBuilder.create(jarFile).build();
        ICommanderCaller commanderCaller = new CommanderCaller();
        CommanderResult result = commanderCaller.call("java -jar " + jarFile.getPath()+
                " --callerPID=1548445 --fromDir=" + fromDir.getPath() + " --toDir=" + toDir.getPath() +
        " --callbackCommand=\"cmd /C start ping wp.pl\" -u false");
        Assert.assertFalse(result.getOutput(), result.isError());
        Thread.sleep(2000);
    }

}