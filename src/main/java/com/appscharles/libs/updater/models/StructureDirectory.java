package com.appscharles.libs.updater.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Structure directory.
 */
public class StructureDirectory {

    private String name;

    private Map<String, String> properties;

    private List<Map<String, String>> files;

    /**
     * Instantiates a new Structure directory.
     */
    public StructureDirectory() {
    }

    /**
     * Instantiates a new Structure directory.
     *
     * @param name the name
     */
    public StructureDirectory(String name) {
        this.name = name;
        this.properties = new HashMap<>();
        this.files = new ArrayList<>();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets properties.
     *
     * @return the properties
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Gets files.
     *
     * @return the files
     */
    public List<Map<String, String>> getFiles() {
        return files;
    }

}
