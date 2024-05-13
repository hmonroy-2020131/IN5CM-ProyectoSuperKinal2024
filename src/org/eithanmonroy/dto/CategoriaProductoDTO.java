/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eithanmonroy.dto;

import org.eithanmonroy.model.CategoriaProducto;

/**
 *
 * @author Eithan
 */
public class CategoriaProductoDTO {
     private static CategoriaProductoDTO instance;
    private CategoriaProducto categoriaP;
    
    private CategoriaProductoDTO(){
    
    }
    
    public static CategoriaProductoDTO getCategoriaPDTO(){
        if(instance == null){
            instance = new CategoriaProductoDTO();
        }
        
        return instance;
    }

    public CategoriaProducto getCategoriaP() {
        return categoriaP;
    }

    public void setCategoriaP(CategoriaProducto categoriaP) {
        this.categoriaP = categoriaP;
    }  
    
}
