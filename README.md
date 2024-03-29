# ARSW-LAB4

# Group:
+ **PEDRO JOSE MAYORGA NAVARRETE** - *Initial work* - [unknownmastercoder](https://github.com/unknownmastercoder)
+ **ANDRES DAVID VASQUEZ IBAÑEZ** - *Initial work* - [VASHIGO](https://github.com/vashigo)
----
# **API REST para la gestión de planos.**
+ # **PARTE 1**
     + ## Integre al proyecto base suministrado los Beans desarrollados en el ejercicio anterior. Sólo copie las clases, NO los archivos de configuración. Rectifique que se tenga correctamente configurado el esquema de inyección de dependencias con las anotaciones @Service y @Autowired:

        <p align="center">
        <img src=".\img\part1-1.PNG" />
        </p>

     + ## Modifique el bean de persistecia 'InMemoryBlueprintPersistence' para que por defecto se inicialice con al menos otros tres planos, y con dos asociados a un mismo autor.:

        <p align="center">
        <img src=".\img\part1-2.PNG" />
        </p>

     + ## Configure su aplicación para que ofrezca el recurso "/blueprints", de manera que cuando se le haga una petición GET, retorne -en formato jSON- el conjunto de todos los planos. Para esto:

        + Modifique la clase BlueprintAPIController teniendo en cuenta el siguiente ejemplo de controlador REST hecho con SpringMVC/SpringBoot:

            ```java
            @RestController
            @RequestMapping(value = "/blueprints")
            public class BlueprintAPIController {

                @Autowired
                BlueprintsServices bpServices;
                @RequestMapping(method = RequestMethod.GET)
                public ResponseEntity<?> getAllBlueprints() throws BlueprintNotFoundException {

                    try {
                        Map<String, Blueprint> blueprints = new HashMap();

                        List<Blueprint> blueprintsArray = new ArrayList<>();
                        blueprintsArray.addAll(bpServices.getAllBlueprints());

                        for (Blueprint x : blueprintsArray) {
                            blueprints.put(x.getName(), x);
                        }

                        String codeToJson = new Gson().toJson(blueprints);

                        return new ResponseEntity<>(codeToJson, HttpStatus.ACCEPTED);
                    } catch (BlueprintNotFoundException ex) {
                        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
                        return new ResponseEntity<>("No se han podido obtener los planos", HttpStatus.NOT_FOUND);
                    }
                }
            ```
        + Haga que en esta misma clase se inyecte el bean de tipo BlueprintServices (al cual, a su vez, se le inyectarán sus dependencias de persisntecia y de filtrado de puntos).

            ```java
            @Autowired
            BlueprintsServices bpServices;
            ```

     + ## Verifique el funcionamiento de a aplicación lanzando la aplicación con maven:

        ```java	
	    $ mvn compile
	    $ mvn spring-boot:run
        ```

        Y luego enviando una petición GET a: http://localhost:8080/blueprints. Rectifique que, como respuesta, se obtenga un objeto jSON con una lista que contenga el detalle de los planos suministados por defecto, y que se haya aplicado el filtrado de puntos correspondiente.

        <p align="center">
        <img src=".\img\part1-4.png" />
        </p>

     + ## Modifique el controlador para que ahora, acepte peticiones GET al recurso /blueprints/{author}, el cual retorne usando una representación jSON todos los planos realizados por el autor cuyo nombre sea {author}. Si no existe dicho autor, se debe responder con el código de error HTTP 404. Para esto, revise en la documentación de Spring, sección 22.3.2, el uso de @PathVariable. De nuevo, verifique que al hacer una petición GET -por ejemplo- a recurso http://localhost:8080/blueprints/juan, se obtenga en formato jSON el conjunto de planos asociados al autor 'juan' (ajuste esto a los nombres de autor usados en el punto 2).

        <p align="center">
        <img src=".\img\part1-5.png" />
        </p>    
            
     + ## Modifique el controlador para que ahora, acepte peticiones GET al recurso /blueprints/{author}/{bpname}, el cual retorne usando una representación jSON sólo UN plano, en este caso el realizado por {author} y cuyo nombre sea {bpname}. De nuevo, si no existe dicho autor, se debe responder con el código de error HTTP 404.

        <p align="center">
        <img src=".\img\part1-6.png" />
        </p>  
