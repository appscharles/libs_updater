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
 * Time: 20:08
 * Project name: updater
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class NewVersionToDiskSenderTask extends DefaultTask {


    /**
     * Generate class of WebService Allegro
     */
    @TaskAction
    public void newVersionSendToDisk() throws UpdaterException {
        NewVersionUpdaterPluginExtension extension = getProject().getExtensions().findByType(NewVersionUpdaterPluginExtension.class);
        if (extension == null) {
            extension = new NewVersionUpdaterPluginExtension();
        }
        System.out.println("Launch send to disk new version: " + getProject().getVersion());
        NewVersionSender.sendToDisk(getProject().getName(), getProject().getVersion().toString(), new File(extension.getFromDir()), new File(extension.getStorageDir()));
    }


}
