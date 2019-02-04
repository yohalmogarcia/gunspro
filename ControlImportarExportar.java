package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import vista.frmBackup;

public class ControlImportarExportar implements ActionListener{
    private Backup backup;
    private frmBackup frameBackup;

    public ControlImportarExportar(Backup backup, frmBackup frameBackup) {
        this.backup = backup;
        this.frameBackup = frameBackup;
        
        frameBackup.btnExportar.addActionListener(this);
        frameBackup.btnImportar.addActionListener(this);
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Date fecha = new Date();
        if(e.getSource()==frameBackup.btnExportar){
            DateFormat hourdateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String fechita = hourdateFormat.format(fecha);
            JFileChooser buscador = new JFileChooser();
            String nameFile = "backup-"+fechita;
            buscador.setSelectedFile(new File(nameFile));
            buscador.showSaveDialog(null);
            buscador.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            
            
            String ruta = (buscador.getSelectedFile()+".sql");
            
            //console.log()
            backup.exportarBD(ruta);
        }
        if(e.getSource()==frameBackup.btnImportar){
            JFileChooser buscador = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("SQL", "sql");
            buscador.setFileFilter(filtro);
            int respuesta=buscador.showOpenDialog(null);
            if(respuesta==JFileChooser.APPROVE_OPTION){
                String ruta=buscador.getSelectedFile().getPath();
                System.out.println("ruta: "+ruta);
                backup.importarBD(ruta);
            }
        }
        
    }
        
}
