package com.appscharles.libs.updater.updaters;

import com.appscharles.libs.updater.builders.ChangerCommandBuilder;
import com.appscharles.libs.updater.builders.ChangerJarBuilder;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.configurations.PathStorageConfig;
import com.appscharles.libs.updater.detectors.NewVersionDetector;
import com.appscharles.libs.updater.downloaders.structure.DiskStructureVersionDownloader;
import com.appscharles.libs.updater.downloaders.structure.HttpStructureVersionDownloader;
import com.appscharles.libs.updater.downloaders.structure.IStructureVersionDownloader;
import com.appscharles.libs.updater.exceptions.UpdaterException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

/**
 * The type New version updater.
 */
public class NewVersionUpdater {

    /**
     * Update from disk.
     *
     * @param name                           the name
     * @param toDir                          the to dir
     * @param storageDir                     the storage dir
     * @param mainPID                        the main pid
     * @param timeoutMainPIDWillKill         the timeout main pid will kill
     * @param callbackCommand                the callback command
     * @param updatedArgsWithCallbackCommand the updated args with callback command
     * @throws UpdaterException the updater exception
     */
    public static void updateFromDisk(String name, File toDir, File storageDir, Long mainPID, Long timeoutMainPIDWillKill, String callbackCommand, Boolean updatedArgsWithCallbackCommand) throws UpdaterException {
        try {
            File temp = Files.createTempDirectory("update_" + name + "_").toFile();
            File changerJarFile = new File(Files.createTempDirectory("changer_" + name + "_").toFile(), "changer.jar");
            String newVersion = NewVersionDetector.versionInDisk(name, storageDir);
            IPathStorageConfig pathStorageConfig = new PathStorageConfig(name, newVersion);
            IStructureVersionDownloader structureDownloader = new DiskStructureVersionDownloader(pathStorageConfig, storageDir, temp);
            structureDownloader.download();
            ChangerJarBuilder.create(changerJarFile).build();
            String command = ChangerCommandBuilder.create(changerJarFile, temp, toDir, mainPID)
                    .setCallbackCommand(callbackCommand)
                    .setUpdatedArgsWithCallbackCommand(updatedArgsWithCallbackCommand)
                    .setTimeoutMainPIDWillKill(timeoutMainPIDWillKill)
                    .build();
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new UpdaterException(e);
        }
    }

    /**
     * Update from http.
     *
     * @param name                           the name
     * @param toDir                          the to dir
     * @param storageURL                     the storage url
     * @param mainPID                        the main pid
     * @param timeoutMainPIDWillKill         the timeout main pid will kill
     * @param callbackCommand                the callback command
     * @param updatedArgsWithCallbackCommand the updated args with callback command
     * @throws UpdaterException the updater exception
     */
    public static void updateFromHTTP(String name, File toDir, URL storageURL, Long mainPID, Long timeoutMainPIDWillKill, String callbackCommand, Boolean updatedArgsWithCallbackCommand) throws UpdaterException {
        try {
            File temp = Files.createTempDirectory("update_" + name + "_").toFile();
            File changerJarFile = new File(Files.createTempDirectory("changer_" + name + "_").toFile(), "changer.jar");
            String newVersion = NewVersionDetector.versionInHTTP(name, storageURL);
            IPathStorageConfig pathStorageConfig = new PathStorageConfig(name, newVersion);
            IStructureVersionDownloader structureDownloader = new HttpStructureVersionDownloader(pathStorageConfig, storageURL, temp);
            structureDownloader.download();
            ChangerJarBuilder.create(changerJarFile).build();
            String command = ChangerCommandBuilder.create(changerJarFile, temp, toDir, mainPID)
                    .setCallbackCommand(callbackCommand)
                    .setUpdatedArgsWithCallbackCommand(updatedArgsWithCallbackCommand)
                    .setTimeoutMainPIDWillKill(timeoutMainPIDWillKill)
                    .build();
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new UpdaterException(e);
        }
    }
}
