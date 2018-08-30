package com.appscharles.libs.updater.builders;

import java.io.File;

/**
 * The type Changer command builder.
 */
public class ChangerCommandBuilder {

    private File changerJarFile;

    private File fromDir;

    private File toDir;

    private Long mainPID;

    private Long timeoutMainPIDWillKill;

    private String callbackCommand;

    private Boolean updatedArgsWithCallbackCommand;

    private ChangerCommandBuilder(){

    }

    /**
     * Create changer command builder.
     *
     * @param changerJarFile the changer jar file
     * @param fromDir        the from dir
     * @param toDir          the to dir
     * @param mainPID        the main pid
     * @return the changer command builder
     */
    public static ChangerCommandBuilder create(File changerJarFile, File fromDir, File toDir, Long mainPID) {
        ChangerCommandBuilder instance = new ChangerCommandBuilder();
        instance.changerJarFile = changerJarFile;
        instance.fromDir = fromDir;
        instance.toDir = toDir;
        instance.mainPID = mainPID;
        return instance;
    }

    /**
     * Build string.
     *
     * @return the string
     */
    public String build(){
        String command = "java -jar " + this.changerJarFile.getPath()+
                " --callerPID="+ this.mainPID+" --fromDir=" +  this.fromDir.getPath() + " --toDir=" +  this.toDir.getPath();
        if ( this.timeoutMainPIDWillKill != null){
            command +=  " --timeoutPIDCloseWaiter=" +  this.timeoutMainPIDWillKill;
        }
        if (callbackCommand != null){
            command +=  " --callbackCommand=" +  this.callbackCommand;
            if (updatedArgsWithCallbackCommand != null){
                command +=  " --updatedArgsWithCallbackCommand=" +  this.updatedArgsWithCallbackCommand;
            }
        }
        return command;
    }

    /**
     * Sets timeout main pid will kill.
     *
     * @param timeoutMainPIDWillKill the timeout main pid will kill
     * @return the timeout main pid will kill
     */
    public ChangerCommandBuilder setTimeoutMainPIDWillKill(Long timeoutMainPIDWillKill) {
        this.timeoutMainPIDWillKill = timeoutMainPIDWillKill;
        return this;
    }

    /**
     * Sets callback command.
     *
     * @param callbackCommand the callback command
     * @return the callback command
     */
    public ChangerCommandBuilder setCallbackCommand(String callbackCommand) {
        this.callbackCommand = callbackCommand;
        return this;
    }

    /**
     * Sets updated args with callback command.
     *
     * @param updatedArgsWithCallbackCommand the updated args with callback command
     * @return the updated args with callback command
     */
    public ChangerCommandBuilder setUpdatedArgsWithCallbackCommand(Boolean updatedArgsWithCallbackCommand) {
        this.updatedArgsWithCallbackCommand = updatedArgsWithCallbackCommand;
        return this;
    }
}
