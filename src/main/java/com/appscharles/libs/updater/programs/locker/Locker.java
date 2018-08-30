package com.appscharles.libs.updater.programs.locker;

import com.appscharles.libs.fxer.parsers.ArgsParser;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.appscharles.libs.updater.services.ThreadSleeper;
import com.google.devtools.common.options.OptionsParsingException;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * The type Program changer.
 */
public class Locker {

    /**
     * The constant NAME.
     */
    public static final String NAME = "locker";

    /**
     * The constant VERSION.
     */
    public static final String VERSION = "1.0.0.0-dev0";

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws UpdaterException        the updater exception
     * @throws OptionsParsingException the options parsing exception
     */
    public static void main(String[] args) throws UpdaterException, OptionsParsingException, IOException {
        LockerOptions options = ArgsParser.parse(args, LockerOptions.class);
        if (options.filePath.isEmpty()){
            throw new UpdaterException("Option 'filePath' is empty.");
        } else  if (options.time.isEmpty()){
            throw new UpdaterException("Option 'time' is empty.");
        }
        FileChannel channel = new RandomAccessFile(new File(options.filePath), "rw").getChannel();
        FileLock fileLock = channel.lock();
        ThreadSleeper.sleep(new Long(options.time));
    }
}
