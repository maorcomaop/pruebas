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
public class MovilGPSChat {

    private Integer pkGPSChat;
    private Integer pkVehiculo;
    private String plate;
    private String internalNum;
    private int fkGPSMessageType;
    private int fkOrigin;
    private String fkGPS;
    private String message;
    private boolean edition;
    private boolean viewed;
    private Integer fkGPSCommandSend;
    private Date creationDate;
    private Integer fkCommandGroup;
    private boolean processed;
    private boolean sent;
    private boolean replied;
    private Date forwardDate;
    private int tries ;
    private int numNotViewed ;
    private int fkTableGPS;
    private int numNews;

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public Integer getPkVehiculo() {
        return pkVehiculo;
    }

    public void setPkVehiculo(Integer pkVehiculo) {
        this.pkVehiculo = pkVehiculo;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getInternalNum() {
        return internalNum;
    }

    public void setInternalNum(String internalNum) {
        this.internalNum = internalNum;
    }

    public int getFkGPSMessageType() {
        return fkGPSMessageType;
    }

    public void setFkGPSMessageType(int fkGPSMessageType) {
        this.fkGPSMessageType = fkGPSMessageType;
    }

    public int getFkOrigin() {
        return fkOrigin;
    }

    public void setFkOrigin(int fkOrigin) {
        this.fkOrigin = fkOrigin;
    }

    public String getFkGPS() {
        return fkGPS;
    }

    public void setFkGPS(String fkGPS) {
        this.fkGPS = fkGPS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String Message) {
        this.message = Message;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public Integer getFkGPSCommandSend() {
        return fkGPSCommandSend;
    }

    public void setFkGPSCommandSend(Integer fkGPSCommandSend) {
        this.fkGPSCommandSend = fkGPSCommandSend;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getFkCommandGroup() {
        return fkCommandGroup;
    }

    public void setFkCommandGroup(Integer fkCommandGroup) {
        this.fkCommandGroup = fkCommandGroup;
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

    public Integer getPkGPSChat() {
        return pkGPSChat;
    }

    public void setPkGPSChat(Integer pkGPSChat) {
        this.pkGPSChat = pkGPSChat;
    }

    public Date getForwardDate() {
        return forwardDate;
    }

    public void setForwardDate(Date forwardDate) {
        this.forwardDate = forwardDate;
    }

    public boolean isEdition() {
        return edition;
    }

    public void setEdition(boolean edition) {
        this.edition = edition;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public int getNumNotViewed() {
        return numNotViewed;
    }

    public void setNumNotViewed(int numNotViewed) {
        this.numNotViewed = numNotViewed;
    }

    public int getFkTableGPS() {
        return fkTableGPS;
    }

    public void setFkTableGPS(int fkTableGPS) {
        this.fkTableGPS = fkTableGPS;
    }

    public int getNumNews() {
        return numNews;
    }

    public void setNumNews(int numNews) {
        this.numNews = numNews;
    }

}
