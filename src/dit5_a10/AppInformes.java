/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dit5_a10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Usuario
 */
public class AppInformes extends Application {

    public static Connection conexion = null;

    @Override
    public void start(Stage stage) throws Exception {
        conectaBD();
        MenuBar mb=new MenuBar();
        Menu m1=new Menu("Informes");
        Menu m2=new Menu("Salir");
        MenuItem mi1=new MenuItem("Lisstado Facturas");
        MenuItem mi2=new MenuItem("Ventas Totales");
        MenuItem mi3=new MenuItem("Factura");
        MenuItem mi4=new MenuItem("Listado Facturas 2");
        
        m1.getItems().addAll(mi1,mi2,mi3,mi4);
        
        mb.getMenus().addAll(m1,m2);
        
        VBox root = new VBox();
        root.getChildren().addAll(mb);

        mi2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                generaInforme();
                System.out.println("Generando informe");
            }
        });
        
        mi1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                generaInforme2();
                System.out.println("Generando informe");

            }
        });
        
        mi3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage=new Stage();
                FlowPane sp=new FlowPane();   
                TextField p=new TextField();
                Button b=new Button("Generar");
                sp.getChildren().add(p);
                sp.getChildren().add(b);
                stage.setTitle("Informe3");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(sp));
                stage.show();
                try{
                    b.setOnAction(e->{
                        int parametro=Integer.parseInt(p.getText());
                        generaInforme3(parametro);
                        System.out.println("Generando informe");
                    });
                } catch(Exception ex){System.out.println("Error en los parametros");}
                
            }
        });
        
        mi4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                generaInforme4();
                System.out.println("Generando informe");

            }
        });

        Scene scene = new Scene(root, 300, 250);

        stage.setTitle("Obtener informe");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void stop() throws Exception {
        try {
            DriverManager.getConnection("jdbc:hsqldb:hsql://localhost;shutdown=true");
        } catch (Exception ex) {
            System.out.println("No se pudo cerrar la conexion a la BD");
        }
    }

    public void conectaBD() {
        //Establecemos conexión con la BD
        String baseDatos = "jdbc:hsqldb:hsql://localhost:9001/xdb";
        String usuario = "sa";
        String clave = "";
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            conexion = DriverManager.getConnection(baseDatos, usuario, clave);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Fallo al cargar JDBC");
            System.exit(1);
        } catch (SQLException sqle) {
            System.err.println("No se pudo conectar a BD");
            System.exit(1);
        } catch (java.lang.InstantiationException sqlex) {
            System.err.println("Imposible Conectar");
            System.exit(1);
        } catch (Exception ex) {
            System.err.println("Imposible Conectar");
            System.exit(1);
        }
    }

    public void generaInforme() {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(AppInformes.class.getResource("VentasTotales.jasper"));

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, null, conexion);
            JasperViewer.viewReport(jp);
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void generaInforme2() {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(AppInformes.class.getResource("Ejer10_A1.jasper"));
            
            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, null, conexion);
            JasperViewer.viewReport(jp);
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void generaInforme3(int parametro) {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(AppInformes.class.getResource("Factura.jasper"));
            //Map de parámetros
            Map parametros = new HashMap();
            parametros.put("AddressId", parametro);

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
            JasperViewer.viewReport(jp);
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void generaInforme4() {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(AppInformes.class.getResource("ListadoFactura2.jasper"));
            //Map de parámetros

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, null, conexion);
            JasperViewer.viewReport(jp);
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
