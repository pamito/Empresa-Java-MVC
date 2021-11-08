/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Model.Conexion;
import Model.Directivo;
import Model.Empresa;
import View.GUIdirectivo;
import View.GUIempresa;
import View.GUIprincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo
 */
public class ControllerDirectivo implements ActionListener {

    private Directivo mdirectivo = new Directivo();

    private GUIdirectivo vistadirectivo = new GUIdirectivo();

    public ControllerDirectivo(GUIdirectivo vistadirectivo) throws SQLException, ClassNotFoundException {

        this.vistadirectivo = vistadirectivo;
        this.vistadirectivo.btnregresar.addActionListener(this);
        this.vistadirectivo.btnguardardir.addActionListener(this);
        this.vistadirectivo.btnactualizardirectivo.addActionListener(this);
        this.vistadirectivo.btneliminarcargo.addActionListener(this);
        agregarCargoTabla();

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if(ae.getSource() == vistadirectivo.btneliminarcargo){
            int row = vistadirectivo.tablacargo.getSelectedRow();
            String id = vistadirectivo.tablacargo.getValueAt(row,0).toString();
            int clave = Integer.parseInt(id);
            Directivo directivo = new Directivo();
           
            try {
                directivo.eliminar(clave);
                agregarCargoTabla();
            } catch (SQLException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(ae.getSource() == vistadirectivo.btnactualizardirectivo){
            int row = vistadirectivo.tablacargo.getSelectedRow();
            String id = vistadirectivo.tablacargo.getValueAt(row,0).toString();
            int clave = Integer.parseInt(id);
            Directivo directivo = new Directivo(clave, Integer.parseInt(vistadirectivo.txtcodigo.getText()), 
                    vistadirectivo.txtnombrecargo.getText());
           
            try {
                directivo.editar(directivo);
                agregarCargoTabla();
            } catch (SQLException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                  
        }

        if (ae.getSource() == vistadirectivo.btnregresar) {
            GUIprincipal gui = new GUIprincipal();
            ControllerPrincipal controller = new ControllerPrincipal(gui);
            gui.setTitle("Empresa Pablo Mayorga Grupo 1");
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
            vistadirectivo.dispose();
        }

        if (ae.getSource() == vistadirectivo.btnguardardir) {

            mdirectivo = new Directivo(Integer.parseInt(vistadirectivo.txtcodigo.getText()), vistadirectivo.txtnombrecargo.getText());

            int resultado = mdirectivo.guardarDirectivo(mdirectivo);
            if (resultado == 1) {
                JOptionPane.showMessageDialog(null, "El Cargo fue guardado con exito");
                limpiarCtlsTexto();
                try {
                    agregarCargoTabla();
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerDirectivo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControllerDirectivo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }
    
    public ArrayList<Directivo>  getDirectivos() throws SQLException, ClassNotFoundException{
        ArrayList<Directivo> directivos = new ArrayList<>();
        directivos = mdirectivo.getDirectivo(Conexion.conectar());
        //System.out.println(trabajadores);
        return directivos;
    }
    public void agregarCargoTabla() throws SQLException, ClassNotFoundException{
        
        String matriz [][] = new String[getDirectivos().size()][3];
        
        for (int i = 0; i < getDirectivos().size(); i++) {
            matriz[i][0] = String.valueOf(getDirectivos().get(i).getIdc());
            matriz[i][1] = String.valueOf(getDirectivos().get(i).getCategoria());
            matriz[i][2] = getDirectivos().get(i).getNombrec();
            
        }
        
        vistadirectivo.tablacargo.setModel(new javax.swing.table.DefaultTableModel(
            matriz,
            new String [] {
                "Id","Codigo","Nombre"
            }
        ));
    }
    
    public void limpiarCtlsTexto(){
        vistadirectivo.txtcodigo.setText("");
        vistadirectivo.txtnombrecargo.setText("");
        
    }

}
