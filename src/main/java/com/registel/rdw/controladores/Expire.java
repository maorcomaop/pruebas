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
public class Expire {
    
    public static final int MINUTES     = 480;
    public static final int TIME_EXPIRE = MINUTES * 60;
    public static final int TIME_WAIT   =  60;           // 1 minuto
    
    public static long time = 0;
    
    /*
     * Valida si tiempo de sesion activa ha expirado.
     */
    public static boolean check (long sessionTime) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        currentTime /= 1000; sessionTime /= 1000;
        
        if (Math.abs(currentTime - sessionTime) >= TIME_EXPIRE)
            return true;
        else
            return false;
    }
    
    /*
     * Valida si tiempo de espera ha pasado.
     */
    public static boolean checkWait () {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        currentTime /= 1000;
        
        if (Math.abs(currentTime - time) >= TIME_WAIT)
            return true;
        else
            return false;
    }
    
    /*
     * Establece nuevo tiempo de referencia.
     */
    public static void setTimeNow () {
        time = Calendar.getInstance().getTimeInMillis();
        time /= 1000;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    ///// Gestiona bloqueo de sesion (Por usuario)
    ////////////////////////////////////////////////////////////////////////////
    
    public static ArrayList<UserSession> lst_session;
    
    // Inicializa lista para almacenar usuarios bloqueados.
    public Expire() {
        if (lst_session == null) {
            lst_session = new ArrayList<UserSession>();
        }
    }
    
    // Consulta si tiempo de bloqueo de sesion 
    // para un usuario bloqueado ha expirado.
    public static boolean is_lock_session (String userq, String empresaq) {
        
        // Consulta posicion de usuario en listado de bloqueados        
        int index = get_index_session(userq, empresaq);
        long time_now = new Date().getTime();
        
        // Comprueba tiempo de bloqueo
        if (index >= 0) {
            UserSession s  = lst_session.get(index);
            long time_lock = s.getTime();
            long time_dif  = (time_now - time_lock) / 1000;
            if (time_dif >= TIME_WAIT) {
                s.setTime(0);
                return false;
            }
            return true;
        }
        return false;
    }
    
    // Bloquea sesion de usuario, estableciendo la hora en que fue bloqueado.
    // Agrega/actualiza registro en listado de bloqueados.
    public static void lock_session (String userq, String empresaq, long lock_time) {
                
        // Consulta posicion de usuario en listado de bloqueados
        int index = get_index_session(userq, empresaq);
        UserSession s;
        
        if (index >= 0) {
            s = lst_session.get(index);            
        } else {
            s = new UserSession();
        }                
        
        // Establece hora de bloqueo
        s.setUser(userq);
        s.setIdEmpresa(Restriction.getNumber(empresaq));
        s.setTime(lock_time);
        
        if (index < 0) {
            lst_session.add(s);
        }        
    }
    
    // Consulta posicion de usuario en listado de bloqueados.
    public static int get_index_session (String userq, String empresaq) {
        
        int id_empresaq = Restriction.getNumber(empresaq);
        
        if (lst_session != null) {
            for (int i = 0; i < lst_session.size(); i++) {
                UserSession s = lst_session.get(i);
                
                String user    = s.getUser().toLowerCase();
                int id_empresa = s.getIdEmpresa();
                
                if (user.compareTo(userq) == 0 &&
                    id_empresa == id_empresaq) {
                    return i;
                }
            }
        }        
        
        return -1;
    }
}
