package com.appscharles.libs.updater.models;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Structure update.
 */
public class StructureUpdate {

    private String name;

    private String version;

    private Map<String, String> properties;

    /**
     * Instantiates a new Structure update.
     */
    public StructureUpdate() {

    }

    /**
     * Instantiates a new Structure update.
     *
     * @param name    the name
     * @param version the version
     * @param url     the url
     */
    public StructureUpdate(String name, String version) {
        this.name = name;
        this.version = version;
        this.properties = new HashMap<>();
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
     * Gets version.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
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
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(String version) {
        this.version = version;
    }


    /**
     * Sets properties.
     *
     * @param properties the properties
     */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
