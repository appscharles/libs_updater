package com.appscharles.libs.updater.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.appscharles.libs.updater.exceptions.UpdaterException;

import java.io.IOException;

/**
 * The type Json pretty formatter.
 */
public class JSONPrettyFormatter implements IFormatter {

    @Override
    public String format(Object object) throws UpdaterException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            throw new UpdaterException(e);
        }
    }
}
