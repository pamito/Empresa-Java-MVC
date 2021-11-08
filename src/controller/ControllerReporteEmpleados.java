/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.*;
import Model.Conexion;
import Model.Empleado;
import Model.Empresa;
import Model.Persona;
import View.GUIempleado;
import View.VistaEmpleados;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Pablo
 */
public class ControllerReporteEmpleados implements ActionListener{
    
   
    
      private Conexion conexion;
      
      private VistaEmpleados vistae = new VistaEmpleados();
      
      private Empleado mempleado = new Empleado();

    public ControllerReporteEmpleados(VistaEmpleados vistae) {
        
        this.vistae = vistae;
        this.vistae.btnregresar.addActionListener(this);
        reporteEmpleados();
        
    }
      
      
      

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if(ae.getSource()== vistae.btnregresar){
            try {
                 GUIempleado gui = new GUIempleado();
                 ControllerEmpleados controllerem = new ControllerEmpleados(gui);
                 controllerem.comboboxcategoria();
                 controllerem.comboboxempresa();
                 
                 gui.setTitle("Empresa Pablo Mayorga Grupo 1");
                 gui.setLocationRelativeTo(null);
                 gui.setVisible(true);
                 vistae.dispose();
             } catch (SQLException ex) {
                 Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        
    }
    
    
    public void reporteEmpleados() {
        ArrayList<Empleado> reporteEmpleados = new ArrayList<>();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {

            reporteEmpleados = mempleado.getEmpleados(Conexion.conectar());

            for (int i = 0; i < reporteEmpleados.size(); i++) {
                dataset.setValue(reporteEmpleados.get(i).getSueldo_bruto(), 
                        String.valueOf(reporteEmpleados.get(i).getSueldo_bruto()), 
                        reporteEmpleados.get(i).getNom_empresa());
                
                JFreeChart graficoBarras = 
                        ChartFactory.createBarChart3D("Reporte Empleados", 
                                "Empleado", "Valor Nomina", dataset);
                ChartPanel panel = new ChartPanel(graficoBarras);
                panel.setMouseWheelEnabled(true);
                panel.setPreferredSize(new Dimension(500,300));
                vistae.ReporteTrabajadores.setLayout(new BorderLayout());
                vistae.ReporteTrabajadores.add(panel,BorderLayout.SOUTH);
            }

        } catch (ClassNotFoundException | SQLException e) {
        }

    }
    
}
