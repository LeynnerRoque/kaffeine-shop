# Estágio 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copia todo o contexto enviado pelo Render
COPY . .

# Lista os arquivos para depuração no log (vai te mostrar exatamente onde o pom.xml caiu)
RUN ls -la

# Executa o build
RUN mvn clean package -DskipTests

# Estágio 2: Runtime (Quarkus)
FROM eclipse-temurin:21-jre-alpine
ENV LANGUAGE='en_US:en'
WORKDIR /deployments

COPY --from=build /build/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /build/target/quarkus-app/*.jar /deployments/
COPY --from=build /build/target/quarkus-app/app/ /deployments/app/
COPY --from=build /build/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185

ENTRYPOINT ["java", "-Dquarkus.http.host=0.0.0.0", "-Djava.util.logging.manager=org.jboss.logmanager.LogManager", "-jar", "/deployments/quarkus-run.jar"]