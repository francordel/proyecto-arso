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





