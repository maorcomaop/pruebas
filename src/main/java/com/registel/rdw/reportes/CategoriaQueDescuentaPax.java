
package com.registel.rdw.reportes;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoriaQueDescuentaPax {
    
    int pk_vehiculo;    
    int pk_categoria;        
    HashMap<Integer, Integer> categorias;
    int totalPasajeroDescontados;
    int totalPasajeroLiquidados;
    int totalPasajeros;
    double indicadorPasajeros;
    String categoria;
    String placa;

    public int getPk_vehiculo() {
        return pk_vehiculo;
    }

    public void setPk_vehiculo(int pk_vehiculo) {
        this.pk_vehiculo = pk_vehiculo;
    }

    public int getPk_categoria() {
        return pk_categoria;
    }

    public void setPk_categoria(int pk_categoria) {
        this.pk_categoria = pk_categoria;
    }

    public HashMap<Integer, Integer> getCategorias() {
        return categorias;
    }

    public void setCategorias(HashMap<Integer, Integer> categorias) {
        this.categorias = categorias;
    }

    public int getTotalPasajeroDescontados() {
        return totalPasajeroDescontados;
    }

    public void setTotalPasajeroDescontados(int totalPasajeroDescontados) {
        this.totalPasajeroDescontados = totalPasajeroDescontados;
    }

    public int getTotalPasajeroLiquidados() {
        return totalPasajeroLiquidados;
    }

    public void setTotalPasajeroLiquidados(int totalPasajeroLiquidados) {
        this.totalPasajeroLiquidados = totalPasajeroLiquidados;
    }

    public int getTotalPasajeros() {
        return totalPasajeros;
    }

    public void setTotalPasajeros(int totalPasajeros) {
        this.totalPasajeros = totalPasajeros;
    }

    public double getIndicadorPasajeros() {
        return indicadorPasajeros;
    }

    public void setIndicadorPasajeros(double indicadorPasajeros) {
        this.indicadorPasajeros = indicadorPasajeros;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    
    
    
    
    
    
    
    
}
