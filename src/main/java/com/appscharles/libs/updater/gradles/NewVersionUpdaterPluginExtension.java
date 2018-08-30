package com.appscharles.libs.updater.gradles;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 06.07.2018
 * Time: 20:08
 * Project name: updater
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class NewVersionUpdaterPluginExtension {

    private String fromDir = "";

    private String storageDir = "";

    private Integer portFTP = 21;

    private String hostFTP;

    private String nameFTP;

    private String passwordFTP;

    private Boolean passiveModeFTP = true;

    /**
     * Getter for property 'fromDir'.
     *
     * @return Value for property 'fromDir'.
     */
    public String getFromDir() {
        return fromDir;
    }

    /**
     * Setter for property 'fromDir'.
     *
     * @param fromDir Value to set for property 'fromDir'.
     */
    public void setFromDir(String fromDir) {
        this.fromDir = fromDir;
    }

    /**
     * Getter for property 'storageDir'.
     *
     * @return Value for property 'storageDir'.
     */
    public String getStorageDir() {
        return storageDir;
    }

    /**
     * Setter for property 'storageDir'.
     *
     * @param storageDir Value to set for property 'storageDir'.
     */
    public void setStorageDir(String storageDir) {
        this.storageDir = storageDir;
    }

    /**
     * Getter for property 'portFTP'.
     *
     * @return Value for property 'portFTP'.
     */
    public Integer getPortFTP() {
        return portFTP;
    }

    /**
     * Setter for property 'portFTP'.
     *
     * @param portFTP Value to set for property 'portFTP'.
     */
    public void setPortFTP(Integer portFTP) {
        this.portFTP = portFTP;
    }

    /**
     * Getter for property 'hostFTP'.
     *
     * @return Value for property 'hostFTP'.
     */
    public String getHostFTP() {
        return hostFTP;
    }

    /**
     * Setter for property 'hostFTP'.
     *
     * @param hostFTP Value to set for property 'hostFTP'.
     */
    public void setHostFTP(String hostFTP) {
        this.hostFTP = hostFTP;
    }

    /**
     * Getter for property 'nameFTP'.
     *
     * @return Value for property 'nameFTP'.
     */
    public String getNameFTP() {
        return nameFTP;
    }

    /**
     * Setter for property 'nameFTP'.
     *
     * @param nameFTP Value to set for property 'nameFTP'.
     */
    public void setNameFTP(String nameFTP) {
        this.nameFTP = nameFTP;
    }

    /**
     * Getter for property 'passwordFTP'.
     *
     * @return Value for property 'passwordFTP'.
     */
    public String getPasswordFTP() {
        return passwordFTP;
    }

    /**
     * Setter for property 'passwordFTP'.
     *
     * @param passwordFTP Value to set for property 'passwordFTP'.
     */
    public void setPasswordFTP(String passwordFTP) {
        this.passwordFTP = passwordFTP;
    }

    /**
     * Getter for property 'passiveModeFTP'.
     *
     * @return Value for property 'passiveModeFTP'.
     */
    public Boolean getPassiveModeFTP() {
        return passiveModeFTP;
    }

    /**
     * Setter for property 'passiveModeFTP'.
     *
     * @param passiveModeFTP Value to set for property 'passiveModeFTP'.
     */
    public void setPassiveModeFTP(Boolean passiveModeFTP) {
        this.passiveModeFTP = passiveModeFTP;
    }
}
