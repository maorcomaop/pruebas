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
public class GpsEnvioComando {

    private Integer pkGpsCommandSend;
    private Integer fkCommandGroup;
    private int idInGroup;
    private Integer fkCommandKey;
    private String commandToBlock;
    private int commandNumerator;
    private String fkGps;
    private String plate;
    private String params;
    private String response;
    private boolean viewed;
    private boolean sent;
    private int tried;
    private boolean replied;
    private boolean cancelled;
    private Date forwardDate;
    private Date validdDate;
    private Date updatedDate;
    private Date creationdDate;

    public Integer getPkGpsCommandSend() {
        return pkGpsCommandSend;
    }

    public void setPkGpsCommandSend(Integer pkGpsCommandSend) {
        this.pkGpsCommandSend = pkGpsCommandSend;
    }

    public Integer getFkCommandGroup() {
        return fkCommandGroup;
    }

    public void setFkCommandGroup(Integer fkCommandGroup) {
        this.fkCommandGroup = fkCommandGroup;
    }

    public int getIdInGroup() {
        return idInGroup;
    }

    public void setIdInGroup(int idInGroup) {
        this.idInGroup = idInGroup;
    }

    public Integer getFkCommandKey() {
        return fkCommandKey;
    }

    public void setFkCommandKey(Integer fkCommandKey) {
        this.fkCommandKey = fkCommandKey;
    }

    public String getFkGps() {
        return fkGps;
    }

    public void setFkGps(String fkGps) {
        this.fkGps = fkGps;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public int getTried() {
        return tried;
    }

    public void setTried(int tried) {
        this.tried = tried;
    }

    public boolean isReplied() {
        return replied;
    }

    public void setReplied(boolean replied) {
        this.replied = replied;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Date getForwardDate() {
        return forwardDate;
    }

    public void setForwardDate(Date forwardDate) {
        this.forwardDate = forwardDate;
    }

    public Date getValiddDate() {
        return validdDate;
    }

    public void setValiddDate(Date validdDate) {
        this.validdDate = validdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getCreationdDate() {
        return creationdDate;
    }

    public void setCreationdDate(Date creationdDate) {
        this.creationdDate = creationdDate;
    }

    public String getCommandToBlock() {
        return commandToBlock;
    }

    public void setCommandToBlock(String commandToBlock) {
        this.commandToBlock = commandToBlock;
    }

    public int getCommandNumerator() {
        return commandNumerator;
    }

    public void setCommandNumerator(int commandNumerator) {
        this.commandNumerator = commandNumerator;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
