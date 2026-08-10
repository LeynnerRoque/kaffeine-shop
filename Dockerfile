# Estágio 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY . .

# Comando de segurança: Procura onde o pom.xml foi parar e copia para a raiz do build se estiver numa subpasta
RUN find . -name "pom.xml" -exec dirname {} \;

# Roda o build utilizando o diretório onde o pom.xml realmente está
RUN find . -name "pom.xml" | head -n 1 | xargs -I {} dirname {} | xargs -I {} sh -c 'cd {} && mvn clean package -DskipTests'

# Estágio 2: Runtime (Quarkus)
FROM eclipse-temurin:21-jre-alpine
ENV LANGUAGE='en_US:en'
WORKDIR /deployments

# Copia os artefatos gerados (procurando dinamicamente pelo target do quarkus)
COPY --from=build /build/**/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /build/**/target/quarkus-app/*.jar /deployments/
COPY --from=build /build/**/target/quarkus-app/app/ /deployments/app/
COPY --from=build /build/**/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185

ENTRYPOINT ["java", "-Dquarkus.http.host=0.0.0.0", "-Djava.util.logging.manager=org.jboss.logmanager.LogManager", "-jar", "/deployments/quarkus-run.jar"]