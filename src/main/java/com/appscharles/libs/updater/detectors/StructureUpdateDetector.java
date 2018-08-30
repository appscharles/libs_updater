package com.appscharles.libs.updater.detectors;

import com.appscharles.libs.ioer.downloaders.content.IContentDownloader;
import com.appscharles.libs.ioer.exceptions.IoerException;
import com.appscharles.libs.updater.comparators.VersionComparator;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.IFormatter;
import com.appscharles.libs.updater.models.StructureUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureUpdateDetector implements IStructureUpdateDetector {

    private static final Logger logger = LogManager.getLogger(StructureUpdateDetector.class);

    private String name;

    private String oldVersion;

    private IContentDownloader contentDownloader;

    private IFormatter structureUpdateFormatter;

    private VersionComparator versionComparator;

    public StructureUpdateDetector(String name, String oldVersion, IContentDownloader contentDownloader, IFormatter structureUpdateFormatter) {
        this.name = name;
        this.oldVersion = oldVersion;
        this.contentDownloader = contentDownloader;
        this.structureUpdateFormatter = structureUpdateFormatter;
        this.versionComparator = new VersionComparator();
    }

    @Override
    public Boolean exist() {
        try {
            String content = this.contentDownloader.getContent();
            StructureUpdate structureUpdate = (StructureUpdate) this.structureUpdateFormatter.format(content);
            if (this.name.equals(structureUpdate.getName()) == false) {
                return false;
            }
            if (this.versionComparator.compare(this.oldVersion, structureUpdate.getVersion()) >= 0) {
                return false;
            }
            return true;
        } catch (UpdaterException | IoerException e) {
            logger.debug(e, e);
            return false;
        }
    }

    @Override
    public String getVersion() throws UpdaterException {
        String content;
        try {
            content = this.contentDownloader.getContent();
        } catch (IoerException e) {
            throw new UpdaterException(e);
        }
        StructureUpdate structureUpdate = (StructureUpdate) this.structureUpdateFormatter.format(content);
        if (this.name.equals(structureUpdate.getName()) == false) {
            throw new UpdaterException("Name structure is not valid: " + this.name);
        }
        return structureUpdate.getVersion();
    }
}
