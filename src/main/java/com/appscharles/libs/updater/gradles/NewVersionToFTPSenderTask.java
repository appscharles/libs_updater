package com.appscharles.libs.updater.gradles;

import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.senders.NewVersionSender;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 06.07.2018
 * Time: 20:09
 * Project name: updater
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class NewVersionToFTPSenderTask extends DefaultTask {

    @TaskAction
    public void newVersionSendToFTP() throws UpdaterException {
        NewVersionUpdaterPluginExtension extension = getProject().getExtensions().findByType(NewVersionUpdaterPluginExtension.class);
        if (extension == null) {
            extension = new NewVersionUpdaterPluginExtension();
        }
        System.out.println("Launch send to FTP '"+extension.getHostFTP()+"' new version: " + getProject().getVersion());
        NewVersionSender.sendToFTP(getProject().getName(), getProject().getVersion().toString(), new File(extension.getFromDir()), extension.getHostFTP(), extension.getPortFTP(), extension.getNameFTP(), extension.getPasswordFTP(), extension.getStorageDir(), extension.getPassiveModeFTP());
    }
}
