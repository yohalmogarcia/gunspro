package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.Cardex;
import modelo.ConsultasEmpresas;
import modelo.ConsultasProducto;
import modelo.Producto;
import vista.frmCardex;

public class ControlCardex implements ActionListener{
    
    private Producto producto;
    private ConsultasProducto consultasProducto;
    private frmCardex frame;
    private ConsultasEmpresas consultasEmpresas;

    public ControlCardex( frmCardex frame) {
        this.producto= new Producto();
        this.consultasProducto = new ConsultasProducto();
        this.frame=frame;
        this.consultasEmpresas = new ConsultasEmpresas();
        
        //listener
        frame.btnBuscar.addActionListener(this);
        frame.btnImprimirCardex.addActionListener(this);
    }
    
    public void cargar() {
        frame.btnImprimirCardex.setEnabled(false);
        frame.txtNombreProducto.setEnabled(false);
        frame.txtIdProducto.setVisible(false);
        llenarComboEmpresas();
    }

    public void llenarComboEmpresas() {
        try {
            ResultSet resultados = consultasEmpresas.mostrarEmpresas();
            while (resultados.next()) {
                frame.comboEmpresas.addItem(String.valueOf(resultados.getInt("id_empresa")) + " - " + resultados.getString("nombre"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR AL LLENAR EL COMBOBOX DE EMPRESAS. " + ex);
        }
    }
    
    public boolean revisarCampos(){
        return frame.txtCodigoProducto.getText().trim().equals("");
    }
    
    public void limpiarCampos(){
        //frame.txtCodigoProducto.setText("");
        frame.txtIdProducto.setText("");
        frame.txtNombreProducto.setText("");
        frame.btnImprimirCardex.setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String idEmpresa = frame.comboEmpresas.getSelectedItem().toString().trim();
        idEmpresa = idEmpresa.substring(0, 1);
        if(e.getSource()==frame.btnBuscar){
            if(!revisarCampos()){
                producto.setCodigo_interno(frame.txtCodigoProducto.getText().trim());
                producto.setId_empresa(Integer.parseInt(idEmpresa));
                if(consultasProducto.buscarPorCodigoInterno(producto)){
                    frame.txtNombreProducto.setText(producto.getDescripcion());
                    frame.txtIdProducto.setText(String.valueOf(producto.getCodigo()));
                    frame.btnImprimirCardex.setEnabled(true);
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontro dicho producto.");
                    limpiarCampos();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Debe ingresar un código de producto.");
                limpiarCampos();
            }
        }
        
        if(e.getSource()==frame.btnImprimirCardex){
            Exportar exportar = new Exportar();
            exportar.imprimirCardex(producto.getCodigo());
        }
    }
    
}
