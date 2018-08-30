package com.appscharles.libs.updater.exctractors;

import com.appscharles.libs.updater.exceptions.UpdaterException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The type Checksum extractor.
 */
public class ChecksumExtractor implements IHashExtractor {

    private String type;

    /**
     * Instantiates a new Checksum extractor.
     *
     * @param type the type
     */
    public ChecksumExtractor(String type) {
        this.type = type;
    }

    @Override
    public String extract(File file) throws UpdaterException {
        if (file.isDirectory()) {
            throw new UpdaterException("Can not extract checksum from directory: " + file.getPath());
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance(this.type);
            byte[] dataBytes = new byte[1024];
            int nread = 0;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            byte[] mdbytes = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString().toUpperCase();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new UpdaterException(e);
        }
    }
}
