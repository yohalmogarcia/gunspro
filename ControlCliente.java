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
import modelo.Cliente;
import modelo.ConsultasCliente;
import modelo.ConsultasEmpresas;
import vista.frmClientes;

public class ControlCliente implements ActionListener, MouseListener {

    private Cliente cliente;
    private ConsultasCliente consultas;
    private ConsultasEmpresas consultasEmpresas;
    private frmClientes frame;
    DefaultTableModel modelo = new DefaultTableModel();

    public ControlCliente(Cliente cliente, ConsultasCliente consultas, frmClientes frame,ConsultasEmpresas consultasEmpresas) {
        this.cliente = cliente;
        this.consultas = consultas;
        this.frame = frame;
        this.consultasEmpresas=consultasEmpresas;

        //listeners
        frame.btnAgregar.addActionListener(this);
        frame.btnEliminar.addActionListener(this);
        frame.btnModificar.addActionListener(this);
        frame.btnNuevoCliente.addActionListener(this);
        frame.tblClientes.addMouseListener(this);
        frame.btnVerClientes.addActionListener(this);

    }

    public void cargar() {
        frame.btnAgregar.setEnabled(false);
        llenarComboEmpresas();
        //llenarTablaClientes();
    }
    
    public void llenarComboEmpresas(){
        try {
            ResultSet resultados = consultasEmpresas.mostrarEmpresas();
            while(resultados.next()){
                frame.comboEmpresas.addItem(String.valueOf(resultados.getInt("id_empresa")) + " - " + resultados.getString("nombre"));
                frame.comboEmpresasTabla.addItem(String.valueOf(resultados.getInt("id_empresa")) + " - " + resultados.getString("nombre"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR AL LLENAR EL COMBOBOX DE EMPRESAS. "+ex);
        }
    }

    public void limpiarCampos() {
        frame.txtCodigo.setText("");
        frame.txtRegistro.setText("");
        frame.txtCliente.setText("");
        frame.txtDireccion.setText("");
        frame.txtNIT.setText("");
        frame.txtGiro.setText("");
        frame.txtNumLicencia.setText("");
        frame.btnAgregar.setEnabled(false);
    }

    public boolean revisarCampos() {
        if(frame.comboEmpresas.getSelectedItem().toString().equals("")){
            return true;
        }
        if (frame.txtCodigo.getText().equals("")) {
            return true;
        }
        if (frame.txtRegistro.getText().equals("")) {
            return true;
        }
        if (frame.txtCliente.getText().equals("")) {
            return true;
        }
        if (frame.txtDireccion.getText().equals("")) {
            return true;
        }
        if (frame.txtNIT.getText().equals("")) {
            return true;
        }
        if (frame.txtGiro.getText().equals("")) {
            return true;
        }
        if(frame.txtNumLicencia.getText().equals("")){
            return true;
        }
        return false;
    }

    public boolean revisarCamposSinID() {
        if(frame.comboEmpresas.getSelectedItem().toString().equals("")){
            return true;
        }
        if (frame.txtRegistro.getText().equals("")) {
            return true;
        }
        if (frame.txtCliente.getText().equals("")) {
            return true;
        }
        if (frame.txtDireccion.getText().equals("")) {
            return true;
        }
        if (frame.txtNIT.getText().equals("")) {
            return true;
        }
        if (frame.txtGiro.getText().equals("")) {
            return true;
        }
        if(frame.txtNumLicencia.getText().equals("")){
            return true;
        }
        return false;
    }

    public void llenarTablaClientes() {
        try {
            String idEmpresa = frame.comboEmpresasTabla.getSelectedItem().toString().trim();
            idEmpresa = idEmpresa.substring(0, 1);
            frame.tblClientes.setModel(modelo);
            ResultSet resultados = consultas.mostrarClienteActivos(Integer.parseInt(idEmpresa));
            modelo.addColumn("ID");
            modelo.addColumn("Registro");
            modelo.addColumn("Nombre");
            modelo.addColumn("Direccion");
            modelo.addColumn("NIT");
            modelo.addColumn("Giro");            
            while (resultados.next()) {
                Object[] filas = new Object[6];
                for (int i = 0; i < 6; i++) {
                    filas[i] = resultados.getObject(i + 1);
                }
                modelo.addRow(filas);
            }
        } catch (SQLException ex) {
            System.out.println("Error al llenar la tabla de clientes. " + ex);
        }
    }

    public void limpiarTabla() {
        modelo.setRowCount(0);
        modelo.setColumnCount(0);
        frame.btnAgregar.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==frame.btnVerClientes){
            limpiarCampos();
            limpiarTabla();
            llenarTablaClientes();
        }
        
        if (e.getSource() == frame.btnAgregar) {
            if (!revisarCamposSinID()) {
                String idEmpresa = frame.comboEmpresas.getSelectedItem().toString().trim();
                idEmpresa = idEmpresa.substring(0, 1);
                cliente.setCodigo_empresa(Integer.parseInt(idEmpresa));
                cliente.setRegistro(frame.txtRegistro.getText());
                cliente.setNombre(frame.txtCliente.getText());
                cliente.setDireccion(frame.txtDireccion.getText());
                cliente.setNIT(frame.txtNIT.getText());
                cliente.setGiro(frame.txtGiro.getText());
                cliente.setNumero_licencia(frame.txtNumLicencia.getText().trim());
                if (consultas.agregarCliente(cliente)) {
                    JOptionPane.showMessageDialog(null, "Se ingreso un nuevo cliente exitosamente.");
                    limpiarCampos();
                    limpiarTabla();
                    llenarTablaClientes();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un error al ingresar un nuevo cliente.\nIntenete mas tarde.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Favor ingresar datos correctos al formulario.");
            }
        }

        if (e.getSource() == frame.btnModificar) {
            if (!revisarCampos()) {
                cliente.setCodigo(Integer.parseInt(frame.txtCodigo.getText()));
                cliente.setRegistro(frame.txtRegistro.getText());
                cliente.setNombre(frame.txtCliente.getText());
                cliente.setDireccion(frame.txtDireccion.getText());
                cliente.setNIT(frame.txtNIT.getText());
                cliente.setGiro(frame.txtGiro.getText());
                cliente.setNumero_licencia(frame.txtNumLicencia.getText().trim());
                if (consultas.modificarCliente(cliente)) {
                    JOptionPane.showMessageDialog(null, "Se actualizo el cliente con exito.");
                    limpiarCampos();
                    limpiarTabla();
                    llenarTablaClientes();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un error al modificar dicho cliente.\nIntente mas tarde.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Favor ingresar datos correctos al formulario");
            }
        }

        if (e.getSource() == frame.btnEliminar) {
            if (frame.txtCodigo.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Favor seleccione un cliente de la lista de la izquierda.");
            } else {
                cliente.setCodigo(Integer.parseInt(frame.txtCodigo.getText()));
                int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar dicho cliente?");
                if (respuesta == JOptionPane.YES_OPTION) {
                    if (consultas.eliminarCliente(cliente)) {
                        JOptionPane.showMessageDialog(null, "Se elimino el cliente con exito.");
                        limpiarCampos();
                        limpiarTabla();
                        llenarTablaClientes();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar eliminar el cliente.\nIntentar mas tarde.");
                    }
                }
            }
        }
        
        if(e.getSource()==frame.btnNuevoCliente){
            limpiarCampos();
            frame.comboEmpresas.setEnabled(true);
            frame.btnAgregar.setEnabled(true);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int filaSeleccionada=frame.tblClientes.getSelectedRow();
        String codigo=frame.tblClientes.getValueAt(filaSeleccionada, 0).toString();
        frame.btnAgregar.setEnabled(false);
        cliente.setCodigo(Integer.parseInt(codigo));
        if(consultas.buscarCliente(cliente)){
            frame.comboEmpresas.setEnabled(false);
            frame.txtCodigo.setText(String.valueOf(cliente.getCodigo()));
            frame.txtRegistro.setText(cliente.getRegistro());
            frame.txtCliente.setText(cliente.getNombre());
            frame.txtDireccion.setText(cliente.getDireccion());
            frame.txtNIT.setText(cliente.getNIT());
            frame.txtGiro.setText(cliente.getGiro());
            frame.txtNumLicencia.setText(cliente.getNumero_licencia());
        }else{
            JOptionPane.showMessageDialog(null, "Ocurrio un error al seleccionar el proveedor.\nIntente mas tarde.");
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
