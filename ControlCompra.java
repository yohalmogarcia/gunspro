package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import modelo.Cardex;
import modelo.Compra;
import modelo.CompraIndividual;
import modelo.ConsultasCardex;
import modelo.ConsultasCompra;
import modelo.ConsultasCompraIndividual;
import modelo.ConsultasEmpresas;
import modelo.ConsultasProducto;
import modelo.ConsultasProveedor;
import modelo.Producto;
import modelo.Proveedor;
import vista.frmCompras;
import vista.index;

public class ControlCompra implements ActionListener, FocusListener {

    private Producto producto;
    private ConsultasProducto consultasProducto;
    private Proveedor proveedor;
    private ConsultasProveedor consultasProveedor;
    private Compra compra;
    private ConsultasCompra consultasCompra;
    private CompraIndividual compraIndividual;
    private ConsultasCompraIndividual consultasIndividual;
    private ConsultasEmpresas consultasEmpresas;
    private ConsultasCardex consultasCardex;
    private frmCompras frameCompras;
    String fechaString = "";

    public ControlCompra(Producto producto, ConsultasProducto consultasProducto, 
            Proveedor proveedor, ConsultasProveedor consultasProveedor, 
            Compra compra, ConsultasCompra consultasCompra, 
            CompraIndividual compraIndividual, ConsultasCompraIndividual consultasIndividual, 
            frmCompras frameCompras, ConsultasEmpresas consultasEmpresas, 
            ConsultasCardex consultasCardex) {
        this.producto = producto;
        this.consultasProducto = consultasProducto;
        this.proveedor = proveedor;
        this.consultasProveedor = consultasProveedor;
        this.compra = compra;
        this.consultasCompra = consultasCompra;
        this.compraIndividual = compraIndividual;
        this.consultasIndividual = consultasIndividual;
        this.frameCompras = frameCompras;
        this.consultasEmpresas= consultasEmpresas;
        this.consultasCardex = consultasCardex;
        //listeners
        frameCompras.txtCodigoProveedor.addFocusListener(this);
        frameCompras.txtCodigoProducto1.addFocusListener(this);
        frameCompras.txtCodigoProducto2.addFocusListener(this);
        frameCompras.txtCodigoProducto3.addFocusListener(this);
        frameCompras.txtCodigoProducto4.addFocusListener(this);
        frameCompras.txtCodigoProducto5.addFocusListener(this);
        frameCompras.txtCodigoProducto6.addFocusListener(this);
        frameCompras.txtCantidadProducto1.addFocusListener(this);
        frameCompras.txtCantidadProducto2.addFocusListener(this);
        frameCompras.txtCantidadProducto3.addFocusListener(this);
        frameCompras.txtCantidadProducto4.addFocusListener(this);
        frameCompras.txtCantidadProducto5.addFocusListener(this);
        frameCompras.txtCantidadProducto6.addFocusListener(this);
        frameCompras.btnAgregarCompra.addActionListener(this);
        frameCompras.btnCancelar.addActionListener(this);
    }

    public void cargar() {
        llenarComboEmpresas();
        frameCompras.txtIdProducto1.setVisible(false);
        frameCompras.txtIdProducto2.setVisible(false);
        frameCompras.txtIdProducto3.setVisible(false);
        frameCompras.txtIdProducto4.setVisible(false);
        frameCompras.txtIdProducto5.setVisible(false);
        frameCompras.txtIdProducto6.setVisible(false);
        frameCompras.txtIdProducto1.setText("");
        frameCompras.txtIdProducto2.setText("");
        frameCompras.txtIdProducto3.setText("");
        frameCompras.txtIdProducto4.setText("");
        frameCompras.txtIdProducto5.setText("");
        frameCompras.txtIdProducto6.setText("");
    }
    
    public void llenarComboEmpresas(){
        try {
            ResultSet resultados = consultasEmpresas.mostrarEmpresas();
            while(resultados.next()){
                frmCompras.comboEmpresas.addItem(String.valueOf(resultados.getInt("id_empresa")) + " - " + resultados.getString("nombre"));
                //frmCompras.comboEmpresasTabla.addItem(String.valueOf(resultados.getInt("id_empresa")) + " - " + resultados.getString("nombre"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR AL LLENAR EL COMBOBOX DE EMPRESAS. "+ex);
        }
    }


    public boolean revisarCampos() {
        if(frameCompras.comboEmpresas.getSelectedItem().toString().equals("")){
            return true;
        }
        if (frameCompras.txtFecha.getDate() == null) {
            return true;
        }
        if (frameCompras.txtCodigoProveedor.getText().equals("")) {
            return true;
        }
        if (frameCompras.txtNumeroComprobante.getText().equals("")) {
            return true;
        }
        if (frameCompras.txtTotalNeto.getText().equals("")) {
            return true;
        }
        if (frameCompras.txtTotalFinal.getText().equals("")) {
            return true;
        }
        return false;
    }

    public boolean revisarProductos() {
        return frameCompras.txtCodigoProducto1.getText().equals("") && frameCompras.txtCodigoProducto2.getText().equals("") && frameCompras.txtCodigoProducto3.getText().equals("") && frameCompras.txtCodigoProducto4.getText().equals("") && frameCompras.txtCodigoProducto5.getText().equals("") && frameCompras.txtCodigoProducto6.getText().equals("") && frameCompras.comboEmpresas.getSelectedItem().toString().equals("");
    }

    public void limpiarCampos() {
        frameCompras.comboEmpresas.setSelectedIndex(0);
        frameCompras.txtFecha.setDate(null);
        frameCompras.txtCodigoProveedor.setText("");
        frameCompras.txtNombreProveedor.setText("");
        frameCompras.txtNumeroComprobante.setText("");
        frameCompras.txtTotalNeto.setText("");
        frameCompras.txtIVA.setText("");
        frameCompras.txtFOSALUD.setText("");
        frameCompras.txtTotalFinal.setText("");
        //productos
        frameCompras.txtCodigoProducto1.setText("");
        frameCompras.txtCodigoProducto2.setText("");
        frameCompras.txtCodigoProducto3.setText("");
        frameCompras.txtCodigoProducto4.setText("");
        frameCompras.txtCodigoProducto5.setText("");
        frameCompras.txtCodigoProducto6.setText("");
        frameCompras.txtDescripcionProducto1.setText("");
        frameCompras.txtDescripcionProducto2.setText("");
        frameCompras.txtDescripcionProducto3.setText("");
        frameCompras.txtDescripcionProducto4.setText("");
        frameCompras.txtDescripcionProducto5.setText("");
        frameCompras.txtDescripcionProducto6.setText("");
        frameCompras.txtPrecioUnitario1.setText("");
        frameCompras.txtPrecioUnitario2.setText("");
        frameCompras.txtPrecioUnitario3.setText("");
        frameCompras.txtPrecioUnitario4.setText("");
        frameCompras.txtPrecioUnitario5.setText("");
        frameCompras.txtPrecioUnitario6.setText("");
        frameCompras.txtCantidadProducto1.setText("");
        frameCompras.txtCantidadProducto2.setText("");
        frameCompras.txtCantidadProducto3.setText("");
        frameCompras.txtCantidadProducto4.setText("");
        frameCompras.txtCantidadProducto5.setText("");
        frameCompras.txtCantidadProducto6.setText("");
        frameCompras.txtTotalProducto1.setText("");
        frameCompras.txtTotalProducto2.setText("");
        frameCompras.txtTotalProducto3.setText("");
        frameCompras.txtTotalProducto4.setText("");
        frameCompras.txtTotalProducto5.setText("");
        frameCompras.txtTotalProducto6.setText("");

    }

    public String[][] informacionProductos() {
        String info[][] = {
            {frameCompras.txtIdProducto1.getText(), frameCompras.txtCantidadProducto1.getText(), frameCompras.txtPrecioUnitario1.getText(), frameCompras.txtTotalProducto1.getText()},
            {frameCompras.txtIdProducto2.getText(), frameCompras.txtCantidadProducto2.getText(), frameCompras.txtPrecioUnitario2.getText(), frameCompras.txtTotalProducto2.getText()},
            {frameCompras.txtIdProducto3.getText(), frameCompras.txtCantidadProducto3.getText(), frameCompras.txtPrecioUnitario3.getText(), frameCompras.txtTotalProducto3.getText()},
            {frameCompras.txtIdProducto4.getText(), frameCompras.txtCantidadProducto4.getText(), frameCompras.txtPrecioUnitario4.getText(), frameCompras.txtTotalProducto4.getText()},
            {frameCompras.txtIdProducto5.getText(), frameCompras.txtCantidadProducto5.getText(), frameCompras.txtPrecioUnitario5.getText(), frameCompras.txtTotalProducto5.getText()},
            {frameCompras.txtIdProducto6.getText(), frameCompras.txtCantidadProducto6.getText(), frameCompras.txtPrecioUnitario6.getText(), frameCompras.txtTotalProducto6.getText()}
        };

        return info;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frameCompras.btnAgregarCompra) {
            if (!revisarCampos() && !revisarProductos()) {
                //Ingresar una nueva compra global
                /*ASIGNACION DE VALORES AL MODELO DE COMPRA*/
                String idEmpresa = frameCompras.comboEmpresas.getSelectedItem().toString().trim();
                idEmpresa = idEmpresa.substring(0, 1);
                fechaString = new SimpleDateFormat("yyyy-MM-dd").format(frameCompras.txtFecha.getDate());
                compra.setFecha(fechaString);
                compra.setCodigoProveedor(Integer.parseInt(frameCompras.txtCodigoProveedor.getText().trim()));
                compra.setNumeroComprobante(frameCompras.txtNumeroComprobante.getText());
                compra.setTotalNeto(Double.parseDouble(frameCompras.txtTotalNeto.getText().trim()));
                if (frameCompras.txtIVA.getText().equals("")) {
                    compra.setIva(0);
                } else {
                    compra.setIva(Double.parseDouble(frameCompras.txtIVA.getText().trim()));
                }
                if (frameCompras.txtFOSALUD.getText().equals("")) {
                    compra.setFosalud(0);
                } else {
                    compra.setFosalud(Double.parseDouble(frameCompras.txtFOSALUD.getText().trim()));
                }
                compra.setTotalFinal(Double.parseDouble(frameCompras.txtTotalFinal.getText().trim()));
                compra.setCodigousuario(Integer.parseInt(index.txtCodigousuario.getText().trim()));
                compra.setId_empresa(Integer.parseInt(idEmpresa));
                if (consultasCompra.agregarCompra(compra)) {
                    /*COMPRA GLOBAL REALIZADA CON EXITO*/
                    Cardex cardex;
                    //agregar las compras individuales
                    if (consultasCompra.codigoUltimaCompra() != 0) {//Recibiendo el codigo de la compra global realizada
                        //compra individual
                        String infoProducto[][] = informacionProductos();
                        
                        int cantidadFilas = infoProducto.length;
                        int codigoCompraGlobal = consultasCompra.codigoUltimaCompra();

                        for (int filas = 0; filas < cantidadFilas; filas++) {
                            if (!infoProducto[filas][0].equals("")) {
                                compraIndividual.setCodigoCompra(codigoCompraGlobal);
                                compraIndividual.setCodigoProducto(Integer.parseInt(infoProducto[filas][0]));
                                compraIndividual.setPrecioUnidad(Double.parseDouble(infoProducto[filas][2]));
                                compraIndividual.setCantidad(Integer.parseInt(infoProducto[filas][1]));
                                compraIndividual.setTotalFinal(Double.parseDouble(infoProducto[filas][3]));
                                producto.setCodigo(Integer.parseInt(infoProducto[filas][0]));
                                producto.setCostos(Double.parseDouble(infoProducto[filas][2]));
                                producto.setCantidad(Integer.parseInt(infoProducto[filas][1]));
                                if (!consultasIndividual.agregarCompraIndividual(compraIndividual)) {
                                    JOptionPane.showMessageDialog(null, "ocurrio un error en la fila: " + filas + 1);
                                    break;
                                }
                                //actulizar el inventario segun el codigo
                                if(!consultasProducto.sumarCantidad(producto)){
                                    JOptionPane.showMessageDialog(null, "Ocurrio un error actualizando el inventario.");
                                    break;
                                }
                                //se actualiza el precio del producto con la ultima compra
                                if(!consultasProducto.actualizarPrecio(producto)){
                                    JOptionPane.showMessageDialog(null, "Ocurrio un error actualizando el costo del producto.");
                                    break;
                                }
                                //agregar al cardex
                                    cardex = new Cardex();
                                    cardex.setId_producto(Integer.parseInt(infoProducto[filas][0]));
                                    cardex.setFecha(fechaString);
                                    cardex.setTipo_documento("Compras");
                                    cardex.setCantidad(Integer.parseInt(infoProducto[filas][1]));
                                    cardex.setValor_unitario(Double.parseDouble(infoProducto[filas][2]));
                                    cardex.setNumero_documento(frameCompras.txtNumeroComprobante.getText());
                                    if(!consultasCardex.agregarACardex(cardex)){
                                        JOptionPane.showMessageDialog(null, "Ocurrio un error.");
                                        break;
                                    }   
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, "EXITO");
                    limpiarCampos();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Favor ingresar datos correctos en el formulario.");
            }
        }
        
        if(e.getSource()==frameCompras.btnCancelar){
            frameCompras.dispose();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        String idEmpresa = frameCompras.comboEmpresas.getSelectedItem().toString().trim();
                idEmpresa = idEmpresa.substring(0, 1);
        if (e.getSource() == frameCompras.txtCodigoProveedor) {
            if (!frameCompras.txtCodigoProveedor.getText().trim().equals("")) {
                proveedor.setCodigoProveedor(Integer.parseInt(frameCompras.txtCodigoProveedor.getText().trim()));
                proveedor.setId_empresa(Integer.parseInt(idEmpresa));
                if (consultasProveedor.buscarProveedor(proveedor)) {
                    frameCompras.txtNombreProveedor.setText(proveedor.getNombre());
                } else {
                    frameCompras.txtNombreProveedor.setText("");
                    JOptionPane.showMessageDialog(null, "No se encontro dicho proveedor.");
                }
            }
        }
        
        /*PRODUCTOS*/
        if(e.getSource()==frameCompras.txtCodigoProducto1){
            if(!frameCompras.txtCodigoProducto1.getText().equals("")){
                //cambiar esta parte por codigo interno
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                producto.setCodigo_interno(frameCompras.txtCodigoProducto1.getText().trim());
                if(consultasProducto.buscarPorCodigoInterno(producto)){
                    String local="";
                    if(producto.getLocal_importado()==1){
                        local="IMPORTADO";
                    }else{
                        local="LOCAL";
                    }
                    frameCompras.txtDescripcionProducto1.setText(producto.getDescripcion()+" - "+local);  
                    frameCompras.txtIdProducto1.setText(String.valueOf(producto.getCodigo()));
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontro el codigo de dicho producto.");
                    frameCompras.txtCodigoProducto1.setText("");
                    frameCompras.txtDescripcionProducto1.setText("");
                    frameCompras.txtPrecioUnitario1.setText("");
                    frameCompras.txtCantidadProducto1.setText("");
                    frameCompras.txtTotalProducto1.setText("");
                    frameCompras.txtIdProducto1.setText("");
                    frameCompras.txtCodigoProducto1.requestFocus();
                }
            }
        }
        
        if(e.getSource()==frameCompras.txtCodigoProducto2){
            if(!frameCompras.txtCodigoProducto2.getText().equals("")){
                //cambiar estar parte por codigo interno
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                producto.setCodigo_interno(frameCompras.txtCodigoProducto2.getText().trim());
                if(consultasProducto.buscarPorCodigoInterno(producto)){
                    String local="";
                    if(producto.getLocal_importado()==1){
                        local="IMPORTADO";
                    }else{
                        local="LOCAL";
                    }
                    frameCompras.txtDescripcionProducto2.setText(producto.getDescripcion()+" - "+local); 
                    frameCompras.txtIdProducto2.setText(String.valueOf(producto.getCodigo()));
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontro el codigo de dicho producto.");
                    frameCompras.txtCodigoProducto2.setText("");
                    frameCompras.txtDescripcionProducto2.setText("");
                    frameCompras.txtPrecioUnitario2.setText("");
                    frameCompras.txtCantidadProducto2.setText("");
                    frameCompras.txtTotalProducto2.setText("");
                    frameCompras.txtIdProducto2.setText("");
                    frameCompras.txtCodigoProducto2.requestFocus();
                }
            }
        }
        
        if(e.getSource()==frameCompras.txtCodigoProducto3){
            if(!frameCompras.txtCodigoProducto3.getText().equals("")){
                //cambiar estar parte por codigo interno
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                producto.setCodigo_interno(frameCompras.txtCodigoProducto3.getText().trim());
                if(consultasProducto.buscarPorCodigoInterno(producto)){
                    String local="";
                    if(producto.getLocal_importado()==1){
                        local="IMPORTADO";
                    }else{
                        local="LOCAL";
                    }
                    frameCompras.txtDescripcionProducto3.setText(producto.getDescripcion()+" - "+local); 
                    frameCompras.txtIdProducto3.setText(String.valueOf(producto.getCodigo()));
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontro el codigo de dicho producto.");
                    frameCompras.txtCodigoProducto3.setText("");
                    frameCompras.txtDescripcionProducto3.setText("");
                    frameCompras.txtPrecioUnitario3.setText("");
                    frameCompras.txtCantidadProducto3.setText("");
                    frameCompras.txtTotalProducto3.setText("");
                    frameCompras.txtIdProducto3.setText("");
                    frameCompras.txtCodigoProducto3.requestFocus();
                }
            }
        }
        
        if(e.getSource()==frameCompras.txtCodigoProducto4){
            if(!frameCompras.txtCodigoProducto4.getText().equals("")){
                //cambiar estar parte por codigo interno
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                producto.setCodigo_interno(frameCompras.txtCodigoProducto4.getText().trim());
                if(consultasProducto.buscarPorCodigoInterno(producto)){
                    String local="";
                    if(producto.getLocal_importado()==1){
                        local="IMPORTADO";
                    }else{
                        local="LOCAL";
                    }
                    frameCompras.txtDescripcionProducto4.setText(producto.getDescripcion()+" - "+local);       
                    frameCompras.txtIdProducto4.setText(String.valueOf(producto.getCodigo()));
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontro el codigo de dicho producto.");
                    frameCompras.txtCodigoProducto4.setText("");
                    frameCompras.txtDescripcionProducto4.setText("");
                    frameCompras.txtPrecioUnitario4.setText("");
                    frameCompras.txtCantidadProducto4.setText("");
                    frameCompras.txtTotalProducto4.setText("");
                    frameCompras.txtIdProducto4.setText("");
                    frameCompras.txtCodigoProducto4.requestFocus();
                }
            }
        }
        
        if(e.getSource()==frameCompras.txtCodigoProducto5){
            if(!frameCompras.txtCodigoProducto5.getText().equals("")){
                //cambiar estar parte por codigo interno
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                producto.setCodigo_interno(frameCompras.txtCodigoProducto5.getText().trim());
                if(consultasProducto.buscarPorCodigoInterno(producto)){
                    String local="";
                    if(producto.getLocal_importado()==1){
                        local="IMPORTADO";
                    }else{
                        local="LOCAL";
                    }
                    frameCompras.txtDescripcionProducto5.setText(producto.getDescripcion()+" - "+local); 
                    frameCompras.txtIdProducto5.setText(String.valueOf(producto.getCodigo()));
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontro el codigo de dicho producto.");
                    frameCompras.txtCodigoProducto5.setText("");
                    frameCompras.txtDescripcionProducto5.setText("");
                    frameCompras.txtPrecioUnitario5.setText("");
                    frameCompras.txtCantidadProducto5.setText("");
                    frameCompras.txtTotalProducto5.setText("");
                    frameCompras.txtIdProducto5.setText("");
                    frameCompras.txtCodigoProducto5.requestFocus();
                }
            }
        }
        
        if(e.getSource()==frameCompras.txtCodigoProducto6){
            if(!frameCompras.txtCodigoProducto6.getText().equals("")){
                //cambiar estar parte por codigo interno
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                producto.setCodigo_interno(frameCompras.txtCodigoProducto6.getText().trim());
                if(consultasProducto.buscarPorCodigoInterno(producto)){
                    String local="";
                    if(producto.getLocal_importado()==1){
                        local="IMPORTADO";
                    }else{
                        local="LOCAL";
                    }
                    frameCompras.txtDescripcionProducto6.setText(producto.getDescripcion()+" - "+local);  
                    frameCompras.txtIdProducto6.setText(String.valueOf(producto.getCodigo()));
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontro el codigo de dicho producto.");
                    frameCompras.txtCodigoProducto6.setText("");
                    frameCompras.txtDescripcionProducto6.setText("");
                    frameCompras.txtPrecioUnitario6.setText("");
                    frameCompras.txtCantidadProducto6.setText("");
                    frameCompras.txtTotalProducto6.setText("");
                    frameCompras.txtIdProducto6.setText("");
                    frameCompras.txtCodigoProducto6.requestFocus();
                }
            }
        }
        
        //CANTIDADES
        if(e.getSource()==frameCompras.txtCantidadProducto1){
            if(!frameCompras.txtCantidadProducto1.getText().trim().equals("") && !frameCompras.txtPrecioUnitario1.getText().equals("")){
                int cantidad=Integer.parseInt(frameCompras.txtCantidadProducto1.getText().trim());
                double precioUnitario=Double.parseDouble(frameCompras.txtPrecioUnitario1.getText().trim());
                double total=cantidad*precioUnitario;
                total=Math.rint(total*100)/100;
                frameCompras.txtTotalProducto1.setText(String.valueOf(total));
            }else{
                frameCompras.txtCantidadProducto1.setText("");
                frameCompras.txtPrecioUnitario1.setText("");
            }
        }
        
        if(e.getSource()==frameCompras.txtCantidadProducto2){
            if(!frameCompras.txtCantidadProducto2.getText().trim().equals("") && !frameCompras.txtPrecioUnitario2.getText().equals("")){
                int cantidad=Integer.parseInt(frameCompras.txtCantidadProducto2.getText().trim());
                double precioUnitario=Double.parseDouble(frameCompras.txtPrecioUnitario2.getText().trim());
                double total=cantidad*precioUnitario;
                total=Math.rint(total*100)/100;
                frameCompras.txtTotalProducto2.setText(String.valueOf(total));
            }else{
                frameCompras.txtCantidadProducto2.setText("");
                frameCompras.txtPrecioUnitario2.setText("");
            }
        }
        
        if(e.getSource()==frameCompras.txtCantidadProducto3){
            if(!frameCompras.txtCantidadProducto3.getText().trim().equals("") && !frameCompras.txtPrecioUnitario3.getText().equals("")){
                int cantidad=Integer.parseInt(frameCompras.txtCantidadProducto3.getText().trim());
                double precioUnitario=Double.parseDouble(frameCompras.txtPrecioUnitario3.getText().trim());
                double total=cantidad*precioUnitario;
                total=Math.rint(total*100)/100;
                frameCompras.txtTotalProducto3.setText(String.valueOf(total));
            }else{
                frameCompras.txtCantidadProducto3.setText("");
                frameCompras.txtPrecioUnitario3.setText("");
            }
        }
        
        if(e.getSource()==frameCompras.txtCantidadProducto4){
            if(!frameCompras.txtCantidadProducto4.getText().trim().equals("") && !frameCompras.txtPrecioUnitario4.getText().equals("")){
                int cantidad=Integer.parseInt(frameCompras.txtCantidadProducto4.getText().trim());
                double precioUnitario=Double.parseDouble(frameCompras.txtPrecioUnitario4.getText().trim());
                double total=cantidad*precioUnitario;
                total=Math.rint(total*100)/100;
                frameCompras.txtTotalProducto4.setText(String.valueOf(total));
            }else{
                frameCompras.txtCantidadProducto4.setText("");
                frameCompras.txtPrecioUnitario4.setText("");
            }
        }
        
        if(e.getSource()==frameCompras.txtCantidadProducto5){
            if(!frameCompras.txtCantidadProducto5.getText().trim().equals("") && !frameCompras.txtPrecioUnitario5.getText().equals("")){
                int cantidad=Integer.parseInt(frameCompras.txtCantidadProducto5.getText().trim());
                double precioUnitario=Double.parseDouble(frameCompras.txtPrecioUnitario5.getText().trim());
                double total=cantidad*precioUnitario;
                total=Math.rint(total*100)/100;
                frameCompras.txtTotalProducto5.setText(String.valueOf(total));
            }else{
                frameCompras.txtCantidadProducto5.setText("");
                frameCompras.txtPrecioUnitario5.setText("");
            }
        }
        
        if(e.getSource()==frameCompras.txtCantidadProducto6){
            if(!frameCompras.txtCantidadProducto6.getText().trim().equals("") && !frameCompras.txtPrecioUnitario6.getText().equals("")){
                int cantidad=Integer.parseInt(frameCompras.txtCantidadProducto6.getText().trim());
                double precioUnitario=Double.parseDouble(frameCompras.txtPrecioUnitario6.getText().trim());
                double total=cantidad*precioUnitario;
                total=Math.rint(total*100)/100;
                frameCompras.txtTotalProducto6.setText(String.valueOf(total));
            }else{
                frameCompras.txtCantidadProducto6.setText("");
                frameCompras.txtPrecioUnitario6.setText("");
            }
        }

    }

}
