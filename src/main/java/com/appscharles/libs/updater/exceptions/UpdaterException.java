package com.appscharles.libs.updater.exceptions;

/**
 * The type Updater exception.
 */
public class UpdaterException extends Exception {
    /**
     * The Serial version uid.
     */
    static final long serialVersionUID = 7818375828146020432L;

    /**
     * Instantiates a new Updater exception.
     */
    public UpdaterException() {
        super();
    }

    /**
     * Instantiates a new Updater exception.
     *
     * @param message the message
     */
    public UpdaterException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Updater exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UpdaterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Updater exception.
     *
     * @param cause the cause
     */
    public UpdaterException(Throwable cause) {
        super(cause);
    }
}

