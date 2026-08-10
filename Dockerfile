# Estágio 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /project

COPY . /project
RUN mvn clean package -DskipTests

# Estágio 2: Runtime (Usa o .jar tradicional gerado na pasta target)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /deployments

# Copia o jar gerado (pega qualquer .jar que não seja original-*.jar)
COPY --from=build /project/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]