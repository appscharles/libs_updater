package com.appscharles.libs.updater.changers;

import java.io.File;

/**
 * The type Abstract file changer.
 */
public abstract class AbstractFileChanger implements IFileChanger{

    /**
     * The From dir.
     */
    protected File fromDir;

    /**
     * The To dir.
     */
    protected File toDir;

    /**
     * Instantiates a new Abstract file changer.
     *
     * @param fromDir the from dir
     * @param toDir   the to dir
     */
    public AbstractFileChanger(File fromDir, File toDir) {
        this.fromDir = fromDir;
        this.toDir = toDir;
    }
}
