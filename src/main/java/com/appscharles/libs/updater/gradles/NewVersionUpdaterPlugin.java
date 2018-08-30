package com.appscharles.libs.updater.gradles;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.util.Arrays;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 06.07.2018
 * Time: 20:05
 * Project name: updater
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class NewVersionUpdaterPlugin  implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        Task clean = project.getTasks().findByName("clean");
        Task assemble = project.getTasks().findByName("assemble");
        Task build = project.getTasks().findByName("build");

        assemble.shouldRunAfter(clean);
        build.shouldRunAfter(assemble);

        project.getExtensions().create("newVersionUpdater", NewVersionUpdaterPluginExtension.class);

        Iterable<Task> beforeTasks = Arrays.asList(clean, assemble, build);

        Task taskNewVersionSendToDisk = project.getTasks().create("newVersionSendToDisk", NewVersionToDiskSenderTask.class);
        taskNewVersionSendToDisk.setDependsOn(beforeTasks);
        taskNewVersionSendToDisk.getOutputs().upToDateWhen((element)->{ return false; });
        taskNewVersionSendToDisk.shouldRunAfter(build);

        Task taskNewVersionSendToFTP =project.getTasks().create("newVersionSendToFTP",  NewVersionToFTPSenderTask.class);
        taskNewVersionSendToFTP.setDependsOn(beforeTasks);
        taskNewVersionSendToFTP.getOutputs().upToDateWhen((element)->{ return false; });
        taskNewVersionSendToFTP.shouldRunAfter(build);
    }
}