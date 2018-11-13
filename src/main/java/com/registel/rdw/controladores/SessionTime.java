/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.controladores;

import com.registel.rdw.logica.UserSession;
import com.registel.rdw.logica.Usuario;
import com.registel.rdw.utils.Restriction;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class SessionTime {
    
    private static ArrayList<UserSession> lst_session;
    
    public SessionTime() {
        if (lst_session == null) {
            lst_session = new ArrayList<UserSession>();
        }
    }
    
    public static int getSessionIndex(String user, String sidempresa) {
        
        if (lst_session == null || lst_session.size() == 0)
            return -1;
        
        user = user.toLowerCase();
        int idempresa = Restriction.getNumber(sidempresa);
        
        for (int i = 0; i < lst_session.size(); i++) {
            UserSession u = lst_session.get(i);
            String userl   = u.getUser().toLowerCase();
            int idempresal = u.getIdEmpresa();
            
            if (userl.compareTo(user) == 0 &&
                idempresal == idempresa) {
                return i;
            }
        }
        return -1;
    }
    
    public static void setTimeSession(String user, String idempresa) {
        int userIndex = getSessionIndex(user, idempresa);
        long sessionTime = new Date().getTime();
        //long sessionTime1 = Calendar.getInstance().getTimeInMillis();        
        
        if (userIndex >= 0) {
            UserSession u = lst_session.get(userIndex);
            u.setTime(sessionTime);
        } else {
            UserSession u = new UserSession();
            u.setUser(user);
            u.setTime(sessionTime);
            u.setIdEmpresa(Restriction.getNumber(idempresa));
            lst_session.add(u);
        }
        //System.out.println("* " + user +" "+ sessionTime);
    }
    
    public static boolean expireTimeSession(String user, String idempresa) {
        int userIndex = getSessionIndex(user, idempresa);
        long currentTime = new Date().getTime();
        currentTime /= 1000;
        
        if (userIndex >= 0) {
            UserSession u = lst_session.get(userIndex);
            long sessionTime = u.getTime();
            sessionTime /= 1000;
            //System.out.println(user +" "+ currentTime +" "+ sessionTime +" "+ (currentTime - sessionTime));
            if (Math.abs(currentTime - sessionTime) >= Expire.TIME_EXPIRE) {
                return true;
            }
            return false;
        }
        return true;
    }
    
    public static int timeToExpire(String user, String idempresa) {
        int userIndex = getSessionIndex(user, idempresa);
        long currentTime = new Date().getTime();
        currentTime /= 1000;
        
        if (userIndex >= 0) {
            UserSession u = lst_session.get(userIndex);
            long sessionTime = u.getTime();
            sessionTime /= 1000;
            long elapseTime = Math.abs(currentTime - sessionTime);
            long remainingTime = Expire.TIME_EXPIRE - elapseTime;
            //System.out.println("> " + elapseTime +" "+ remainingTime);
            if (remainingTime <= 60) return 1;
            if (remainingTime <= 120) return 2;
            if (remainingTime <= 180) return 3;
            if (remainingTime <= 240) return 4;
        }
        return 0;
    }
    
    public static void setTimeSession(Usuario user) {
        if (user != null) {
            setTimeSession(user.getLogin(), "" + user.getIdempresa());
        }
    }
    
    public static boolean expireTimeSession(Usuario user) {
        if (user != null) {
            return expireTimeSession(user.getLogin(), "" + user.getIdempresa());
        } 
        return true;
    }
    
    public static int timeToExpire(Usuario user) {
        if (user != null) {
            return timeToExpire(user.getLogin(), "" + user.getIdempresa());
        }
        return 0;
    }
}
