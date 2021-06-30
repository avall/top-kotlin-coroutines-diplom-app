FROM openjdk:13
COPY target/*.jar diplom-app.jar
ENTRYPOINT java -jar diplom-app.jar
EXPOSE 8080