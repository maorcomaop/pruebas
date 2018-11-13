/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.Date;
import java.util.List;

/**
 *
 * @author USER
 */
public class GpsGrupoComando {

    private Integer fkGpsCommandSend;
    private Integer fkGpsCommandPackage;
    private Integer pkGpsCommandGroup;
    private int numCommands;
    private Integer fkCommandGroupType;
    private String fkGPS;
    private String plate;

    private Integer fkTableType1;
    private Integer fkTable1;
    private Integer fkTableType2;
    private Integer fkTable2;
    
    private Integer numPostponed;
    private Integer maxPostponed;

    private Integer fkStateInGps;

    private boolean needReset;
    private boolean status;
    private Date updateDate;
    private Date creationDate;

    private List<GpsEnvioComando> gpsCommandSendList;

    public Integer getFkGpsCommandSend() {
        return fkGpsCommandSend;
    }

    public void setFkGpsCommandSend(Integer fkGpsCommandSend) {
        this.fkGpsCommandSend = fkGpsCommandSend;
    }

    public Integer getPkGpsCommandGroup() {
        return pkGpsCommandGroup;
    }

    public void setPkGpsCommandGroup(Integer pkGpsCommandGroup) {
        this.pkGpsCommandGroup = pkGpsCommandGroup;
    }

    public int getNumCommands() {
        return numCommands;
    }

    public void setNumCommands(int numCommands) {
        this.numCommands = numCommands;
    }

    public Integer getFkCommandGroupType() {
        return fkCommandGroupType;
    }

    public void setFkCommandGroupType(Integer fkCommandGroupType) {
        this.fkCommandGroupType = fkCommandGroupType;
    }

    public String getFkGPS() {
        return fkGPS;
    }

    public void setFkGPS(String fkGPS) {
        this.fkGPS = fkGPS;
    }

    public Integer getFkTableType1() {
        return fkTableType1;
    }

    public void setFkTableType1(Integer fkTableType1) {
        this.fkTableType1 = fkTableType1;
    }

    public Integer getFkTable1() {
        return fkTable1;
    }

    public void setFkTable1(Integer fkTable1) {
        this.fkTable1 = fkTable1;
    }

    public Integer getFkTableType2() {
        return fkTableType2;
    }

    public void setFkTableType2(Integer fkTableType2) {
        this.fkTableType2 = fkTableType2;
    }

    public Integer getFkTable2() {
        return fkTable2;
    }

    public void setFkTable2(Integer fkTable2) {
        this.fkTable2 = fkTable2;
    }

    public Integer getFkStateInGps() {
        return fkStateInGps;
    }

    public void setFkStateInGps(Integer fkStateInGps) {
        this.fkStateInGps = fkStateInGps;
    }

    public boolean isNeedReset() {
        return needReset;
    }

    public void setNeedReset(boolean needReset) {
        this.needReset = needReset;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<GpsEnvioComando> getGpsCommandSendList() {
        return gpsCommandSendList;
    }

    public void setGpsCommandSendList(List<GpsEnvioComando> gpsCommandSendList) {
        this.gpsCommandSendList = gpsCommandSendList;
    }

    public Integer getFkGpsCommandPackage() {
        return fkGpsCommandPackage;
    }

    public void setFkGpsCommandPackage(Integer fkGpsCommandPackage) {
        this.fkGpsCommandPackage = fkGpsCommandPackage;
    }

    public Integer getNumPostponed() {
        return numPostponed;
    }

    public void setNumPostponed(Integer numPostponed) {
        this.numPostponed = numPostponed;
    }

    public Integer getMaxPostponed() {
        return maxPostponed;
    }

    public void setMaxPostponed(Integer maxPostponed) {
        this.maxPostponed = maxPostponed;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

}
