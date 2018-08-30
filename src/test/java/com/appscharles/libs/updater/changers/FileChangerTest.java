package com.appscharles.libs.updater.changers;

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
 * The type File changer test.
 */
public class FileChangerTest {

    /**
     * The Temp.
     */
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    /**
     * Should change files without exception.
     *
     * @throws IOException        the io exception
     * @throws GeneratorException the generator exception
     * @throws UpdaterException   the updater exception
     */
    @Test
    public void shouldChangeFilesWithoutException() throws IOException, GeneratorException, UpdaterException {
        File fromDir = this.temp.newFolder();
        File toDir = this.temp.newFolder();
        List<RelativeFile> relativeFiles = generateRelativeFiles(fromDir);
        IFileChanger fileChanger = new FileChanger(fromDir, toDir, 60000L, 2344224L);
        fileChanger.change();
        Assert.assertEquals(DirReader.getRegularFiles(fromDir).size(), DirReader.getRegularFiles(toDir).size());
    }

    /**
     * Generate relative files list.
     *
     * @param toDir the to dir
     * @return the list
     * @throws GeneratorException the generator exception
     */
    public static List<RelativeFile> generateRelativeFiles(File toDir) throws GeneratorException {
        IGenerator generator = new TreeFilesGenerator(toDir.getPath(), 12, 12, 3, 3);
        List<File> files = generator.random();
        return RelativeFileConverter.convert(toDir, files);
    }

}