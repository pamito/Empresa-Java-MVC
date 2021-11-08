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
public class Cliente extends Persona {

    private int idc;
    private String telefono;
    private int id_empresa;
    private int id_persona;
    private String nom_empresa;
    private ArrayList<Empresa> listempresa;
    private ArrayList<Cliente> cliente;

    public Cliente(int idc, String telefono, int id, String nombre, Date fecha_nacimiento) {
        super(id, nombre, fecha_nacimiento);
        this.idc = id;
        this.telefono = telefono;

    }

    public Cliente(String telefono, String nombre, Date fecha_nacimiento) {
        super(nombre, fecha_nacimiento);

        this.telefono = telefono;

    }

    public Cliente(String telefono, int id, String nombre, Date fecha_nacimiento) {
        super(id, nombre, fecha_nacimiento);

        this.telefono = telefono;
    }

    public Cliente(String telefono, int id_empresa, int id_persona) {

        this.telefono = telefono;
        this.id_empresa = id_empresa;
        this.id_persona = id_persona;

    }

    public Cliente(int idc, String telefono, String nom_empresa, String nombre, Date fecha_nacimiento) {
        super(nombre, fecha_nacimiento);
        this.idc = idc;
        this.telefono = telefono;
        this.nom_empresa = nom_empresa;

    }

    public Cliente(int idc, String telefono, int id_empresa, int id_persona) {
        this.idc = idc;
        this.telefono = telefono;
        this.id_empresa = id_empresa;
        this.id_persona = id_persona;

    }

    public Cliente() {
        super(0, null, null);
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

        return empresa;

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

    public int guardarCliente(Cliente mcliente) {
        try {

            PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                    + "INSERT INTO cliente (telefono,id_persona,id_empresa) values( ? , ? , ? )");

            consulta.setString(1, mcliente.getTelefono());
            consulta.setInt(2, mcliente.getId_persona());
            consulta.setInt(3, mcliente.getId_empresa());

            return consulta.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    public ArrayList<Cliente> getClientes(Connection conectar) throws SQLException {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql;
        sql = "SELECT cl.id, cl.telefono, per.nombre, per.fecha_nacimiento, empre.nombre FROM ((cliente as cl "
                + "INNER JOIN  persona as per ON per.id = cl.id_persona) "
                + "INNER JOIN  empresa as empre ON empre.id = cl.id_empresa)";
        try {
            PreparedStatement consulta = conectar.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                clientes.add(new Cliente(resultado.getInt("cl.id"), resultado.getString("cl.telefono"),
                        resultado.getString("empre.nombre"), resultado.getString("per.nombre"),
                        resultado.getDate("per.fecha_nacimiento")));

            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return clientes;
    }

    /**
     * @return the nom_empresa
     */
    public String getNom_empresa() {
        return nom_empresa;
    }

    /**
     * @param nom_empresa the nom_empresa to set
     */
    public void setNom_empresa(String nom_empresa) {
        this.nom_empresa = nom_empresa;
    }

    public void eliminar(int clave) throws ClassNotFoundException, SQLException {
        try {
            PreparedStatement consulta = Conexion.conectar().prepareStatement("DELETE FROM cliente WHERE id = ?");

            consulta.setInt(1, clave);

            int res = consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente eliminado");

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

    }

    public void editarcli(Cliente mcliente) throws ClassNotFoundException, SQLException {

        try {
            System.out.println(mcliente);
            PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                    + "UPDATE cliente SET telefono = ?, id_empresa = ?, id_persona = ? WHERE id = ?");
            consulta.setString(1, mcliente.getTelefono());
            consulta.setInt(2, mcliente.getId_empresa());
            consulta.setInt(3, mcliente.getId_persona());
            consulta.setInt(4, mcliente.getIdc());

            int resultado = consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente actualizado");

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

}
