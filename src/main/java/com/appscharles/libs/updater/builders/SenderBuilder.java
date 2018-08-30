package com.appscharles.libs.updater.builders;

import com.appscharles.libs.ioer.models.RelativeFile;
import com.appscharles.libs.updater.configurations.IPathStorageConfig;
import com.appscharles.libs.updater.configurations.PathStorageConfig;
import com.appscharles.libs.updater.formatters.IFormatter;
import com.appscharles.libs.updater.formatters.JSONPrettyFormatter;
import com.appscharles.libs.updater.senders.ISender;
import com.appscharles.libs.updater.senders.Sender;
import com.appscharles.libs.updater.storages.IStorage;

import java.net.URL;
import java.util.List;

/**
 * The type Sender builder.
 */
public class SenderBuilder {

    private String name;

    private String version;

    private IStructureBuilder structureVersionBuilder;

    private IStructureBuilder structureUpdateBuilder;

    private IPathStorageConfig pathStorageConfig;

    private List<RelativeFile> relativeFiles;

    private IStorage storage;

    private SenderBuilder(){

    }

    /**
     * Create sender builder.
     *
     * @param name          the name
     * @param version       the version
     * @param url           the url
     * @param relativeFiles the relative files
     * @return the sender builder
     */
    public static SenderBuilder create(String name, String version, List<RelativeFile> relativeFiles, IStorage storage){
        SenderBuilder instance = new SenderBuilder();
        instance.name = name;
        instance.version = version;
        instance.relativeFiles = relativeFiles;
        instance.storage = storage;
        instance.structureVersionBuilder = StructureVersionBuilder
                .create(instance.name, instance.relativeFiles, new JSONPrettyFormatter())
                .addProperty("version", instance.version);
        instance.structureUpdateBuilder = StructureUpdateBuilder.create(instance.name, instance.version, new JSONPrettyFormatter());
        instance.pathStorageConfig = new PathStorageConfig(instance.name, instance.version);
        return instance;
    }

    /**
     * Build sender.
     *
     * @return the sender
     */
    public ISender build(){
        return new Sender(this.pathStorageConfig, this.structureVersionBuilder, this.structureUpdateBuilder, this.storage);
    }

    /**
     * Format structure.
     *
     * @param formatter the formatter
     */
    public void formatStructure(IFormatter formatter){
        this.structureVersionBuilder.setFormatter(formatter);
    }

    /**
     * Path storage config.
     *
     * @param pathStorageConfig the path storage config
     */
    public void pathStorageConfig(IPathStorageConfig pathStorageConfig) {
        this.pathStorageConfig = pathStorageConfig;
    }
}
