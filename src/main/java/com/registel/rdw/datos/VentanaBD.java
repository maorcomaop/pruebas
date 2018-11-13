/**
 * CREADO POR JAIR VIDAL 27/11/2017 1:10:09 AM;
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.datos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Lider Desarrollo
 */
public class VentanaBD {
    
    public static String readFile(String path) throws ExisteRegistroBD {
                
		BufferedReader fichero = null;
		String sFichero ="";
		try{
			FileReader flujo = new FileReader(path);
			fichero = new BufferedReader(flujo);
			int i = 0;
			String linea;
			while((linea=fichero.readLine())!= null){
				sFichero += new String(linea.getBytes("ISO-8859-1"), "UTF-8");
			}
			fichero.close();
		}
		catch(Exception e){
			sFichero = "Excepcion " + e;
			}

		return sFichero;
    }

    
}
