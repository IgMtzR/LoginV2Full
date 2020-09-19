package com.crud.LoginV2Full.repository;

import com.crud.LoginV2Full.entity.Inventario;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepositoryImplementation<Inventario, Integer> {
    Optional< Inventario > findByCodigo(String codigo);//busco en el inventario por nombre
    boolean existsByCodigo(String codigo);//si existe por codigo
}
