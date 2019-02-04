package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.Cliente;
import modelo.ConsultasCliente;
import modelo.ConsultasCredito;
import modelo.ConsultasEmpresas;
import modelo.ConsultasFactura;
import modelo.Credito;
import modelo.Factura;
import vista.frmReimpresion;

public class ControlReimpresionDocumentos implements ActionListener {

    private Factura factura;
    private ConsultasFactura consultasFactura;
    private Credito credito;
    private ConsultasCredito consultasCredito;
    private Cliente cliente;
    private ConsultasCliente consultasCliente;
    private frmReimpresion frameReimpresion;
    private ConsultasEmpresas consultasEmpresas;

    public ControlReimpresionDocumentos(Factura factura,
            ConsultasFactura consultasFactura, Credito credito,
            ConsultasCredito consultasCredito, Cliente cliente,
            ConsultasCliente consultasCliente, frmReimpresion frameReimpresion,
            ConsultasEmpresas consultasEmpresas) {
        this.factura = factura;
        this.consultasFactura = consultasFactura;
        this.credito = credito;
        this.consultasCredito = consultasCredito;
        this.cliente = cliente;
        this.consultasCliente = consultasCliente;
        this.frameReimpresion = frameReimpresion;
        this.consultasEmpresas = consultasEmpresas;

        //listeners
        frameReimpresion.btnBuscar.addActionListener(this);
        frameReimpresion.btnImprimir.addActionListener(this);

    }

    public void cargar() {
        frameReimpresion.btnImprimir.setEnabled(false);
        llenarComboEmpresas();
    }

    public void llenarComboEmpresas() {
        try {
            ResultSet resultados = consultasEmpresas.mostrarEmpresas();
            while (resultados.next()) {
                frameReimpresion.comboEmpresas.addItem(String.valueOf(resultados.getInt("id_empresa")) + " - " + resultados.getString("nombre"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR AL LLENAR EL COMBOBOX DE EMPRESAS. " + ex);
        }
    }

    public boolean revisarCampos() {
        return frameReimpresion.txtNumeroComprobante.getText().equals("");
    }

    public void limpiarCampos() {
        frameReimpresion.txtFecha.setText("");
        frameReimpresion.txtNombreCliente.setText("");
        frameReimpresion.txtTotalFinal.setText("");
    }

    public void infoFactura(int idEmpresa) {
        factura.setNumeroCorrelativo(frameReimpresion.txtNumeroComprobante.getText().trim());
        factura.setId_empresa(idEmpresa);
        if (consultasFactura.buscarFactura(factura)) {
            frameReimpresion.txtFecha.setText(factura.getFecha());
            frameReimpresion.txtNombreCliente.setText(factura.getCliente());
            frameReimpresion.txtTotalFinal.setText(String.valueOf(factura.getTotalFinal()));
            frameReimpresion.btnImprimir.setEnabled(true);
        }
    }

    public void infoCredito(int idEmpresa) {
        credito.setNumeroCorrelativo(frameReimpresion.txtNumeroComprobante.getText().trim());
        credito.setId_empresa(idEmpresa);
        if (consultasCredito.buscarCredito(credito)) {
            cliente.setCodigo(credito.getCodigoCliente());
            if (consultasCliente.buscarCliente(cliente)) {
                frameReimpresion.txtFecha.setText(credito.getFecha());
                frameReimpresion.txtNombreCliente.setText(cliente.getNombre());
                frameReimpresion.txtTotalFinal.setText(String.valueOf(credito.getTotalFinal()));
                frameReimpresion.btnImprimir.setEnabled(true);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String formatoImpresion = frameReimpresion.radioFormatoASEPRI.isSelected()? "formatoASEPRI" :"formatoMARDIMA";
        String idEmpresa = frameReimpresion.comboEmpresas.getSelectedItem().toString().trim();
        idEmpresa = idEmpresa.substring(0, 1);
        if (e.getSource() == frameReimpresion.btnBuscar) {
            if (!revisarCampos()) {
                if (frameReimpresion.rbtCredito.isSelected()) {
                    frameReimpresion.btnImprimir.setEnabled(false);
                    limpiarCampos();
                    infoCredito(Integer.parseInt(idEmpresa));
                } else {
                    frameReimpresion.btnImprimir.setEnabled(false);
                    limpiarCampos();
                    infoFactura(Integer.parseInt(idEmpresa));
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe ingresar un numero de correlativo.");
                frameReimpresion.txtNumeroComprobante.requestFocus();
            }
        }

        if (e.getSource() == frameReimpresion.btnImprimir) {
            if (frameReimpresion.rbtCredito.isSelected()) {
                Exportar exportar = new Exportar();
                exportar.reimpresionCredito(credito.getCodigoCredito(),formatoImpresion);
            } else {
                Exportar exportar = new Exportar();
                exportar.reimpresionFactura(factura.getCodigoFactura(),formatoImpresion);
            }
        }

    }

}
