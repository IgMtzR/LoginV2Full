package com.crud.LoginV2Full.service;

import com.crud.LoginV2Full.entity.Inventario;
import com.crud.LoginV2Full.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
//me permite generar consultas aprueba de error en caso de uno se produce un backend y vuelve al estado anterior
@Transactional
public class InventarioService {

    @Autowired//inyeccion del repository
    InventarioRepository inventarioRepository;

    public List<Inventario> getAllInventario(){
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> getOneInventario(Integer id){
        return inventarioRepository.findById(id);
    }

    public Optional<Inventario> getOneInventarioCode(String codigo){
        return inventarioRepository.findByCodigo(codigo);
    }

    public  void saveInventario(Inventario inventario){
        inventarioRepository.save(inventario);
    }

    public  void  deleteInventario(Integer id){
        inventarioRepository.deleteById(id);
    }

    public boolean existById( Integer id){
        return inventarioRepository.existsById(id);
    }

    public boolean existByCodigo(String codigo){
        return inventarioRepository.existsByCodigo(codigo);
    }
}
