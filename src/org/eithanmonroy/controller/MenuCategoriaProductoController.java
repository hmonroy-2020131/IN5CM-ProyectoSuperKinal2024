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
import org.eithanmonroy.dto.CategoriaProductoDTO;
import org.eithanmonroy.model.CategoriaProducto;
import org.eithanmonroy.system.main;
import org.eithanmonroy.utils.SuperKinalAlert;

/**
 *
 * @author Eithan
 */
public class MenuCategoriaProductoController implements Initializable {
    
    private main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblCategoriasP;
    
    @FXML
    TableColumn colCategoriaPId,colNombreCategoria,colDescripcionCategoria;
    
    @FXML
    Button btnRegresar,btnAgregar,btnEditar,btnEliminar,btnBuscar;
    
    @FXML
    TextField tfCategoriaPId;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formCategoriaPView(1);
        }else if(event.getSource() == btnEditar){
            CategoriaProductoDTO.getCategoriaPDTO().setCategoriaP((CategoriaProducto)tblCategoriasP.getSelectionModel().getSelectedItem());
            stage.formCategoriaPView(2);
        }else if(event.getSource() == btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                eliminarCategoriaP(((CategoriaProducto)tblCategoriasP.getSelectionModel().getSelectedItem()).getCategoriaproductosId());
                cargarDatos();
            }
        }else if (event.getSource() == btnBuscar){
            tblCategoriasP.getItems().clear();
            if(tfCategoriaPId.getText().equals("")){
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
            tblCategoriasP.getItems().add(buscarCategoriaP());
            op = 0;
            
        }else{
            tblCategoriasP.setItems(listarCategoriasP()); 
        }
        colCategoriaPId.setCellValueFactory(new PropertyValueFactory<CategoriaProducto, Integer>("categoriaproductosId"));
        colNombreCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProducto, String>("nombreCategoria"));
        colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProducto, String>("descripcionCategoria"));
        
    }
    
    public ObservableList<CategoriaProducto> listarCategoriasP(){
        ArrayList<CategoriaProducto> categoriasP = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarCategoriasProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int categoriaPId = resultSet.getInt("categoriaproductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
            
                categoriasP.add(new CategoriaProducto(categoriaPId, nombreCategoria, descripcionCategoria));
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
        
        
        return FXCollections.observableList(categoriasP);
    }
    
    public void eliminarCategoriaP(int catProdId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EliminarCategoriaProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,catProdId);
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
    
    public CategoriaProducto buscarCategoriaP(){
        CategoriaProducto categoriaP = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_BuscarCategoriaProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfCategoriaPId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int categoriaPId = resultSet.getInt("categoriaproductosId");
                String nombreCategoriaP = resultSet.getString("nombreCategoria");
                String descripcionCategoriaP = resultSet.getString("descripcionCategoria");
                
                categoriaP = new CategoriaProducto(categoriaPId, nombreCategoriaP, descripcionCategoriaP);
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
        return categoriaP;
    }
    
    public main getStage() {
        return stage;
    }

    public void setStage(main stage) {
        this.stage = stage;
    }
    
}
