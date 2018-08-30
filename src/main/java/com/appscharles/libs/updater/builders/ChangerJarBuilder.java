package com.appscharles.libs.updater.builders;

import com.appscharles.libs.dialoger.factories.ExceptionDialogFactory;
import com.appscharles.libs.fxer.stages.FXStage;
import com.appscharles.libs.ioer.services.DirReader;
import com.appscharles.libs.jarer.builders.JarCreatorBuilder;
import com.appscharles.libs.jarer.creators.IJarCreator;
import com.appscharles.libs.jarer.exceptions.JarerException;
import com.appscharles.libs.logger.configurators.Log4j2Console;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.programs.changer.Changer;
import com.google.common.xml.XmlEscapers;
import com.google.devtools.common.options.Converter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Core;

import java.io.File;

/**
 * The type Changer jar builder.
 */
public class ChangerJarBuilder {

    private File jarFile;

    private ChangerJarBuilder() {

    }

    /**
     * Create changer jar builder.
     *
     * @param jarFile the jar file
     * @return the changer jar builder
     */
    public static ChangerJarBuilder create(File jarFile) {
        ChangerJarBuilder instance = new ChangerJarBuilder();
        instance.jarFile = jarFile;
        return instance;
    }

    /**
     * Build.
     *
     * @throws UpdaterException the updater exception
     */
    public void build() throws UpdaterException {
        try {
            IJarCreator jarCreator = JarCreatorBuilder.create(Changer.NAME, Changer.VERSION, Changer.class, this.jarFile).build();
            jarCreator.addDependency("com/appscharles/libs/updater", ChangerJarBuilder.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.addDependency("com/appscharles/libs/dialoger", ExceptionDialogFactory.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.addDependency("com/appscharles/libs/processer", com.appscharles.libs.processer.callers.ICommanderCaller.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.addDependency("com/appscharles/libs/ioer", DirReader.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.addDependency("com/appscharles/libs/logger", Log4j2Console.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.addDependency("com/appscharles/libs/fxer", FXStage.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.addDependency("com/google/devtools/common/options", Converter.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.addDependency("com/google/common", XmlEscapers.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.addDependency("org/apache/logging/log4j", Level.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.addDependency("org/apache/logging/log4j", Core.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.create();
        } catch (JarerException e) {
            throw new UpdaterException(e);
        }
    }
}
