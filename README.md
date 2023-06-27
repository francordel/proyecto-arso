# Back-End + Lógica del proyecto Gestion de Restaurantes
## Comandos para despliegue con docker compose
*Que no se te olvide poner variable de entorno en Opinion-rest/Models/OpinionesRepositorio.cs*
- docker build -t pasarela:2.0 -f Dockerfile.pasarela .
- docker build -t opiniones-rest:2.0 ./opiniones-rest/
- docker build -t restaurantes:2.0 -f Dockerfile.restaurantes .
- docker compose up -d 

Terminamos con:
- docker compose down 
## Comandos para despliegue en kubernetes
- cd k8s/ 
- kubectl apply -f . 
- kubectl get all 

Terminamos con:
- kubectl delete -f .

## Pasos para correr en local
  ### ECLIPSE
  - Cambiar en restaurantes ServicioOpiniones.java en el constructor la linea comentada `.baseUrl("http://localhost:5047/api/")` y comentar la descomentada
  - Click derecho sobre pasarela y run as... maven install y despues lo mismo con usuarios-pasarela, restaurantes y restaurantes-rest.
  - Click derecho sobre restaurantes y run as... maven build. En goals poner jetty:run
  - Click derecho sobre pasarela y run as... Java Application y selecciona PasarelaZuulApplication
  - En la url `http://localhost:8080/index.html#/` puedes realizar peticiones
    
  ### Visual Studio Code
  - Abre visual studio code y accede a Opinion-rest/Models/OpinionesRepositorio.cs cambia las variables de entorno por la comentada:
      -  `//var client = new MongoClient("mongodb://localhost:27017");`
  -  Comenta la linea: `var client = new MongoClient(Environment.GetEnvironmentVariable("URI_MONGODB"));`
  -  ejecuta en la terminal de visual studio code `cd .\opiniones-rest\` y `dotnet run`
  -  En la url `http://localhost:5047/swagger/index.html` puedes realizar peticiones
    
## Nota GraphQL
El graphql funcional es opiniones-graphql. Para utilizarlo necesitas:
  - Abrir el proyecto maven opiniones. Click derecho run as... maven install 
  - Abrir el proyecto opiniones-graphql. Click derecho run as... maven install
  - Click derecho sobre opniones-graphql, run as... Maven Build. En goal poner jetty:run
  - En el navegador acceder a la URL `http://localhost:8082/graphiql.html`

### Pruebas para hacer en GraphQL

- **CreateOpinion**: 
    ```graphql
    mutation CreateOpinion {
      create(nombreRecurso: "@pieroti_ig")
    }
    ```
- **GetOpinion**:  
    ```graphql
    query GetOpinion {
      getOpinion(id: "6499d95a40c88a2be04979a8") {
        nombreRecurso
      }
    }
    ```
- **AddValoracion**:  
    ```graphql
    mutation AddValoracion
    {
        addValoracion(id:"6499d95a40c88a2be04979a8",
                correoElectronico : "prueba@prueba.com",
                calificacion: 5,
                comentario : "Prueba"
        )
    }
    ```
- **RemoveOpinion**:  
  ```graphql
  mutation RemoveOpinion
  {
      removeOpinion(id:"649b263c72649c6a7d96c197")
  }
  ```



## Nota Añadir usuarios en el proyecto 
En usuarios-pasarela en la carpeta usuarios.repositorio modificar la clase RepositorioUsuariosMemoria.java y añadir ahí el usuario con el correo y rol que queramos

## Nota acoplar el proyecto como back de un front
En el proyecto pasarela en pasarela.zuul.seguridad cambiar la clase SecuritySuccessHandler.java comentando lña Opcion 1 y descomentando la Opcion2 permitiendo que te redirija a donde quieras.
