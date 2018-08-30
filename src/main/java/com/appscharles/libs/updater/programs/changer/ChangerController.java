package com.appscharles.libs.updater.programs.changer;

import com.appscharles.libs.dialoger.factories.ExceptionDialogFactory;
import com.appscharles.libs.fxer.controllers.AbstractStageControllerFX;
import com.appscharles.libs.ioer.models.StatusProgress;
import com.appscharles.libs.updater.changers.FileChanger;
import com.appscharles.libs.updater.changers.IFileChanger;
import com.appscharles.libs.updater.exceptions.UpdaterException;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 03.07.2018
 * Time: 15:53
 * Project name: updater
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class ChangerController extends AbstractStageControllerFX {

    final static Logger logger = LogManager.getLogger(ChangerController.class);

    private ChangerOptions options;

    private ResourceBundle resourceBundle;

    @FXML
    private Label filePath;

    @FXML
    private ProgressBar progressBar;

    private StatusProgress statusProgress;

    public ChangerController(ChangerOptions options) {
        this.options = options;
        this.statusProgress = new StatusProgress();
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.progressBar.progressProperty().bind(this.statusProgress.progressProperty());
        this.statusProgress.statusProperty().addListener((args, oldVal, newVal)->{
            PlatformImpl.runAndWait(()->{
                this.filePath.setText(newVal);
            });
        });
        Platform.runLater(() -> {
            this.fXStage.setTitle(this.resourceBundle.getString("stage.title"));
        });
    }

    @Override
    public void onShown(WindowEvent event) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                checkOptions();
                IFileChanger fileChanger = new FileChanger(
                        new File(this.options.fromDir),
                        new File(this.options.toDir),
                        new Long(this.options.timeoutPIDCloseWaiter),
                        new Long(this.options.callerPID),
                        this.statusProgress);
                fileChanger.change();
                String command = this.options.callbackCommand;
                if (command.isEmpty() == false) {
                    if (Boolean.valueOf(this.options.updatedArgsWithCallbackCommand)) {
                        command += " --updated=true";
                    }
                    Runtime.getRuntime().exec(command);
                }
            } catch (UpdaterException | IOException e) {
                logger.error(e, e);
                try {
                        PlatformImpl.runAndWait(() -> {
                            Alert alert = ExceptionDialogFactory.create(
                                    this.resourceBundle.getString("exception_dialog.title"),
                                    this.resourceBundle.getString("exception_dialog.content_text"),
                                    e).setIconStageResource("/com/appscharles/libs/updater/programs/changer/ChangerIcon.png")
                                    .build();
                            if (Boolean.valueOf(this.options.test) == false) {
                                alert.showAndWait();
                            } else {
                                alert.show();
                            }
                        });
                } catch (Exception e1) {
                    logger.error(e1, e1);
                }
            }
            System.exit(0);
        });
        executor.shutdown();
    }

    private void checkOptions() throws UpdaterException {
        if (this.options.fromDir.isEmpty()) {
            throw new UpdaterException("Option 'fromDir' is empty.");
        } else if (this.options.toDir.isEmpty()) {
            throw new UpdaterException("Option 'toDir' is empty.");
        } else if (this.options.timeoutPIDCloseWaiter.isEmpty()) {
            throw new UpdaterException("Option 'timeoutPIDCloseWaiter' is empty.");
        } else if (this.options.callerPID.isEmpty()) {
            throw new UpdaterException("Option 'callerPID' is empty.");
        }
    }
}
