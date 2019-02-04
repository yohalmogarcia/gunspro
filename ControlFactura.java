package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
import modelo.DetalleCredito;
import modelo.Producto;
import modelo.Venta;
import modelo.Factura;

import vista.frmVentasTabla;

public class ControlFactura implements ActionListener, FocusListener {

    private Producto producto;
    private ConsultasProducto consultas;
    private frmVentasTabla frame;
    private Cliente cliente;
    private ConsultasCliente consultasCliente;
    private Venta venta;
    private ConsultasVenta consultasVenta;
    private Factura factura;
    private ConsultasFactura consultasFactura;
    private Credito credito;
    private ConsultasCredito consultasCredito;
    private DetalleCredito detalleCredito;
    private ConsultasDetalleCredito consultasDetalleCredito;
    private ConsultasEmpresas consultasEmpresas;
    private ConsultasCardex consultasCardex;
    public String fechaString;

    public ControlFactura(Producto producto, ConsultasProducto consultas, 
            frmVentasTabla frame, Cliente cliente, 
            ConsultasCliente consultasCliente, Venta venta, 
            ConsultasVenta consultasVenta, Factura factura, 
            ConsultasFactura consultasFactura, Credito credito, 
            ConsultasCredito consultasCredito, ConsultasEmpresas consultasEmpresas, 
            DetalleCredito detalleCredito, ConsultasDetalleCredito consultasDetalleCredito,
            ConsultasCardex consultasCardex) {
        this.producto = producto;
        this.consultas = consultas;
        this.frame = frame;
        this.cliente = cliente;
        this.consultasCliente = consultasCliente;
        this.venta = venta;
        this.consultasVenta = consultasVenta;
        this.factura = factura;
        this.consultasFactura = consultasFactura;
        this.credito = credito;
        this.consultasEmpresas=consultasEmpresas;
        this.consultasCredito = consultasCredito;
        this.detalleCredito=detalleCredito;
        this.consultasDetalleCredito=consultasDetalleCredito;
        this.consultasCardex=consultasCardex;

        //agregando Listeners
        frame.txtRegistro.addFocusListener(this);

        frame.txtCodigoProducto1.addFocusListener(this);
        frame.txtCodigoProducto2.addFocusListener(this);
        frame.txtCodigoProducto3.addFocusListener(this);
        frame.txtCodigoProducto4.addFocusListener(this);
        frame.txtCodigoProducto5.addFocusListener(this);
        frame.txtCodigoProducto6.addFocusListener(this);

        frame.txtCantidadProducto1.addFocusListener(this);
        frame.txtCantidadProducto2.addFocusListener(this);
        frame.txtCantidadProducto3.addFocusListener(this);
        frame.txtCantidadProducto4.addFocusListener(this);
        frame.txtCantidadProducto5.addFocusListener(this);
        frame.txtCantidadProducto6.addFocusListener(this);

        frame.btnCalculaTotal.addActionListener(this);
        frame.btnProcesar.addActionListener(this);
        frame.btnCerrar.addActionListener(this);

        frame.rbtCredito.addActionListener(this);
        frame.rbtFactura.addActionListener(this);
    }

    public void cargar() {
        frame.txtCodigoCliente.setVisible(false);
        frame.txtRegistro.setEnabled(false);
        frame.txtIdProducto1.setVisible(false);
        frame.txtIdProducto2.setVisible(false);
        frame.txtIdProducto3.setVisible(false);
        frame.txtIdProducto4.setVisible(false);
        frame.txtIdProducto5.setVisible(false);
        frame.txtIdProducto6.setVisible(false);
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

    public void limpiarEncabezados() {
        frame.txtRegistro.setText("");
        frame.txtCliente.setText("");
        frame.txtDireccion.setText("");
        frame.txtNIT.setText("");
        frame.txtNumLicencia.setText("");
    }

    public void limpiarTotales() {
        frame.txtTotalNeto.setText("0.00");
        frame.txtIVA.setText("0.00");
        frame.txtFOSALUD.setText("0.00");
        frame.txtTotal.setText("0.00");
        frame.txtTotalDescuento.setText("0.00");
    }

    public void limpiarInfo() {
        frame.txtFecha.setDate(null);
        frame.txtNumeroCorrelativo.setText("");
        frame.txtCodigoCliente.setText("");
    }

    public void limpiar() {
        //limpiar
        frame.txtCodigoProducto1.setText("");
        frame.txtCodigoProducto2.setText("");
        frame.txtCodigoProducto3.setText("");
        frame.txtCodigoProducto4.setText("");
        frame.txtCodigoProducto5.setText("");
        frame.txtCodigoProducto6.setText("");
        frame.txtDescripcionProducto1.setText("");
        frame.txtDescripcionProducto2.setText("");
        frame.txtDescripcionProducto3.setText("");
        frame.txtDescripcionProducto4.setText("");
        frame.txtDescripcionProducto5.setText("");
        frame.txtDescripcionProducto6.setText("");
        frame.txtPrecioProducto1.setText("");
        frame.txtPrecioProducto2.setText("");
        frame.txtPrecioProducto3.setText("");
        frame.txtPrecioProducto4.setText("");
        frame.txtPrecioProducto5.setText("");
        frame.txtPrecioProducto6.setText("");
        frame.txtCantidadProducto1.setText("");
        frame.txtCantidadProducto2.setText("");
        frame.txtCantidadProducto3.setText("");
        frame.txtCantidadProducto4.setText("");
        frame.txtCantidadProducto5.setText("");
        frame.txtCantidadProducto6.setText("");
        frame.txtTotalProducto1.setText("");
        frame.txtTotalProducto2.setText("");
        frame.txtTotalProducto3.setText("");
        frame.txtTotalProducto4.setText("");
        frame.txtTotalProducto5.setText("");
        frame.txtTotalProducto6.setText("");
        frame.txtTotal.setText("0.00");
        frame.txtDescuento.setText("");
    }

    public boolean revisarCamposCredito() {
        if (frame.txtFecha.getDate()==null) {
            return true;
        }
        if (frame.txtRegistro.getText().equals("")) {
            return true;
        }
        if (frame.txtNumeroCorrelativo.getText().equals("")) {
            return true;
        }
        return false;
    }

    public boolean revisarCamposFactura() {
        if (frame.txtFecha.getDate()==null) {
            return true;
        }
        if (frame.txtNumeroCorrelativo.getText().equals("")) {
            return true;
        }
        return false;
    }

    public Double[] impuestos(JTextField campoTotal, JTextField campoDescripcion) {
        Double[] impuestos = new Double[3];
        double precioUni = Double.parseDouble(campoTotal.getText().trim());
        if (campoDescripcion.getText().substring(0, 5).equals("IMPOR")) {
            impuestos[0] = precioUni * 0.3;
            impuestos[0] = Math.rint(impuestos[0] * 100) / 100;
            impuestos[1] = (precioUni + impuestos[0]) * 0.13;
            impuestos[1] = Math.rint(impuestos[1] * 100) / 100;
            impuestos[2] = precioUni + impuestos[0] + impuestos[1];
            impuestos[2] = Math.rint(impuestos[2] * 100) / 100;
            return impuestos;
        } else {
            impuestos[0] = 0 * 0.3;
            impuestos[0] = Math.rint(impuestos[0] * 100) / 100;
            impuestos[1] = (precioUni + impuestos[0]) * 0.13;
            impuestos[1] = Math.rint(impuestos[1] * 100) / 100;
            impuestos[2] = precioUni + impuestos[0] + impuestos[1];
            impuestos[2] = Math.rint(impuestos[2] * 100) / 100;
            return impuestos;
        }
    }

    public Double[] calcular() {
        double fosalud = 0, iva = 0, totalNeto = 0;
        double totalFinal = 0;
        Double[] calculosARetornar;
        if (!frame.txtCodigoProducto1.getText().trim().equals("")) {
            Double[] calculos = impuestos(frame.txtTotalProducto1, frame.txtDescripcionProducto1);
            totalNeto += Double.parseDouble(frame.txtTotalProducto1.getText().trim());
            fosalud += calculos[0];
            iva += calculos[1];

        }
        if (!frame.txtCodigoProducto2.getText().trim().equals("")) {
            Double[] calculos = impuestos(frame.txtTotalProducto2, frame.txtDescripcionProducto2);
            totalNeto += Double.parseDouble(frame.txtTotalProducto2.getText().trim());
            fosalud += calculos[0];
            iva += calculos[1];

        }
        if (!frame.txtCodigoProducto3.getText().trim().equals("")) {
            Double[] calculos = impuestos(frame.txtTotalProducto3, frame.txtDescripcionProducto3);
            totalNeto += Double.parseDouble(frame.txtTotalProducto3.getText().trim());
            fosalud += calculos[0];
            iva += calculos[1];

        }
        if (!frame.txtCodigoProducto4.getText().trim().equals("")) {
            Double[] calculos = impuestos(frame.txtTotalProducto4, frame.txtDescripcionProducto4);
            totalNeto += Double.parseDouble(frame.txtTotalProducto4.getText().trim());
            fosalud += calculos[0];
            iva += calculos[1];
        }
        if (!frame.txtCodigoProducto5.getText().trim().equals("")) {
            Double[] calculos = impuestos(frame.txtTotalProducto5, frame.txtDescripcionProducto5);
            totalNeto += Double.parseDouble(frame.txtTotalProducto5.getText().trim());
            fosalud += calculos[0];
            iva += calculos[1];
        }
        if (!frame.txtCodigoProducto6.getText().trim().equals("")) {
            Double[] calculos = impuestos(frame.txtTotalProducto6, frame.txtDescripcionProducto6);
            totalNeto += Double.parseDouble(frame.txtTotalProducto6.getText().trim());
            fosalud += calculos[0];
            iva += calculos[1];
        }

        if (frame.rbtCredito.isSelected()) {
            //CREDITO FISCAL 
//            totalNeto = Math.rint(totalNeto*100)/100;
//            iva = Math.rint(iva*100)/100;
//            fosalud= Math.rint(fosalud*100)/100;
            
            totalFinal = totalNeto + fosalud + iva;
            //totalFinal = Math.rint(totalFinal*100)/100;
            calculosARetornar = new Double[4];
            calculosARetornar[0] = totalNeto;
            calculosARetornar[1] = fosalud;
            calculosARetornar[2] = iva;
            calculosARetornar[3] = totalFinal;
            System.out.println("IVA= "+calculosARetornar[2]);
            return calculosARetornar;
        } else {
            //CONSUMIDOR FINAL
//            totalNeto = Math.rint(totalNeto*100)/100;
//            iva = Math.rint(iva*100)/100;
//            fosalud= Math.rint(fosalud*100)/100;
            totalFinal = totalNeto + fosalud + iva;
           // totalFinal = Math.rint(totalFinal*100)/100;
            calculosARetornar = new Double[4];
            calculosARetornar[0] = totalNeto + iva;
            calculosARetornar[1] = fosalud;
            calculosARetornar[2] = 0.0;
            calculosARetornar[3] = totalFinal;
            return calculosARetornar;
        }

    }

    public String[][] informacionProductos() {
        String info[][] = {
            {frame.txtIdProducto1.getText(), frame.txtCantidadProducto1.getText(), frame.txtTotalProducto1.getText(), frame.txtPrecioProducto1.getText().trim()},
            {frame.txtIdProducto2.getText(), frame.txtCantidadProducto2.getText(), frame.txtTotalProducto2.getText(), frame.txtPrecioProducto2.getText().trim()},
            {frame.txtIdProducto3.getText(), frame.txtCantidadProducto3.getText(), frame.txtTotalProducto3.getText(), frame.txtPrecioProducto3.getText().trim()},
            {frame.txtIdProducto4.getText(), frame.txtCantidadProducto4.getText(), frame.txtTotalProducto4.getText(), frame.txtPrecioProducto4.getText().trim()},
            {frame.txtIdProducto5.getText(), frame.txtCantidadProducto5.getText(), frame.txtTotalProducto5.getText(), frame.txtPrecioProducto5.getText().trim()},
            {frame.txtIdProducto6.getText(), frame.txtCantidadProducto6.getText(), frame.txtTotalProducto6.getText(), frame.txtPrecioProducto6.getText().trim()}
        };

        return info;
    }
    
    public String[][] informacionProductosImprimir() {
        String info[][] = {
            {frame.txtCantidadProducto1.getText(), frame.txtDescripcionProducto1.getText(), frame.txtPrecioProducto1.getText(),frame.txtTotalProducto1.getText()},
            {frame.txtCantidadProducto2.getText(), frame.txtDescripcionProducto2.getText(), frame.txtPrecioProducto2.getText(),frame.txtTotalProducto2.getText()},
            {frame.txtCantidadProducto3.getText(), frame.txtDescripcionProducto3.getText(), frame.txtPrecioProducto3.getText(),frame.txtTotalProducto3.getText()},
            {frame.txtCantidadProducto4.getText(), frame.txtDescripcionProducto4.getText(), frame.txtPrecioProducto4.getText(),frame.txtTotalProducto4.getText()},
            {frame.txtCantidadProducto5.getText(), frame.txtDescripcionProducto5.getText(), frame.txtPrecioProducto5.getText(),frame.txtTotalProducto5.getText()},
            {frame.txtCantidadProducto6.getText(), frame.txtDescripcionProducto6.getText(), frame.txtPrecioProducto6.getText(),frame.txtTotalProducto6.getText()},
            
        };

        return info;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String idEmpresa = frame.comboEmpresas.getSelectedItem().toString().trim();
        idEmpresa = idEmpresa.substring(0, 1);
        if (e.getSource() == frame.rbtCredito) {
            frame.txtCliente.setEnabled(false);
            frame.txtDireccion.setEnabled(false);
            frame.txtNIT.setEnabled(false);
            frame.txtRegistro.setEnabled(true);
            frame.txtNumLicencia.setEnabled(false);
            limpiarEncabezados();
        }
        if (e.getSource() == frame.rbtFactura) {
            frame.txtCliente.setEnabled(true);
            frame.txtDireccion.setEnabled(true);
            frame.txtNIT.setEnabled(true);
            frame.txtRegistro.setEnabled(false);
            frame.txtNumLicencia.setEnabled(true);
            limpiarEncabezados();
        }

        if (e.getSource() == frame.btnCalculaTotal) {
            Double[] resultados = calcular();
            resultados[0] = Math.rint(resultados[0]*100)/100;
            resultados[1] = Math.rint(resultados[1]*100)/100;
            System.out.println(resultados[2]);
            resultados[2] = Math.rint(resultados[2]*100)/100;
            System.out.println(resultados[2]);
            resultados[3] = Math.rint(resultados[3]*100)/100;
            double descuento = 0;
            //PARA OBTENER EL DESCUENTO
            if(!frame.txtDescuento.getText().trim().equals("")){
                descuento = Double.parseDouble(frame.txtDescuento.getText().trim());
            }
            descuento/=100;
            descuento *=resultados[3];
            descuento = Math.rint(descuento*100)/100;
            frame.txtTotalDescuento.setText(String.valueOf(descuento));
            frame.txtTotalNeto.setText(String.valueOf(resultados[0]));
            frame.txtIVA.setText(String.valueOf(resultados[2]));
            frame.txtFOSALUD.setText(String.valueOf(resultados[1]));
            double totFinal = resultados[3] - descuento;
            totFinal = Math.rint(totFinal*100)/100;
            frame.txtTotal.setText(String.valueOf(totFinal));
        }
        if (e.getSource() == frame.btnProcesar) {
            double totalFinal = Double.parseDouble(frame.txtTotal.getText().trim());
            String formatoImpresion = frame.radioFormatoASEPRI.isSelected()? "formatoASEPRI" :"formatoMARDIMA";
            if (totalFinal != 0) {
                if (frame.rbtCredito.isSelected()) {
                    //ES un credito                    
                    if (!revisarCamposCredito()) {
                        fechaString = new SimpleDateFormat("yyyy-MM-dd").format(frame.txtFecha.getDate());
                        String numeroCorrelativo = frame.txtNumeroCorrelativo.getText().trim();
                        credito.setNumeroCorrelativo(numeroCorrelativo);
                        credito.setFecha(fechaString);
                        credito.setNumeroCorrelativo(frame.txtNumeroCorrelativo.getText().trim());
                        credito.setCodigoCliente(Integer.parseInt(frame.txtCodigoCliente.getText().trim()));
                        credito.setTotalNeto(Double.parseDouble(frame.txtTotalNeto.getText().trim()));
                        credito.setIVA(Double.parseDouble(frame.txtIVA.getText().trim()));
                        credito.setFosalud(Double.parseDouble(frame.txtFOSALUD.getText().trim()));
                        credito.setTotalFinal(Double.parseDouble(frame.txtTotal.getText().trim()));
                        credito.setId_empresa(Integer.parseInt(idEmpresa));
                        if(frame.txtDescuento.getText().trim().equals("")){
                            credito.setPorcentaje_descuento(0);
                        }else{
                            credito.setPorcentaje_descuento(Double.parseDouble(frame.txtDescuento.getText().trim()));
                        }
                        credito.setTotal_descuento(Double.parseDouble(frame.txtTotalDescuento.getText().trim()));
                        
                        if (consultasCredito.agregarCredito(credito)) {
                            //Credito agregado exitosamente
                            //GREGAR VENTAS INDIVIDUALES --> para creditos
                            Cardex cardex;
                            String[][] infoProductos = informacionProductos();
                            int cantidadFilas = infoProductos.length;
                            int idCredito = consultasCredito.ultimoCredito();
                            if(idCredito!=0){
                                detalleCredito.setId_credito(idCredito);
                            }
                            for (int fila = 0; fila < cantidadFilas; fila++) {
                                if (!infoProductos[fila][0].equals("")) {
                                    detalleCredito.setFecha(fechaString);
                                    detalleCredito.setId_producto(Integer.parseInt(infoProductos[fila][0]));
                                    detalleCredito.setCantidad(Integer.parseInt(infoProductos[fila][1]));
                                    detalleCredito.setTotal(Double.parseDouble(infoProductos[fila][2]));
                                    detalleCredito.setIva(Double.parseDouble(infoProductos[fila][3]));
                                    producto.setCodigo(Integer.parseInt(infoProductos[fila][0]));
                                    producto.setCantidad(Integer.parseInt(infoProductos[fila][1]));
                                    if (!consultasDetalleCredito.agregarDetalleCredito(detalleCredito)) {
                                        JOptionPane.showMessageDialog(null, "Ocurrio un error al agregar una venta en la fila " + fila + ". ");
                                        break;
                                    }
                                    //actualizar el inventario
                                    if (!consultas.restarCantidad(producto)) {
                                        JOptionPane.showMessageDialog(null, "Ocurrio un error al actualizar el inventario");
                                        break;
                                    }
                                    
                                    //agregar al cardex
                                    cardex = new Cardex();
                                    cardex.setId_producto(Integer.parseInt(infoProductos[fila][0]));
                                    cardex.setFecha(fechaString);
                                    cardex.setTipo_documento("Ventas");
                                    cardex.setCantidad(Integer.parseInt(infoProductos[fila][1]) * -1);
                                    cardex.setValor_unitario(Double.parseDouble(infoProductos[fila][3]) * -1);
                                    cardex.setNumero_documento(numeroCorrelativo);
                                    if(!consultasCardex.agregarACardex(cardex)){
                                        JOptionPane.showMessageDialog(null, "Ocurrio un error.");
                                        break;
                                    }                                    
                                }
                            }//fin for filas
                            
                            int respuesta=JOptionPane.showConfirmDialog(null, "¿Desea imprimir dicha factura?");
                            if(respuesta==JOptionPane.YES_OPTION){
                                Exportar expo=new Exportar();
                                expo.reporteCredito(credito, informacionProductosImprimir(),
                                        frame.txtRegistro.getText().trim(), 
                                        frame.txtCliente.getText(), 
                                        frame.txtDireccion.getText(), 
                                        frame.txtNIT.getText(),
                                        frame.txtNumLicencia.getText(),
                                        formatoImpresion,Double.parseDouble(frame.txtDescuento.getText().trim()),
                                        Double.parseDouble(frame.txtTotalDescuento.getText().trim()));
                            }
                            
                            limpiar();
                            limpiarEncabezados();
                            limpiarInfo();
                            limpiarTotales();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ocurrio un error al agregar el credito");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Favor ingresar todos los campos necesarios para procesar el credito");
                    }//FIN DEL CREDITO
                } else{ //ES una factura                
                    if (!revisarCamposFactura()) {
                        fechaString = new SimpleDateFormat("yyyy-MM-dd").format(frame.txtFecha.getDate());
                        String numeroCorrelativo = frame.txtNumeroCorrelativo.getText().trim();

                        factura.setFecha(fechaString);
                        factura.setNumeroCorrelativo(numeroCorrelativo);
                        factura.setCliente(frame.txtCliente.getText());
                        factura.setDireccion(frame.txtDireccion.getText());
                        factura.setNIT(frame.txtNIT.getText());
                        factura.setTotalNeto(Double.parseDouble(frame.txtTotalNeto.getText().trim()));
                        factura.setFosalud(Double.parseDouble(frame.txtFOSALUD.getText().trim()));
                        factura.setTotalFinal(Double.parseDouble(frame.txtTotal.getText().trim()));
                        factura.setNumero_licencia(frame.txtNumLicencia.getText());
                        factura.setId_empresa(Integer.parseInt(idEmpresa));
                        factura.setPorcentaje_descuento(Double.parseDouble(frame.txtDescuento.getText().trim()));
                        factura.setTotal_descuento(Double.parseDouble(frame.txtTotalDescuento.getText().trim()));
                        if(frame.txtDescuento.getText().trim().equals("")){
                            factura.setPorcentaje_descuento(0);
                        }else{
                            factura.setPorcentaje_descuento(Double.parseDouble(frame.txtDescuento.getText().trim()));
                        }
                        factura.setTotal_descuento(Double.parseDouble(frame.txtTotalDescuento.getText().trim()));

                        if (consultasFactura.agregarFactura(factura)) {
                            //Factura agregado exitosamente
                            //AGREGAR VENTAS INDIVIDUALES --> para facturas, consumidor final
                            Cardex cardex;
                            System.out.println("COMPROBANTE: "+numeroCorrelativo);
                            String[][] infoProductos = informacionProductos();
                            int cantidadFilas = infoProductos.length;
                            int idComprobante = consultasFactura.ultimaFactura();
                            if(idComprobante!=0){
                                venta.setIdComprobante(idComprobante);
                            }
                            for (int fila = 0; fila < cantidadFilas; fila++) {
                                if (!infoProductos[fila][0].equals("")) {
                                    venta.setNumeroCorrelativo(numeroCorrelativo);
                                    venta.setFecha(fechaString);
                                    venta.setCodigoProducto(Integer.parseInt(infoProductos[fila][0]));
                                    venta.setCantidad(Integer.parseInt(infoProductos[fila][1]));
                                    venta.setTotal(Double.parseDouble(infoProductos[fila][2]));
                                    venta.setPrecio_venta(Double.parseDouble(infoProductos[fila][3]));
                                    producto.setCodigo(Integer.parseInt(infoProductos[fila][0]));
                                    producto.setCantidad(Integer.parseInt(infoProductos[fila][1]));
                                    if (!consultasVenta.agregarVenta(venta)) {
                                        JOptionPane.showMessageDialog(null, "Ocurrio un error al agregar una venta en la fila " + fila + ". ");
                                        break;
                                    }
                                    //actualizar el inventario
                                    if (!consultas.restarCantidad(producto)) {
                                        JOptionPane.showMessageDialog(null, "Ocurrio un error al actualizar el inventario");
                                        break;
                                    }//fin restar inventario
                                    
                                    //agregar al cardex
                                    cardex = new Cardex();
                                    cardex.setId_producto(Integer.parseInt(infoProductos[fila][0]));
                                    cardex.setFecha(fechaString);
                                    cardex.setTipo_documento("Ventas");
                                    cardex.setCantidad(Integer.parseInt(infoProductos[fila][1]) * -1);
                                    cardex.setValor_unitario(Double.parseDouble(infoProductos[fila][3]) * -1);
                                    cardex.setNumero_documento(numeroCorrelativo);
                                    if(!consultasCardex.agregarACardex(cardex)){
                                        JOptionPane.showMessageDialog(null, "Ocurrio un error.");
                                        break;
                                    }   
                                    
                                }//fin si producto esta vacio
                            }//fin for filas
                            int respuesta=JOptionPane.showConfirmDialog(null, "¿Desea imprimir dicha factura?");
                            if(respuesta==JOptionPane.YES_OPTION){
                                Exportar expo=new Exportar();
                                expo.reporteFactura(factura, informacionProductosImprimir(),formatoImpresion);
                            }                            
                            limpiar();
                            limpiarEncabezados();
                            limpiarInfo();
                            limpiarTotales();
                            
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "Ocurrio un error al agregar la factura.");
                        }
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Favor Agregar los campos necesarios para procesar la factura.");
                    }//FIN DE LA FACTURA
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Favor ingresar productos, o dar clic en el boton CALCULAR.");
            }
        }//fin procesar
        if (e.getSource() == frame.btnCerrar) {
            frame.dispose();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        String idEmpresa = frame.comboEmpresas.getSelectedItem().toString().trim();
        idEmpresa = idEmpresa.substring(0, 1);
        //CODIGOS
        if (e.getSource() == frame.txtCodigoProducto1) {
            if (!frame.txtCodigoProducto1.getText().equals("")) {
                //cambiar esta parte, por codigo_interno
                producto.setCodigo_interno(frame.txtCodigoProducto1.getText().trim());
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                if (consultas.buscarConStockPorEmpresa(producto)) {
                    String local = "";
                    if (producto.getLocal_importado() == 1) {
                        local = "IMPOR";
                    } else {
                        local = "LOCAL";
                    }
                    frame.txtIdProducto1.setText(String.valueOf(producto.getCodigo()));
                    frame.txtDescripcionProducto1.setText(local + " - " + producto.getDescripcion());
                    frame.txtPrecioProducto1.setText(producto.getPrecio().toString());
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro producto con dicho codigo.", "Error en COdigo de Producto", JOptionPane.ERROR_MESSAGE);
                    frame.txtIdProducto1.setText("");
                    frame.txtCodigoProducto1.setText("");
                    frame.txtDescripcionProducto1.setText("");
                    frame.txtPrecioProducto1.setText("");
                    frame.txtCantidadProducto1.setText("");
                    frame.txtTotalProducto1.setText("");
                    frame.txtCodigoProducto1.requestFocus();
                }
            } else {
                frame.txtIdProducto1.setText("");
                frame.txtCodigoProducto1.setText("");
                frame.txtDescripcionProducto1.setText("");
                frame.txtPrecioProducto1.setText("");

            }
        }

        if (e.getSource() == frame.txtCodigoProducto2) {
            if (!frame.txtCodigoProducto2.getText().equals("")) {
                //cambiar esta parte por codigo interno
                producto.setCodigo_interno(frame.txtCodigoProducto2.getText().trim());
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                if (consultas.buscarConStockPorEmpresa(producto)) {
                    String local = "";
                    if (producto.getLocal_importado() == 1) {
                        local = "IMPOR";
                    } else {
                        local = "LOCAL";
                    }
                    frame.txtIdProducto2.setText(String.valueOf(producto.getCodigo()));
                    frame.txtDescripcionProducto2.setText(local + " - " + producto.getDescripcion());
                    frame.txtPrecioProducto2.setText(producto.getPrecio().toString());
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro producto con dicho codigo.", "Error en COdigo de Producto", JOptionPane.ERROR_MESSAGE);
                    frame.txtIdProducto2.setText("");
                    frame.txtCodigoProducto2.setText("");
                    frame.txtDescripcionProducto2.setText("");
                    frame.txtPrecioProducto2.setText("");
                    frame.txtCantidadProducto2.setText("");
                    frame.txtTotalProducto2.setText("");
                    frame.txtCodigoProducto2.requestFocus();
                }
            } else {
                frame.txtIdProducto2.setText("");
                frame.txtCodigoProducto2.setText("");
                frame.txtDescripcionProducto2.setText("");
                frame.txtPrecioProducto2.setText("");

            }
        }

        if (e.getSource() == frame.txtCodigoProducto3) {
            if (!frame.txtCodigoProducto3.getText().equals("")) {
                //cambiar esta parte por codigo_inerno
                producto.setCodigo_interno(frame.txtCodigoProducto3.getText().trim());
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                if (consultas.buscarConStockPorEmpresa(producto)) {
                    String local = "";
                    if (producto.getLocal_importado() == 1) {
                        local = "IMPOR";
                    } else {
                        local = "LOCAL";
                    }
                    frame.txtIdProducto3.setText(String.valueOf(producto.getCodigo()));
                    frame.txtDescripcionProducto3.setText(local + " - " + producto.getDescripcion());
                    frame.txtPrecioProducto3.setText(producto.getPrecio().toString());
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro producto con dicho codigo.", "Error en COdigo de Producto", JOptionPane.ERROR_MESSAGE);
                    frame.txtIdProducto3.setText("");
                    frame.txtCodigoProducto3.setText("");
                    frame.txtDescripcionProducto3.setText("");
                    frame.txtPrecioProducto3.setText("");
                    frame.txtCantidadProducto3.setText("");
                    frame.txtTotalProducto3.setText("");
                    frame.txtCodigoProducto3.requestFocus();
                }
            } else {
                frame.txtIdProducto3.setText("");
                frame.txtCodigoProducto3.setText("");
                frame.txtDescripcionProducto3.setText("");
                frame.txtPrecioProducto3.setText("");

            }
        }

        if (e.getSource() == frame.txtCodigoProducto4) {
            if (!frame.txtCodigoProducto4.getText().equals("")) {
                //cambiar esta parte por codigo interno
                producto.setCodigo_interno(frame.txtCodigoProducto4.getText().trim());
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                if (consultas.buscarConStockPorEmpresa(producto)) {
                    String local = "";
                    if (producto.getLocal_importado() == 1) {
                        local = "IMPOR";
                    } else {
                        local = "LOCAL";
                    }
                    frame.txtIdProducto4.setText(String.valueOf(producto.getCodigo()));
                    frame.txtDescripcionProducto4.setText(local + " - " + producto.getDescripcion());
                    frame.txtPrecioProducto4.setText(producto.getPrecio().toString());
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro producto con dicho codigo.", "Error en COdigo de Producto", JOptionPane.ERROR_MESSAGE);
                    frame.txtIdProducto4.setText("");
                    frame.txtCodigoProducto4.setText("");
                    frame.txtDescripcionProducto4.setText("");
                    frame.txtPrecioProducto4.setText("");
                    frame.txtCantidadProducto4.setText("");
                    frame.txtTotalProducto4.setText("");
                    frame.txtCodigoProducto4.requestFocus();
                }
            } else {
                frame.txtIdProducto4.setText("");
                frame.txtCodigoProducto4.setText("");
                frame.txtDescripcionProducto4.setText("");
                frame.txtPrecioProducto4.setText("");

            }
        }

        if (e.getSource() == frame.txtCodigoProducto5) {
            if (!frame.txtCodigoProducto5.getText().equals("")) {
                //cambiar esta parte por codigointerno
                producto.setCodigo_interno(frame.txtCodigoProducto5.getText().trim());
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                if (consultas.buscarConStockPorEmpresa(producto)) {
                    String local = "";
                    if (producto.getLocal_importado() == 1) {
                        local = "IMPOR";
                    } else {
                        local = "LOCAL";
                    }
                    frame.txtIdProducto5.setText(String.valueOf(producto.getCodigo()));
                    frame.txtDescripcionProducto5.setText(local + " - " + producto.getDescripcion());
                    frame.txtPrecioProducto5.setText(producto.getPrecio().toString());
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro producto con dicho codigo.", "Error en COdigo de Producto", JOptionPane.ERROR_MESSAGE);
                    frame.txtIdProducto5.setText("");
                    frame.txtCodigoProducto5.setText("");
                    frame.txtDescripcionProducto5.setText("");
                    frame.txtPrecioProducto5.setText("");
                    frame.txtCantidadProducto5.setText("");
                    frame.txtTotalProducto5.setText("");
                    frame.txtCodigoProducto5.requestFocus();
                }
            } else {
                frame.txtIdProducto5.setText("");
                frame.txtCodigoProducto5.setText("");
                frame.txtDescripcionProducto5.setText("");
                frame.txtPrecioProducto5.setText("");

            }
        }

        if (e.getSource() == frame.txtCodigoProducto6) {
            if (!frame.txtCodigoProducto6.getText().equals("")) {
                //cambiar esta parte por codigo interno
                producto.setCodigo_interno(frame.txtCodigoProducto6.getText().trim());
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                if (consultas.buscarConStockPorEmpresa(producto)) {
                    String local = "";
                    if (producto.getLocal_importado() == 1) {
                        local = "IMPOR";
                    } else {
                        local = "LOCAL";
                    }
                    frame.txtIdProducto6.setText(String.valueOf(producto.getCodigo()));
                    frame.txtDescripcionProducto6.setText(local + " - " + producto.getDescripcion());
                    frame.txtPrecioProducto6.setText(producto.getPrecio().toString());
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro producto con dicho codigo.", "Error en COdigo de Producto", JOptionPane.ERROR_MESSAGE);
                    frame.txtIdProducto6.setText("");
                    frame.txtCodigoProducto6.setText("");
                    frame.txtDescripcionProducto6.setText("");
                    frame.txtPrecioProducto6.setText("");
                    frame.txtCantidadProducto6.setText("");
                    frame.txtTotalProducto6.setText("");
                    frame.txtCodigoProducto6.requestFocus();
                }
            } else {
                frame.txtIdProducto6.setText("");
                frame.txtCodigoProducto6.setText("");
                frame.txtDescripcionProducto6.setText("");
                frame.txtPrecioProducto6.setText("");

            }
        }

        //CANTIDADES
        if (e.getSource() == frame.txtCantidadProducto1) {
            if (!frame.txtCantidadProducto1.getText().equals("") && !frame.txtPrecioProducto1.getText().equals("")) {
                int cantidad = Integer.parseInt(frame.txtCantidadProducto1.getText().trim());
                double precio = Double.parseDouble(frame.txtPrecioProducto1.getText().trim());
                double total = cantidad * precio;
                total = Math.rint(total * 100) / 100;
                
                producto.setCodigo(Integer.parseInt(frame.txtIdProducto1.getText().trim()));
                producto.setCantidad(cantidad);
                if(consultas.revisarCantidad(producto)){
                    frame.txtTotalProducto1.setText(String.valueOf(total));
                }else{
                    frame.txtCodigoProducto1.setText("");
                    frame.txtDescripcionProducto1.setText("");
                    frame.txtCantidadProducto1.setText("");
                }                
            } else {
                frame.txtCantidadProducto1.setText("");
                frame.txtTotalProducto1.setText("");
            }
        }

        if (e.getSource() == frame.txtCantidadProducto2) {
            if (!frame.txtCantidadProducto2.getText().equals("") && !frame.txtPrecioProducto2.getText().equals("")) {
                int cantidad = Integer.parseInt(frame.txtCantidadProducto2.getText().trim());
                Double precio = Double.parseDouble(frame.txtPrecioProducto2.getText().trim());
                double total = cantidad * precio;
                total = Math.rint(total * 100) / 100;
                producto.setCodigo(Integer.parseInt(frame.txtIdProducto2.getText().trim()));
                producto.setCantidad(cantidad);
                if(consultas.revisarCantidad(producto)){
                    frame.txtTotalProducto2.setText(String.valueOf(total));
                }else{
                    frame.txtCodigoProducto2.setText("");
                    frame.txtDescripcionProducto2.setText("");
                    frame.txtCantidadProducto2.setText("");
                }
            } else {
                frame.txtCantidadProducto2.setText("");
                frame.txtTotalProducto2.setText("");
            }
        }

        if (e.getSource() == frame.txtCantidadProducto3) {
            if (!frame.txtCantidadProducto3.getText().equals("") && !frame.txtPrecioProducto3.getText().equals("")) {
                int cantidad = Integer.parseInt(frame.txtCantidadProducto3.getText().trim());
                Double precio = Double.parseDouble(frame.txtPrecioProducto3.getText().trim());
                double total = cantidad * precio;
                total = Math.rint(total * 100) / 100;
                producto.setCodigo(Integer.parseInt(frame.txtIdProducto3.getText().trim()));
                producto.setCantidad(cantidad);
                if(consultas.revisarCantidad(producto)){
                    frame.txtTotalProducto3.setText(String.valueOf(total));
                }else{
                    frame.txtCodigoProducto3.setText("");
                    frame.txtDescripcionProducto3.setText("");
                    frame.txtCantidadProducto3.setText("");
                }
            } else {
                frame.txtCantidadProducto3.setText("");
                frame.txtTotalProducto3.setText("");
            }
        }

        if (e.getSource() == frame.txtCantidadProducto4) {
            if (!frame.txtCantidadProducto4.getText().equals("") && !frame.txtPrecioProducto4.getText().equals("")) {
                int cantidad = Integer.parseInt(frame.txtCantidadProducto4.getText().trim());
                Double precio = Double.parseDouble(frame.txtPrecioProducto4.getText().trim());
                double total = cantidad * precio;
                total = Math.rint(total * 100) / 100;
                producto.setCodigo(Integer.parseInt(frame.txtIdProducto4.getText().trim()));
                producto.setCantidad(cantidad);
                if(consultas.revisarCantidad(producto)){
                    frame.txtTotalProducto4.setText(String.valueOf(total));
                }else{
                    frame.txtCodigoProducto4.setText("");
                    frame.txtDescripcionProducto4.setText("");
                    frame.txtCantidadProducto4.setText("");
                }
            } else {
                frame.txtCantidadProducto4.setText("");
                frame.txtTotalProducto4.setText("");
            }
        }

        if (e.getSource() == frame.txtCantidadProducto5) {
            if (!frame.txtCantidadProducto5.getText().equals("") && !frame.txtPrecioProducto5.getText().equals("")) {
                int cantidad = Integer.parseInt(frame.txtCantidadProducto5.getText().trim());
                Double precio = Double.parseDouble(frame.txtPrecioProducto5.getText().trim());
                double total = cantidad * precio;
                total = Math.rint(total * 100) / 100;
                producto.setCodigo(Integer.parseInt(frame.txtIdProducto5.getText().trim()));
                producto.setCantidad(cantidad);
                if(consultas.revisarCantidad(producto)){
                    frame.txtTotalProducto5.setText(String.valueOf(total));
                }else{
                    frame.txtCodigoProducto5.setText("");
                    frame.txtDescripcionProducto5.setText("");
                    frame.txtCantidadProducto5.setText("");
                }
            } else {
                frame.txtCantidadProducto5.setText("");
                frame.txtTotalProducto5.setText("");
            }
        }

        if (e.getSource() == frame.txtCantidadProducto6) {
            if (!frame.txtCantidadProducto6.getText().equals("") && !frame.txtPrecioProducto6.getText().equals("")) {
                int cantidad = Integer.parseInt(frame.txtCantidadProducto6.getText().trim());
                Double precio = Double.parseDouble(frame.txtPrecioProducto6.getText().trim());
                double total = cantidad * precio;
                total = Math.rint(total * 100) / 100;
                producto.setCodigo(Integer.parseInt(frame.txtIdProducto6.getText().trim()));
                producto.setCantidad(cantidad);
                if(consultas.revisarCantidad(producto)){
                    frame.txtTotalProducto6.setText(String.valueOf(total));
                }else{
                    frame.txtCodigoProducto6.setText("");
                    frame.txtDescripcionProducto6.setText("");
                    frame.txtCantidadProducto6.setText("");
                }
            } else {
                frame.txtCantidadProducto6.setText("");
                frame.txtTotalProducto6.setText("");
            }
        }

        //Codigo de registro cliente
        if (e.getSource() == frame.txtRegistro) {
            if (!frame.txtRegistro.getText().equals("")) {
                cliente.setRegistro(frame.txtRegistro.getText().trim());

                if (consultasCliente.buscarClientePorRegistro(cliente)) {
                    frame.txtCodigoCliente.setText(String.valueOf(cliente.getCodigo()));
                    frame.txtCliente.setText(cliente.getNombre());
                    frame.txtDireccion.setText(cliente.getDireccion());
                    frame.txtNIT.setText(cliente.getNIT());
                    frame.txtNumLicencia.setText(cliente.getNumero_licencia());
                } else {
                    JOptionPane.showMessageDialog(null, "El cliente no se ha podido encontrar.");
                    frame.txtRegistro.requestFocus();
                }
            }
        }
    }

}
