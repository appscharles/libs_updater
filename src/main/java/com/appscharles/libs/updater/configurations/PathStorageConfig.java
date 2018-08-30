package com.appscharles.libs.updater.configurations;

/**
 * The type Path storage config.
 */
public class PathStorageConfig extends AbstractPathStorageConfig {


    /**
     * Instantiates a new Path storage config.
     *
     * @param name    the name
     * @param version the version
     */
    public PathStorageConfig(String name, String version) {
        super(name, version);
    }

    @Override
    public String getPathVersion() {
        return "/" + this.name + "/" + this.version;
    }

    @Override
    public String getPathFiles() {
        return "/" + this.name + "/" + this.version + "/files";
    }

    @Override
    public String getPathName() {
        return "/" + this.name;
    }

    @Override
    public String getStructureFileName() {
        return "structure.json";
    }

    @Override
    public String getCurrentUpdateFileName() {
        return "update.json";
    }

    @Override
    public String getPathCurrentUpdate() {
        return "/" + this.name + "/" + getCurrentUpdateFileName();
    }
}
