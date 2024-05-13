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
import org.eithanmonroy.dto.CategoriaProductoDTO;
import org.eithanmonroy.model.CategoriaProducto;
import org.eithanmonroy.system.main;
import org.eithanmonroy.utils.SuperKinalAlert;

/**
 *
 * @author Eithan
 */
public class FormCategoriaProductoController implements Initializable {
    
    private main stage;
    private int op;
    
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
   @FXML
    Button btnCancelar,btnGuardar;
   
   @FXML
   TextField tfCategoriaPId,tfNombreCategoriaP,tfDescripcionCategoriaP;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnCancelar){
            CategoriaProductoDTO.getCategoriaPDTO().setCategoriaP(null);
            stage.menuCategoriaPView();
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombreCategoriaP.getText().equals("") && !tfDescripcionCategoriaP.getText().equals("")){
                    agregarCategoriaP();
                    SuperKinalAlert.getInstance().mostrarAlertasInformacion(400);
                    stage.menuCategoriaPView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertasInformacion(600);
                    if(tfNombreCategoriaP.getText().equals("")){
                        tfNombreCategoriaP.requestFocus();
                    }else if(tfDescripcionCategoriaP.getText().equals("")){
                        tfDescripcionCategoriaP.requestFocus();
                    }
                }
                
            }else if(op == 2){
                if(!tfNombreCategoriaP.getText().equals("") && !tfDescripcionCategoriaP.getText().equals("")){
                    editarCategoriaP();
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                        CategoriaProductoDTO.getCategoriaPDTO().setCategoriaP(null);
                        SuperKinalAlert.getInstance().mostrarAlertasInformacion(500);
                        stage.menuCategoriaPView();
                    }else{
                        stage.menuCategoriaPView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertasInformacion(600);
                    if(tfNombreCategoriaP.getText().equals("")){
                        tfNombreCategoriaP.requestFocus();
                    }else if(tfDescripcionCategoriaP.getText().equals("")){
                        tfDescripcionCategoriaP.requestFocus();
                    }
                }
                
            }
        }
    
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       if(CategoriaProductoDTO.getCategoriaPDTO().getCategoriaP() != null){
            cargarDatos(CategoriaProductoDTO.getCategoriaPDTO().getCategoriaP());
        }
    }
    
    public void cargarDatos(CategoriaProducto categoriaP){
        tfCategoriaPId.setText(Integer.toString(categoriaP.getCategoriaproductosId()));
        tfNombreCategoriaP.setText(categoriaP.getNombreCategoria());
        tfDescripcionCategoriaP.setText(categoriaP.getDescripcionCategoria());

    }
    
    public void agregarCategoriaP(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCategoriaProducto(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCategoriaP.getText());
            statement.setString(2, tfDescripcionCategoriaP.getText());
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
    
    public void editarCategoriaP(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarCategoriaProducto(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCategoriaPId.getText()));
            statement.setString(2, tfNombreCategoriaP.getText());
            statement.setString(3, tfDescripcionCategoriaP.getText());
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
