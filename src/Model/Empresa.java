/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.GUIempleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo
 */
public class Empresa extends GUIempleado {

    private int id;
    private String nombre;
    private String cif;
    private ArrayList<Cliente> clientes;
    private ArrayList<Empleado> empleados;
    private ArrayList<Empresa> empresas;

    public Empresa(int id, String nombre, String cif) {
        this.id = id;
        this.nombre = nombre;
        this.cif = cif;
        empleados = new ArrayList<>();
        clientes = new ArrayList<>();

    }

    public Empresa(String nombre, String cif) {
        this.nombre = nombre;
        this.cif = cif;

    }

    public Empresa(int id) {
        this.id = id;
        
    }
    
    

    public Empresa() {
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the cif
     */
    public String getCif() {
        return cif;
    }

    /**
     * @param cif the cif to set
     */
    public void setCif(String cif) {
        this.cif = cif;
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

    public void contratarEmpleado(Empleado empleado) {
        getEmpleados().add(empleado);
    }

    public void crearCliente(Cliente cliente) {
        getClientes().add(cliente);
    }

    /**
     * @return the clientes
     */
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    /**
     * @param clientes the clientes to set
     */
    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public int guardarEmpresa(Empresa empresa) {

        try {
            PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                    + "INSERT INTO empresa (nombre,cif) values(? , ? )");

            consulta.setString(1, empresa.getNombre());
            consulta.setString(2, empresa.getCif());

            return consulta.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    public static int ObtenerIdEmpresa(Object selectedItem) {

        int id = 0;
        try {
            PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                    + "SELECT id from empresa where nombre = ?");
            consulta.setString(1, selectedItem.toString());

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                id = resultado.getInt("id");
            }

        } catch (ClassNotFoundException | SQLException e) {
        }
        
        return id;

    }

    public ArrayList<Empresa> getEmpresas(Connection conectar) throws SQLException {
        ArrayList<Empresa> empresas = new ArrayList<>();
        String sql;
        sql = "SELECT id, nombre, cif FROM empresa";
        try {
            PreparedStatement consulta = conectar.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                empresas.add(new Empresa(resultado.getInt("id"), resultado.getString("nombre"), resultado.getString("cif")));

            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return empresas;
    }

    public void editar( Empresa empresa) throws SQLException, ClassNotFoundException {
       
        try {
            PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                    + "UPDATE empresa SET nombre = ?, cif = ? WHERE id = ?");
            consulta.setString(1, empresa.getNombre());
            consulta.setString(2, empresa.getCif());
            consulta.setInt(3, empresa.getId());

            int resultado = consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro actualizado");

           } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
             
        
    }

    /**
     * @return the empresas
     */
    public ArrayList<Empresa> getEmpresas() {
        return empresas;
    }

    /**
     * @param empresas the empresas to set
     */
    public void setEmpresas(ArrayList<Empresa> empresas) {
        this.empresas = empresas;
    }

    public void eliminar(int clave) throws ClassNotFoundException, SQLException {
        try {
            PreparedStatement consulta = Conexion.conectar().prepareStatement("DELETE FROM empresa WHERE id = ?");
            
            consulta.setInt(1, clave);

            int res = consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro eliminado");

           } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        
    }
    
    

}
