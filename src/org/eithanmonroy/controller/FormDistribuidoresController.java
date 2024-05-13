/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eithanmonroy.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.eithanmonroy.dao.Conexion;
import org.eithanmonroy.dto.DistribuidorDTO;
import org.eithanmonroy.model.Distribuidor;
import org.eithanmonroy.system.main;
import org.eithanmonroy.utils.SuperKinalAlert;

/**
 *
 * @author informatica
 */
public class FormDistribuidoresController implements Initializable{
    
    private main stage;
    private int op;
    
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
   @FXML
    Button btnCancelar,btnGuardar;
   
   @FXML
   TextField tfDistribuidorId,tfNombreDistribuidor,tfDireccionDistribuidor,tfNitDistribuidor,tfTelefonoDistribuidor,tfWeb;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnCancelar){
            DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
            stage.menuDistribuidoresView();
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombreDistribuidor.getText().equals("") && !tfDireccionDistribuidor.getText().equals("") && !tfNitDistribuidor.getText().equals("") && !tfTelefonoDistribuidor.getText().equals("")){
                    agregarDistribuidor();
                    SuperKinalAlert.getInstance().mostrarAlertasInformacion(400);
                    stage.menuDistribuidoresView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertasInformacion(600);
                    if(tfNombreDistribuidor.getText().equals("")){
                        tfNombreDistribuidor.requestFocus();
                    }else if(tfDireccionDistribuidor.getText().equals("")){
                        tfDireccionDistribuidor.requestFocus();
                    }else if(tfNitDistribuidor.getText().equals("")){
                        tfNitDistribuidor.requestFocus();
                    }else if(tfTelefonoDistribuidor.getText().equals("")){
                        tfTelefonoDistribuidor.requestFocus();
                    }
                }
                
            }else if(op == 2){
                if(!tfNombreDistribuidor.getText().equals("") && !tfDireccionDistribuidor.getText().equals("") && !tfNitDistribuidor.getText().equals("") && !tfTelefonoDistribuidor.getText().equals("")){
                    editarCargo();
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                        DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
                        SuperKinalAlert.getInstance().mostrarAlertasInformacion(500);
                        stage.menuDistribuidoresView();
                    }else{
                        stage.menuDistribuidoresView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertasInformacion(600);
                    if(tfNombreDistribuidor.getText().equals("")){
                        tfNombreDistribuidor.requestFocus();
                    }else if(tfDireccionDistribuidor.getText().equals("")){
                        tfDireccionDistribuidor.requestFocus();
                    }else if(tfNitDistribuidor.getText().equals("")){
                        tfNitDistribuidor.requestFocus();
                    }else if(tfTelefonoDistribuidor.getText().equals("")){
                        tfTelefonoDistribuidor.requestFocus();
                    }
                }
                
            }
        }
    
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(DistribuidorDTO.getDistribuidorDTO().getDistribuidor() != null){
            cargarDatos(DistribuidorDTO.getDistribuidorDTO().getDistribuidor());
        }
    }    
    
    public void cargarDatos(Distribuidor distribuidor){
        tfDistribuidorId.setText(Integer.toString(distribuidor.getDistribuidorId()));
        tfNombreDistribuidor.setText(distribuidor.getNombreDistribuidor());
        tfDireccionDistribuidor.setText(distribuidor.getDireccionDistribuidor());
        tfNitDistribuidor.setText(distribuidor.getNitDistribuidor());
        tfTelefonoDistribuidor.setText(distribuidor.getTelefonoDistribuidor());
        tfWeb.setText(distribuidor.getWeb());

    }
    
    public void agregarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDistribuidor(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreDistribuidor.getText());
            statement.setString(2, tfDireccionDistribuidor.getText());
            statement.setString(3, tfNitDistribuidor.getText());
            statement.setString(4, tfTelefonoDistribuidor.getText());
            statement.setString(5, tfWeb.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void editarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarDistribuidor(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setString(2, tfNombreDistribuidor.getText());
            statement.setString(3, tfDireccionDistribuidor.getText());
            statement.setString(4, tfNitDistribuidor.getText());
            statement.setString(5, tfTelefonoDistribuidor.getText());
            statement.setString(6, tfWeb.getText());
            statement.execute();
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void setStage(main stage) {
        this.stage = stage;
    }
    
    public void setOp(int op) {
        this.op = op;
    }
    
}
