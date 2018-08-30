package com.appscharles.libs.updater.builders;

import com.appscharles.libs.fxer.controllers.AbstractStageControllerFX;
import com.appscharles.libs.jarer.builders.JarCreatorBuilder;
import com.appscharles.libs.jarer.creators.IJarCreator;
import com.appscharles.libs.jarer.exceptions.JarerException;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.programs.locker.Locker;
import com.google.common.xml.XmlEscapers;
import com.google.devtools.common.options.Converter;

import java.io.File;

/**
 * The type Changer jar builder.
 */
public class LockerJarBuilder {

    private File jarFile;

    private LockerJarBuilder() {

    }

    /**
     * Create changer jar builder.
     *
     * @param jarFile the jar file
     * @return the changer jar builder
     */
    public static LockerJarBuilder create(File jarFile) {
        LockerJarBuilder instance = new LockerJarBuilder();
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
            IJarCreator jarCreator = JarCreatorBuilder.create(Locker.NAME, Locker.VERSION, Locker.class, this.jarFile).build();
            jarCreator.addDependency("com/appscharles/libs/updater", ChangerJarBuilder.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.addDependency("com/appscharles/libs/fxer", AbstractStageControllerFX.class.getProtectionDomain().getCodeSource().getLocation());

            jarCreator.addDependency("com/google/devtools/common/options", Converter.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.addDependency("com/google/common", XmlEscapers.class.getProtectionDomain().getCodeSource().getLocation());
            jarCreator.create();
        } catch (JarerException e) {
            throw new UpdaterException(e);
        }
    }
}
