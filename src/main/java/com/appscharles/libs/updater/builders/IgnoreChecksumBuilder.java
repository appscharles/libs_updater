package com.appscharles.libs.updater.builders;

import com.appscharles.libs.ioer.services.DirReader;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.exctractors.ChecksumExtractor;
import com.appscharles.libs.updater.exctractors.IHashExtractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Ignore checksum builder.
 */
public class IgnoreChecksumBuilder {

    private File dir;

    private IHashExtractor hashExtractor;

    private IgnoreChecksumBuilder() {

    }

    /**
     * Create ignore checksum builder.
     *
     * @param dir the dir
     * @return the ignore checksum builder
     */
    public static IgnoreChecksumBuilder create(File dir) {
        IgnoreChecksumBuilder instance = new IgnoreChecksumBuilder();
        instance.dir = dir;
        instance.hashExtractor = new ChecksumExtractor("MD5");
        return instance;
    }

    /**
     * Sets hash extractor.
     *
     * @param hashExtractor the hash extractor
     * @return the hash extractor
     */
    public IgnoreChecksumBuilder setHashExtractor(IHashExtractor hashExtractor) {
        this.hashExtractor = hashExtractor;
        return this;
    }

    /**
     * Build list.
     *
     * @return the list
     * @throws UpdaterException the updater exception
     */
    public List<String> build() throws UpdaterException {
        try {
            List<String> ignoreChecksums = new ArrayList<>();
            for (File file : DirReader.getRegularFiles(this.dir)) {
                String hash = this.hashExtractor.extract(file);
                ignoreChecksums.add(hash);
            }
            return ignoreChecksums;
        } catch (IOException e) {
            throw new UpdaterException(e);
        }
    }
}
