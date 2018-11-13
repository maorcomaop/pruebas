/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.DatosVehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lider_desarrollador
 */
public class IPKBD2 {
    
    private static Map<String, DatosVehiculo> hmap = null;
    
    // Calcula indice IPK para datos de vehiculo pasados como argumento
    // Si valor de distancia recorrida es 0, el indice se establece a 0.0,
    // de lo contrario se calcula: IPK = #pasajeros / distancia
    public static double ipk(DatosVehiculo dv) {
        
        if (dv == null) return 0.0;
        
        long npasajeros   = dv.getNumeracionFinal() - dv.getNumeracionInicial();
        long distancia    = dv.getDistanciaRecorrida();
        double ddistancia = (double) distancia / 1000;

        if (distancia == 0) {
            return 0.0;
        } else {
            double ipk = (double) npasajeros / ddistancia;
            System.out.println(dv.getPlaca() +" "+ ipk);
            return ipk;
        }       
    }
    
    // Consulta indices IPK para cada vehiculo en cada fecha del rango
    // especificado.
    public static void ipk_pordia(
                        String fecha_inicial,
                        String fecha_final) {
        
        ConexionExterna conext = new ConexionExterna();
        
        String sql = "SELECT * FROM tbl_estadistica_vehiculo"
                    + " WHERE fecha_gps BETWEEN ? AND ?"
                    + " ORDER BY placa, fecha_gps";
        
        try {
            Connection con = conext.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fecha_inicial);
            ps.setString(2, fecha_final);
            ResultSet rs = ps.executeQuery();
            
            hmap = new HashMap<String, DatosVehiculo>();
            while (rs.next()) {
                DatosVehiculo dv = new DatosVehiculo();
                dv.setPlaca(rs.getString("PLACA"));
                dv.setFecha(rs.getString("FECHA_GPS"));
                dv.setNumeracionInicial(rs.getLong("NUMERACION_INICIAL"));
                dv.setNumeracionFinal(rs.getLong("NUMERACION_FINAL"));
                dv.setDistanciaRecorrida(rs.getLong("DISTANCIA"));
                double ipk = ipk(dv); dv.setIpk(ipk);
                
                // Conservamos indice IPK para registro/llave vehiculo-dia
                String key = dv.getPlaca() + "-" + dv.getFecha();
                hmap.put(key, dv);
            }
            
        } catch (SQLException e) {
            System.err.println("ipk_pordia: " + e);
        } catch (ClassNotFoundException e) {
            System.err.println("ipk_pordia: " + e);
        }        
    }
    
    // Consulta indice IPK de empresa (registro de todos los
    // vehiculos asociados a dicha empresa) en un rango de
    // dias especifico
    public static double ipk_empresa(
                        String fecha_inicial,
                        String fecha_final) {
        
        ConexionExterna conext = new ConexionExterna();
        
        String sql = "SELECT"
                    + " sum(numeracion_final - numeracion_inicial) as PASAJEROS,"
                    + " sum(distancia) as DISTANCIA"
                    + " FROM tbl_estadistica_vehiculo"
                    + " WHERE fecha_gps BETWEEN ? AND ?";
        
        try {
            Connection con = conext.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fecha_inicial);
            ps.setString(2, fecha_final);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                long npasajeros   = rs.getLong("PASAJEROS");
                long distancia    = rs.getLong("DISTANCIA");
                double ddistancia = (double) distancia / 1000;
                
                if (distancia == 0) {
                    return 0.0;
                } else {
                    double ipk = (double) npasajeros / ddistancia;
                    System.out.println("ipk-emp2 "+ ipk +" "+ npasajeros +" "+ distancia);
                    return ipk;
                }                
            }
            ps.close();
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("ipk_empresa: " + e);
        } catch (ClassNotFoundException e) {
            System.err.println("ipk_empresa: " + e);
        } finally {
            conext.desconectar();
        }
        return 0.0;
    }
    
    public static Map<String, DatosVehiculo> getHmapIpk() {
        return hmap;
    }    
}
