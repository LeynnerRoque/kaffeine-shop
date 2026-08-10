# Estágio 1: Build direto na raiz
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia os arquivos da raiz
COPY pom.xml .
COPY src ./src

# Compila o projeto
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /deployments

# Copia o JAR gerado na pasta target para app.jar de forma exata
COPY --from=build /app/target/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]