/**
 * Clase modelo que permite la comunicacion entre la base de datos y la aplicacion
 * CREADO POR JAIR HERNANDO VIDAL
 */
package com.registel.rdw.datos;

import com.registel.rdw.logica.Empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.registel.rdw.logica.ServicioLocal;
import servicio.Servicio;

public class LicenciaBD {

    public static boolean exist(ServicioLocal c) throws SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tbl_clic as c WHERE c.id = ?";

        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, c.getId());
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            rs.close();
            con.close();
        }
        return false;
    }

    public static int insert(ServicioLocal c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();        
         sql.append("INSERT INTO tbl_clic(cl, activa, suspendido) VALUES (?, ?, ?)");
        if (exist(c)) {
            return 0;
        } else {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql.toString());                
                ps.setString(1, c.getClave());
                ps.setInt(1, c.getActualizado());
                ps.setInt(1, c.getSuspendido());
                int retorno = ps.executeUpdate();
                con.commit();
                if (retorno > 0) {
                    return 1;
                } else {
                    con.rollback();
                }                
            } catch (SQLException ex) {
                System.err.println(ex);
                if (con != null) {
                    try {
                        con.rollback();
                    } catch (SQLException ie) {
                        System.err.println(ie);
                    }
                }
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println(e);
                }
                UtilBD.closeStatement(ps);
                pila_con.liberarConexion(con);
            }
        }
        return 0;
    }
    
    public static int insertKeyLocal(ServicioLocal c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet resultset = null;

        StringBuilder sql = new StringBuilder();
        try {
            
            sql.append("INSERT tbl_clic(fk_tipo_clave, clave, num_doc, nombre_cliente) VALUES(?,?,?,?)");
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());            
            ps.setInt(1, c.getFk_tipo_clave());
            ps.setString(2, c.getClave());
            ps.setString(3, c.getNum_doc());
            ps.setString(4, c.getNom_cliente());            
            int retorno = ps.executeUpdate();            
             con.commit();
            if (retorno > 0) {
                return 1;
            } else {
                con.rollback();
            }

        } catch (SQLException ex) {
             System.err.println(ex);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    System.err.println(ie);
                }
            } 

        } finally {
              try { con.setAutoCommit(true); } 
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);

        }
        return 0;
    }

    public static ServicioLocal selectByOne(ServicioLocal c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ServicioLocal licenciaEncontrado;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_clic WHERE (id=?)");        
        try {
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, c.getId());
            rs = ps.executeQuery();
            
            licenciaEncontrado = new ServicioLocal();
            if (rs.next()) {
                licenciaEncontrado.setId(rs.getInt("id")); 
                licenciaEncontrado.setClave(rs.getString("cl"));
                licenciaEncontrado.setActualizado(rs.getInt("activa"));
                licenciaEncontrado.setSuspendido(rs.getInt("suspendido"));
                licenciaEncontrado.setFecha_creacion(rs.getString("fecha_creacion"));
                licenciaEncontrado.setFecha_actualizacion(rs.getString("fecha_actualizacion"));
                licenciaEncontrado.setFecha_proxima_actualizacion(rs.getString("fecha_proxima_actualizacion"));
            }
            return licenciaEncontrado;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            rs.close();
            con.close();
        }
        return null;
    }
    /*METODO QUE COMPRUEBA SI TIENE O NO ASIGNADA UNA CLAVE*/
    public static ServicioLocal selectLocalKeyByEnterprise(ServicioLocal e) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ServicioLocal s;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbl_clic WHERE (num_doc=?) AND (estado=1)");        
        try {
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, e.getNum_doc());
            rs = ps.executeQuery();
            
            s = new ServicioLocal();
            if (rs.next()) {
                s.setId(rs.getInt("id")); 
                s.setFk_clave(rs.getInt("fk_clave"));
                s.setFk_tipo_clave(rs.getInt("fk_tipo_clave"));
                s.setFk_cliente(rs.getInt("fk_cliente"));
                s.setFk_producto(rs.getInt("fk_producto"));                                
                s.setClave(rs.getString("clave")); 
                s.setNum_doc(rs.getString("num_doc")); 
                s.setNom_cliente(rs.getString("nombre_cliente"));                 
                s.setSuspendido(rs.getInt("suspendido"));
                s.setActualizado(rs.getInt("actualizado"));
                s.setFecha_creacion(rs.getString("fecha_instalacion"));
                s.setFecha_actualizacion(rs.getString("fecha_renovacion"));                
                s.setFecha_expiracion(rs.getString("fecha_expiracion"));
                s.setEstado(rs.getInt("estado"));
            }
            return s;
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            UtilBD.closeResultSet(rs);
            UtilBD.closePreparedStatement(ps);
            pila_con.liberarConexion(con);
            rs.close();
            con.close();
        }
        return null;
    }

    
    public static int updateKeyLocalWithKeyRemote(servicio.Servicio c) {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet resultset = null;

        StringBuilder sql = new StringBuilder();
        try {
            
            sql.append("UPDATE tbl_clic SET fk_clave=?, fk_tipo_clave=?, fk_cliente=?, fk_producto=?, producto=?, suspendido=?, actualizado=?, fecha_instalacion=?, fecha_renovacion=?, fecha_expiracion=? WHERE (num_doc = ?) AND (clave = ?)");
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());            
            ps.setInt(1, c.getFkClave());
            ps.setInt(2, c.getFkTipoClave());
            ps.setInt(3, c.getFkCliente());
            ps.setInt(4, c.getFkProducto());
            ps.setString(5, c.getProducto());
            ps.setInt(6, c.getSuspendido()); 
            ps.setInt(7, 1); 
            ps.setString(8, c.getFechaInstalacion());
            ps.setString(9, c.getFechaRenovacion());
            ps.setString(10, c.getFechaExpiracion());
            ps.setString(11, c.getNumDoc());
            ps.setString(12, c.getClave());
            int retorno = ps.executeUpdate();
            
             con.commit();
            if (retorno > 0) {
                return 1;
            } else {
                con.rollback();
            }

        } catch (SQLException ex) {
             System.err.println(ex);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    System.err.println(ie);
                }
            } 

        } finally {
              try { con.setAutoCommit(true); } 
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);

        }
        return 0;
    }
    
    public static int updateDateClave(ServicioLocal c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet resultset = null;

        StringBuilder sql = new StringBuilder();
        try {
            
            sql.append("UPDATE tbl_clic SET fecha_actualizacion =? WHERE id = ?");
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());            
            ps.setString(1, c.getFecha_actualizacion());
            ps.setInt(2, c.getId());
            int retorno = ps.executeUpdate();
            
             con.commit();
            if (retorno > 0) {
                return 1;
            } else {
                con.rollback();
            }

        } catch (SQLException ex) {
             System.err.println(ex);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    System.err.println(ie);
                }
            } 

        } finally {
              try { con.setAutoCommit(true); } 
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);

        }
        return 0;
    }
    
    public static int updateDateNextClave(ServicioLocal c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet resultset = null;

        StringBuilder sql = new StringBuilder();
        try {
            
            sql.append("UPDATE tbl_clic SET fecha_proxima_actualizacion =? WHERE id = ?");
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());            
            ps.setString(1, c.getFecha_proxima_actualizacion());
            ps.setInt(2, c.getId());
            int retorno = ps.executeUpdate();
            
             con.commit();
            if (retorno > 0) {
                return 1;
            } else {
                con.rollback();
            }

        } catch (SQLException ex) {
             System.err.println(ex);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    System.err.println(ie);
                }
            } 

        } finally {
              try { con.setAutoCommit(true); } 
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closeStatement(ps);
            pila_con.liberarConexion(con);

        }
        return 0;
    }

    public static int updateEstado(ServicioLocal c) throws ExisteRegistroBD, SQLException {
        PilaConexiones pila_con = PilaConexiones.obtenerInstancia();
        Connection con = pila_con.obtenerConexion();
        PreparedStatement ps_update = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tbl_clic SET estado=? WHERE id=? ");
        try {
             con.setAutoCommit(false);
            ps_update = con.prepareStatement(sql.toString());
            ps_update.setInt(1, c.getEstado());
            ps_update.setInt(2, c.getId());
            int retorno = ps_update.executeUpdate();
            //return true;
            con.commit();
            if (retorno > 0) {
                return 1;
            } else {
                con.rollback();
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ie) {
                    System.err.println(ie);
                }
            } 
        } finally {
           try { con.setAutoCommit(true); } 
            catch (SQLException e) { System.err.println(e); }
            UtilBD.closeStatement(ps_update);
            pila_con.liberarConexion(con);
        }
    return 0;
    }          

    public static Servicio consultKeyRemote(ServicioLocal c){
        Servicio r = null;
        r = consultarLicencia(c.getNum_doc(), c.getNom_cliente());
        if (r != null) {
            return r;
        }
        return r;
    }

   
    public static Integer consultServiciosPorCliente(ServicioLocal c){        
        int r = consultarServiciosSuspendidosPorCliente(c.getNum_doc(), c.getNom_cliente());        
        return r;
    }
    
    private static Servicio consultarLicencia(java.lang.String nit, java.lang.String nombre) {
        servicio.ServicioWeb_Service service = new servicio.ServicioWeb_Service();
        servicio.ServicioWeb port = service.getServicioWebPort();
        return port.consultarLicencia(nit, nombre);
    }

    private static Integer consultarServiciosSuspendidosPorServicio(int idCliente, int idLicencia) {
        servicio.ServicioWeb_Service service = new servicio.ServicioWeb_Service();
        servicio.ServicioWeb port = service.getServicioWebPort();
        return port.consultarServiciosSuspendidosPorServicio(idCliente, idLicencia);
    }

    private static Integer consultarServiciosSuspendidosPorCliente(java.lang.String nit, java.lang.String nomCliente) {
        servicio.ServicioWeb_Service service = new servicio.ServicioWeb_Service();
        servicio.ServicioWeb port = service.getServicioWebPort();
        return port.consultarServiciosSuspendidosPorCliente(nit, nomCliente);
    }

       
    
}//FIN DE CLASE
