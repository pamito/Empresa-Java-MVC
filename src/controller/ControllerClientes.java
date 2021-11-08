/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Model.Cliente;
import Model.Conexion;
import Model.Directivo;
import Model.Empleado;
import Model.Empresa;
import Model.Persona;
import View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo
 */
public class ControllerClientes implements ActionListener {
    
    private Empresa empresa = new Empresa();
    private GUIcliente vista = new GUIcliente();
    private Cliente mcliente = new Cliente();
    private Persona mpersona = new Persona();
     
     
     
     
    
    
    public ControllerClientes(GUIcliente vista) throws SQLException, ClassNotFoundException{
        this.vista = vista;
        this.vista.BtnGuardar.addActionListener(this);
        this.vista.Regresar.addActionListener(this);
        this.vista.btnactualizar.addActionListener(this);
        this.vista.btneliminar.addActionListener(this);
        agregarClientesATabla();
        
    }
        
        

     @Override
    public void actionPerformed(ActionEvent ae) {
        
        if (ae.getSource() == vista.btnactualizar) {
            
            int row = vista.TableCliente.getSelectedRow();
            String id = vista.TableCliente.getValueAt(row, 0).toString();
            int idcli = Integer.parseInt(id);
                        
            int idempresa = Empresa.ObtenerIdEmpresa(vista.Comboxempresa.getSelectedItem());
            int idper = mpersona.ObtenerIdPersona(vista.TableCliente.getValueAt(row, 1).toString());
            String telefono = vista.TxtTelef.getText();
           
            mcliente = new Cliente(idcli, telefono, idempresa, idper);
           
            mpersona = new Persona(idper, vista.TxtNomCliente.getText(),Date.valueOf(vista.TxtFechaCliente.getText()));
            

            try {
                mpersona.editar(mpersona);
                mcliente.editarcli(mcliente);
              
                agregarClientesATabla();
            } catch (SQLException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        if (ae.getSource() == vista.btneliminar) {
            int row = vista.TableCliente.getSelectedRow();
            String id = vista.TableCliente.getValueAt(row, 0).toString();
            int idper = mcliente.ObtenerIdPersona(vista.TableCliente.getValueAt(row, 1).toString());
            int clave = Integer.parseInt(id);
            Cliente cliente = new Cliente();

            try {
                cliente.eliminar(clave);
                mpersona.eliminarper(idper);
                agregarClientesATabla();
            } catch (SQLException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControllerEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(ae.getSource() == vista.BtnGuardar){
            
            if(vista.ChxCliente.isSelected()){
            
            mpersona = new Persona(vista.TxtNomCliente.getText(),Date.valueOf(vista.TxtFechaCliente.getText()));
            mpersona.guardarPersona(mpersona);
            int idper = mpersona.ObtenerIdPersona(vista.TxtNomCliente.getText());
           
            
            int idempresa = Empresa.ObtenerIdEmpresa(vista.Comboxempresa.getSelectedItem());
            
            String telefono = vista.TxtTelef.getText();
           
            
            mcliente = new Cliente(telefono, idempresa, idper);
           
            
            int resultado = mcliente.guardarCliente(mcliente);
            if(resultado == 1){
                JOptionPane.showMessageDialog(null, "El Cliente fue guardado con exito");
                limpiarTabla();
                try {
                    agregarClientesATabla();
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerClientes.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControllerClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            }else{
                
                JOptionPane.showMessageDialog(null, "Para agregar un nuevo Cliente"
                    + " debes tener seleccionado el campo 'Agregar Cliente'");
                
            }
            
        }
        if(ae.getSource()== vista.Regresar){
        GUIprincipal gui = new GUIprincipal();
        ControllerPrincipal controller = new ControllerPrincipal(gui);
        gui.setTitle("Empresa Pablo Mayorga Grupo 1");
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
        vista.dispose();
        
        
        }
    
    }
    
    public ArrayList<Cliente>  getClientes() throws SQLException, ClassNotFoundException{
        ArrayList<Cliente> clientes = new ArrayList<>();
        clientes = mcliente.getClientes(Conexion.conectar());
        //System.out.println(trabajadores);
        return clientes;
    }

    
    public void agregarClientesATabla() throws SQLException, ClassNotFoundException{
        
        String matriz [][] = new String[getClientes().size()][5];
        
        for (int i = 0; i < getClientes().size(); i++) {
            matriz[i][0] = String.valueOf(getClientes().get(i).getIdc());
            matriz[i][1] = getClientes().get(i).getNombre();
            matriz[i][2] = String.valueOf(getClientes().get(i).getFecha_nacimiento());
            matriz[i][3] = String.valueOf(getClientes().get(i).getTelefono());
            matriz[i][4] = getClientes().get(i).getNom_empresa();
                       
            
        }
        
        vista.TableCliente.setModel(new javax.swing.table.DefaultTableModel(
            matriz,
            new String [] {
               "Id", "Nombre","Fecha", "Telefono", "Empresa"
            }
        ));
        
    }

    
    
    public void limpiarTabla(){
        vista.TxtNomCliente.setText("");
        vista.TxtTelef.setText("");
        vista.TxtFechaCliente.setText("");
        
       
    }
    
    
    
    
    public void comboboxempresa () throws SQLException, ClassNotFoundException{
        
        ArrayList<Empresa>empresa= new ArrayList<>();
         empresa = mcliente.getListempresa();
         String matriz [] = new String[empresa.size()];
    
                  
         for (int i = 0; i < empresa.size(); i++) {
              matriz[i] = String.valueOf(empresa.get(i).getNombre());
              System.out.println(matriz[i]);
              
                                  
        }
        
         vista.Comboxempresa.setModel(new javax.swing.DefaultComboBoxModel<>(matriz));
    }

   
    
}
