package com.crud.LoginV2Full.controller;

import com.crud.LoginV2Full.entity.Inventario;
import com.crud.LoginV2Full.exception.Msg;
import com.crud.LoginV2Full.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inventario")
@CrossOrigin
//@CrossOrigin(origins = "http://localhost:4200")
public class InventarioController {

    @Autowired//generando la inyeccion
    InventarioService inventarioService;

    @GetMapping("/getAllInventario")
    public ResponseEntity<List<Inventario>> getAll(){
        List<Inventario> inventarios = inventarioService.getAllInventario();
        return new ResponseEntity<List<Inventario>>(inventarios, HttpStatus.OK);
    }

    @GetMapping("/getOneByIdInventario/{id}")
    public ResponseEntity<Inventario> getOne(@PathVariable("id") Integer id){
        if(!inventarioService.existById(id))
            return new ResponseEntity(new Msg("No Existe"), HttpStatus.NOT_FOUND);
        Inventario inventario =  inventarioService.getOneInventario(id).get();
        return  new ResponseEntity<>(inventario, HttpStatus.OK);
    }

    @GetMapping("/getOneByCodeInventario/{codigo}")
    public ResponseEntity<Inventario> getOne(@PathVariable("codigo") String codigo){
        if(!inventarioService.existByCodigo(codigo))
            return new ResponseEntity(new Msg("No Existe"), HttpStatus.NOT_FOUND);
        Inventario inventario =  inventarioService.getOneInventarioCode(codigo).get();
        return  new ResponseEntity<>(inventario, HttpStatus.OK);
    }

    @PostMapping("/create-Inventario")
    public ResponseEntity<?> createInventario(@RequestBody Inventario inventario){
        if(inventario.getCodigo()==null || inventario.getCodigo().isEmpty()){
            return new ResponseEntity(new Msg("Campo codigo es obligatorio"), HttpStatus.BAD_REQUEST);
        }else if (inventarioService.existByCodigo(inventario.getCodigo())){
            return new ResponseEntity(new Msg("Este codigo ya existe"),HttpStatus.BAD_REQUEST);
        }
        if(inventario.getTipo()==null || inventario.getTipo().isEmpty()){
            return new ResponseEntity(new Msg("Campo tipo es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if(inventario.getDescripcion()==null || inventario.getDescripcion().isEmpty()){
            return new ResponseEntity(new Msg("Campo descripcion es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if(inventario.getEstado()==null || inventario.getEstado().isEmpty()){
            return new ResponseEntity(new Msg("Campo estado es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if(inventario.getDepartamento()==null || inventario.getDepartamento().isEmpty()){
            return new ResponseEntity(new Msg("Campo departameto es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        Inventario inventario1 =  new Inventario(
                inventario.getCodigo(),
                inventario.getTipo(),
                inventario.getDescripcion(),
                inventario.getEstado(),
                inventario.getDepartamento()
        );
        inventarioService.saveInventario(inventario);
        return new ResponseEntity<>(new Msg("Guardado con exito"),HttpStatus.OK);
    }

    @PutMapping("/update-Inventario/{id}")
    public ResponseEntity<?> updateInventario(@PathVariable("id") Integer id, @RequestBody Inventario inventario){
        if (!inventarioService.existById(id)){
            return new ResponseEntity<>(new Msg("No existe el codigo en el inventario o fue eliminado"),HttpStatus.NOT_FOUND);
        }
        if(inventario.getCodigo()==null || inventario.getCodigo().isEmpty()){
            return new ResponseEntity(new Msg("Campo codigo es obligatorio"), HttpStatus.BAD_REQUEST);
        }else if (inventarioService.existByCodigo(inventario.getCodigo()) && inventarioService.getOneInventarioCode(inventario.getCodigo()).get().getId() != id){
            return new ResponseEntity(new Msg("Este codigo ya existe"),HttpStatus.BAD_REQUEST);
        }
        if(inventario.getTipo()==null || inventario.getTipo().isEmpty()){
            return new ResponseEntity(new Msg("Campo tipo es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if(inventario.getDescripcion()==null || inventario.getDescripcion().isEmpty()){
            return new ResponseEntity(new Msg("Campo descripcion es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if(inventario.getEstado()==null || inventario.getEstado().isEmpty()){
            return new ResponseEntity(new Msg("Campo estado es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if(inventario.getDepartamento()==null || inventario.getDepartamento().isEmpty()){
            return new ResponseEntity(new Msg("Campo departameto es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        Inventario inventario1 =  inventarioService.getOneInventario(id).get();
                inventario1.setCodigo(inventario.getCodigo());;
                inventario1.setTipo(inventario.getTipo());
                inventario1.setDescripcion(inventario.getDescripcion());
                inventario1.setEstado(inventario.getEstado());
                inventario1.setDepartamento(inventario.getDepartamento());
        inventarioService.saveInventario(inventario1);
        return new ResponseEntity<>(new Msg("Actualizado con exito"),HttpStatus.OK);
    }

    @DeleteMapping("/delete-Inventario/{id}")
    public ResponseEntity<?> deleteInventario(@PathVariable("id") Integer id){
        if (!inventarioService.existById(id)){
            return new ResponseEntity<>(new Msg("No existe en el inventario o ya se habia eliminado"),HttpStatus.NOT_FOUND);
        }
        inventarioService.deleteInventario(id);
        return new ResponseEntity<>(new Msg("Eliminado con exito"),HttpStatus.OK);
    }
}
