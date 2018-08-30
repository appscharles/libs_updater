package com.appscharles.libs.updater.storages;

import com.appscharles.libs.generator.exceptions.GeneratorException;
import com.appscharles.libs.generator.services.IGenerator;
import com.appscharles.libs.generator.services.TreeFilesGenerator;
import com.appscharles.libs.ioer.converters.RelativeFileConverter;
import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.ioer.services.DirReader;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The type Disk storage test.
 */
public class DiskStorageTest {

    /**
     * The Temp.
     */
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    /**
     * Should save file in disk.
     *
     * @throws IOException        the io exception
     * @throws GeneratorException the generator exception
     * @throws UpdaterException   the updater exception
     */
    @Test
    public void shouldSaveFileInDisk() throws IOException, GeneratorException, UpdaterException {
        File rootDir = this.temp.newFolder("root");
        File storageDir = this.temp.newFolder("storage");
        IGenerator generator = new TreeFilesGenerator(rootDir.getPath(), 12, 12, 3, 3);

        List<File> files = generator.random();
        List<RelativeFile> relativeFiles = RelativeFileConverter.convert(rootDir, files);
        IStorage storage = new DiskStorage(storageDir.getPath());
        storage.save(relativeFiles);
        Assert.assertEquals(DirReader.getFiles(rootDir).size(), DirReader.getFiles(storageDir).size());
    }

}