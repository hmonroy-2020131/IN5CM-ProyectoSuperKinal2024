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
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.eithanmonroy.dao.Conexion;
import org.eithanmonroy.dto.EmpleadoDTO;
import org.eithanmonroy.model.Cargo;
import org.eithanmonroy.model.Empleado;
import org.eithanmonroy.system.main;
import org.eithanmonroy.utils.SuperKinalAlert;


/**
 *
 * @author Eithan
 */
public class FormEmpleadosController implements  Initializable {
    
    private main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
   @FXML
    Button btnCancelar,btnGuardar;
   
   @FXML
   TextField tfEmpleadoId,tfNombreE,tfApellidoE,tfSueldo,tfHoraEntrada,tfHoraSalida;
   
   @FXML
   ComboBox cmbCargos,cmbEncargados;
   
   @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnCancelar){
            EmpleadoDTO.getEmpleadoDTO().setEmpleado(null);
            stage.menuEmpleadosView();
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombreE.getText().equals("") && !tfApellidoE.getText().equals("") && !tfSueldo.getText().equals("") && !tfHoraEntrada.getText().equals("") && !tfHoraSalida.getText().equals("")){
                    agregarEmpleado();
                    SuperKinalAlert.getInstance().mostrarAlertasInformacion(400);
                    stage.menuEmpleadosView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertasInformacion(600);
                    if(tfNombreE.getText().equals("")){
                        tfNombreE.requestFocus();
                    }else if(tfApellidoE.getText().equals("")){
                        tfApellidoE.requestFocus();
                    }else if(tfSueldo.getText().equals("")){
                        tfSueldo.requestFocus();
                    }else if(tfHoraEntrada.getText().equals("")){
                        tfHoraEntrada.requestFocus();
                    }else if(tfHoraSalida.getText().equals("")){
                        tfHoraSalida.requestFocus();
                    }
                }
                
               
            }else if(op == 2){
                if(!tfNombreE.getText().equals("") && !tfApellidoE.getText().equals("") && !tfSueldo.getText().equals("") && !tfHoraEntrada.getText().equals("") && !tfHoraSalida.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                        editarEmpleado();
                        EmpleadoDTO.getEmpleadoDTO().setEmpleado(null);
                        SuperKinalAlert.getInstance().mostrarAlertasInformacion(500);
                        stage.menuEmpleadosView();
                    }else{
                        stage.menuEmpleadosView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertasInformacion(600);
                    if(tfNombreE.getText().equals("")){
                        tfNombreE.requestFocus();
                    }else if(tfApellidoE.getText().equals("")){
                        tfApellidoE.requestFocus();
                    }else if(tfSueldo.getText().equals("")){
                        tfSueldo.requestFocus();
                    }else if(tfHoraEntrada.getText().equals("")){
                        tfHoraEntrada.requestFocus();
                    }else if(tfHoraSalida.getText().equals("")){
                        tfHoraSalida.requestFocus();
                    }
                }
                
            }
        }
    }
   
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(EmpleadoDTO.getEmpleadoDTO().getEmpleado() != null){
            cargarDatos(EmpleadoDTO.getEmpleadoDTO().getEmpleado());
        }
        cmbCargos.setItems(listarCargos());
        
    }

    public void cargarDatos(Empleado empleado){
        tfEmpleadoId.setText(Integer.toString(empleado.getEmpleadoId()));
        tfNombreE.setText(empleado.getNombreE());
        tfApellidoE.setText(empleado.getApellidoE());
        tfSueldo.setText(Double.toString(empleado.getSueldo()));
        tfHoraEntrada.setText(empleado.getHoraEntrada().toString());
        tfHoraSalida.setText(empleado.getHoraSalida().toString());
    }
    
    public void agregarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarEmpleado(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreE.getText());
            statement.setString(2, tfApellidoE.getText());
            statement.setString(3, tfSueldo.getText());
            statement.setString(4, tfHoraEntrada.getText());
            statement.setString(5, tfHoraSalida.getText());
            statement.setInt(6,((Cargo)cmbCargos.getSelectionModel().getSelectedItem()).getCargoId());
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
    
    public void editarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarEmpleado(?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpleadoId.getText()));
            statement.setString(2, tfNombreE.getText());
            statement.setString(3, tfApellidoE.getText());
            statement.setString(4, tfSueldo.getText());
            statement.setString(5, tfHoraEntrada.getText());
            statement.setString(6, tfHoraSalida.getText());
            statement.setInt(7,((Cargo)cmbCargos.getSelectionModel().getSelectedItem()).getCargoId());
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
    
    public ObservableList<Cargo> listarCargos(){
        ArrayList<Cargo> cargos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarCargos()";
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
    
    public ObservableList<Empleado> listarEmpleados(){
        ArrayList<Empleado> empleados = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarEmpleados()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                Time horaentrada = resultSet.getTime("horaentrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                String cargoId = resultSet.getString("cargo");
                String encargadoId = resultSet.getString("nombreEncargado");
            
                empleados.add(new Empleado(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaentrada, horaSalida,cargoId,encargadoId));
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
        
        
        return FXCollections.observableList(empleados);
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
