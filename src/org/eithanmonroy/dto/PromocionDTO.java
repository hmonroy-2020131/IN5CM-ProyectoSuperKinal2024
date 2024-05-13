/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eithanmonroy.dto;

import org.eithanmonroy.model.Promocion;

/**
 *
 * @author Eithan
 */
public class PromocionDTO {
    private static PromocionDTO instance;
    private Promocion promocion;
    
    private PromocionDTO(){
    
    }
    
    public static PromocionDTO getPromocionDTO(){
        if(instance == null){
            instance = new PromocionDTO();
        }
        
        return instance;
    }

    public Promocion getPromocion() {
        return promocion;
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }
    
}
