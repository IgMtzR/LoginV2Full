package com.crud.LoginV2Full.repository;

import com.crud.LoginV2Full.entity.Inventario;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository//estableciendo typo Repository a la interfaz
public interface InventarioRepository extends JpaRepositoryImplementation<Inventario, Integer> {
    Optional< Inventario > findByCodigo(String codigo);//defino buscar en el inventario por nombre, resivira una cadena de caracteres y retornara un objeto entidad '[object]' = '[Entity]'
    boolean existsByCodigo(String codigo);//defino buscar el inventariosi existe por codigo, recibira una cadena de caracteres y retornara un bolean
}
