package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import modelo.ConsultasEmpresas;
import vista.frmReporteVentas;

public class ControlImpresionVentas implements ActionListener{
    
    private frmReporteVentas frame;
    public String fechaDesde, fechaHasta;
    private ConsultasEmpresas consultasEmpresas;
    
    
    public ControlImpresionVentas(frmReporteVentas frame, ConsultasEmpresas consultasEmpresas) {
        this.frame = frame;
        this.consultasEmpresas=consultasEmpresas;
        
        frame.btnImprimir.addActionListener(this);
    }
    
    public void cargar() {
        //llenarTablaProductos();
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
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String idEmpresa = frame.comboEmpresas.getSelectedItem().toString().trim();
            idEmpresa = idEmpresa.substring(0, 1);
        if(e.getSource()==frame.btnImprimir){
            if(frame.txtFechaDesde.getDate()!=null && frame.txtFechaHasta.getDate()!=null){
                fechaDesde = new SimpleDateFormat("yyyy-MM-dd").format(frame.txtFechaDesde.getDate());
                fechaHasta = new SimpleDateFormat("yyyy-MM-dd").format(frame.txtFechaHasta.getDate());
                Exportar exportar = new Exportar();
                //REVISAR SI ES POR CREDITOS O POR FACTURAS
                if(frame.radioFacturas.isSelected()){
                    //llamar al reporte de ventas por facturas
                    exportar.reporteVentasPorFacturas(fechaDesde, fechaHasta,Integer.parseInt(idEmpresa));
                }else{
                    //llamar al reporte de ventas por creditos
                    exportar.reporteVentasPorCreditos(fechaDesde, fechaHasta,Integer.parseInt(idEmpresa));
                }                
            }else{
                JOptionPane.showMessageDialog(null, "Favor rellenar los campos del formulario.");
            }
        }
    }
    
    
    
}
