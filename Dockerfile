# Debe construirse desde la carpeta que contiene a los dos proyectos
# Por ejemplo: docker build -t restaurantes-rest:v2 -f restaurantes-rest/Dockerfile .
# Usamos la estrategia division de preocupaciones creando dos imagenes una que compila el codigo con maven y otra que lo ejecuta con tomcat
# La imagen de maven es mas pesada pero se puede reutilizar para compilar otros proyectos
# se basa en la imagen oficial de maven y se copian los archivos de codigo fuente y pom.xml para compilarlos
FROM maven:3.8.5-jdk-11 AS builder

WORKDIR /app-base/
COPY restaurantes/pom.xml .
RUN mvn -e -B dependency:resolve
COPY restaurantes/src ./src
RUN mvn install

WORKDIR /app/
COPY restaurantes-rest/pom.xml .
RUN mvn -e -B dependency:resolve
COPY restaurantes-rest/src ./src
RUN mvn package

# La imagen de tomcat se basa en la imagen oficial de tomcat y se copia el war generado por maven en la carpeta webapps
FROM tomcat:9.0.58-jdk11
WORKDIR /usr/local/tomcat/webapps/
# El war se renombra a ROOT.war para que sea el war por defecto
COPY --from=builder /app/target/restaurantes-rest.war ROOT.war
EXPOSE 8080
# Se ejecuta el script "catalina.sh" para iniciar el servidor Tomcat.
CMD ["catalina.sh", "run"]
