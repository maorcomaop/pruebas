/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.Date;

/**
 *
 * @author USER
 */
public class CommandGPS {

    private int id;
    private int fkGpsSetCommand;
    private int fkCommandGroup;
    private String commandKey;
    private String commandToBlock;
    private int commandNumerator;
    private int idInGroup;
    private String fkGPS;
    private String plate;
    private String parameters;
    private String response;
    private boolean viewed;
    private boolean sent;
    private boolean replied;
    private int tried;
    private Date updateTimedate;
    private Date forwardTimedate;
    private Date insertTimedate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommandKey() {
        return commandKey;
    }

    public void setCommandKey(String commandKey) {
        this.commandKey = commandKey;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isReplied() {
        return replied;
    }

    public void setReplied(boolean replied) {
        this.replied = replied;
    }

    public String getFkGPS() {
        return fkGPS;
    }

    public void setFkGPS(String fkGPS) {
        this.fkGPS = fkGPS;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public int getTried() {
        return tried;
    }

    public void setTried(int tried) {
        this.tried = tried;
    }

    public Date getUpdateTimedate() {
        return updateTimedate;
    }

    public void setUpdateTimedate(Date updateTimedate) {
        this.updateTimedate = updateTimedate;
    }

    public Date getInsertTimedate() {
        return insertTimedate;
    }

    public void setInsertTimedate(Date insertTimedate) {
        this.insertTimedate = insertTimedate;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getFkCommandGroup() {
        return fkCommandGroup;
    }

    public void setFkCommandGroup(int fkCommandGroup) {
        this.fkCommandGroup = fkCommandGroup;
    }

    public int getCommandNumerator() {
        return commandNumerator;
    }

    public void setCommandNumerator(int commandNumerator) {
        this.commandNumerator = commandNumerator;
    }

    public String getCommandToBlock() {
        return commandToBlock;
    }

    public void setCommandToBlock(String commandToBlock) {
        this.commandToBlock = commandToBlock;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public Date getForwardTimedate() {
        return forwardTimedate;
    }

    public void setForwardTimedate(Date forwardTimedate) {
        this.forwardTimedate = forwardTimedate;
    }

    public int getIdInGroup() {
        return idInGroup;
    }

    public void setIdInGroup(int idInGroup) {
        this.idInGroup = idInGroup;
    }

    public int getFkGpsSetCommand() {
        return fkGpsSetCommand;
    }

    public void setFkGpsSetCommand(int fkGpsSetCommand) {
        this.fkGpsSetCommand = fkGpsSetCommand;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
