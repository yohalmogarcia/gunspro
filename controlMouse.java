/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import modelo.ConsultasProducto;
import modelo.Producto;
import vista.frmProducto;

/**
 *
 * @author Yohalmo Garcia
 */
public class controlMouse implements MouseListener{
    
    private frmProducto frame;
    private Producto modeloProducto;
    private ConsultasProducto modeloConsultas;
    
    public controlMouse(frmProducto frame,Producto modeloProducto, ConsultasProducto modeloConsultas) {
        this.frame=frame;
        this.modeloProducto=modeloProducto;
        this.modeloConsultas=modeloConsultas;
        
        frame.tblProductos.addMouseListener(this);
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int filaSeleccionada=frame.tblProductos.getSelectedRow();
        String codigo=frame.tblProductos.getValueAt(filaSeleccionada, 0).toString();
        
        modeloProducto.setCodigo(Integer.parseInt(codigo));
        frame.btnAgregarProducto.setEnabled(false);
        if(modeloConsultas.buscar(modeloProducto)){
            frame.comboEmpresas.setEnabled(false);
            frame.txtCodigoInterno.setEnabled(true);
            frame.txtCodigoProducto.setText(String.valueOf(modeloProducto.getCodigo()));
            frame.txtDescripcionProducto.setText(modeloProducto.getDescripcion());
            frame.txtCantidadProducto.setText(String.valueOf(modeloProducto.getCantidad()));
            frame.txtCOstoProducto.setText(String.valueOf(modeloProducto.getCostos()));
            frame.txtPrecioVentaProducto.setText(String.valueOf(modeloProducto.getPrecio()));
            frame.txtCodigoInterno.setText(modeloProducto.getCodigo_interno());
            frame.txtCodigoInternoOld.setText(modeloProducto.getCodigo_interno());
            if(modeloProducto.getLocal_importado()==1){
                frame.ckbInventarioProducto.setSelected(true);
            }else{
                frame.ckbInventarioProducto.setSelected(false);
            }            
        }else{
            JOptionPane.showMessageDialog(null, "Ocurrio un error al momento de seleccionar el producto.");
        }
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
