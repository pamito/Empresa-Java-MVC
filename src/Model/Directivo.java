/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo
 */
public class Directivo extends Empleado {
    
     @Override
    public String toString() {
        return "Directivo{" + "idc=" + idc + ", categoria=" + categoria + ", nombrec=" + nombrec + ", empleados=" + empleados + '}';
    }
    
    private int idc;
    private int categoria;
    private String nombrec;
    private ArrayList<Empleado> empleados;
    private ArrayList<Directivo> directivo;
   
    
    

    public Directivo(int idc,int categoria,String nombrec, int sueldo_bruto, String nombre, Date fecha_nacimiento) {
        super(sueldo_bruto, nombre, fecha_nacimiento);
        this.idc = idc;
        this.categoria = categoria;
        this.nombrec = nombrec;
        this.directivo = new ArrayList<>();
    }

    public Directivo() {
        super(0, null, null);
    }

    public Directivo(int idc, int categoria, String nombrec) {
        this.idc = idc;
        this.categoria = categoria;
        this.nombrec = nombrec;
        
    }

    public Directivo( int categoria, String nombrec) {
        this.categoria = categoria;
        this.nombrec = nombrec;
        
    }
    
    

    /**
     * @return the categoria
     */
    public int getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the empleados
     */
    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    /**
     * @param empleados the empleados to set
     */
    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    /**
     * @return the idc
     */
    public int getIdc() {
        return idc;
    }

    /**
     * @param idc the idc to set
     */
    public void setIdc(int idc) {
        this.idc = idc;
    }

    /**
     * @return the nombrec
     */
    public String getNombrec() {
        return nombrec;
    }

    /**
     * @param nombrec the nombrec to set
     */
    public void setNombrec(String nombrec) {
        this.nombrec = nombrec;
    }

   public static int ObtenerIdDirectivo(Object selectedItem) {
        
        int id = 0;
        try {
             PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                     + "SELECT id from directivo where nombre = ?");
             consulta.setString(1, selectedItem.toString());
             
             ResultSet resultado = consulta.executeQuery();
             
             while(resultado.next()){
                id = resultado.getInt("id");
             }
             
             
        } catch (ClassNotFoundException | SQLException e) {
        }
        System.out.println(id);
        return id;
       
    }

    public int guardarDirectivo(Directivo mdirectivo) {
       try {
            PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                    + "INSERT INTO directivo (categoria,nombre) values(? , ? )");
            
            consulta.setInt(1, mdirectivo.getCategoria());
            consulta.setString(2, mdirectivo.getNombrec());
                      
            
            return consulta.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return 0;
    }
    
 
    /**
     * @return the directivo
     */
    public ArrayList<Directivo> getDirectivo(Connection conexion) throws SQLException {
       
        ArrayList<Directivo> directivos = new ArrayList<>();
     
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT id, categoria, nombre FROM directivo");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            directivos.add(new Directivo(resultado.getInt("id"),resultado.getInt("categoria"), resultado.getString("nombre")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
        return directivos;
        
    }

    /**
     * @param directivo the directivo to set
     */
    public void setDirectivo(ArrayList<Directivo> directivo) {
        this.directivo = directivo;
    }

    /**
     *
     * @param clave
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public void eliminar(int clave) throws ClassNotFoundException, SQLException {
        try {
            PreparedStatement consulta = Conexion.conectar().prepareStatement("DELETE FROM directivo WHERE id = ?");
            
            consulta.setInt(1, clave);

            int res = consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cargo eliminado");

           } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void editar(Directivo directivo) throws SQLException,  ClassNotFoundException {
        try {
            System.out.println("hola");
            PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                    + "UPDATE directivo SET categoria = ?, nombre = ? WHERE id = ?");
            consulta.setInt(1, directivo.getCategoria());
            consulta.setString(2, directivo.getNombrec());
            consulta.setInt(3, directivo.getIdc());

            int resultado = consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro actualizado");

           } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

    }

    
}
