package com.appscharles.libs.updater.builders;

import com.appscharles.libs.generator.exceptions.GeneratorException;
import com.appscharles.libs.generator.services.IGenerator;
import com.appscharles.libs.generator.services.TreeFilesGenerator;
import com.appscharles.libs.ioer.converters.RelativeFileConverter;
import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.JSONPrettyFormatter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * The type Structure builder test.
 */
public class StructureBuilderTest {

    /**
     * The Temp.
     */
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    /**
     * Should create json structure files of directory.
     *
     * @throws GeneratorException the generator exception
     * @throws IOException        the io exception
     * @throws UpdaterException   the updater exception
     */
    @Test
    public void shouldCreateJsonStructureFilesOfDirectory() throws GeneratorException, IOException, UpdaterException {
        File rootDir = this.temp.newFolder();
        IGenerator generator = new TreeFilesGenerator(rootDir.getPath(), 12, 12, 3, 3);
        List<File> files = generator.random();
        List<RelativeFile> relativeFiles = RelativeFileConverter.convert(rootDir, files);
        String json = StructureVersionBuilder
                .create("MyApp", relativeFiles, new JSONPrettyFormatter())
                .addProperty("version", "1.0.0.0-dev0")
                .addProperty("latinCharacters", "ążćźńęł")
                .build();
        assertTrue(json.contains("MyApp"));
    }

    /**
     * Should create and write structure json to file.
     *
     * @throws IOException        the io exception
     * @throws GeneratorException the generator exception
     * @throws UpdaterException   the updater exception
     */
    @Test
    public void shouldCreateAndWriteStructureJsonToFile() throws IOException, GeneratorException, UpdaterException {
        File rootDir = this.temp.newFolder();
        IGenerator generator = new TreeFilesGenerator(rootDir.getPath(), 12, 12, 3, 3);
        List<File> files = generator.random();
        List<RelativeFile> relativeFiles = RelativeFileConverter.convert(rootDir, files);
        File file = new File(rootDir ,"dir1/dir2/data.json");
        StructureVersionBuilder
                .create("MyApp", relativeFiles, new JSONPrettyFormatter())
                .addProperty("version", "1.0.0.0-dev0")
                .addProperty("latinCharacters", "ążćźńęł")
                .build(file);
    }
}