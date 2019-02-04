package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ConsultasEmpresas;
import vista.frmImprimirInventario;

public class ControlImprimir implements ActionListener{

    private Exportar exportar;
    private frmImprimirInventario frameImprimir;
    private ConsultasEmpresas consultasEmpresas;

    public ControlImprimir(Exportar exportar, frmImprimirInventario frameImprimir, ConsultasEmpresas consultasEmpresas) {
        this.exportar = exportar;
        this.frameImprimir = frameImprimir;
        this.consultasEmpresas=consultasEmpresas;
        
        frameImprimir.btnImprimirExistencias.addActionListener(this);
        frameImprimir.btnImprimirTodo.addActionListener(this);
    }
    
    public void cargar() {
        llenarComboEmpresas();        
    }
    
    public void llenarComboEmpresas(){
        try {
            ResultSet resultados = consultasEmpresas.mostrarEmpresas();
            while(resultados.next()){
                frameImprimir.comboEmpresas.addItem(String.valueOf(resultados.getInt("id_empresa")) + " - " + resultados.getString("nombre"));                
            }
        } catch (SQLException ex) {
            System.out.println("ERROR AL LLENAR EL COMBOBOX DE EMPRESAS. "+ex);
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String idEmpresa = frameImprimir.comboEmpresas.getSelectedItem().toString().trim();
        idEmpresa = idEmpresa.substring(0, 1);
        int id = Integer.parseInt(idEmpresa);
        if(e.getSource()==frameImprimir.btnImprimirExistencias){
            exportar.reporteInventario(0,id);
            
        }
        if(e.getSource()==frameImprimir.btnImprimirTodo){
            System.out.println("idempresa: "+id);
            exportar.reporteInventario(-1,id);
            
        }
    }
    
}
