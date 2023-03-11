# Back-End + Lógica del proyecto Gestion de Restaurantes
## Repositorio 
La lógica del repositorio se implementa en el paquete `repositorio` donde tenemos la interfaz `Repositorio`  
Esta nos permite realizar las operaciones necesarias para interactuar con un repositorio:
- add
- update
- delete
- getByID
- getAll
- getIds  

Usamos las clases `RepoositorioException` y  `EntidadNoEncontrada` para lanzar *excepciones* explicativas  
Cuando queramos que los identificadores del repositorio sean strings usaremos la interfaz `RepositorioStrings` que es un repositorio cuyos *IDs* serán del tipo siempre *string*  
Cuando queramos forzar a que un elemento tenga un identificador este deberá de extender de la interfaz `Identificable`  

* * * 
La clase `RepositorioMemoria` va a implementar la interfaz **RepositorioString** que recordemos que es una interfaz que viene de **Repositorio** que simplemente nos fuerza a que sus IDs sean *strings*. Esta clase además va a persistir elementos que contengan un *ID* pues los objetos que persiste deben de implementar la interfaz **Identificable**  
Esta clase nos va a permitir persistir en local una entidad, concretamente usando como estructura de datos un *HashMap* 
* * *
La clase `FactoriaRepositorios` nos da una abstracción de los repositorios de cada entidad. Tendremos un **repositorio** por **entidad** y para obtener su repositorio para trabajar con él cuando queramos hacer uso de la persistencia lo obtendremos a partir de la llamada a su método `getRepositorio`