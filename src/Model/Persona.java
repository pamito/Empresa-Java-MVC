/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;



import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JOptionPane;


/**
 *
 * @author Pablo
 */
public class Persona {
    private int id;
    private String nombre;
    private Date fecha_nacimiento;

    public Persona(int id, String nombre, Date fecha_nacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
    }
    
    public Persona( String nombre, Date fecha_nacimiento) {
        
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Persona() {
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
     * @return the fecha_nacimiento
     */
   
    
    
    public int Calcular_Edad (Date fecha){
        int edad=0;
        Date fechaNac = fecha;
        Calendar fechaHoy = Calendar.getInstance();
       
       
        int anoHoy = fechaHoy.get(Calendar.YEAR);

        edad = anoHoy ;

        System.out.println("Tu edad es: " + edad + " años");
    
        return edad;
    }

    /**
     * @return the fecha_nacimiento
     */
    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    /**
     * @param fecha_nacimiento the fecha_nacimiento to set
     */
    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int guardarPersona(Persona persona) {
        try {
            
            PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                    + "INSERT INTO persona (nombre,fecha_nacimiento) values(? , ? )");
            
            consulta.setString(1, nombre);
            consulta.setDate(2, fecha_nacimiento);
            
            
            return consulta.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return 0;
    }
    
   public int ObtenerIdPersona(String nombre) {
         
       
        int id = 0;
        try {
            
            
             PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                     + "SELECT id from persona where nombre = ?");
             consulta.setString(1, nombre);
             
             
             ResultSet resultado = consulta.executeQuery();
             
             while(resultado.next()){
                id = resultado.getInt("id");
             }
             
             
        } catch (ClassNotFoundException | SQLException e) {
        }
        
        return id;
       
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

    public void eliminarper(int idper) throws ClassNotFoundException, SQLException {
        try {
            PreparedStatement consulta = Conexion.conectar().prepareStatement("DELETE FROM persona WHERE id = ?");

            consulta.setInt(1, idper);

            int res = consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Persona eliminado");

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void editar(Persona mpersona) throws ClassNotFoundException, SQLException {
        try {

            PreparedStatement consulta = Conexion.conectar().prepareStatement(""
                    + "UPDATE persona SET nombre = ?, fecha_nacimiento = ? WHERE id = ?");
            consulta.setString(1, mpersona.getNombre());
            consulta.setDate(2, mpersona.getFecha_nacimiento());
            consulta.setInt(3, mpersona.getId());
           

            int resultado = consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Persona actualizado");

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        
    }

    
    
    
}
