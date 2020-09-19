package com.crud.LoginV2Full.controller;

import com.crud.LoginV2Full.entity.Inventario;
import com.crud.LoginV2Full.exception.Msg;
import com.crud.LoginV2Full.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//estableciendo a clase como un controlador de tipo HttpRest
@RequestMapping("api/inventario")//Generando el mapeo del http inicial, en este casoel endpoint para la api inventario
@CrossOrigin//envio de cabeceras para politicas de Cors
//@CrossOrigin(origins = "http://localhost:4200")//envio de cabeceras para politicas de Cors directamente a Angular
public class InventarioController {

    @Autowired//generando la inyeccion de dependencias
    InventarioService inventarioService;


    //************************************  Notas  ******************************************//
    /*
    El metodo retorna un ResponseEntity que asigna una respuesta HTTP incluyendo un cuerpo,
    cabecera y códigos en total libertad de configurar la respuesta que queremos que se
    envié desde nuestros endpoints.
    */
    //**************************************************************************************//


    @GetMapping("/getAllInventario")//Generando sub mapeo para consultar todas las entidades en la database
    public ResponseEntity<List<Inventario>> getAll(){//en este caso el ResponseEntity debera de retornar una lista de entidades
        List<Inventario> inventarios = inventarioService.getAllInventario();//definiendo la lista de entidades y mediante el metodo getAllInventario de la dependencia inyectada
        return new ResponseEntity<List<Inventario>>(inventarios, HttpStatus.OK);//retornando la lista de entidades mediante una respuesta http la cual contien dicha lista y una respuesta definida mediante un codigo http, en este caso '200 ok'
    }

    @GetMapping("/getOneByIdInventario/{id}")//Generando sub mapeo para consultar una entidad en la database mediante id
    public ResponseEntity<Inventario> getOne(@PathVariable("id") Integer id){//en este caso el ResponseEntity debera de retornar una entidad correspondiente a un id
        if(!inventarioService.existById(id))//consultando si dicho id existe en la database
            return new ResponseEntity(new Msg("No Existe"), HttpStatus.NOT_FOUND);//de no encontrarse se genera una ResponseEntity http donde se envia un mensaje y  respuesta definida mediante un codigo http, en este caso '404'
        Inventario inventario =  inventarioService.getOneInventario(id).get();//definiendo la entidad y medinate el metodo getOneInventario de la dependencia inyectada obteniendo los datos del Optional<>, para ello se finaliza con .get()
        return  new ResponseEntity<>(inventario, HttpStatus.OK);//retornando una entidad mediante una respuesta http la cual contiene dicha entidad y una respuesta definida mediante un codigo http, en este caso '200'
    }

    @GetMapping("/getOneByCodeInventario/{codigo}")//Generando sub mapeo para consultar una entidad en la database mediante codigo
    public ResponseEntity<Inventario> getOne(@PathVariable("codigo") String codigo){//en este caso el ResponseEntity debera de retornar una entidad correspondiente a un codigo
        if(!inventarioService.existByCodigo(codigo))//consultando si dicho codigo existe en la database
            return new ResponseEntity(new Msg("No Existe"), HttpStatus.NOT_FOUND);//de no encontrarse se genera una ResponseEntity http donde se envia un mensaje y  respuesta definida mediante un codigo http, en este caso '404'
        Inventario inventario =  inventarioService.getOneInventarioCode(codigo).get();//definiendo la entidad y medinate el metodo getOneInventarioCode de la dependencia inyectada obteniendo los datos del Optional<>, para ello se finaliza con .get()
        return  new ResponseEntity<>(inventario, HttpStatus.OK);//retornando una entidad mediante una respuesta http la cual contiene dicha entidad y una respuesta definida mediante un codigo http, en este caso '200'
    }

    @PostMapping("/create-Inventario")//Generando sub mapeo para Agregar una entidad en la database
    public ResponseEntity<?> createInventario(@RequestBody Inventario inventario){//en este caso el ResponseEntity debera de retornar un opcinal ya sea respuesta http con o sin entidad
        if(inventario.getCodigo()==null || inventario.getCodigo().isEmpty()){//verificando si existe un campo codigo o si este es null
            return new ResponseEntity(new Msg("Campo codigo es obligatorio"), HttpStatus.BAD_REQUEST);//en caso de false se genera una ResponseEntity http donde se envia un mensaje y  respuesta definida mediante un codigo http, en este caso '400'
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

        //en este punto si paso los datos de entrada pasan los filtros de seguridad se prosigue a la insecion
        Inventario inventario1 =  new Inventario(//se genera un nuevo modelo entidad a la que se le asignaran los valores del @RequestBody
                inventario.getCodigo(),
                inventario.getTipo(),
                inventario.getDescripcion(),
                inventario.getEstado(),
                inventario.getDepartamento()
        );
        inventarioService.saveInventario(inventario1);//se guarda en la database mediante el metodo saveInventario enviandole la entidad creada
        return new ResponseEntity<>(new Msg("Guardado con exito"),HttpStatus.OK);//respuesta http 200
    }

    @PutMapping("/update-Inventario/{id}")//este metodo es parecido al anterior
    public ResponseEntity<?> updateInventario(@PathVariable("id") Integer id, @RequestBody Inventario inventario){
        if (!inventarioService.existById(id)){
            return new ResponseEntity<>(new Msg("No existe el codigo en el inventario o fue eliminado"),HttpStatus.NOT_FOUND);//respuesta 404
        }
        //**********************************************************************//
        if(inventario.getCodigo()==null || inventario.getCodigo().isEmpty()){//el segundo if ayuda a determinar que entidad en la database se upede modificar sin riesgo de sobre escibir alguna otra por accidente
            return new ResponseEntity(new Msg("Campo codigo es obligatorio"), HttpStatus.BAD_REQUEST);//respuesta 400
        }else if (inventarioService.existByCodigo(inventario.getCodigo()) && inventarioService.getOneInventarioCode(inventario.getCodigo()).get().getId() != id){
            return new ResponseEntity(new Msg("Este codigo ya existe"),HttpStatus.BAD_REQUEST);//respuesta 400
        }
        //***********************************************************************//

        if(inventario.getTipo()==null || inventario.getTipo().isEmpty()){
            return new ResponseEntity(new Msg("Campo tipo es obligatorio"), HttpStatus.BAD_REQUEST);//respuesta 400
        }
        if(inventario.getDescripcion()==null || inventario.getDescripcion().isEmpty()){
            return new ResponseEntity(new Msg("Campo descripcion es obligatorio"), HttpStatus.BAD_REQUEST);//respuesta 400
        }
        if(inventario.getEstado()==null || inventario.getEstado().isEmpty()){
            return new ResponseEntity(new Msg("Campo estado es obligatorio"), HttpStatus.BAD_REQUEST);//respuesta 400
        }
        if(inventario.getDepartamento()==null || inventario.getDepartamento().isEmpty()){
            return new ResponseEntity(new Msg("Campo departameto es obligatorio"), HttpStatus.BAD_REQUEST);//respuesta 400
        }

        //en este punto si paso los datos de entrada pasan los filtros de seguridad se prosigue a la actualizacion
        Inventario inventario1 =  inventarioService.getOneInventario(id).get();//a diferencia del metodo anterior aqui se consulta la entidad para recuperarla
        // a la entidad recuperada de la database se le asignan los valores de entrada a actualizar
        inventario1.setCodigo(inventario.getCodigo());
        inventario1.setTipo(inventario.getTipo());
        inventario1.setDescripcion(inventario.getDescripcion());
        inventario1.setEstado(inventario.getEstado());
        inventario1.setDepartamento(inventario.getDepartamento());

        inventarioService.saveInventario(inventario1);//se actualizara en la database mediante el metodo saveInventario enviandole la entidad actualizada
        return new ResponseEntity<>(new Msg("Actualizado con exito"),HttpStatus.OK);//respuesta http 200
    }

    @DeleteMapping("/delete-Inventario/{id}")//Generando sub mapeo para eliminar una entidad en la database mediante id
    public ResponseEntity<?> deleteInventario(@PathVariable("id") Integer id){
        if (!inventarioService.existById(id)){//se consulta si la entidad aun existe en el momento de la eliminacion evitando con ello problemas al tener ultiples usuarios trabajando la misma database
            return new ResponseEntity<>(new Msg("No existe en el inventario o ya se habia eliminado"),HttpStatus.NOT_FOUND);//de no encontrarse se genera una ResponseEntity http donde se envia un mensaje y  respuesta definida mediante un codigo http, en este caso '404'
        }
        inventarioService.deleteInventario(id);//si pasa el fltrado se elimina la entidad de la database mediante el metodo 'deleteInventario' asignandole un id
        return new ResponseEntity<>(new Msg("Eliminado con exito"),HttpStatus.OK);//retornando una entidad mediante una respuesta http la cual contiene una respuesta definida mediante un codigo http, en este caso '200'
    }
}
