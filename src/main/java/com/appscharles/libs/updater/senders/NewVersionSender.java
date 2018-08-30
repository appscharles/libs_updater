package com.appscharles.libs.updater.senders;

import com.appscharles.libs.updater.exceptions.UpdaterException;

import java.io.File;

/**
 * The type Update sender.
 */
public class NewVersionSender {

    /**
     * Send to disk.
     *
     * @param name       the name
     * @param version    the version
     * @param fromDir    the from dir
     * @param storageDir the storage dir
     * @throws UpdaterException the updater exception
     */
    public static void sendToDisk(String name, String version, File fromDir, File storageDir) throws UpdaterException {
        NewVersionDiskSender.sendToDisk(name, version, fromDir, storageDir);
    }

    /**
     * Send to ftp.
     *
     * @param name           the name
     * @param version        the version
     * @param fromDir        the from dir
     * @param hostFTP        the host ftp
     * @param portFTP        the port ftp
     * @param nameFTP        the name ftp
     * @param passwordFTP    the password ftp
     * @param storageDirFTP  the storage dir ftp
     * @param passiveModeFTP the passive mode ftp
     * @throws UpdaterException the updater exception
     */
    public static void sendToFTP(String name, String version, File fromDir, String hostFTP, Integer portFTP, String nameFTP, String passwordFTP, String storageDirFTP, Boolean passiveModeFTP) throws UpdaterException {
        NewVersionFTPSender.sendToFTP(name, version, fromDir, hostFTP, portFTP, nameFTP, passwordFTP, storageDirFTP, passiveModeFTP);
    }
}
