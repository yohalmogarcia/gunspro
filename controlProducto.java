package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cardex;
import modelo.ConsultasCardex;
import modelo.ConsultasEmpresas;
import modelo.ConsultasProducto;
import modelo.Producto;
import vista.frmImprimirInventario;
import vista.frmProducto;
import vista.index;

public class controlProducto implements ActionListener {

    private Producto mod;
    private ConsultasProducto modConsulta;
    private ConsultasEmpresas consultasEmpresas;
    private frmProducto frame;
    DefaultTableModel modelo = new DefaultTableModel();

    public controlProducto(Producto mod, ConsultasProducto modConsulta, frmProducto frame, ConsultasEmpresas consultasEmpresas) {
        this.mod = mod;
        this.modConsulta = modConsulta;
        this.frame = frame;
        this.consultasEmpresas=consultasEmpresas;

        //botones
        frame.btnAgregarProducto.addActionListener(this);
        frame.btnEliminarProducto.addActionListener(this);
        frame.btnModificarProducto.addActionListener(this);
        frame.btnNuevoProducto.addActionListener(this);
        frame.btnImprimir.addActionListener(this);
        frame.btnVerProductos.addActionListener(this);
    }

    public boolean revisarCampos() {
        if(frame.comboEmpresas.getSelectedItem().toString().equals("")){
            return true;
        }
        if (frame.txtCodigoProducto.getText().trim().equals("")) {
            return true;
        }
        if (frame.txtDescripcionProducto.getText().trim().equals("")) {
            return true;
        }
        if (frame.txtCantidadProducto.getText().trim().equals("")) {
            return true;
        }
        if (frame.txtCOstoProducto.getText().trim().equals("")) {
            return true;
        }
        if (frame.txtPrecioVentaProducto.getText().trim().equals("")) {
            return true;
        }
        if(frame.txtCodigoInterno.getText().trim().equals("")){
            return true;
        }
        return false;
    }
    
    public boolean revisarCamposSinID() {
        if(frame.comboEmpresas.getSelectedItem().toString().equals("")){
            return true;
        }
        if (frame.txtDescripcionProducto.getText().trim().equals("")) {
            return true;
        }
        if (frame.txtCantidadProducto.getText().trim().equals("")) {
            return true;
        }
        if (frame.txtCOstoProducto.getText().trim().equals("")) {
            return true;
        }
        if (frame.txtPrecioVentaProducto.getText().trim().equals("")) {
            return true;
        }
        if(frame.txtCodigoInterno.getText().trim().equals("")){
            return true;
        }
        return false;
    }
    public void cargar() {
        //llenarTablaProductos();
        llenarComboEmpresas();
        frame.btnAgregarProducto.setEnabled(false);  
        frame.txtCodigoProducto.setVisible(false);
        frame.txtCodigoInternoOld.setVisible(false);
        if(index.txtTipoUsuario.getText().equals("2")){
            frame.txtCantidadProducto.setEnabled(false);
        }
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

    public void llenarTablaProductos() {
        try {
            String idEmpresa = frame.comboEmpresasTabla.getSelectedItem().toString().trim();
            idEmpresa = idEmpresa.substring(0, 1);
            String palabraClave = frame.txtPalabraClave.getText().trim();
            frame.tblProductos.setModel(modelo);            
            ResultSet resultados = modConsulta.mostrarProductos(Integer.parseInt(idEmpresa),palabraClave);
            ResultSetMetaData rsMD = resultados.getMetaData();
            int cantidadColumnas = rsMD.getColumnCount();
            modelo.addColumn("ID");
            modelo.addColumn("Código");
            modelo.addColumn("Descripción");
            modelo.addColumn("STOCK");
            modelo.addColumn("Costo");
            modelo.addColumn("Precio");
            modelo.addColumn("FOSALUD");
            while (resultados.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    if(i==6){//para FOSALUD
                        if(Integer.parseInt(resultados.getObject(i + 1).toString())==0){
                            filas[i] = "NO";
                        }else{
                            filas[i] = "SI";
                        }                        
                    }else{
                        filas[i] = resultados.getObject(i + 1);
                    }                    
                }
                modelo.addRow(filas);
            }
            frame.tblProductos.getColumnModel().getColumn(0).setMinWidth(0);
            frame.tblProductos.getColumnModel().getColumn(0).setMaxWidth(0);
            frame.tblProductos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            frame.tblProductos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        } catch (SQLException ex) {
            System.err.println("Error al mostrar productos en la tabla. " + ex);
        }

    }

    public void limpiarTabla() {
        modelo.setColumnCount(0);
        modelo.setRowCount(0);
        frame.btnAgregarProducto.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == frame.btnVerProductos){
            limpiar();
            limpiarTabla();
            llenarTablaProductos();
        }
        if (e.getSource() == frame.btnAgregarProducto) {
            if (!revisarCamposSinID()) {
                String idEmpresa = frame.comboEmpresas.getSelectedItem().toString().trim();
                idEmpresa = idEmpresa.substring(0, 1);
                mod.setId_empresa(Integer.parseInt(idEmpresa));
                mod.setDescripcion(frame.txtDescripcionProducto.getText());
                mod.setCantidad(Integer.parseInt(frame.txtCantidadProducto.getText()));
                mod.setCostos(Double.parseDouble(frame.txtCOstoProducto.getText()));
                mod.setPrecio(Double.parseDouble(frame.txtPrecioVentaProducto.getText()));
                mod.setLocal_importado(frame.ckbInventarioProducto.isSelected() ? 1 : 0);
                mod.setCodigo_interno(frame.txtCodigoInterno.getText().trim());
                
                if(!modConsulta.buscarCodigoInterno(Integer.parseInt(idEmpresa), frame.txtCodigoInterno.getText().trim())){
                    if (modConsulta.registrar(mod)) {
                        //se debe agregar al cardex
                        ConsultasCardex conCardex = new ConsultasCardex();
                        Cardex car = new Cardex();
                        car.setId_producto(modConsulta.ultimoCodigoProducto());
                        if(car.getId_producto()!=0 && car.getId_producto()!=-1){
                            Date fechaHoy = new Date();
                            String fecha = new SimpleDateFormat("yyyy-MM-dd").format(fechaHoy);
                            car.setFecha(fecha);
                            car.setTipo_documento("Inventario");
                            car.setCantidad(Integer.parseInt(frame.txtCantidadProducto.getText().trim()));
                            car.setValor_unitario(Double.parseDouble(frame.txtCOstoProducto.getText().trim()));
                            car.setNumero_documento("----");
                            if(conCardex.agregarACardex(car)){
                                JOptionPane.showMessageDialog(null, "Producto Almacenado correctamente");
                                limpiar();
                                limpiarTabla();
                                llenarTablaProductos();
                            }else{
                                JOptionPane.showMessageDialog(null, "No se pudo guardar el producto.");
                                System.out.println("ERROR AL GUARDAR EL CARDEX");
                            }
                        }                        
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo guardar el producto.");
                        System.out.println("ERROR AL GUARDAR EL PRODUCTO");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "El código interno ingresado ya existe, favor ingresa uno nuevo");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe ingresar datos correctos al formulario");
            }
        }
        if (e.getSource() == frame.btnModificarProducto) {
            if (!revisarCampos()) {
                String idEmpresa = frame.comboEmpresas.getSelectedItem().toString().trim();
                idEmpresa = idEmpresa.substring(0, 1);
                mod.setCodigo(Integer.parseInt(frame.txtCodigoProducto.getText()));
                mod.setDescripcion(frame.txtDescripcionProducto.getText());
                mod.setCantidad(Integer.parseInt(frame.txtCantidadProducto.getText()));
                mod.setCostos(Double.parseDouble(frame.txtCOstoProducto.getText()));
                mod.setPrecio(Double.parseDouble(frame.txtPrecioVentaProducto.getText()));
                mod.setLocal_importado(frame.ckbInventarioProducto.isSelected() ? 1 : 0);
                mod.setCodigo_interno(frame.txtCodigoInterno.getText().trim());
                if(!modConsulta.buscarCodigoInternoModificar(Integer.parseInt(idEmpresa), 
                        mod.getCodigo_interno(), frame.txtCodigoInternoOld.getText())){
                    if (modConsulta.modificar(mod)) {
                        JOptionPane.showMessageDialog(null, "Producto Actualizado Correctamente");
                        limpiar();
                        limpiarTabla();
                        llenarTablaProductos();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar el producto.");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "El código interno agregado ya existe registrado.\nFavor registre el producto con uno nuevo.");
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Debe ingresar datos correctos al formulario.");
            }
        }
        if (e.getSource() == frame.btnEliminarProducto) {
            if (!frame.txtCodigoProducto.getText().trim().equals("")) {
                mod.setCodigo(Integer.parseInt(frame.txtCodigoProducto.getText()));

                if (modConsulta.eliminar(mod)) {
                    JOptionPane.showMessageDialog(null, "Producto Eliminado correctamente");
                    limpiarTabla();
                    llenarTablaProductos();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el producto.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto para eliminarlo.");
            }
        }
        if (e.getSource() == frame.btnNuevoProducto) {
            frame.btnAgregarProducto.setEnabled(true);
            limpiar();
            frame.txtCantidadProducto.setText("0");
            frame.comboEmpresas.setEnabled(true);            
            frame.txtCodigoInterno.setEnabled(true);
        }
        
        if(e.getSource()==frame.btnImprimir){
            Exportar exportar = new Exportar();
            frmImprimirInventario frameImprimir = new frmImprimirInventario();
            ConsultasEmpresas consultasEmpresas = new ConsultasEmpresas();
            ControlImprimir control = new ControlImprimir(exportar, frameImprimir, this.consultasEmpresas);
            control.cargar();
            index.desktopPane.add(frameImprimir);            
            frameImprimir.show();
        }
        
    }

    public void limpiar() {
        frame.txtCodigoProducto.setText("");
        frame.txtDescripcionProducto.setText("");
        frame.txtCantidadProducto.setText("");
        frame.txtCOstoProducto.setText("");
        frame.txtPrecioVentaProducto.setText("");
        frame.ckbInventarioProducto.setSelected(false);
        frame.txtCodigoInterno.setText("");
        frame.txtCodigoInternoOld.setText("");
    }

}
