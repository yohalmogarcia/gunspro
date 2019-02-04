package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultasEmpresas;
import modelo.ConsultasProveedor;
import modelo.Proveedor;
import vista.frmProveedores;

/**
 * @author Yohalmo Garcia
 */
public class ControlProveedor implements ActionListener, MouseListener {

    private Proveedor proveedor;
    private ConsultasProveedor consultas;
    private ConsultasEmpresas consultasEmpresas;
    private frmProveedores frame;
    DefaultTableModel modelo = new DefaultTableModel();

    public ControlProveedor(Proveedor proveedor, ConsultasProveedor consultas, frmProveedores frame,ConsultasEmpresas consultasEmpresas) {
        this.proveedor = proveedor;
        this.consultas = consultas;
        this.frame = frame;
        this.consultasEmpresas=consultasEmpresas;

        //listener
        frame.btnAgregar.addActionListener(this);
        frame.btnEliminar.addActionListener(this);
        frame.btnModificar.addActionListener(this);
        frame.btnNuevo.addActionListener(this);
        frame.tblProveedores.addMouseListener(this);
        frame.btnVerClientes.addActionListener(this);

    }

    public void cargar() {
        //llenarTablaProveedores();
        llenarComboEmpresas();
        frame.btnAgregar.setEnabled(false);
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

    public void llenarTablaProveedores() {
        try {
            String idEmpresa = frame.comboEmpresasTabla.getSelectedItem().toString().trim();
            idEmpresa = idEmpresa.substring(0, 1);
            frame.tblProveedores.setModel(modelo);            
            ResultSet resultados = consultas.mostrarProveedoresActivos(Integer.parseInt(idEmpresa));
            ResultSetMetaData rsMD = resultados.getMetaData();
            modelo.addColumn("ID");
            modelo.addColumn("Nombre");
            modelo.addColumn("Dirección");
            modelo.addColumn("Contacto");
            modelo.addColumn("Telefonos");
            
            while (resultados.next()) {
                Object[] filas = new Object[5];
                for (int i = 0; i < 5; i++) {
                    filas[i] = resultados.getObject(i + 1);
                }
                modelo.addRow(filas);
            }
        } catch (SQLException ex) {
            System.out.println("ERRor al llenar la tabla de proveedores. " + ex);
        }

    }

    public void limpiarTablaProveedores() {
        modelo.setColumnCount(0);
        modelo.setRowCount(0);
        frame.btnAgregar.setEnabled(false);
    }

    public void limpiarCampos() {
        frame.txtCodigo.setText("");
        frame.txtNombre.setText("");
        frame.txtDireccion.setText("");
        frame.txtContacto.setText("");
        frame.txtTelefono.setText("");
        frame.txtNumIVA.setText("");
    }

    public boolean revisarCampos() {
        if(frame.comboEmpresas.getSelectedItem().toString().equals("")){
            return true;
        }
        if (frame.txtCodigo.getText().equals("")) {
            return true;
        }
        if (frame.txtNombre.getText().equals("")) {
            return true;
        }
        if (frame.txtDireccion.getText().equals("")) {
            return true;
        }
        if (frame.txtContacto.getText().equals("")) {
            return true;
        }
        if (frame.txtTelefono.getText().equals("")) {
            return true;
        }
        if(frame.txtNumIVA.getText().equals("")){
            return true;
        }
        return false;
    }

    public boolean revisarCamposSinID() {
        if(frame.comboEmpresas.getSelectedItem().toString().equals("")){
            return true;
        }
        if (frame.txtNombre.getText().equals("")) {
            return true;
        }
        if (frame.txtDireccion.getText().equals("")) {
            return true;
        }
        if (frame.txtContacto.getText().equals("")) {
            return true;
        }
        if (frame.txtTelefono.getText().equals("")) {
            return true;
        }
        if(frame.txtNumIVA.getText().equals("")){
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == frame.btnVerClientes){
            limpiarCampos();
            limpiarTablaProveedores();
            llenarTablaProveedores();
        }
        
        if (e.getSource() == frame.btnAgregar) {
            if (!revisarCamposSinID()) {
                String idEmpresa = frame.comboEmpresas.getSelectedItem().toString().trim();
                idEmpresa = idEmpresa.substring(0, 1);
                proveedor.setId_empresa(Integer.parseInt(idEmpresa));
                proveedor.setNombre(frame.txtNombre.getText());
                proveedor.setDireccion(frame.txtDireccion.getText());
                proveedor.setContacto(frame.txtContacto.getText());
                proveedor.setTelefono(frame.txtTelefono.getText());
                proveedor.setNum_iva(frame.txtNumIVA.getText());
                if (consultas.agregarProveedor(proveedor)) {
                    JOptionPane.showMessageDialog(null, "Se agrego un nuevo proveedor exitosamente.");
                    limpiarTablaProveedores();
                    llenarTablaProveedores();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un error al agregar un proveedor nuevo.\nIntente mas tarde.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Agrega informacion correcta en el formulario.");
            }

        }
        if (e.getSource() == frame.btnModificar) {
            if(!revisarCampos()){
                proveedor.setCodigoProveedor(Integer.parseInt(frame.txtCodigo.getText()));
                proveedor.setNombre(frame.txtNombre.getText());
                proveedor.setDireccion(frame.txtDireccion.getText());
                proveedor.setContacto(frame.txtContacto.getText());
                proveedor.setTelefono(frame.txtTelefono.getText());
                proveedor.setNum_iva(frame.txtNumIVA.getText());
                if(consultas.modificarProveedor(proveedor)){
                    JOptionPane.showMessageDialog(null, "Se actualizo el proveedor exitosamente");
                    limpiarCampos();
                    limpiarTablaProveedores();
                    llenarTablaProveedores();
                }else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar modificar el proveedor.\nIntente mas tarde.");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Favor agregar informacion correcta en el formulario.");
            }
        }
        
        if(e.getSource()==frame.btnEliminar){
            if(frame.txtCodigo.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Favor seleccione un proveedor de la lista de la izquierda.");
            }else{
                int respuesta=JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este proveedor?");
                if(respuesta==JOptionPane.YES_OPTION){
                    proveedor.setCodigoProveedor(Integer.parseInt(frame.txtCodigo.getText()));
                    if(consultas.eliminarProveedor(proveedor)){
                        JOptionPane.showMessageDialog(null, "El Proveedor se elimino con exito");
                        limpiarCampos();
                        limpiarTablaProveedores();
                        llenarTablaProveedores();
                    }else{
                        JOptionPane.showMessageDialog(null, "Ocurio un error al intentar eliminar el proveedor.\nIntente mas tarde.");
                    }
                }
            }
        }
        
        if(e.getSource()==frame.btnNuevo){
            limpiarCampos();
            frame.btnAgregar.setEnabled(true);
            frame.comboEmpresas.setEnabled(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int filaSeleccionada=frame.tblProveedores.getSelectedRow();
        String codigo=frame.tblProveedores.getValueAt(filaSeleccionada, 0).toString();
        String idEmpresa = frame.comboEmpresasTabla.getSelectedItem().toString().trim();
        idEmpresa = idEmpresa.substring(0, 1);
        frame.btnAgregar.setEnabled(false);
        proveedor.setCodigoProveedor(Integer.parseInt(codigo));
        proveedor.setId_empresa(Integer.parseInt(idEmpresa));
        if(consultas.buscarProveedor(proveedor)){
            frame.comboEmpresas.setEnabled(false);
            frame.txtCodigo.setText(String.valueOf(proveedor.getCodigoProveedor()));
            frame.txtNombre.setText(proveedor.getNombre());
            frame.txtDireccion.setText(proveedor.getDireccion());
            frame.txtContacto.setText(proveedor.getContacto());
            frame.txtTelefono.setText(proveedor.getTelefono());
            frame.txtNumIVA.setText(proveedor.getNum_iva());
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
