package com.appscharles.libs.updater.waiters;

import com.appscharles.libs.processer.exceptions.ProcesserException;
import com.appscharles.libs.processer.validators.PIDExistValidator;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.services.ThreadSleeper;

/**
 * The type Pid close waiter.
 */
public class PIDCloseWaiter implements IPIDCloseWaiter {

    private Long pID;

    /**
     * Instantiates a new Pid close waiter.
     *
     * @param pID the p id
     */
    public PIDCloseWaiter(Long pID) {
        this.pID = pID;
    }

    @Override
    public Boolean isClosed(Long timeout) throws UpdaterException {
        Long timeEnd = System.currentTimeMillis() + timeout;
        while (System.currentTimeMillis() <= timeEnd) {
            try {
                if (PIDExistValidator.isValid(this.pID) == false){
                    return true;
                }
            } catch (ProcesserException e) {
                throw new UpdaterException(e);
            }
            ThreadSleeper.sleep(200);
        }
        return false;
    }
}
