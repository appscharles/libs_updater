package com.appscharles.libs.updater.services;

import com.appscharles.libs.updater.exceptions.UpdaterException;

/**
 * The type Thread sleeper.
 */
public class ThreadSleeper {

    /**
     * Sleep.
     *
     * @param millis the millis
     * @throws UpdaterException the updater exception
     */
    public static void sleep(long millis) throws UpdaterException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new UpdaterException(e);
        }
    }
}
