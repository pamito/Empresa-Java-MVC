/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Model.Conexion;
import Model.Empresa;
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
public class ControllerEmpresa implements ActionListener {

    private Empresa mempresa = new Empresa();

    private GUIempresa vista = new GUIempresa();

    private Conexion conexion;

    public ControllerEmpresa(GUIempresa vista) throws SQLException, ClassNotFoundException {

        this.vista = vista;
        this.vista.btnguardarempre.addActionListener(this);
        this.vista.btnactualizarempresa.addActionListener(this);
        this.vista.btneliminarempresa.addActionListener(this);
        this.vista.btnregresar.addActionListener(this);
        agregarEmppresasATabla();

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == vista.btneliminarempresa) {
            int row = vista.tablaempresa.getSelectedRow();
            String id = vista.tablaempresa.getValueAt(row, 0).toString();
            int clave = Integer.parseInt(id);
            Empresa empresa = new Empresa();

            try {
                empresa.eliminar(clave);
                agregarEmppresasATabla();
            } catch (SQLException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ae.getSource() == vista.btnactualizarempresa) {
            int row = vista.tablaempresa.getSelectedRow();
            String id = vista.tablaempresa.getValueAt(row, 0).toString();
            int clave = Integer.parseInt(id);
            Empresa empresa = new Empresa(clave, vista.texnomempresa.getText(),
                    vista.txtcifempresa.getText());

            try {
                empresa.editar(empresa);
                agregarEmppresasATabla();
            } catch (SQLException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (ae.getSource() == vista.btnregresar) {
            GUIprincipal gui = new GUIprincipal();
            ControllerPrincipal controller = new ControllerPrincipal(gui);
            gui.setTitle("Empresa Pablo Mayorga Grupo 1");
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
            vista.dispose();
        }

        if (ae.getSource() == vista.btnguardarempre) {

            mempresa = new Empresa(vista.texnomempresa.getText(), vista.txtcifempresa.getText());

            int resultado = mempresa.guardarEmpresa(mempresa);
            if (resultado == 1) {
                JOptionPane.showMessageDialog(null, "La empresa fue guardado con exito");
                limpiarTabla();
                try {
                    agregarEmppresasATabla();
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    public ArrayList<Empresa> getEmpresas() throws SQLException, ClassNotFoundException {
        ArrayList<Empresa> empresas = new ArrayList<>();
        empresas = mempresa.getEmpresas(Conexion.conectar());

        return empresas;
    }

    public void agregarEmppresasATabla() throws SQLException, ClassNotFoundException {

        String matriz[][] = new String[getEmpresas().size()][3];

        for (int i = 0; i < getEmpresas().size(); i++) {
            matriz[i][0] = String.valueOf(getEmpresas().get(i).getId());
            matriz[i][1] = getEmpresas().get(i).getNombre();
            matriz[i][2] = getEmpresas().get(i).getCif();

        }

        vista.tablaempresa.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                new String[]{
                    "Id", "Nombre", "CIF"
                }
        ));

    }

    public void limpiarTabla() {
        vista.texnomempresa.setText("");
        vista.txtcifempresa.setText("");

    }

}
