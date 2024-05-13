/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eithanmonroy.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.eithanmonroy.dao.Conexion;
import org.eithanmonroy.dto.CompraDTO;
import org.eithanmonroy.model.Compra;
import org.eithanmonroy.system.main;
import org.eithanmonroy.utils.SuperKinalAlert;

/**
 *
 * @author Eithan
 */
public class FormComprasController implements Initializable  {
    private main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
   @FXML
    Button btnCancelar,btnGuardar;
   
   @FXML
   TextField tfCompraId,tfFecha;
   
   @FXML
   private void handleButtonAction(ActionEvent event) {
    if (event.getSource() == btnCancelar) {
        CompraDTO.getCompraDTO().setCompra(null);
        stage.menuComprasView();
    } else if (event.getSource() == btnGuardar) {
        
            stage.menuComprasView();
        if (op == 2) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK) {
                editarCompra();
                CompraDTO.getCompraDTO().setCompra(null);
                SuperKinalAlert.getInstance().mostrarAlertasInformacion(500);
                stage.menuComprasView();
            } else {
                stage.menuComprasView();
            }
        }
    }
}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(CompraDTO.getCompraDTO().getCompra() != null){
            cargarDatos(CompraDTO.getCompraDTO().getCompra());
        }
        
    }    
    
    public void cargarDatos(Compra compra) {
    tfCompraId.setText(Integer.toString(compra.getCompraId()));

    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
    tfFecha.setText(formatoFecha.format(compra.getFechaCompra()));
}

    
    public void editarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarCompra(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCompraId.getText()));
            statement.setString(2,tfFecha.getText());
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
    
    public main getStage() {
        return stage;
    }

    public void setStage(main stage) {
        this.stage = stage;
    }
    
    public void setOp(int op) {
        this.op = op;
    }
    
}
