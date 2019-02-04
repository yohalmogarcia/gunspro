package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultasEmpresas;
import modelo.Empresas;
import vista.frmEmpresas;

public class ControlEmpresas implements ActionListener, MouseListener{
    
    private Empresas empresa;
    private ConsultasEmpresas consultas;
    private frmEmpresas frame;
    DefaultTableModel modelo = new DefaultTableModel();

    public ControlEmpresas(Empresas empresa, ConsultasEmpresas consultas, frmEmpresas frame) {
        this.empresa = empresa;
        this.consultas = consultas;
        this.frame = frame;
        
        //listeners para poder escuchar las acciones del frame
        frame.btnAgregar.addActionListener(this);
        frame.btnEliminar.addActionListener(this);
        frame.btnModificar.addActionListener(this);
        frame.btnNuevaEmpresa.addActionListener(this);
        frame.tblEmpresas.addMouseListener(this);
    }
    
    public void cargar(){
        frame.btnAgregar.setEnabled(false);
        llenarTablaEmpresas();
    }
    
    public void limpiarCampos(){
        frame.txtCodigo.setText("");
        frame.txtContacto.setText("");
        frame.txtDireccion.setText("");
        frame.txtNit.setText("");
        frame.txtNombre.setText("");
        frame.txtRazon.setText("");
        frame.txtRegistro.setText("");
        frame.txtTelefono.setText("");
        frame.btnAgregar.setEnabled(false);
    }  
    
    public boolean revisarCampos(){
        if(frame.txtCodigo.equals("")){
            return true;
        }
        if(frame.txtContacto.equals("")){
            return true;
        }
        if(frame.txtDireccion.equals("")){
            return true;
        }
        if(frame.txtNit.equals("")){
            return true;
        }
        if(frame.txtNombre.equals("")){
            return true;
        }
        if(frame.txtRazon.equals("")){
            return true;
        }
        if(frame.txtRegistro.equals("")){
            return true;
        }
        if(frame.txtTelefono.equals("")){
            return true;
        }
        return false;
    }
    
    public boolean revisarCamposSinID(){
        if(frame.txtContacto.equals("")){
            return true;
        }
        if(frame.txtDireccion.equals("")){
            return true;
        }
        if(frame.txtNit.equals("")){
            return true;
        }
        if(frame.txtNombre.equals("")){
            return true;
        }
        if(frame.txtRazon.equals("")){
            return true;
        }
        if(frame.txtRegistro.equals("")){
            return true;
        }
        if(frame.txtTelefono.equals("")){
            return true;
        }
        return false;
    }
    
    public void llenarTablaEmpresas(){
        try {
            frame.tblEmpresas.setModel(modelo);
            ResultSet resultados = consultas.mostrarEmpresas();
            modelo.addColumn("ID");
            modelo.addColumn("NOMBRE");
            modelo.addColumn("NIT");
            modelo.addColumn("NUMERO REGISTRO");
            modelo.addColumn("DIRECCION");
            modelo.addColumn("TELEFONO");
            modelo.addColumn("CONTACTO");
            
            while(resultados.next()){
                Object[] filas = new Object[7];
                for(int i=0;i<7;i++){
                    filas[i] = resultados.getObject(i+1);
                }
                modelo.addRow(filas);
            }
        } catch (SQLException ex) {
            System.out.println("ERROR AL ITERAR LA TABLA DE EMPRESAS.");
        }
        
    }
    
    public void limpiarTabla(){
        modelo.setRowCount(0);
        modelo.setColumnCount(0);
        frame.btnAgregar.setEnabled(false);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == frame.btnAgregar){
            if(!revisarCamposSinID()){
                empresa.setNombre(frame.txtNombre.getText());
                empresa.setRazon(frame.txtRazon.getText());
                empresa.setNumero_registro(frame.txtRegistro.getText());
                empresa.setDireccion(frame.txtDireccion.getText());
                empresa.setTelefono(frame.txtTelefono.getText());
                empresa.setContacto(frame.txtContacto.getText());
                empresa.setNit(frame.txtNit.getText());
                if(consultas.agregarEmpresa(empresa)){
                    JOptionPane.showMessageDialog(null, "Se ingreso un nuevo registro exitosamente.");
                    limpiarCampos();
                    limpiarTabla();
                    llenarTablaEmpresas();
                }else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar agregar un nuevo registro.\nComuniquese con un administrador.");
                }
            } else{
                JOptionPane.showMessageDialog(null, "Favor ingresar datos correctos al formulario.");
            }
        }
        
        if(e.getSource()==frame.btnModificar){
            if(!revisarCampos()){
                empresa.setId_empresa(Integer.parseInt(frame.txtCodigo.getText()));
                empresa.setNombre(frame.txtNombre.getText());
                empresa.setRazon(frame.txtRazon.getText());
                empresa.setNumero_registro(frame.txtRegistro.getText());
                empresa.setDireccion(frame.txtDireccion.getText());
                empresa.setTelefono(frame.txtTelefono.getText());
                empresa.setContacto(frame.txtContacto.getText());
                empresa.setNit(frame.txtNit.getText());
                if(consultas.actualizarEmpresa(empresa)){
                    JOptionPane.showMessageDialog(null, "Se Actualizo el registro exitosamente.");
                    limpiarCampos();
                    limpiarTabla();
                    llenarTablaEmpresas();
                }else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar actualizar el registro.\nFavor comuniquese con un administrador");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Favor ingresar datos correctos al formulario.");
            }
        }
        
        if(e.getSource()==frame.btnEliminar){
            if(frame.txtCodigo.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Favor seleccione una empresa de la lista de la izquierda.");
            }else{
                empresa.setId_empresa(Integer.parseInt(frame.txtCodigo.getText()));
                int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar dicha empresa?");
                if(respuesta==JOptionPane.YES_OPTION){
                    if(consultas.eliminarEmpresa(empresa)){
                        JOptionPane.showMessageDialog(null, "Se elimino el reistro exitosamente.");
                        limpiarCampos();
                        limpiarTabla();
                        llenarTablaEmpresas();
                    }else{
                        JOptionPane.showMessageDialog(null, "");
                    }
                }
            }
        }
        
        if(e.getSource()==frame.btnNuevaEmpresa){
            limpiarCampos();
            frame.btnAgregar.setEnabled(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int filaSeleccionada  = frame.tblEmpresas.getSelectedRow();
        String codigo = frame.tblEmpresas.getValueAt(filaSeleccionada, 0).toString();
        frame.btnAgregar.setEnabled(false);
        empresa.setId_empresa(Integer.parseInt(codigo));
        if(consultas.buscarEmpresa(empresa)){
            frame.txtCodigo.setText(String.valueOf(empresa.getId_empresa()));
            frame.txtNombre.setText(empresa.getNombre());
            frame.txtRazon.setText(empresa.getRazon());
            frame.txtRegistro.setText(empresa.getNumero_registro());
            frame.txtDireccion.setText(empresa.getDireccion());
            frame.txtTelefono.setText(empresa.getTelefono());
            frame.txtContacto.setText(empresa.getContacto());
            frame.txtNit.setText(empresa.getNit());
        }else{
            JOptionPane.showMessageDialog(null, "Ocurrio un error al seleccionar una empresa.\nComuniquese con un administrador");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
