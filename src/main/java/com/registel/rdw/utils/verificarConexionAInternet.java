
package com.registel.rdw.utils;

import com.mysql.jdbc.Constants;
import com.registel.rdw.datos.ExisteRegistroBD;
import com.registel.rdw.datos.LicenciaBD;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.swing.SwingWorker;

public class verificarConexionAInternet extends SwingWorker<Integer, Void> {

    private boolean stop = true;
    private servicio.Servicio servicioRemoto = null;
    private int retorno = 0;
    private int intentos = 0;
    
    private boolean estoyConectadoAInternet(){
         String dirWeb = "www.google.com.co";         
         int puerto = 80;         
         try{   
             Socket s = new Socket(dirWeb, puerto);
             if(s.isConnected()){
                return true;         
             }
      }catch(Exception e){System.err.println(e.getMessage());}
      return false;
    }
    
    public void adicionarServicio(servicio.Servicio s){
        this.servicioRemoto = s;
    }
    
    public boolean actualizarLicenciaLocalConRemota(){                        
           int r = LicenciaBD.updateKeyLocalWithKeyRemote(servicioRemoto);
           if (r==1) {
               return true;            
           }
        return false;
    }
    @Override
    protected Integer doInBackground() {        
        /*while (stop) {*/
            if (estoyConectadoAInternet()) {
                System.out.println("Estoy conectado");
                if (actualizarLicenciaLocalConRemota()) {
                    System.out.println("Actualiza la licencia y se detiene el proceso ");                                                            
                    retorno = 1;
                    /*this.stop=false;*/
                }                
            }
            else{
                /*this.stop = false;*/
                intentos++;
                System.out.println("NO Estoy conectado, Reintenta "+intentos);
            }            
            /*try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }*/
            /*if (intentos == 3) {
                this.stop=false;
            }*/
        /*}/*FIN WHILE*/
        return retorno;
    }
    
}

