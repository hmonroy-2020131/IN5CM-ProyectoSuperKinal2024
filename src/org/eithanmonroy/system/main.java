/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eithanmonroy.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.eithanmonroy.controller.FormCargoController;
import org.eithanmonroy.controller.FormClienteController;
import org.eithanmonroy.controller.FormDistribuidoresController;
import org.eithanmonroy.controller.FormEmpleadosController;
import org.eithanmonroy.controller.MenuCargoController;
import org.eithanmonroy.controller.MenuClienteController;
import org.eithanmonroy.controller.MenuDistribuidoresController;
import org.eithanmonroy.controller.MenuEmpleadosController;
import org.eithanmonroy.controller.MenuPrincipalController;
import org.eithanmonroy.controller.MenuTicketSoporteController;


/**
 *
 * @author Eithan
 */

public class main extends Application {
    private final String URLVIEW = "/org/eithanmonroy/view/";
    private Stage stage;
    private Scene scene;
    private int op;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Super Kinal APP");
        menuPrincipalView();
        stage.show();
    }
    public Initializable switchScene(String fxmlName, int width, int height) throws Exception{
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(main.class.getResource(URLVIEW + fxmlName));
        scene = new Scene((AnchorPane)loader.load(file), width, height);
        stage.setScene(scene);
        stage.sizeToScene();
        resultado = (Initializable)loader.getController();
        return resultado;
    }
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml", 1200, 775);
            menuPrincipalView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void menuClienteView(){
        try{
            MenuClienteController menuClientesView = (MenuClienteController)switchScene("MenuClienteView.fxml", 1200, 750);
            menuClientesView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuTicketSoporteView(){
        try{
            MenuTicketSoporteController menuTicketSoporteView = (MenuTicketSoporteController)switchScene("MenuTicketSoporteView.fxml", 1200, 750);
            menuTicketSoporteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formClientesView(int op){
        try{
            FormClienteController formClientesView = (FormClienteController)switchScene("FormClienteView.fxml", 500, 750);
            formClientesView.setOp(op);
            formClientesView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCargoView(){
        try{
            MenuCargoController menuCargosView = (MenuCargoController)switchScene("MenuCargoView.fxml", 1200, 750);
            menuCargosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formCargosView(int op){
        try{
            FormCargoController formCargoView = (FormCargoController)switchScene("FormCargoView.fxml", 500, 750);
            formCargoView.setOp(op);
            formCargoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuDistribuidoresView(){
        try{
            MenuDistribuidoresController menuCargosView = (MenuDistribuidoresController)switchScene("MenuDistribuidoresView.fxml", 1200, 750);
            menuCargosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formDistribuidoresView(int op){
        try{
            FormDistribuidoresController formDistribuidorView = (FormDistribuidoresController)switchScene("FormDistribuidoresView.fxml", 500, 750);
            formDistribuidorView.setOp(op);
            formDistribuidorView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuEmpleadosView(){
        try{
            MenuEmpleadosController menuEmpleado = (MenuEmpleadosController)switchScene("MenuEmpleadosView.fxml", 1200, 750);
            menuEmpleado.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formEmpleadosView(int op){
        try{
            FormEmpleadosController formEmpleado = (FormEmpleadosController)switchScene("FormEmpleadosView.fxml", 500, 750);
            formEmpleado.setOp(op);
            formEmpleado.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}