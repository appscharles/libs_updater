package com.appscharles.libs.updater.exctractors;

import com.appscharles.libs.generator.exceptions.GeneratorException;
import com.appscharles.libs.generator.services.IGenerator;
import com.appscharles.libs.generator.services.TreeFilesGenerator;
import com.appscharles.libs.ioer.converters.RelativeFileConverter;
import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.apache.logging.log4j.core.util.Assert.isNonEmpty;

/**
 * The type Checksum extractor test.
 */
public class ChecksumExtractorTest {

    /**
     * The Temp.
     */
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    /**
     * Should get checksum of files.
     *
     * @throws IOException        the io exception
     * @throws GeneratorException the generator exception
     * @throws UpdaterException   the updater exception
     */
    @Test
    public void shouldGetChecksumOfFiles() throws IOException, GeneratorException, UpdaterException {
        File rootDir = this.temp.newFolder();
        IGenerator generator = new TreeFilesGenerator(rootDir.getPath(), 12, 12, 3, 3);
        List<File> files = generator.random();
        List<RelativeFile> relativeFiles = RelativeFileConverter.convert(rootDir, files);
        for (RelativeFile relativeFile : relativeFiles) {
            if (relativeFile.isFile()){
                isNonEmpty(new ChecksumExtractor("MD5").extract(relativeFile));
            }
        }
    }
}