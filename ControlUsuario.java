package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultasEmpresas;
import modelo.ConsultasUsuario;
import modelo.Usuario;
import vista.frmUsuarios;

public class ControlUsuario implements ActionListener, MouseListener {

    private Usuario usuario;
    private ConsultasUsuario consultas;
    private ConsultasEmpresas consultasEmpresas;
    private frmUsuarios frame;
    DefaultTableModel modelo = new DefaultTableModel();

    public ControlUsuario(Usuario usuario, ConsultasUsuario consultas, frmUsuarios frame, ConsultasEmpresas consultasEmpresas) {
        this.usuario = usuario;
        this.consultas = consultas;
        this.frame = frame;
        this.consultasEmpresas = consultasEmpresas;

        //Listeners
        frame.btnAgregar.addActionListener(this);
        frame.btnEliminar.addActionListener(this);
        frame.btnModificar.addActionListener(this);
        frame.btnNuevo.addActionListener(this);

        frame.tblUsuarios.addMouseListener(this);

    }

    public void cargar() {
        llenarTablaUsuarios();
        llenarComboEmpresas();
        frame.txtUsuarioOld.setText("");
        frame.txtUsuarioOld.setVisible(false);
        frame.btnAgregar.setEnabled(false);
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

    public void llenarTablaUsuarios() {
        try {
            frame.tblUsuarios.setModel(modelo);
            ResultSet resultados = consultas.usuariosActivos();
            ResultSetMetaData rsmd = resultados.getMetaData();
            modelo.addColumn("CODIGO");
            modelo.addColumn("NOMBRES");
            modelo.addColumn("APELLIDOS");
            modelo.addColumn("USUARIO");
            modelo.addColumn("NIVEL");
            modelo.addColumn("ESTADO");
            modelo.addColumn("EMPRESA");
            while (resultados.next()) {
                Object[] filas = new Object[7];
                for (int i = 0; i < 7; i++) {
                    if (i == 4) {
                        if (resultados.getObject(i + 1).equals(1)) {
                            filas[i] = "SuperUsuario";
                        } else if ((resultados.getObject(i + 1)).equals(2)) {
                            filas[i] = "Contador";
                        } else {
                            filas[i] = "Secretaria";
                        }
                    }
                    if (i == 5) {
                        if (resultados.getObject(i + 1).equals(1)) {
                            filas[i] = "Activo";
                        } else {
                            filas[i] = "Inactivo";
                        }
                    } else {
                        filas[i] = resultados.getObject(i + 1);
                    }

                }
                modelo.addRow(filas);
            }
        } catch (SQLException ex) {
            System.out.println("Error al llenar la tabla de USUARIO. " + ex);
        }
    }

    public void limpiartablaUsuarios() {
        modelo.setColumnCount(0);
        modelo.setRowCount(0);
    }

    public void limpiarCampos() {
        frame.txtCodigo.setText("");
        frame.txtNombres.setText("");
        frame.txtApellidos.setText("");
        frame.txtUsuario.setText("");
        frame.txtContrasenia.setText("");
        frame.rbtSecretaria.isSelected();
        frame.rbtActivo.isSelected();
        frame.btnAgregar.setEnabled(false);
        frame.txtUsuarioOld.setText("");
    }

    public boolean revisarCampos() {
        if (frame.comboEmpresas.getSelectedItem().toString().equals("")) {
            return true;
        }
        if (frame.txtCodigo.getText().trim().equals("")) {
            return true;
        }
        if (frame.txtNombres.getText().equals("")) {
            return true;
        }
        if (frame.txtApellidos.getText().equals("")) {
            return true;
        }
        if (frame.txtUsuario.getText().equals("")) {
            return true;
        }
        if (frame.txtContrasenia.getPassword().equals("")) {
            return true;
        }
        return false;
    }

    public boolean revisarCamposSinID() {
        if (frame.comboEmpresas.getSelectedItem().toString().equals("")) {
            return true;
        }
        if (frame.txtNombres.getText().equals("")) {
            return true;
        }
        if (frame.txtApellidos.getText().equals("")) {
            return true;
        }
        if (frame.txtUsuario.getText().equals("")) {
            return true;
        }
        if (frame.txtContrasenia.getPassword().equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frame.btnAgregar) {
            if (!revisarCamposSinID()) {
                if (frame.txtContrasenia.getPassword().length < 5) {
                    JOptionPane.showMessageDialog(null, "La contraseña debe tener 5 caracteres como minimo");
                } else {
                    String idEmpresa = frame.comboEmpresas.getSelectedItem().toString().trim();
                    idEmpresa = idEmpresa.substring(0, 1);
                    usuario.setId_empresa(Integer.parseInt(idEmpresa));
                    usuario.setNombresUsuario(frame.txtNombres.getText());
                    usuario.setApellidosUsuario(frame.txtApellidos.getText());
                    usuario.setUsuario(frame.txtUsuario.getText().trim().toLowerCase());
                    usuario.setContrasenia(String.valueOf(frame.txtContrasenia.getPassword()));
                    if (frame.rbtSecretaria.isSelected()) {
                        usuario.setTipoUsuario(3);
                    } else if (frame.rbtContador.isSelected()) {
                        usuario.setTipoUsuario(2);
                    } else if (frame.rbtSuperUsuario.isSelected()) {
                        usuario.setTipoUsuario(1);
                    }
                    usuario.setEstado(1);
                    if(!consultas.buscarUsuarioActivoSinEmpresa(usuario.getUsuario(),"")){
                        if (consultas.agregarUsuario(usuario)) {
                            JOptionPane.showMessageDialog(null, "Se agrego el usuario satisfactoriamente.");
                            limpiarCampos();
                            limpiartablaUsuarios();
                            llenarTablaUsuarios();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ocurrio un error ingresando el nuevo usuario.");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "El usuario ya existe");
                    }                    
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe agregar informacion correcta en el formulario.");
            }

        }
        if (e.getSource() == frame.btnModificar) {
            if (!revisarCampos()) {
                if (frame.txtContrasenia.getPassword().length < 5) {
                    JOptionPane.showMessageDialog(null, "La contraseña debe tener 5 caracteres como minimo");
                } else {
                    usuario.setIdUsuario(Integer.parseInt(frame.txtCodigo.getText()));
                    usuario.setNombresUsuario(frame.txtNombres.getText());
                    usuario.setApellidosUsuario(frame.txtApellidos.getText());
                    usuario.setUsuario(frame.txtUsuario.getText().trim().toLowerCase());
                    usuario.setContrasenia(String.valueOf(frame.txtContrasenia.getPassword()));
                    if (frame.rbtSecretaria.isSelected()) {
                        usuario.setTipoUsuario(3);
                    } else if (frame.rbtContador.isSelected()) {
                        usuario.setTipoUsuario(2);
                    } else if (frame.rbtSuperUsuario.isSelected()) {
                        usuario.setTipoUsuario(1);
                    }
                    if (frame.rbtActivo.isSelected()) {
                        usuario.setEstado(1);
                    } else {
                        usuario.setEstado(2);
                    }
                    if(!consultas.buscarUsuarioActivoSinEmpresa(usuario.getUsuario(),frame.txtUsuarioOld.getText().trim())){
                        if (consultas.modificarUsuario(usuario)) {
                            JOptionPane.showMessageDialog(null, "Se actualizo el usuario exitosamente.");
                            limpiarCampos();
                            limpiartablaUsuarios();
                            llenarTablaUsuarios();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ocurrio un error modificando el usuario.");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "El usuario ya existe.");
                    }                    
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe agregar informacion correcta en el formulario.");
            }
        }

        if (e.getSource() == frame.btnEliminar) {
            if (frame.txtCodigo.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un usuario de la tabla de la izquierda.");
            } else {
                usuario.setIdUsuario(Integer.parseInt(frame.txtCodigo.getText()));
                if (consultas.eliminarUsuario(usuario)) {
                    JOptionPane.showMessageDialog(null, "El usuario se ha inactivado con exito.");
                    limpiarCampos();
                    limpiartablaUsuarios();
                    llenarTablaUsuarios();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un error eliminando el usuario.");
                }
            }
        }

        if (e.getSource() == frame.btnNuevo) {
            limpiarCampos();
            frame.comboEmpresas.setEnabled(true);
            frame.btnAgregar.setEnabled(true);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int filaSeleccionada = frame.tblUsuarios.getSelectedRow();
        String codigo = frame.tblUsuarios.getValueAt(filaSeleccionada, 0).toString();

        usuario.setIdUsuario(Integer.parseInt(codigo));
        if (consultas.buscarUsuario(usuario)) {
            frame.comboEmpresas.setEnabled(false);
            frame.txtCodigo.setText(String.valueOf(usuario.getIdUsuario()));
            frame.txtNombres.setText(usuario.getNombresUsuario());
            frame.txtApellidos.setText(usuario.getApellidosUsuario());
            frame.txtUsuario.setText(usuario.getUsuario());
            frame.txtContrasenia.setText(usuario.getContrasenia());
            frame.txtUsuarioOld.setText(usuario.getUsuario());
            int tipoUsuario = usuario.getTipoUsuario();
            if (tipoUsuario == 1) {
                frame.rbtSuperUsuario.setSelected(true);
            } else if (tipoUsuario == 2) {
                frame.rbtContador.setSelected(true);
            } else {
                frame.rbtSecretaria.setSelected(true);
            }
            int estado = usuario.getEstado();
            if (estado == 1) {
                frame.rbtActivo.setSelected(true);
            } else {
                frame.rbtInactivo.setSelected(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al seleccionar el usuario.");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
