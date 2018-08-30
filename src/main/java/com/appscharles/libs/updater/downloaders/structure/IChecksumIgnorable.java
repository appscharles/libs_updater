package com.appscharles.libs.updater.downloaders.structure;

import java.util.List;

/**
 * The interface Checksum ignorable.
 */
public interface IChecksumIgnorable {

    /**
     * Gets ignore checksums.
     *
     * @return the ignore checksums
     */
    List<String>  getIgnoreChecksums();
}
