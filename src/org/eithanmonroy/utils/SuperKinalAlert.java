/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eithanmonroy.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author informatica
 */
public class SuperKinalAlert {
    private static SuperKinalAlert instance;
    
    private  SuperKinalAlert(){
    }
    
    public static SuperKinalAlert getInstance(){
        if(instance == null){
            instance = new SuperKinalAlert();
        }
        
        return instance;
    }
    
    public void mostrarAlertasInformacion(int code){
        if(code == 400){//Codigo 400 sirve para agregación de registros
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmacion Registro");
            alert.setHeaderText("Confirmacion de Registro");
            alert.setContentText("Registro Realizado con Exito");
            alert.showAndWait();
        }else if(code == 500){//500 sirve para confirmar edicion de registros
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edicion Registra");
            alert.setHeaderText("Confirmacion de Edicion");
            alert.setContentText("Edicion Realizada con Exito");
            alert.showAndWait();
        } else if(code == 600){// Codigo 600 sirve alertas de campos nulos
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Obligatorios");
            alert.setHeaderText("Campos Obligatorios");
            alert.setContentText("Falta Campos Obligatorios, Ingrese los datos. Revise los Datos");
            alert.showAndWait();
        }else if(code == 5){// Codigo 5 sirve errores al no encontrar un usuario
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Usuario Incorrecto");
            alert.setHeaderText("Usuario Incorrecto");
            alert.setContentText("Verifique su Usuario");
            alert.showAndWait();
        }else if(code == 7){// Codigo 7 sirve error en la contraseña
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Contraseña Incorrecta ");
            alert.setHeaderText("Contraseña Incorrecta");
            alert.setContentText("Verifique su  Contraseña");
            alert.showAndWait();
        }
        
        
        
        
        
    }
    
    public Optional<ButtonType> mostrarAlertaConfirmacion(int code){
        Optional<ButtonType> action = null;
        
        if(code == 404){//Codigo 404 sirve para confirmar la eliminacion del registro
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminacion de Registro ");
            alert.setHeaderText("Eliminacion de Registro");
            alert.setContentText("¿Desea confirmar la eliminacion del registro? No se podra recuperar");
            action = alert.showAndWait();
        }
        
        if(code == 505){//Codigo 404 sirve para confirmar la edicion del registro
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edicion de Registro ");
            alert.setHeaderText("Edicion de Registro");
            alert.setContentText("¿Desea confirmar la edicion del registro?");
            action = alert.showAndWait();
        }
        return action;
    }
    
    public void alertaSaludo(String usuario){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("HOLA ");
        alert.setHeaderText("Bienvenido " + usuario);
        alert.setContentText("Ahora puedes ver el programa");
        alert.showAndWait();    
       
    }
}
