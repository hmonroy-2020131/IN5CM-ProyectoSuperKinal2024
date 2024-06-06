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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.eithanmonroy.dao.Conexion;
import org.eithanmonroy.model.Usuario;
import org.eithanmonroy.system.main;
import org.eithanmonroy.utils.PasswordUtils;
import org.eithanmonroy.utils.SuperKinalAlert;

/**
 *
 * @author Eithan
 */
public class MenuInicioSesionController implements Initializable {
    private main stage;
    private int op = 0;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    PasswordField tfContrasenia;
    
    @FXML
    TextField tfUsuario;
    
    @FXML
    Button btnIniciar, btnRegistrar;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        
        if(event.getSource() == btnRegistrar){
                stage.menuRegistrarUsuarioView();
        }else if(!tfUsuario.getText().equals("") && !tfContrasenia.getText().equals("")){
            if(event.getSource() == btnIniciar){
                Usuario usuario = buscarUsuario();
                if(op == 0){
                    if(usuario != null){
                        if(PasswordUtils.getIntance().checkPassword(tfContrasenia.getText(), usuario.getContrasenia())){
                            if(usuario.getNivelAccesoId() == 1){
                                btnRegistrar.setDisable(false);
                                btnIniciar.setText("ir al menu");
                                op = 33;
                            }else if(usuario.getNivelAccesoId() == 2){                 
                                 stage.menuPrincipalView(); 
                                 SuperKinalAlert.getInstance().alertaSaludo(usuario.getUsuario());
                            }
                        }else{
                            SuperKinalAlert.getInstance().mostrarAlertasInformacion(7);
                        }
                        
                    }else{
                        SuperKinalAlert.getInstance().mostrarAlertasInformacion(5);
                    }
                }else{
                    stage.menuPrincipalView();
                    SuperKinalAlert.getInstance().alertaSaludo(usuario.getUsuario());
                }

            }
        }else{
            SuperKinalAlert.getInstance().mostrarAlertasInformacion(33);
            if(tfUsuario.getText().equals("")){
                tfUsuario.requestFocus();
            }else if(tfContrasenia.getText().equals("")){
                tfContrasenia.requestFocus();
            }   
        }
        
        
        
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }    
    
    public main getStage() {
        return stage;
    }

    public void setStage(main stage) {
        this.stage = stage;
    }  
    
    public Usuario buscarUsuario(){
        Usuario usuario = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarUsuarios(?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfUsuario.getText());
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int usuarioId = resultSet.getInt("usarioId");
                String user = resultSet.getString("usuario");
                String contrasenia = resultSet.getString("contrasenia");
                int nivelAccesoId = resultSet.getInt("nivelAccesoId");
                int empleadoId = resultSet.getInt("empleadoId");
                
                usuario = new Usuario(usuarioId,user,contrasenia,nivelAccesoId,empleadoId);
            
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                
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
        
        return usuario;
    
    }
    
}
