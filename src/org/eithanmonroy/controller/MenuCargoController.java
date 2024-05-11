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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.eithanmonroy.dao.Conexion;
import org.eithanmonroy.dto.CargoDTO;
import org.eithanmonroy.model.Cargo;
import org.eithanmonroy.system.main;
import org.eithanmonroy.utils.SuperKinalAlert;

/**
 *
 * @author informatica
 */
public class MenuCargoController implements Initializable{
    
    private main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblCargos;
    
    @FXML
    TableColumn colCargoId,colNombreCargo,colDescripcionCargo;
    
    @FXML
    Button btnRegresar,btnAgregar,btnEditar,btnEliminar,btnBuscar;
    
    @FXML
    TextField tfCargoId;

    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formCargosView(1);
        }else if(event.getSource() == btnEditar){
            CargoDTO.getCargoDTO().setCargo((Cargo)tblCargos.getSelectionModel().getSelectedItem());
            stage.formCargosView(2);
        }else if(event.getSource() == btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                eliminarCargo(((Cargo)tblCargos.getSelectionModel().getSelectedItem()).getCargoId());
                cargarDatos();
            }
        }else if (event.getSource() == btnBuscar){
            tblCargos.getItems().clear();
            if(tfCargoId.getText().equals("")){
                cargarDatos();
            
            }else{
                op = 3;
                cargarDatos();
            }
        }
    
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos(){
        if(op == 3){
            tblCargos.getItems().add(buscarCargo());
            op = 0;
            
        }else{
            tblCargos.setItems(listarCargos()); 
        }
        colCargoId.setCellValueFactory(new PropertyValueFactory<Cargo, Integer>("cargoId"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargo, String>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Cargo, String>("descripcionCargo"));
        
    }
    
    public ObservableList<Cargo> listarCargos(){
        ArrayList<Cargo> cargos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCargos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
            
                cargos.add(new Cargo(cargoId, nombreCargo, descripcionCargo));
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
        
        
        return FXCollections.observableList(cargos);
    }
    
    
    public void eliminarCargo(int carId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCargo(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,carId);
            statement.execute();
            
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
    }
    
    
    public Cargo buscarCargo(){
        Cargo cargo = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCargoPorID(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfCargoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargo = new Cargo(cargoId, nombreCargo, descripcionCargo);
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
        return cargo;
    }
    
    
    public main getStage() {
        return stage;
    }

    public void setStage(main stage) {
        this.stage = stage;
    }
    
}
