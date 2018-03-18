# Proyecto TPV. Tecnología Spring

#### Back-end con Tecnologías de Código Abierto (SPRING). [Máster en Ingeniería Web por la U.P.M.](http://miw.etsisi.upm.es)
Este proyecto es la práctica de Spring desarrollada de forma colaborativa por todos los alumnos. Se parte de la versión `core`, ya implementada, y se pretende ampliar con un conjunto de mejoras.  

Un **T**erminal **P**unto de **V**enta es un sistema informático que gestiona el proceso de venta mediante una interfaz accesible para los vendedores o compradores.

Un único sistema informático permite la creación e impresión del recibo ticket o factura de venta —con los detalles de las referencias y precios— de los artículos vendidos, actualiza los cambios en el nivel de existencias de mercancías (STOCK) en la base de datos...

## Tecnologías
* `Spring-boot`
* `Mongodb`

## Organización de test
> :+1: Test independientes, y al finalizar, no han alterado la bd
* Test unitarios: `**/*Test`
   * Pruebas de métodos aislados con cierta complejidad
* Test de integración: `**/*IT`
   * Pruebas de la BD, Querys...
   * Pruebas completas de la lógica de negocio, correctas e incorrectas
* Test de funcionalidad: `**/*FunctionalTesting`
   * Pruebas de seguridad del API
   * Pruebas de las peticiones correctas del API
* Agrupación de test para pruebas locales (:exclamation: por orden alfabético):
   * `All<package>Tests`
   * `All<package>IntegrationTests`
   * `All<package>FunctionalTests`
* El test raíz dispara todos los test de la aplicación, es(:exclamation: ordenado por Test, IT, FunctionalTesting y orden alfabético):
   * `AllMiwTests`  

## Ecosistema
### Github
Se utilizará un flujo de trabajo ramificado (_Git Workflow_). Una rama por **Historia**, con aportaciones frecuentes a _develop_, y dividido por tareas.  

#### Proceso de subida de nuevo código:
> 1. Comprobar el [Travis-CI](https://travis-ci.org/miw-upm/betca-tpv-spring/builds) del proyecto y ver que todos los test son correctos en develop. [![Build Status](https://travis-ci.org/miw-upm/betca-tpv-spring.svg?branch=develop)](https://travis-ci.org/miw-upm/betca-tpv-spring)
> 1. Actualizar el proyecto mediante **fetch**: `>git fetch origin` 
> 1. ¿ _develop_ esta más abajo que _origin/develop_, es decir, apuntan a distintos commit?
>    * (SI)
>      1. Activar la rama _develop_: `>git checkout develop`
>      1. **merge** con _origin/develop_: `>git merge origin/develop`
>      1. Actualizar la rama _issue_ con los últimos cambios de _develop_:   
>         * `>git checkout issue`   
>         * `>git merge develop --no-ff`
> 1. Lanzar todos los test y asegurarse que no hay errores
> 1. Actualizar _develop_ con los nuevos cambios de mi rama (**repetir los pasos 2 y 3**):  
>      * `>git checkout develop`   
>      * `>git merge issue --no-ff`
> 1. Subir la rama _develop_ al remoto: `>git push origin develop`   
> 1. Activar la rama _issue_ para seguir con el desarrollo y subirla al remoto:   
>      * `>git checkout issue`   
>      * `>git push origin issue`   

### Travis-CI
Integración continua con **Travis-CI**. Se despliega para pruebas con el servicio de BD de mongodb y ejecución secuencial de los test: Unitarios, de Integración y Funcionales
```yaml
services:
  - mongodb
script:
# Test
- mvn -Dtest=**/*Test test
# Integration Test
- if [ $TRAVIS_TEST_RESULT == 0 ];
     then  mvn -Dtest=**/*IT package; 
     else  echo "ERRORES!!! ... se abortan resto de test"; 
  fi
# Functional Testing
- if [ $TRAVIS_TEST_RESULT == 0 ];
     then  mvn -Dtest=**/*FunctionalTesting verify;
     else  echo "ERRORES!!! ... se abortan resto de test"; 
  fi
```
### Sonarcloud
En el la cuenta de **Sonarcloud**, en la página `My Account>Security`, se ha generado un **token**.   
En la cuenta de **Travis-CI**, dentro del proyecto, en `More options>Settings`, se ha creado una variable de entorno llamada `SONAR` cuyo contenido es el **token** de **Sonar**.    
Se ha incorporado al fichero de `.travis.yml` para la conexión con Travis-CI lo siguiente:
```yml
# Sonarcloud
- mvn org.jacoco:jacoco-maven-plugin:prepare-agent verify
- mvn sonar:sonar -Dsonar.organization=miw-upm-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=$SONAR
```

### Swagger
Se monta un cliente swagger para mostrar el API: http://localhost:8080/api/v0/swagger-ui.html. Aunque queda pendiente por resolver el uso de **Basic Auth** para pruebas manuales.

## Arquitectura de paquetes
![](https://github.com/miw-upm/betca-tpv-spring/blob/develop/docs/tpv-architecture.png)

### Responsabilidades
* _config_. Clases de configuración de **Spring**
* _resources_. Clases que conforman el **API**
   * Deben organizar la recepción de datos de la petición
   * Delega en los _Dtos_ la validación de campos
   * Gestiona las respuestas de errores _Http_
   * Delega en los _controladores_ la ejecución de la petición
      * `Optional<String> error = this.controller.method(); if(error.isPresent()){throw new Exception(error.get());}`
      * `Optional<Dto> result = this.controller.method(); return result.orElseThrow(() -> new Exception());`
* _controllers_. Clases que procesan la petición
   * Desarrollan el proceso que conlleva la ejecución de la petición
   * Construye los _documentos_ a partir de los _Dtos_
   * Delega en los _repositorios_ el acceso a las BD básicos
   * Delega en los _servicios_ procesos genéricos y de acceso a las BD avanzados
* _servicios_. Clases de servicios genéricos
* _repositories_. Clases de acceso a BD mediante el patrón DAO
   * Operaciones CRUD sobre BD
   * Consultas a BD
* _documents_. Clases con los documentos persistentes en BD

## Autenticación
Se plantean mediante _Basic Auth_ en dos variantes: **usuario:contraseña** o **token:** con la contraseña en blanco. El primero sólo se utiliza para obtener un **token**, y posteriormente, todas las operaciones se realizan enviando el **token**, que se encuentra asociado a un usuario, e indirectamente, a un conjunto de roles. Se envía en el _Header_ de la petición:  
> Authorization = Basic "user:pass"<sub>Base64</sub>  
> Authorization = Basic "token:"<sub>Base64</sub>  

Para asegurar los recursos, se plantea una filosofía distribuida, es decir, se establece sobre cada recursos. Para ello, se anotará sobre cada clase los roles permitidos; por ejemplo, `@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")` `@PreAuthorize("hasRole('ADMIN')")`, modificando el rol sobre el método si éste, tuviese un rol diferente.  

Existe un rol especial que se obtiene cuando se envía el usuario y contraseña por _Basic Auth_ y es el rol de `authenticated`, sólo se utiliza para logearse

### Diagrama de clases 
![](https://github.com/miw-upm/betca-tpv-spring/blob/develop/docs/authentication.png)

### API. Descripción genérica

#### `GET /admins/state` Comprueba el estado del servidor  
> Si el servidor está levantado: `Ok (200) {"state"="ok"}`

#### `POST /tokens` Crea un nuevo token  
> Realizan un login . El user y pass van en `Basic Auth`  
> Devuelve un _TokenOutputDto_

#### `POST /users` Crea un nuevo usuario con el rol de _CUSTOMER_  
> Es necesario pasar un token en `Basic Auth` con rol de _ADMIN_, _MANAGER_ o _OPERATOR_  
> Se pasa en el cuerpo un _UserDto_

#### `DELETE /users/{mobile}` Borrar un nuevo usuario con el rol de _CUSTOMER_   
> Es necesario pasar un token en `Basic Auth` con rol de _ADMIN_, _MANAGER_ o _OPERATOR_

## DTOs
Son los objetos de transferencia del API, para recibir los datos (input) y enviar las respuestas (output).

### Input
Los _input_ se encargan de las validaciones de entrada mediante las anotaciones.   
Se utilizarán las: _javax.validation.constraints.*_, _org.hibernate.validator.constraints.*_, y si fuera necesario, se realizarán propias en el paquete _validations_ 

### Output
Son los _dtos_ de respuesta. Si existen versiones diferentes del mismo documento se podría utilizar la herencia.   
También, para reutilizar un _dto_ en diferentes opciones, añadir la anotación `@JsonInclude(Include.NON_NULL)` en la clase, para que no se devuelvan null en el Json.   
![](https://github.com/miw-upm/betca-tpv-spring/blob/develop/docs/dtos.png)   

## Bases de datos
> Se dispone de un servicio para poblar la BD, a partir de un fichero YML, tanto para **test (tpv-db-test.yml)** como para su puesta en **producción* (tpv-db-test.yml)*.  
> **La carga se realiza una sola vez, al arrancar los test y de forma automática. Si se necesita borrar o alterar dicho contenido, se deberá reponer cuando se finalicen los test mediante: `DatabaseSeederService.seedDatabase()`.**  
> > :exclamation: Para cargar los datos necesita `public set** (...)`. Se debe intentar respetar la visibilidad de los métodos según el diseño de Documentos

Los pasos a seguir para inlcuir un nuevo documento son:
1. Rellenar el YML con el nuevo documento
   * Fichero de test: `tpv-bd-test.yml`
1. Incluir en la clase `TpvGraph`, la lista del nuevo documento, si no existiese, con getters & setters
1. Incluir en la clase `DatabaseSeederService`, en el médoto `seedDatabase`, el `save` del repositorio del nuevo documento
1. Incluir en la clase `DatabaseSeederService`, en el médoto `deleteAllAndCreateAdmin`, el `deleteAll` del repositorio del nuevo documento
```yml
tokenList:
  - &t0
    value: PaE8iPX3AekAaV945_ujcm7q1ik
    creationDate: 2017-08-14
userList:
  - mobile: 666666000
    username: u000
    password: p000
    roles:
      - ADMIN
      - MANAGER
      - OPERATOR
    token: *t0
```
![](https://github.com/miw-upm/betca-tpv-spring/blob/develop/docs/tpv-database-seeder.png)

### API. Descripción general

#### `DELETE /admins/db` Borra la bd menos la cuenta de **admin**   
> Es necesario pasar un token en `Basic Auth` con rol de _ADMIN_  

#### `POST /admins/db` Rellena la bd a partir de un fichero YML   
> Es necesario pasar un token en `Basic Auth` con rol de _ADMIN_  
> Se en vía en el cuerpo un String con el nombre del fichero  

## Persistencia del TPV. Visión global
![](https://github.com/miw-upm/betca-tpv-spring/blob/develop/docs/tpv-documents-core.png)
![](https://github.com/miw-upm/betca-tpv-spring/blob/develop/docs/tpv-repositories-core.png)

## DTOs
Son los objetos de transferencia del API, para recibir los datos (input) y enviar las respuestas (output).

### Input
Los _input_ se encargan de las validaciones de entrada mediante las anotaciones.   
Se utilizarán las: _javax.validation.constraints.*_, _org.hibernate.validator.constraints.*_, y si fuera necesario, se realizarán propias en el paquete _validations_ 

### Output
Son los _dtos_ de respuesta. Si existen versiones diferentes del mismo documento se podría utilizar la herencia.   
También, para reutilizar un _dto_ en diferentes opciones, añadir la anotación `@JsonInclude(Include.NON_NULL)` en la clase, para que no se devuelvan null en el Json.   
![](https://github.com/miw-upm/betca-tpv-spring/blob/develop/docs/dtos.png)   
