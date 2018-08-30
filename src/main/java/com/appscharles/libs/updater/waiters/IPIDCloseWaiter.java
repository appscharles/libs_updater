package com.appscharles.libs.updater.waiters;

import com.appscharles.libs.updater.exceptions.UpdaterException;

/**
 * The interface Writable waiter.
 */
public interface IPIDCloseWaiter {

    /**
     * Is writable boolean.
     *
     * @param timeout the timeout
     * @return the boolean
     * @throws UpdaterException the updater exception
     */
    Boolean isClosed(Long timeout) throws UpdaterException;
}
