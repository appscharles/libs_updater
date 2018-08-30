package com.appscharles.libs.updater.builders;

import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.exctractors.ChecksumExtractor;
import com.appscharles.libs.updater.formatters.IFormatter;
import com.appscharles.libs.updater.models.StructureDirectory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Structure version builder.
 */
public class StructureVersionBuilder implements IStructureBuilder {

    private String name;

    private List<RelativeFile> relativeFiles;

    private IFormatter formatter;

    private StructureDirectory structureDirectory;

    private String checksumType = "MD5";

    private StructureVersionBuilder() {

    }

    /**
     * Create structure version builder.
     *
     * @param name          the name
     * @param relativeFiles the relative files
     * @param formatter     the formatter
     * @return the structure version builder
     */
    public static StructureVersionBuilder create(String name, List<RelativeFile> relativeFiles, IFormatter formatter) {
        StructureVersionBuilder instance = new StructureVersionBuilder();
        instance.name = name;
        instance.formatter = formatter;
        instance.relativeFiles = relativeFiles;
        instance.structureDirectory = new StructureDirectory(instance.name);
        return instance;
    }

    /**
     * Add property structure version builder.
     *
     * @param key   the key
     * @param value the value
     * @return the structure version builder
     */
    public StructureVersionBuilder addProperty(String key, String value) {
        this.structureDirectory.getProperties().put(key, value);
        return this;
    }

    /**
     * Gets structure directory.
     *
     * @return the structure directory
     */
    public StructureDirectory getStructureDirectory() {
        return structureDirectory;
    }

    /**
     * Checksum type structure version builder.
     *
     * @param type the type
     * @return the structure version builder
     */
    public StructureVersionBuilder checksumType(String type) {
        this.checksumType = type;
        return this;
    }

    public String build() throws UpdaterException {
        for (RelativeFile relativeFile : this.relativeFiles) {
            Map<String, String> file = new HashMap<>();
            file.put("relativePath", relativeFile.getRelativePath());
            if (relativeFile.isFile()) {
                file.put("checksum", new ChecksumExtractor(this.checksumType).extract(relativeFile));
                file.put("size", String.valueOf(relativeFile.length()));
            }
            this.structureDirectory.getFiles().add(file);
        }
        return (String)this.formatter.format(this.structureDirectory);
    }

    public void build(File toFile) throws UpdaterException {
        if (toFile.getParentFile().exists() == false){
            toFile.getParentFile().mkdirs();
        }
        try (OutputStreamWriter writer =
                     new OutputStreamWriter(new FileOutputStream(toFile) , StandardCharsets.UTF_8)) {
            writer.write(build());
        } catch (IOException e) {
            throw new UpdaterException(e);
        }
    }

    public void setFormatter(IFormatter formatter) {
        this.formatter = formatter;
    }
}
