package com.appscharles.libs.updater.programs.changer;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/**
 * The type Changer options.
 */
public class ChangerOptions extends OptionsBase {

    /**
     * The From dir.
     */
    @Option(name = "fromDir",
            abbrev = 'f',
            help = "Copy files from dir.",
            defaultValue = "")
    public String fromDir;

    /**
     * The To dir.
     */
    @Option(name = "toDir",
            abbrev = 't',
            help = "Copy files to dir.",
            defaultValue = "")
    public String toDir;

    /**
     * The Timeout writable waiter.
     */
    @Option(name = "timeoutPIDCloseWaiter",
            abbrev = 'w',
            help = "Timeout wait for close PID.",
            defaultValue = "60000")
    public String timeoutPIDCloseWaiter;

    /**
     * The Caller pid.
     */
    @Option(name = "callerPID",
            abbrev = 'p',
            help = "Caller PID to kill by changer program.",
            defaultValue = "")
    public String callerPID;

    @Option(name = "callbackCommand",
            abbrev = 'c',
            help = "Callback run command after changer process.",
            defaultValue = "")
    public String callbackCommand;

    @Option(name = "updatedArgsWithCallbackCommand",
            abbrev = 'u',
            help = "Add argument --updated to callback command.",
            defaultValue = "true")
    public String updatedArgsWithCallbackCommand;

    @Option(name = "test",
            abbrev = 's',
            help = "For tests of package.",
            defaultValue = "false")
    public String test;
}
