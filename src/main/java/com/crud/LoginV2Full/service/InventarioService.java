package com.crud.LoginV2Full.service;

import com.crud.LoginV2Full.entity.Inventario;
import com.crud.LoginV2Full.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service//estableciendo que la clase es un tipo servicio
//me permite generar consultas aprueba de error en caso de uno se produce un backend y vuelve al estado anterior
@Transactional
public class InventarioService {

    @Autowired//inyeccion del repository
    InventarioRepository inventarioRepository;//instanciando la interfaz repository

    public List<Inventario> getAllInventario(){//creando metodo consulto todoo retorna una lista de la entidades
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> getOneInventario(Integer id){//creando metodo consulta de una entidad, retorna un objeto que puede ser vacio o no, en este caso es una entidad
        return inventarioRepository.findById(id);//se pasa el id al metodo para consultar
    }

    public Optional<Inventario> getOneInventarioCode(String codigo){//reando metodo consulta de una entidad, retorna un objeto que puede ser vacio o no, en esta caso es una entidad
        return inventarioRepository.findByCodigo(codigo);//se la entidad buscame diante el metodo codigo proporcionado
    }

    public  void saveInventario(Inventario inventario){//este metodo solo escucha no retorna, resive los valores a almacenar mediante una entidad
        inventarioRepository.save(inventario);//se guarda haciendo uso de el metodo save y asignandole la entidad resivida
    }

    public  void  deleteInventario(Integer id){//este metodo solo escucha no retorna, resive un id
        inventarioRepository.deleteById(id);//se elimina la entidad de la base de datos metidiante el metodo deleteById y asignandole el id resivido
    }




    //**********************************************************************************************************//
    //estos dos ultimos metodos se generan para el uso de validaciones, de estas dependera si se realiza una eliminacion o actualizacion
    //**********************************************************************************************************//
    public boolean existById( Integer id){//este metodo solo retorna un true o false, resive un id
        return inventarioRepository.existsById(id);//consulta si dentro de la base de datos existe una entidad con la id que se le asigna
    }

    public boolean existByCodigo(String codigo){//este metodo solo retorna un true o false, resibe una cadena de caracteres llamada codigo
        return inventarioRepository.existsByCodigo(codigo);//haciendo uso del metodo creado en la interfaz se consulta si existe un codigo el cual es asignado a este, retorna un bolean
    }
}
