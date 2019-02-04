package controlador;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Backup {

    public void exportarBD(String ruta) {
        Process p;
        //String url = System.getProperty("user.dir") + "/backup/backup.sql";
        String url = ruta;
        try {
            p = Runtime.getRuntime().exec("C:\\wamp\\bin\\mysql\\mysql5.5.24\\bin\\mysqldump --opt -uroot  -B gunspro ");
            
            InputStream is = p.getInputStream();
            FileOutputStream fos = new FileOutputStream(url);
            byte[] buffer = new byte[1000];
            int leido = is.read(buffer);
            while (leido > 0) {
                fos.write(buffer, 0, leido);
                leido = is.read(buffer);
            }
            fos.close();
            JOptionPane.showMessageDialog(null, "Ha finalizado el proceso");
        } catch (IOException ex) {
            System.out.println("Ocurrio un error al exportar la base de datos. "+ex+" - "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error al realizar el respaldo");
        }
    }
    
    public void importarBD(String direccion){
        Process p;
        String url = direccion;
        try{
            p = Runtime.getRuntime().exec("C:\\wamp\\bin\\mysql\\mysql5.5.24\\bin\\mysql -u root  gunspro");
            OutputStream os = p.getOutputStream();
            FileInputStream fis = new FileInputStream(url);
            byte[] buffer = new byte[1000];

            int leido = fis.read(buffer);
            System.out.println("EMPEZANDO EL PROCESO: "+leido);
            while (leido > 0) {
                os.write(buffer, 0, leido);
                leido = fis.read(buffer);
                System.out.println("PROCESANDO...");
            }
            os.flush();
            os.close();
            fis.close();
            JOptionPane.showMessageDialog(null, "Ha finalizado el proceso");
        }catch(IOException ex){
            System.out.println("ERROR AL IMPORTAR LA BASE DE DATOS. "+ex);
            JOptionPane.showMessageDialog(null, "Ocurrio un error al realizar el respaldo");
        }
    }

}
