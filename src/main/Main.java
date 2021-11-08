/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;



import View.*;
import controller.*;
import Model.*;
import java.sql.SQLException;





/**
 *
 * @author Pablo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
        Conexion.conectar();
                
        GUIprincipal gui = new GUIprincipal();
        ControllerPrincipal controller = new ControllerPrincipal(gui);
        gui.setTitle("Empresa Pablo Mayorga Grupo 1");
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
        
        
    }
    
}
