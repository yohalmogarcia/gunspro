package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Credito;
import modelo.Factura;
import modelo.ProductoImprimir;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class Exportar {

    public void reporteFactura(Factura factura, String[][] infoProductos, String formato) {
        String nombreArchivo = "facturaASEPRI";
        if(formato.equals("formatoMARDIMA")){
            nombreArchivo="facturaMARDIMA";
        }
        ProductoImprimir productos;
        try {
            List<ProductoImprimir> listaProductos = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                if (!infoProductos[i][0].equals("")) {
                    productos = new ProductoImprimir(Integer.parseInt(infoProductos[i][0]), infoProductos[i][1].substring(7), Double.parseDouble(infoProductos[i][2]), Double.parseDouble(infoProductos[i][3]));
                    listaProductos.add(productos);
                }
            }

            String url = System.getProperty("user.dir") + "/reportes/"+nombreArchivo+".jasper";
            HashMap parametros = new HashMap();

            parametros.put("numeroFactura", factura.getNumeroCorrelativo());
            parametros.put("fecha", factura.getFecha());
            parametros.put("cliente", factura.getCliente());
            parametros.put("direccion", factura.getDireccion());
            parametros.put("nit", factura.getNIT());
            parametros.put("totalNeto", String.valueOf(factura.getTotalNeto()));
            parametros.put("fosalud", String.valueOf(factura.getFosalud()));
            parametros.put("totalFinal", String.valueOf(factura.getTotalFinal()));
            parametros.put("numero_licencia", factura.getNumero_licencia());
            parametros.put("porcentaje_descuento", factura.getPorcentaje_descuento());
            parametros.put("total_descuento", factura.getTotal_descuento());

            JasperPrint informe = JasperFillManager.fillReport(url, parametros, new JRBeanCollectionDataSource(listaProductos));
            JasperViewer.viewReport(informe, false);

        } catch (NullPointerException e) {
            System.out.println("null pointer en informe: " + e);
            JOptionPane.showMessageDialog(null, "No Hay datos para mostrar");
        } catch (NumberFormatException | JRException e) {
            System.out.println("ERROR " + e);
        }

    }

    public void reporteCredito(Credito credito, 
            String[][] infoProductos, String registro, 
            String cliente, String direccion, String nit, 
            String numero_licencia, String formato, double porcentaje_descuento, double total_descuento) {
        String nombreArchivo = "creditoASEPRI";
        if(formato.equals("formatoMARDIMA")){
            nombreArchivo="creditoMARDIMA";
        }
        ProductoImprimir productos;
        try {
            List<ProductoImprimir> listaProductos = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                if (!infoProductos[i][0].equals("")) {
                    productos = new ProductoImprimir(Integer.parseInt(infoProductos[i][0]), infoProductos[i][1].substring(7), Double.parseDouble(infoProductos[i][2]), Double.parseDouble(infoProductos[i][3]));
                    listaProductos.add(productos);
                }
            }

            String url = System.getProperty("user.dir") + "/reportes/"+nombreArchivo+".jasper";
            HashMap parametros = new HashMap();
            System.out.println(registro);
            parametros.put("numeroCredito", credito.getNumeroCorrelativo());
            parametros.put("fecha", credito.getFecha());
            parametros.put("cliente", cliente);
            parametros.put("direccion", direccion);
            parametros.put("nit", nit);
            parametros.put("totalNeto", String.valueOf(credito.getTotalNeto()));
            parametros.put("iva", String.valueOf(credito.getIVA()));
            parametros.put("fosalud", String.valueOf(credito.getFosalud()));
            parametros.put("totalFinal", String.valueOf(credito.getTotalFinal()));
            parametros.put("registro", registro);
            parametros.put("numero_licencia", numero_licencia);
            parametros.put("porcentaje_descuento", porcentaje_descuento);
            parametros.put("total_descuento", total_descuento);

            JasperPrint informe = JasperFillManager.fillReport(url, parametros, new JRBeanCollectionDataSource(listaProductos));
            JasperViewer.viewReport(informe, false);

        } catch (NullPointerException e) {
            System.out.println("null pointer en informe: " + e);
            JOptionPane.showMessageDialog(null, "No Hay datos para mostrar");
        } catch (NumberFormatException | JRException e) {
            System.out.println("ERROR " + e);
        }

    }

    public void reporteInventario(int opcion, int id_empresa) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gunspro", "root", "");
            HashMap parametros = new HashMap();
            parametros.put("condicion", opcion);
            parametros.put("id_empresa", id_empresa);
            String url = System.getProperty("user.dir") + "/reportes/inventario.jasper";
            System.out.println(url);
            System.out.println("parametros: " + parametros);
            System.out.println("con: " + con);
            try {
                JasperPrint informe = JasperFillManager.fillReport(url, parametros, con);
                System.out.println("informe: " + informe);
                JasperViewer.viewReport(informe, false);
                con.close();
            } catch (NullPointerException e) {
                System.out.println("null pointer en informe: " + e);
                JOptionPane.showMessageDialog(null, "No Hay datos para mostrar");
            }
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR EN REPORTE DE INVENTARIO: " + ex);
        }

    }

    public void reimpresionCredito(int idCredito,String formato) {
        String nombreArchivo = "rCreditoASEPRI";
        if(formato.equals("formatoMARDIMA")){
            nombreArchivo="rCreditoMARDIMA";
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gunspro", "root", "");
            HashMap parametros = new HashMap();
            parametros.put("id_credito", idCredito);
            String url = System.getProperty("user.dir") + "/reportes/"+nombreArchivo+".jasper";
            JasperPrint informe = JasperFillManager.fillReport(url, parametros, con);
            JasperViewer.viewReport(informe, false);
            con.close();
        } catch (NullPointerException e) {
            System.out.println("null pointer en informe: " + e);
            JOptionPane.showMessageDialog(null, "No Hay datos para mostrar");
        } catch (NumberFormatException e) {
            System.out.println("ERROR " + e);
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void reimpresionFactura(int idFactura, String formato) {
        String nombreArchivo = "rFacturaASEPRI";
        if(formato.equals("formatoMARDIMA")){
            nombreArchivo="rFacturaMARDIMA";
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gunspro", "root", "");
            HashMap parametros = new HashMap();
            parametros.put("id_factura", idFactura);
            String url = System.getProperty("user.dir") + "/reportes/"+nombreArchivo+".jasper";
            JasperPrint informe = JasperFillManager.fillReport(url, parametros, con);
            JasperViewer.viewReport(informe, false);
            con.close();

        } catch (NullPointerException e) {
            System.out.println("null pointer en informe: " + e);
            JOptionPane.showMessageDialog(null, "No Hay datos para mostrar");
        } catch (NumberFormatException e) {
            System.out.println("ERROR " + e);
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void reporteVentas(String fechaDesde, String fechaHasta, int idEmpresa) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gunspro", "root", "");
            HashMap parametros = new HashMap();
            parametros.put("fechaDesde", fechaDesde);
            parametros.put("fechaHasta", fechaHasta);
            parametros.put("id_empresa", idEmpresa);
            String url = System.getProperty("user.dir") + "/reportes/ventas.jasper";
            JasperPrint informe = JasperFillManager.fillReport(url, parametros, con);
            JasperViewer.viewReport(informe, false);
            con.close();
        } catch (NullPointerException e) {
            System.out.println("null pointer en informe: " + e);
            JOptionPane.showMessageDialog(null, "No Hay datos para mostrar");
        } catch (NumberFormatException e) {
            System.out.println("ERROR " + e);
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void reporteVentasPorFacturas(String fechaDesde, String fechaHasta, int idEmpresa) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gunspro", "root", "");
            HashMap parametros = new HashMap();
            parametros.put("fechaDesde", fechaDesde);
            parametros.put("fechaHasta", fechaHasta);
            parametros.put("id_empresa", idEmpresa);
            String url = System.getProperty("user.dir") + "/reportes/ventas_facturas.jasper";//cambiar
            JasperPrint informe = JasperFillManager.fillReport(url, parametros, con);
            JasperViewer.viewReport(informe, false);
            con.close();
        } catch (NullPointerException e) {
            System.out.println("null pointer en informe: " + e);
            JOptionPane.showMessageDialog(null, "No Hay datos para mostrar");
        } catch (NumberFormatException e) {
            System.out.println("ERROR " + e);
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void reporteVentasPorCreditos(String fechaDesde, String fechaHasta, int idEmpresa) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gunspro", "root", "");
            HashMap parametros = new HashMap();
            parametros.put("fechaDesde", fechaDesde);
            parametros.put("fechaHasta", fechaHasta);
            parametros.put("id_empresa", idEmpresa);
            String url = System.getProperty("user.dir") + "/reportes/ventas_creditos.jasper";//cambiar
            JasperPrint informe = JasperFillManager.fillReport(url, parametros, con);
            JasperViewer.viewReport(informe, false);
            con.close();
        } catch (NullPointerException e) {
            System.out.println("null pointer en informe: " + e);
            JOptionPane.showMessageDialog(null, "No Hay datos para mostrar");
        } catch (NumberFormatException e) {
            System.out.println("ERROR " + e);
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
        public void imprimirCardex(int codigoProducto) {
            System.out.println("CODIGO DE PRODUCTO: "+codigoProducto);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gunspro", "root", "");
            HashMap parametros = new HashMap();
            parametros.put("id_producto", codigoProducto);
            String url = System.getProperty("user.dir") + "/reportes/cardex.jasper";
            JasperPrint informe = JasperFillManager.fillReport(url, parametros, con);
            JasperViewer.viewReport(informe, false);
            con.close();
        } catch (NullPointerException e) {
            System.out.println("null pointer en informe: " + e);
            JOptionPane.showMessageDialog(null, "No Hay datos para mostrar");
        } catch (NumberFormatException e) {
            System.out.println("ERROR CON FORMATO DE NUMERO" + e);
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            System.out.println("ERROR CON: "+ex);
        }
    }
        
        

}
