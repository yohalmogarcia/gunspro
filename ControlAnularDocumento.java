package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Cardex;
import modelo.Cliente;
import modelo.ConsultasCardex;
import modelo.ConsultasCliente;
import modelo.ConsultasCredito;
import modelo.ConsultasDetalleCredito;
import modelo.ConsultasEmpresas;
import modelo.ConsultasFactura;
import modelo.ConsultasProducto;
import modelo.ConsultasVenta;
import modelo.Credito;
import modelo.Factura;
import modelo.Producto;
import vista.frmAnularDocumentos;

public class ControlAnularDocumento implements ActionListener{
    
    private Factura factura;
    private ConsultasFactura consultasFactura;
    private ConsultasVenta consultasVenta;
    private Credito credito;
    private ConsultasCredito consultasCredito;
    private ConsultasDetalleCredito consultasDetalleCredito;
    private Cliente cliente;
    private ConsultasCliente consultasCliente;
    private frmAnularDocumentos frame;
    private Producto producto;
    private ConsultasProducto consultasProducto;
    private ConsultasEmpresas consultasEmpresas;

    public ControlAnularDocumento(Factura factura, 
            ConsultasFactura consultasFactura, Credito credito, 
            ConsultasCredito consultasCredito, Cliente cliente, 
            ConsultasCliente consultasCliente, frmAnularDocumentos frame, 
            ConsultasEmpresas consultasEmpresas, Producto producto, 
            ConsultasProducto consultasProducto, ConsultasDetalleCredito consultasDetalleCredito,
            ConsultasVenta consultasVenta) {
        this.factura = factura;
        this.consultasFactura = consultasFactura;
        this.credito = credito;
        this.consultasCredito = consultasCredito;
        this.cliente = cliente;
        this.consultasCliente = consultasCliente;
        this.frame = frame;
        this.consultasEmpresas = consultasEmpresas;
        this.producto=producto;
        this.consultasProducto=consultasProducto;
        this.consultasDetalleCredito=consultasDetalleCredito;
        this.consultasVenta= consultasVenta;
        
        //listener
        frame.btnBuscar.addActionListener(this);
        frame.btnAnular.addActionListener(this);
    }
    
    public void cargar(){
        frame.btnAnular.setEnabled(false);
        frame.txtCodigoDocumento.setVisible(false);
        llenarComboEmpresas();
    }
    
     public void llenarComboEmpresas(){
        try {
            ResultSet resultados = consultasEmpresas.mostrarEmpresas();
            while(resultados.next()){
                frame.comboEmpresas.addItem(String.valueOf(resultados.getInt("id_empresa")) + " - " + resultados.getString("nombre"));                
            }
        } catch (SQLException ex) {
            System.out.println("ERROR AL LLENAR EL COMBOBOX DE EMPRESAS. "+ex);
        }
    }
     
     public boolean revisarCampos(){
         if(frame.txtNumCorrelativo.getText().equals("")){
             return true;
         }
         return false;
     }
     
     public void limpiarCamposResultados(){
         frame.txtCodigoDocumento.setText("");
         frame.txtFecha.setText("");
         frame.txtCliente.setText("");
         frame.txtTotal.setText("");
         frame.btnAnular.setEnabled(false);
     }
     
     public void limpiarCabecera(){
         frame.txtNumCorrelativo.setText("");
         frame.radioFactura.setSelected(true);
         frame.comboEmpresas.setSelectedIndex(0);
     }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==frame.btnBuscar){
            String idEmpresa = frame.comboEmpresas.getSelectedItem().toString().trim();
            idEmpresa = idEmpresa.substring(0, 1);
            if(!revisarCampos()){
                if(frame.radioCredito.isSelected()){
                    //es credito
                    credito.setId_empresa(Integer.parseInt(idEmpresa));
                    credito.setNumeroCorrelativo(frame.txtNumCorrelativo.getText().trim());
                    if(consultasCredito.buscarCreditoPorEmpresa(credito)){
                        cliente.setCodigo(credito.getCodigoCliente());
                        if(consultasCliente.buscarCliente(cliente)){
                            frame.txtCodigoDocumento.setText(String.valueOf(credito.getCodigoCredito()));
                            frame.txtFecha.setText(credito.getFecha());
                            frame.txtCliente.setText(cliente.getNombre());
                            frame.txtTotal.setText(String.valueOf(credito.getTotalFinal()));
                            frame.btnAnular.setEnabled(true);
                        }else{
                            System.out.println("OCURRIO UN ERROR AL BUSCAR EL CLIENTE");
                            //JOptionPane.showMessageDialog(null, "Ocurrio un error al buscar el credito");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "No se encontro dicho numero correlativo\nO dicho documento ya ha sido anulado.");
                        limpiarCamposResultados();
                    }
                }else{
                    //es factura
                    factura.setId_empresa(Integer.parseInt(idEmpresa));
                    factura.setNumeroCorrelativo(frame.txtNumCorrelativo.getText().trim());
                    if(consultasFactura.buscarFactura(factura)){
                        frame.txtCodigoDocumento.setText(String.valueOf(factura.getCodigoFactura()));
                        frame.txtFecha.setText(factura.getFecha());
                        frame.txtCliente.setText(factura.getCliente());
                        frame.txtTotal.setText(String.valueOf(factura.getTotalFinal()));
                        frame.btnAnular.setEnabled(true);
                    }else{
                        JOptionPane.showMessageDialog(null, "No se encontro dicho numero correlativo\nO el documento ya fue anulado");
                        limpiarCamposResultados();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Se debe rellenar correctamente el formulario");
                limpiarCamposResultados();
            }
        }
        
        if(e.getSource()==frame.btnAnular){
            System.out.println("VAMOS A ANULAR");
            int codigoDocumento= Integer.parseInt(frame.txtCodigoDocumento.getText().trim());            
            if(!revisarCampos()){
                if(frame.radioCredito.isSelected()){
                    //es credito
                    credito.setCodigoCredito(codigoDocumento);
                    if(consultasCredito.anularCredito(credito)){
                        ResultSet res = consultasDetalleCredito.buscarDetallePorIdCredito(credito);                        
                        try {
                            while(res.next()){
                                //int codigoDetalle = res.getInt("id_detalle_credito");
                                int idProducto = res.getInt("id_producto");
                                int cantidad = res.getInt("cantidad");
                                producto.setCodigo(idProducto);
                                producto.setCantidad(cantidad);                                
                                if(!consultasProducto.sumarCantidad(producto)){
                                    JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado. ");
                                    System.out.println("ERROR AL MODIFICAR EL INVENTARIO. ");
                                    break;
                                }
                                //agregar al cardex
                                Date fecha = new Date();
                                String fechaString = new SimpleDateFormat("yyyy-MM-dd").format(fecha);
                                Cardex cardex = new Cardex();
                                ConsultasCardex cCardex = new ConsultasCardex();
                                cardex.setId_producto(idProducto);
                                cardex.setFecha(fechaString);
                                cardex.setTipo_documento("Anulada");
                                cardex.setCantidad(cantidad*-1);
                                cardex.setValor_unitario(0.0);
                                cardex.setNumero_documento(frame.txtNumCorrelativo.getText().trim());
                                
                                if(!cCardex.agregarACardex(cardex)){
                                    JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado. ");
                                    System.out.println("ERROR AL AGREGAR AL CARDEX LA ANULACION. ");
                                    break;
                                }
                                //fin agregar cardex
                            }
                            JOptionPane.showMessageDialog(null, "El Credito se anulo exitosamente.");
                            limpiarCamposResultados();
                        } catch (SQLException ex) {
                            System.out.println("ERROR AL MODIFICAR INVENTARIO DE ANULACION DE CREDITO. "+ex);
                            JOptionPane.showMessageDialog(null, "OCURRIO UN ERROR EN EL PROCESO");
                        }finally{
                            try {
                                consultasDetalleCredito.getConnection().close();
                            } catch (SQLException ex) {
                                System.out.println("ERROR CERRANDO LA CONEXION DESDE CONTROL ANULAR. "+ex);
                            }
                        }
                    }
                }else{
                    //es factura
                    factura.setCodigoFactura(codigoDocumento);
                    if(consultasFactura.anularFactura(factura)){
                        ResultSet res = consultasVenta.buscarVentasPorIdFactura(factura);                        
                        try {
                            while(res.next()){
                                //int codigoDetalle = res.getInt("id_detalle_credito");
                                int idProducto = res.getInt("id_producto");
                                int cantidad = res.getInt("cantidad");
                                producto.setCodigo(idProducto);
                                producto.setCantidad(cantidad);                                
                                if(!consultasProducto.sumarCantidad(producto)){
                                    JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado. ");
                                    System.out.println("ERROR AL MODIFICAR EL INVENTARIO DESDE FACTURA. ");
                                    break;
                                }
                                //agregar al cardex
                                Date fecha = new Date();
                                String fechaString = new SimpleDateFormat("yyyy-MM-dd").format(fecha);
                                Cardex cardex = new Cardex();
                                ConsultasCardex cCardex = new ConsultasCardex();
                                cardex.setId_producto(idProducto);
                                cardex.setFecha(fechaString);
                                cardex.setTipo_documento("Anulada");
                                cardex.setCantidad(cantidad*-1);
                                cardex.setValor_unitario(0.0);
                                cardex.setNumero_documento(frame.txtNumCorrelativo.getText().trim());
                                
                                if(!cCardex.agregarACardex(cardex)){
                                    JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado. ");
                                    System.out.println("ERROR AL AGREGAR AL CARDEX LA ANULACION. ");
                                    break;
                                }
                                //fin agregar cardex
                            }
                            JOptionPane.showMessageDialog(null, "La factura se anulo exitosamente.");
                            limpiarCamposResultados();
                        } catch (SQLException ex) {
                            System.out.println("ERROR AL MODIFICAR INVENTARIO DE ANULACION DE FACTURA. "+ex);
                            JOptionPane.showMessageDialog(null, "OCURRIO UN ERROR EN EL PROCESO");
                        }finally{
                            try {
                                consultasVenta.getConnection().close();
                            } catch (SQLException ex) {
                                System.out.println("ERROR CERRANDO LA CONEXION DESDE CONTROL ANULAR FACTURA. "+ex);
                            }
                        }
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Debe rellenar el formulario.");
                limpiarCamposResultados();
            }            
        }
    }
    
}
