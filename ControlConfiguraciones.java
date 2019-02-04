package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Configuraciones;
import modelo.ConsultasConfiguraciones;
import vista.frmConfiguraciones;

public class ControlConfiguraciones implements ActionListener{
    
    private Configuraciones conf;
    private ConsultasConfiguraciones consultasConf;
    private frmConfiguraciones frame;

    public ControlConfiguraciones(Configuraciones conf, ConsultasConfiguraciones consultasConf, frmConfiguraciones frame) {
        this.conf=conf;
        this.consultasConf=consultasConf;
        this.frame=frame;
        //listener
        frame.btnGuardar.addActionListener(this);
        frame.btnCancelar.addActionListener(this);
    }
    
    public void cargar(){
        conf=consultasConf.verConfiguraciones();
        if(conf!=null){
            frame.txtNumEmpresas.setText(String.valueOf(conf.getNum_empresas()));
            frame.txtNumUsuarios.setText(String.valueOf(conf.getNum_usuarios()));
        }else{
            JOptionPane.showMessageDialog(null, "Ocurrio un error al consultar las configuraciones");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==frame.btnGuardar){
            conf.setNum_empresas(Integer.parseInt(frame.txtNumEmpresas.getText().trim()));
            conf.setNum_usuarios(Integer.parseInt(frame.txtNumUsuarios.getText().trim()));
            if(consultasConf.modificarConfiguraciones(conf)){
                JOptionPane.showMessageDialog(null, "Se Actualizaron las configuraciones exitosamente");
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error al modificar las configuraciones");
            }
        }
        if(e.getSource()==frame.btnCancelar){
            frame.hide();
        }
    }
    
    
    
}
