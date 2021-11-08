/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pablo
 */
public class ControllerPrincipal implements ActionListener {
    
    private GUIprincipal vista = new GUIprincipal();
    

    public ControllerPrincipal(GUIprincipal vista) {
        
        this.vista = vista;
        this.vista.ButtonEmpleado.addActionListener(this);
        this.vista.ButtonCliente.addActionListener(this);
        this.vista.ButtonEmpresa.addActionListener(this);
        this.vista.btncargo.addActionListener(this);
        
    }
    
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        
         if(ae.getSource()== vista.btncargo){
             
                              
                 GUIdirectivo vistadirectivo = new GUIdirectivo();
             try {
                 ControllerDirectivo controllerdirec = new ControllerDirectivo(vistadirectivo);
             } catch (SQLException ex) {
                 Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
             }
                 
                 vistadirectivo.setTitle("Empresa Pablo Mayorga Grupo 1");
                 vistadirectivo.setLocationRelativeTo(null);
                 vistadirectivo.setVisible(true);
                 vista.dispose();
              
         }
         
        
         if(ae.getSource()== vista.ButtonEmpresa){
             
                              
                 GUIempresa vistaempresa = new GUIempresa();
             try {
                 ControllerEmpresa controllerempresa = new ControllerEmpresa(vistaempresa);
             } catch (SQLException ex) {
                 Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
             }
                 
                 vistaempresa.setTitle("Empresa Pablo Mayorga Grupo 1");
                 vistaempresa.setLocationRelativeTo(null);
                 vistaempresa.setVisible(true);
                 vista.dispose();
              
         }
        
         if(ae.getSource() == vista.ButtonEmpleado){
             try {
                 GUIempleado gui = new GUIempleado();
                 ControllerEmpleados controllerem = new ControllerEmpleados(gui);
                 controllerem.comboboxcategoria();
                 controllerem.comboboxempresa();
                 
                 gui.setTitle("Empresa Pablo Mayorga Grupo 1");
                 gui.setLocationRelativeTo(null);
                 gui.setVisible(true);
                 vista.dispose();
             } catch (SQLException ex) {
                 Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        if(ae.getSource()== vista.ButtonCliente){
            
             try {
                 GUIcliente guicli = new GUIcliente();
                 ControllerClientes controller = new ControllerClientes(guicli);
                 controller.comboboxempresa();
                 
                 guicli.setTitle("Empresa Pablo Mayorga Grupo 1");
                 guicli.setLocationRelativeTo(null);
                 guicli.setVisible(true);
                 vista.dispose();
             } catch (SQLException ex) {
                 Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
             }
        
        
        
        
        }
    }
    
}
