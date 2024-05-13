/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eithanmonroy.dto;

import org.eithanmonroy.model.DetalleFactura;

/**
 *
 * @author Eithan
 */
public class DetalleFacturaDTO {
    private static DetalleFacturaDTO instance;
    private DetalleFactura detallefactura;
    
    private DetalleFacturaDTO(){
    
    }
    
    public static DetalleFacturaDTO getDetalleFacturaDTO(){
        if(instance == null){
            instance = new DetalleFacturaDTO();
        }
        
        return instance;
    }

    public DetalleFactura getDetalleFactura() {
        return detallefactura;
    }

    public void setDetalleFactura(DetalleFactura detallefactura) {
        this.detallefactura = detallefactura;
    }
    
}
