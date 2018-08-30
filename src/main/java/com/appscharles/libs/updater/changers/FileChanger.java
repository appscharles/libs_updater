package com.appscharles.libs.updater.changers;

import com.appscharles.libs.ioer.checkers.InDirPermissionChecker;
import com.appscharles.libs.ioer.models.StatusProgress;
import com.appscharles.libs.ioer.services.DirCopier;
import com.appscharles.libs.processer.exceptions.ProcesserException;
import com.appscharles.libs.processer.managers.IKillManager;
import com.appscharles.libs.processer.managers.WinKillManager;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.waiters.IPIDCloseWaiter;
import com.appscharles.libs.updater.waiters.PIDCloseWaiter;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type File changer.
 */
public class FileChanger extends AbstractFileChanger {

    private Long callerPID;

    private Long timeoutPIDCloseWaiter;

    private IPIDCloseWaiter iPIDCloseWaiter;

    private IKillManager killManager;

    private StatusProgress statusProgress;

    public FileChanger(File fromDir,  File toDir,Long timeoutPIDCloseWaiter, Long callerPID) {
        this(fromDir, toDir, timeoutPIDCloseWaiter, callerPID, null);
    }

    /**
     * Instantiates a new File changer.
     *
     * @param fromDir               the from dir
     * @param toDir                 the to dir
     * @param timeoutPIDCloseWaiter the timeout pid close waiter
     * @param callerPID             the caller pid
     */
    public FileChanger(File fromDir,  File toDir,Long timeoutPIDCloseWaiter, Long callerPID, StatusProgress statusProgress) {
        super(fromDir, toDir);
        this.callerPID = callerPID;
        this.timeoutPIDCloseWaiter = timeoutPIDCloseWaiter;
        this.statusProgress = statusProgress;
        this.iPIDCloseWaiter = new PIDCloseWaiter(callerPID);
        this.killManager = new WinKillManager();
    }

    public void change() throws UpdaterException {
        try {
            if (this.iPIDCloseWaiter.isClosed(this.timeoutPIDCloseWaiter) == false){
                this.killManager.killWithChild(this.callerPID);
            }
            InDirPermissionChecker.check(this.toDir, new ArrayList<>(Arrays.asList(new File(this.toDir, "logs"))));
            DirCopier.copy(this.fromDir, this.toDir, StandardCopyOption.REPLACE_EXISTING, this.statusProgress);
        } catch (IOException | ProcesserException e) {
            throw new UpdaterException(e);
        }
    }
}
