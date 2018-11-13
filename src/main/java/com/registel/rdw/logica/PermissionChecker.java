/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.logica;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author USER
 */
public class PermissionChecker {

    private Integer profileId;
    private Integer maxLevel;
    private String profileName;

    private List<AccesoAccesoPerfil> access;
    private List<AccesoAccesoPerfil> accessProfile;

    public PermissionChecker(List<AccesoAccesoPerfil> access, Integer profileId, String profileName) {
        this.access = access;
        this.profileId = profileId;
        this.profileName = profileName;
    }

    public String print(String enter) {
        return enter;
    }

    public boolean check(List<String> thorns) {

        // super user profile allows all access
        if (profileId == 1) {
            return true;
        }

        List<AccesoAccesoPerfil> accessTemp = new ArrayList<>();
        boolean permission = false;
        if (access == null) {
            return false;
        }
        int i = 0;
        for (String t : thorns) {
            if (i == 0) {
                accessTemp = access;
            }
            permission = false;
            for (AccesoAccesoPerfil aap : accessTemp) {
                if (aap.getAliasAcceso().equalsIgnoreCase(t) && profileId == aap.getFkPerfil()) {
                    permission = true;
                    accessTemp = aap.getSubGrupos();
                    break;
                }
            }
            i++;
            if (!permission) {
                break;
            }
        }
        return permission;
    }

    public boolean check(int accessId) {

        // super user profile allows all access
        if (profileId == 1) {
            return true;
        }

        boolean permission = false;

        if (accessProfile == null) {
            return false;
        }

        for (AccesoAccesoPerfil aap : accessProfile) {
            if (aap.getPkAcceso() == accessId) {
                permission = true;
                break;
            }
        }
        return permission;
    }

    public List<AccesoAccesoPerfil> getAccess() {
        return access;
    }

    public void setAccess(List<AccesoAccesoPerfil> access) {
        this.access = access;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    public List<AccesoAccesoPerfil> getAccessProfile() {
        return accessProfile;
    }

    public void setAccessProfile(List<AccesoAccesoPerfil> accessProfile) {
        this.accessProfile = accessProfile;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

}
