package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.ConsultasUsuario;
import modelo.Usuario;
import vista.frmLogin;
import vista.index;

public class ControlLogin implements ActionListener{
    private Usuario usuario;
    private ConsultasUsuario consultas;
    private frmLogin frame;

    public ControlLogin(Usuario usuario, ConsultasUsuario consultas, frmLogin frame) {
        this.usuario = usuario;
        this.consultas = consultas;
        this.frame = frame;
        
        //listeners
        //frame.btnCancelar.addActionListener(this);
        frame.btnIngresar.addActionListener(this);
        
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==frame.btnIngresar){
            usuario.setUsuario(frame.txtUsuario.getText().trim());
            usuario.setContrasenia(String.valueOf(frame.txtContrasenia.getPassword()));
            if(consultas.loguear(usuario)){
                index in= new index();
                in.setVisible(true);
                frame.setVisible(false);
                in.txtBienvenida.setText("Bienvenid@ "+usuario.getNombresUsuario()+" "+usuario.getApellidosUsuario());
                in.txtCodigousuario.setText(String.valueOf(usuario.getIdUsuario()));
                in.txtTipoUsuario.setText(String.valueOf(usuario.getTipoUsuario()));
                //PERMISOS DE USUARIOS
                if(usuario.getTipoUsuario()==3){//SECRETARIA
                    //in.menuProductos.setEnabled(false);
                    in.menuUsuarios.setEnabled(false);
                    //in.menuCompras.setEnabled(false);
                    //in.menuReportes.setEnabled(false);
                    //in.menuRespaldo.setEnabled(false);
                    in.menuCardex.setVisible(false);
                }
                if(usuario.getTipoUsuario()==2){//CONTADOR
                    in.menuUsuarios.setEnabled(false);
                    //in.menuReportes.setEnabled(false);
                    //in.menuRespaldo.setEnabled(false);
                }
                if(usuario.getTipoUsuario()==1 || usuario.getTipoUsuario()==2 || usuario.getTipoUsuario()==3){
                    in.menuConfiguraciones.setVisible(false);
                }
                
            }else{
                frame.txtError.setText("Usuario y/o contraseña incorrectos");
            }
        }
    }
    
}
