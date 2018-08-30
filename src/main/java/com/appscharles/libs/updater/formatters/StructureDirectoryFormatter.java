package com.appscharles.libs.updater.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.models.StructureDirectory;

import java.io.IOException;

/**
 * The type Structure directory formatter.
 */
public class StructureDirectoryFormatter implements IFormatter {

    @Override
    public StructureDirectory format(Object object) throws UpdaterException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            StructureDirectory structureDirectory = mapper.readValue(object.toString(), StructureDirectory.class);
            return structureDirectory;
        } catch (IOException e) {
            throw new UpdaterException(e);
        }
    }
}
