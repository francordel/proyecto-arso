# Imagen base
FROM maven:3.8.5-jdk-11 AS builder

# Copiar el pom.xml y descargar las dependencias
COPY ./pom.xml .
RUN mvn -e -B dependency:resolve

# Copiar el código fuente y compilar el proyecto
COPY ./src ./src
RUN mvn package

# Crear la imagen final
FROM openjdk:11-jre-slim

# Copiar el archivo jar de la pasarela a la imagen
COPY --from=builder /target/pasarela-*.jar /pasarela.jar

# Exponer el puerto que utiliza la pasarela
EXPOSE 8090

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/pasarela.jar"]
