package com.appscharles.libs.updater.storages;

import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.updater.builders.FTPPathBuilder;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.services.FTPFileService;
import it.sauronsoftware.ftp4j.FTPClient;

import java.util.List;

/**
 * The type Ftp storage.
 */
public class FTPStorage extends Storage{

    private FTPClient ftpClient;

    private String host;

    private Integer port;

    private String name;

    private String password;


    private Boolean passiveMode;

    /**
     * Instantiates a new Ftp storage.
     *
     * @param host        the host
     * @param port        the port
     * @param name        the name
     * @param password    the password
     * @param storageDir  the storage dir
     * @param passiveMode the passive mode
     */
    public FTPStorage(String host, Integer port, String name, String password, String storageDir, Boolean passiveMode) {
        super(FTPPathBuilder.buildAbsolutePath(storageDir));
        this.host = host;
        this.port = port;
        this.name = name;
        this.password = password;
        this.storageDir = storageDir;
        this.passiveMode = passiveMode;
        this.ftpClient = new FTPClient();
        this.ftpClient.setPassive(this.passiveMode);
    }

    @Override
    public void save(List<RelativeFile> relativeFiles) throws UpdaterException {
        if (relativeFiles.size() == 0){
            throw new UpdaterException("Not files to save storage.");
        }
        try {
            this.ftpClient.connect(this.host, this.port);
            this.ftpClient.login(name, password);
            for (RelativeFile relativeFile : relativeFiles) {
                if (relativeFile.isDirectory()){
                    FTPFileService.createDirectories(FTPPathBuilder.buildAbsolutePath(this.storageDir, relativeFile.getRelativePath()), this.ftpClient);
                } else {
                    FTPFileService.uploadFile(relativeFile, FTPPathBuilder.buildAbsolutePath(this.storageDir, relativeFile.getRelativePath()), this.ftpClient);
                }
            }
            this.ftpClient.disconnect(false);
        } catch (Exception e) {
           throw new UpdaterException(e);
        }
    }
}
