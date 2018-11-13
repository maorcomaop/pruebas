/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

/**
 *
 * @author USER
 */
public class CommandGroupGPS {

    private int fkCommandGroup;
    private String fkGPS;
    private int fkCommandGroupType;
    private int fkStatusInGPS;
    private int numCommands;
    private int replies;
    private boolean needReset;
    private boolean groupComplete;

    public int getFkCommandGroup() {
        return fkCommandGroup;
    }

    public void setFkCommandGroup(int fkCommandGroup) {
        this.fkCommandGroup = fkCommandGroup;
    }

    public String getFkGPS() {
        return fkGPS;
    }

    public void setFkGPS(String fkGPS) {
        this.fkGPS = fkGPS;
    }

    public int getNumCommands() {
        return numCommands;
    }

    public void setNumCommands(int numCommands) {
        this.numCommands = numCommands;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public boolean isNeedReset() {
        return needReset;
    }

    public void setNeedReset(boolean needReset) {
        this.needReset = needReset;
    }

    public boolean isGroupComplete() {
        return groupComplete;
    }

    public void setGroupComplete(boolean groupComplete) {
        this.groupComplete = groupComplete;
    }

    public int getFkCommandGroupType() {
        return fkCommandGroupType;
    }

    public void setFkCommandGroupType(int fkCommandGroupType) {
        this.fkCommandGroupType = fkCommandGroupType;
    }

    public int getFkStatusInGPS() {
        return fkStatusInGPS;
    }

    public void setFkStatusInGPS(int fkStatusInGPS) {
        this.fkStatusInGPS = fkStatusInGPS;
    }

}
