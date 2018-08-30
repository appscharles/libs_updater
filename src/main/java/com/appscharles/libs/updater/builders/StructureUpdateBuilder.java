package com.appscharles.libs.updater.builders;

import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.formatters.IFormatter;
import com.appscharles.libs.updater.models.StructureUpdate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * The type Structure update builder.
 */
public class StructureUpdateBuilder implements IStructureBuilder {

    private String name;

    private String version;

    private IFormatter formatter;

    private StructureUpdate structureUpdate;

    private StructureUpdateBuilder() {

    }

    /**
     * Create structure update builder.
     *
     * @param name      the name
     * @param version   the version
     * @param url       the url
     * @param formatter the formatter
     * @return the structure update builder
     */
    public static StructureUpdateBuilder create(String name, String version, IFormatter formatter) {
        StructureUpdateBuilder instance = new StructureUpdateBuilder();
        instance.name = name;
        instance.version = version;
        instance.formatter = formatter;
        instance.structureUpdate = new StructureUpdate(instance.name, instance.version);
        return instance;
    }

    /**
     * Add property structure update builder.
     *
     * @param key   the key
     * @param value the value
     * @return the structure update builder
     */
    public StructureUpdateBuilder addProperty(String key, String value) {
        this.structureUpdate.getProperties().put(key, value);
        return this;
    }

    /**
     * Gets structure update.
     *
     * @return the structure update
     */
    public StructureUpdate getStructureUpdate() {
        return this.structureUpdate;
    }


    public String build() throws UpdaterException {
        return (String)this.formatter.format(this.structureUpdate);
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
