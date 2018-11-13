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
public class GpsPaqueteComando {

    private Integer pkGpsCommandPackage;
    private Integer fkGPSCommandPackageType;

    private String code;
    private int tries;
    private int fkUsuario;

    private int stateInGPSs;
    private boolean status;
    private Date updateDate;
    private Date creationDate;

    private boolean isMainResetEnd;

    private List<GpsGrupoComando> gpsCommandGroupList;

    public Integer getPkGpsCommandPackage() {
        return pkGpsCommandPackage;
    }

    public void setPkGpsCommandPackage(Integer pkGpsCommandPackage) {
        this.pkGpsCommandPackage = pkGpsCommandPackage;
    }

    public Integer getFkGPSCommandPackageType() {
        return fkGPSCommandPackageType;
    }

    public void setFkGPSCommandPackageType(Integer fkGPSCommandPackageType) {
        this.fkGPSCommandPackageType = fkGPSCommandPackageType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public int getStateInGPSs() {
        return stateInGPSs;
    }

    public void setStateInGPSs(int stateInGPSs) {
        this.stateInGPSs = stateInGPSs;
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

    public List<GpsGrupoComando> getGpsCommandGroupList() {
        return gpsCommandGroupList;
    }

    public void setGpsCommandGroupList(List<GpsGrupoComando> gpsCommandGroupList) {
        this.gpsCommandGroupList = gpsCommandGroupList;
    }

    public boolean isIsMainResetEnd() {
        return isMainResetEnd;
    }

    public void setIsMainResetEnd(boolean isMainResetEnd) {
        this.isMainResetEnd = isMainResetEnd;
    }

    public int getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(int fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

}
