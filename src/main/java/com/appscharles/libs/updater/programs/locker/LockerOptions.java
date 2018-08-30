package com.appscharles.libs.updater.programs.locker;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/**
 * The type Changer options.
 */
public class LockerOptions extends OptionsBase {

    /**
     * The From dir.
     */
    @Option(name = "filePath",
            abbrev = 'f',
            help = "Path file to lock.",
            defaultValue = "")
    public String filePath;

    /**
     * The From dir.
     */
    @Option(name = "time",
            abbrev = 't',
            help = "Time in milliseconds for lock file.",
            defaultValue = "")
    public String time;
}
