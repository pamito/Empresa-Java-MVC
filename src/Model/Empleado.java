/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.*;
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
public class Empleado extends Persona {

    private int ide;
    private int sueldo_bruto;
    private int id_persona;
    private int id_empresa;
    private int id_directivo;
    private String nom_empresa;
    private String nom_directivo;
    private String nombre_empleado;

    private ArrayList<Directivo> listdirectivo;
    private ArrayList<Empresa> listempresa;
    private ArrayList<Empleado> trabajador;

    private final String tablaEmpleado = "empleado";

    public Empleado(int ide, int sueldo_bruto, int id_persona, int id_empresa, int id_directivo) {

        this.ide = ide;
        this.sueldo_bruto = sueldo_bruto;
        this.id_persona = id_persona;
        this.id_empresa = id_empresa;
        this.id_directivo = id_directivo;
        

    }

    public Empleado(int sueldo_bruto, String nombre, Date fecha_nacimiento) {
        super(nombre, fecha_nacimiento);
        this.sueldo_bruto = sueldo_bruto;
        this.listdirectivo = new ArrayList<>();
        this.listempresa = new ArrayList<>();
    }

    public Empleado(int sueldo_bruto, int id_persona, int id_empresa, int id_directivo) {

        this.sueldo_bruto = sueldo_bruto;
        this.id_persona = id_persona;
        this.id_empresa = id_empresa;
        this.id_directivo = id_directivo;

    }

    public Empleado(String nombre_empleado) {
        
        this.nombre_empleado = nombre_empleado;
        
    }
    
    

    public Empleado(int sueldo_bruto, int id_persona, int id_empresa, int id_directivo, String nombre, Date fecha_nacimiento) {
        super(nombre, fecha_nacimiento);
        
        this.sueldo_bruto = sueldo_bruto;
        this.id_persona = id_persona;
        this.id_empresa = id_empresa;
        this.id_directivo = id_directivo;
        this.listdirectivo = new ArrayList<>();
    }

    public Empleado(int ide, int sueldo_bruto, String nom_empresa, String nom_directivo, String nombre, Date fecha_nacimiento) {
        super(nombre, fecha_nacimiento);
        this.ide = ide;
        this.sueldo_bruto = sueldo_bruto;
        this.nom_empresa = nom_empresa;
        this.nom_directivo = nom_directivo;

    }
    
    public Empleado(int ide, int sueldo_bruto, String nom_empresa, String nom_directivo, String nombre_empleado, String nombre, Date fecha_nacimiento) {
        super(nombre, fecha_nacimiento);
        this.nombre_empleado = nombre_empleado;
        this.ide = ide;
        this.sueldo_bruto = sueldo_bruto;
        this.nom_empresa = nom_empresa;
        this.nom_directivo = nom_directivo;

    }

    public Empleado() {
        super(null, null);
    }

    /**
     * @return the sueldo_bruto
     */
    public int getSueldo_bruto() {
        return sueldo_bruto;
    }

    /**
     * @param sueldo_bruto the sueldo_bruto to set
     */
    public void setSueldo_bruto(int sueldo_bruto) {
        this.sueldo_bruto = sueldo_bruto;
    }

    /**
     * @return the ide
     */
    public int getIde() {
        return ide;
    }

    /**
     * @param ide the ide to set
     */
    public void setIde(int ide) {
        this.ide = ide;
    }

    /**
     * @return the id_persona
     */
    public int getId_persona() {
        return id_persona;
    }

    /**
     * @param id_persona the id_persona to set
     */
    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    /**
     * @return the id_empresa
     */
    public int getId_empresa() {
        return id_empresa;
    }

    /**
     * @param id_empresa the id_empresa to set
     */
    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    /**
     * @return the id_directivo
     */
    public int getId_directivo() {
        return id_directivo;
    }

    /**
     * @param id_directivo the id_directivo to set
     */
    public void setId_directivo(int id_directivo) {
        this.id_directivo = id_directivo;
    }

    /**
     * @return the listdirectivo
     */
    public ArrayList<Directivo> getListdirectivo() throws SQLException, ClassNotFoundException {

        ArrayList<Directivo> directivo = new ArrayList<>();

        PreparedStatement consulta = Conexion.conectar().prepareStatement("SELECT * FROM directivo");
        ResultSet resultadodirectivo = consulta.executeQuery();
        while (resultadodirectivo.next()) {
            directivo.add(new Directivo(resultadodirectivo.getInt("id"), resultadodirectivo.getInt("categoria"),
                    resultadodirectivo.getString("nombre")));

        }

        return directivo;

    }

    /**
     * @return the listempresa
     */
    public ArrayList<Empresa> getListempresa() throws SQLException, ClassNotFoundException {

        ArrayList<Empresa> empresa = new ArrayList<>();

        PreparedStatement consulta = Conexion.conectar().prepareStatement("SELECT * FROM empresa");
        ResultSet resultadoempresa = consulta.executeQuery();
        while (resultadoempresa.next()) {
            empresa.add(new Empresa(resultadoempresa.getInt("id"), resultadoempresa.getString("nombre"),
                    resultadoempresa.getString("cif")));

        }
        System.out.println(empresa);
        return empresa;

    }

    public int guardarEmpleado(Empleado mempleado) {
        try {

            PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                    + "INSERT INTO empleado (sueldo,id_persona,id_empresa,id_directivo) values( ? , ? , ? , ? )");

            consulta.setInt(1, mempleado.getSueldo_bruto());
            consulta.setInt(2, mempleado.getId_persona());
            consulta.setInt(3, mempleado.getId_empresa());
            consulta.setInt(4, mempleado.getId_directivo());
            

            return consulta.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    /**
     * @return the trabajador
     * @throws java.sql.SQLException
     */
    public ArrayList<Empleado> getEmpleados(Connection conexion) throws SQLException {

        ArrayList<Empleado> empleados = new ArrayList<>();
        String sql;
        sql = "SELECT emp.id, per.nombre, per.fecha_nacimiento, emp.sueldo, empre.nombre, dir.nombre FROM (((empleado as emp "
                + "INNER JOIN  persona as per ON per.id = emp.id_persona) "
                + "INNER JOIN  empresa as empre ON empre.id = emp.id_empresa) "
                + "INNER JOIN  directivo as dir ON dir.id = emp.id_directivo) ";
        try {
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                empleados.add(new Empleado(resultado.getInt("emp.id"), resultado.getInt("emp.sueldo"), resultado.getString("per.nombre"), resultado.getString("dir.nombre"), resultado.getString("empre.nombre"), resultado.getDate("per.fecha_nacimiento")));
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return empleados;
    }

    /**
     * @return the nom_empresa
     */
    public String getNom_empresa() {
        return nom_empresa;
    }

    /**
     * @return the nom_directivo
     */
    public String getNom_directivo() {
        return nom_directivo;
    }

    /**
     * @return the trabajador
     */
    public ArrayList<Empleado> getTrabajador() {
        return trabajador;
    }

    /**
     * @return the tablaEmpleado
     */
    public String getTablaEmpleado() {
        return tablaEmpleado;
    }

    public void eliminar(int clave) throws ClassNotFoundException, SQLException {
        try {
            PreparedStatement consulta = Conexion.conectar().prepareStatement("DELETE FROM empleado WHERE id = ?");

            consulta.setInt(1, clave);

            int res = consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Empleado eliminado");

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

    }

    public void editar(Empleado empleado) throws ClassNotFoundException, SQLException {
        try {
            System.out.println(empleado);
            PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                    + "UPDATE empleado SET sueldo = ?,  id_empresa = ?, id_directivo = ? WHERE id = ?");
            consulta.setInt(1, empleado.getSueldo_bruto());
            consulta.setInt(2, empleado.getId_empresa());
            consulta.setInt(3, empleado.getId_directivo());
            consulta.setInt(4, empleado.getIde());

            int resultado = consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Empleado actualizado");

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * @return the nombre_empleado
     */
    public String getNombre_empleado() {
        return nombre_empleado;
    }

    /**
     * @param nombre_empleado the nombre_empleado to set
     */
    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }
}
