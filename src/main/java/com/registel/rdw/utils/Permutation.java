
package com.registel.rdw.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Permutation {
    
    // Mezcla/ordena aleatoriamente los elementos de lista especificada.
    public ArrayList<String> shuffle(ArrayList<String> lst) {
        
        ArrayList<String> nlst = new ArrayList<String>();
        for (String s : lst) {
            String ns = new String(s);
            nlst.add(ns);
        }
        
        long seed = System.nanoTime();
        Collections.shuffle(nlst, new Random(seed));        
        
        return nlst;
    }
    
    // Verifica si listas son completamente diferentes 
    // (ningun elemento en la misma posicion).
    public boolean completeDifferent(
                        ArrayList<String> lsta, 
                        ArrayList<String> lstb) {
        
        if (lsta.size() == 1) return true;
        
        String a, b;
        if (lsta.size() == lstb.size()) {
            for (int i = 0; i < lsta.size(); i++) {
                a = lsta.get(i);
                b = lstb.get(i);
                if (a.compareTo(b) == 0)
                    return false;
            }
            return true;
        }
        return false;
    }
    
    // Verifica si listas no poseen pareja con igual sucesor o antecesor
    // (Igualmente deben ser completamente diferentes)
    public boolean successorDifferent(
                        ArrayList<String> lsta,
                        ArrayList<String> lstb) {
        
        if (!completeDifferent(lsta, lstb)) return false;
        if (lsta.size() < 4) return true;
        
        String i1, i2, j1, j2;
        for (int i = 0; i < lsta.size()-1; i++) {
            i1 = lsta.get(i);
            i2 = lsta.get(i+1);
            for (int j = 0; j < lstb.size()-1; j++) {                
                j1 = lstb.get(j);
                j2 = lstb.get(j+1);
                if ((i1.compareTo(j1) == 0 && i2.compareTo(j2) == 0) ||
                    (i1.compareTo(j2) == 0 && i2.compareTo(j1) == 0)) {
                    return false;
                }                    
            }
        }
        return true;
    }
    
    // Genera ordenamiento aleatorio completamente diferente al anterior.
    public ArrayList<String> nextOrder(ArrayList<String> lst) {        
        
        if (lst == null)     return null;
        if (lst.size() == 0) return lst;
        
        ArrayList<String> nlst = shuffle(lst);        
        
        while (!completeDifferent(nlst, lst)) {            
            nlst = shuffle(nlst);
        }        
                
        return nlst;
    }        
    
    // Mezcla/reordena lista hasta ser complementamente diferente.
    public ArrayList<String> resetSuccessor(ArrayList<String> lst) {                
        
        if (lst == null)     return null;
        if (lst.size() == 0) return lst;
        
        ArrayList<String> nlst = shuffle(lst);                      

        while (!successorDifferent(nlst, lst)) {
            nlst = shuffle(nlst);
        }        
        
        return nlst;
    }
    
    // Intercambia elementos entre listados.
    public void swap(ArrayList<String> lsta, ArrayList<String> lstb) {
        
        if (lsta == null || lstb == null) return;        
        
        String s1, s2;
        if (lsta.size() >= lstb.size()) {
            for (int i = 0; i < lstb.size(); i++) {
                s1 = lstb.get(i);
                s2 = lsta.set(i, s1);
                lstb.set(i, s2);
            }
        } else {
            for (int i = 0; i < lsta.size(); i++) {
                s1 = lsta.get(i);
                s2 = lstb.set(i, s1);
                lsta.set(i, s2);
            }
        }
        //fixswap(lsta);
        //fixswap(lstb);
    }        
    
    // Hace rotacion lineal intercalada cada dos elementos.
    public void fixswap(ArrayList<String> lst) {    
        
        if (lst.size() <= 3) return;
        
        int n = 1, i = 2;
        do {
            String s = lst.remove(i);
            lst.add(n++, s);
            i += 2;
        } while (i < lst.size());            
    }
    
    public static void print(String title, ArrayList<String> lst) {
        
        if (lst == null)        { System.out.println("> Lista nula."); return; }
        if (lst.size() == 0)    { System.out.println("> Sin datos.");  return; }
        
        String str = "";
        for (String s : lst) {
            str += (str == "") ? s : " " + s;
        }
        System.out.print(title + " ");
        System.out.println(str);
    }                
}
