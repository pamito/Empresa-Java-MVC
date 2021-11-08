/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import Model.*;

import View.*;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import java.util.ArrayList;

import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 *
 * @author Pablo
 */
public class ControllerEmpleados implements ActionListener{
    
     
    
     private Empresa empresa = new Empresa();
    
     private GUIempleado vista = new GUIempleado();
     
     private Empleado mempleado = new Empleado();
     
     private Persona mpersona = new Persona();
     
   
     
     private Conexion conexion;
          
     
    
    
    public ControllerEmpleados(GUIempleado vista) throws SQLException{
         
             this.vista = vista;
             this.vista.BtnGuardaremp.addActionListener(this);
             this.vista.BtnRegresar.addActionListener(this);
             this.vista.btneditar.addActionListener(this);
             this.vista.btneliminar.addActionListener(this);
             this.vista.btngraficos.addActionListener(this);
         try {
             agregarEmpleadosATabla();
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(ControllerEmpleados.class.getName()).log(Level.SEVERE, null, ex);
         }
             
             
         
    }

    
        

     @Override
    public void actionPerformed(ActionEvent ae) {
        
         if (ae.getSource() == vista.btneliminar) {
            int row = vista.TableEmple.getSelectedRow();
            String id = vista.TableEmple.getValueAt(row, 0).toString();
            int idper = mpersona.ObtenerIdPersona(vista.TableEmple.getValueAt(row, 1).toString());
            int clave = Integer.parseInt(id);
            Empleado empleado = new Empleado();

            try {
                empleado.eliminar(clave);
                mpersona.eliminarper(idper);
                agregarEmpleadosATabla();
            } catch (SQLException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
         

        if (ae.getSource() == vista.btneditar) {
            
            int row = vista.TableEmple.getSelectedRow();
            String id = vista.TableEmple.getValueAt(row, 0).toString();
            int idemp = Integer.parseInt(id);
                        
            int idempresa = Empresa.ObtenerIdEmpresa(vista.comboboxempresa.getSelectedItem());
            int idirectivo = Directivo.ObtenerIdDirectivo(vista.comboboxcargo.getSelectedItem());
            int sueldo = Integer.parseInt(vista.TxtSueldo.getText());
            
                     
            int idper = mpersona.ObtenerIdPersona(vista.TableEmple.getValueAt(row, 1).toString());
           
            
            mempleado = new Empleado(idemp, sueldo, idper, idempresa, idirectivo);
            
            mpersona = new Persona(idper, vista.TextNombreEmpleado.getText(),Date.valueOf(vista.TxtFecha.getText()));
            

            try {
                mpersona.editar(mpersona);
                mempleado.editar(mempleado);
                            
                agregarEmpleadosATabla();
            } catch (SQLException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        if(ae.getSource() == vista.BtnGuardaremp){
            
             if(vista.ChkAgregarTrabajador.isSelected()){
            
            mpersona = new Persona(vista.TextNombreEmpleado.getText(),Date.valueOf(vista.TxtFecha.getText()));
            mpersona.guardarPersona(mpersona);
            int idper = mpersona.ObtenerIdPersona(vista.TextNombreEmpleado.getText());
           
            
            int idempresa = Empresa.ObtenerIdEmpresa(vista.comboboxempresa.getSelectedItem());
            int idirectivo = Directivo.ObtenerIdDirectivo(vista.comboboxcargo.getSelectedItem());
            int sueldo = Integer.parseInt(vista.TxtSueldo.getText());
           
            
            mempleado = new Empleado(sueldo, idper, idempresa, idirectivo);
           
            
            int resultado = mempleado.guardarEmpleado(mempleado);
            if(resultado == 1){
                JOptionPane.showMessageDialog(null, "El Empleado fue guardado con exito");
                limpiarTabla();
                try {
                    agregarEmpleadosATabla();
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControllerEmpleados.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            }else{
                
                JOptionPane.showMessageDialog(null, "Para agregar un nuevo empleado"
                    + " debes tener seleccionado el campo 'Agregar empleado'");
                
            }
            
        }
        if(ae.getSource()== vista.BtnRegresar){
            GUIprincipal gui = new GUIprincipal();
            ControllerPrincipal controller = new ControllerPrincipal(gui);
            gui.setTitle("Empresa Pablo Mayorga Grupo 1");
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
            vista.dispose();
        }
        
         if(ae.getSource()== vista.btngraficos){
            VistaEmpleados gui = new VistaEmpleados();
            ControllerReporteEmpleados controller = new ControllerReporteEmpleados(gui);
            gui.setTitle("Empresa Pablo Mayorga Grupo 1");
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
            vista.dispose();
        }
    
    }
    
    public ArrayList<Empleado>  getEmpleados() throws SQLException, ClassNotFoundException{
        ArrayList<Empleado> empleados = new ArrayList<>();
        empleados = mempleado.getEmpleados(conexion.conectar());
        //System.out.println(trabajadores);
        return empleados;
    }

    
    public void agregarEmpleadosATabla() throws SQLException, ClassNotFoundException{
        
        String matriz [][] = new String[getEmpleados().size()][6];
        
        for (int i = 0; i < getEmpleados().size(); i++) {
            matriz[i][0] = String.valueOf(getEmpleados().get(i).getIde());
            matriz[i][1] = getEmpleados().get(i).getNom_empresa();
            matriz[i][2] = String.valueOf(getEmpleados().get(i).getFecha_nacimiento());
            matriz[i][3] = String.valueOf(getEmpleados().get(i).getSueldo_bruto());
            matriz[i][4] = getEmpleados().get(i).getNom_directivo();
            matriz[i][5] = getEmpleados().get(i).getNombre();
            
            
        }
        
        vista.TableEmple.setModel(new javax.swing.table.DefaultTableModel(
            matriz,
            new String [] {
               "Id", "Nombre","Fecha", "Sueldo", "Cargo", "Empresa"
            }
        ));
        
    }


    
    public void limpiarTabla(){
        vista.TextNombreEmpleado.setText("");
        vista.TxtFecha.setText("");
        vista.TxtSueldo.setText("");
        
       
    }
    
   
    
    public void comboboxcategoria () throws SQLException, ClassNotFoundException{
        
         ArrayList<Directivo>directivo= new ArrayList<>();
         directivo = mempleado.getListdirectivo();
         String matriz [] = new String[directivo.size()];
    
                  
         for (int i = 0; i < directivo.size(); i++) {
              matriz[i] = String.valueOf(directivo.get(i).getNombrec());
              System.out.println(matriz[i]);
              
                                  
        }
        
         vista.comboboxcargo.setModel(new javax.swing.DefaultComboBoxModel<>(matriz));
        
    }
    
    public void comboboxempresa () throws SQLException, ClassNotFoundException{
        
         ArrayList<Empresa>empresa= new ArrayList<>();
         empresa = mempleado.getListempresa();
         String matriz [] = new String[empresa.size()];
    
                  
         for (int i = 0; i < empresa.size(); i++) {
              matriz[i] = String.valueOf(empresa.get(i).getNombre());
              System.out.println(matriz[i]);
              
                                  
        }
        
         vista.comboboxempresa.setModel(new javax.swing.DefaultComboBoxModel<>(matriz));
        
    }
    
     


    
     
    
}


   
