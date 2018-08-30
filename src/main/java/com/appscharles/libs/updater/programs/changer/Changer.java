package com.appscharles.libs.updater.programs.changer;

import com.appscharles.libs.fxer.exceptions.FxerException;
import com.appscharles.libs.fxer.factories.FXStageFactory;
import com.appscharles.libs.fxer.factories.IFXStageFactory;
import com.appscharles.libs.fxer.parsers.ArgsParser;
import com.appscharles.libs.fxer.stages.FXStage;
import com.appscharles.libs.logger.configurators.Log4j2ConsoleFileRoller;
import com.appscharles.libs.logger.services.LoggerConfigurator;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.google.devtools.common.options.OptionsParsingException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * The type Program changer.
 */
public class Changer {

    /**
     * The constant NAME.
     */
    public static final String NAME = "changer";

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
    public static void main(String[] args) throws OptionsParsingException {
        ChangerOptions options = ArgsParser.parse(args, ChangerOptions.class);
        File logsDir = (options.toDir.isEmpty()) ? new File(System.getProperty("user.home"), "com/appscharles/libs/updater/changer") : new File(options.toDir, "logs/changer");
        LoggerConfigurator.config(new Log4j2ConsoleFileRoller(Level.INFO).setLogsDir(logsDir));
        Logger logger = LogManager.getLogger(ChangerController.class);
        try {
            IFXStageFactory stageFactory = new FXStageFactory("/com/appscharles/libs/updater/programs/changer/ChangerView.fxml",
                    "com/appscharles/libs/updater/programs/changer/translations/Changer");
            stageFactory.setIcon("/com/appscharles/libs/updater/programs/changer/ChangerIcon.png");
            stageFactory.setController(new ChangerController(options));
            FXStage stage = stageFactory.create();
            stage.showAndWaitFX();
        } catch (FxerException e) {
            logger.error(e, e);
        }
    }
}
