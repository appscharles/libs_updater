package com.appscharles.libs.updater.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.models.StructureUpdate;

import java.io.IOException;

/**
 * The type Structure update formatter.
 */
public class StructureUpdateFormatter implements IFormatter {


    @Override
    public StructureUpdate format(Object object) throws UpdaterException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            StructureUpdate structureUpdate = mapper.readValue(object.toString(), StructureUpdate.class);
            return structureUpdate;
        } catch (IOException e) {
            throw new UpdaterException(e);
        }
    }
}
