package com.appscharles.libs.updater.services;

import it.sauronsoftware.ftp4j.*;

import java.io.File;
import java.io.IOException;

/**
 * The type Ftp file service.
 */
public class FTPFileService {
    /**
     * Create directories.
     *
     * @param absolutePath the absolute path
     * @param ftpClient    the ftp client
     * @throws FTPException             the ftp exception
     * @throws IOException              the io exception
     * @throws FTPIllegalReplyException the ftp illegal reply exception
     * @throws FTPAbortedException      the ftp aborted exception
     * @throws FTPDataTransferException the ftp data transfer exception
     * @throws FTPListParseException    the ftp list parse exception
     */
    public static void createDirectories(String absolutePath, FTPClient ftpClient) throws FTPException, IOException, FTPIllegalReplyException, FTPAbortedException, FTPDataTransferException, FTPListParseException {
        ftpClient.changeDirectory("/");
        String[] dirs = absolutePath.split("/");
        for (String dir : dirs) {
            if (dir.isEmpty() == false){
                existOrCreateDirectory(dir, ftpClient);
                ftpClient.changeDirectory(dir);
            }
        }
    }

    /**
     * Upload file.
     *
     * @param file         the file
     * @param absolutePath the absolute path
     * @param ftpClient    the ftp client
     * @throws FTPException             the ftp exception
     * @throws IOException              the io exception
     * @throws FTPIllegalReplyException the ftp illegal reply exception
     * @throws FTPAbortedException      the ftp aborted exception
     * @throws FTPDataTransferException the ftp data transfer exception
     * @throws FTPListParseException    the ftp list parse exception
     */
    public static void uploadFile(File file, String absolutePath, FTPClient ftpClient) throws FTPException, IOException, FTPIllegalReplyException, FTPAbortedException, FTPDataTransferException, FTPListParseException {
        String[] pathParts = absolutePath.split("/");
        ftpClient.changeDirectory("/");
        for (Integer i = 0; i < pathParts.length; i++) {
            if (pathParts[i].isEmpty()){
                continue;
            }
            if (i.equals(pathParts.length - 1)){
                ftpClient.upload(file);
            } else {
                existOrCreateDirectory(pathParts[i], ftpClient);
                ftpClient.changeDirectory(pathParts[i]);
            }
        }
    }

    /**
     * Exist or create directory.
     *
     * @param directoryName the directory name
     * @param ftpClient     the ftp client
     * @throws IOException              the io exception
     * @throws FTPIllegalReplyException the ftp illegal reply exception
     * @throws FTPException             the ftp exception
     */
    public static void existOrCreateDirectory(String directoryName, FTPClient ftpClient) throws IOException, FTPIllegalReplyException, FTPException {
        FTPReply r = ftpClient.sendCustomCommand("MKD " + directoryName);
        if (r.getMessages()[0].contains("File exists")){
            return;
        }
        if (!r.isSuccessCode() && r.getMessages()[0].contains("already exist") == false) {
            throw new FTPException(r);
        }
    }
}
